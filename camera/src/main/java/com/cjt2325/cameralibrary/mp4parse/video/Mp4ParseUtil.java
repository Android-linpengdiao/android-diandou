package com.cjt2325.cameralibrary.mp4parse.video;

import android.util.Log;

import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.FileDataSourceImpl;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.AACTrackImpl;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;
import com.googlecode.mp4parser.authoring.tracks.CroppedTrack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 视频合成
 * 视频分离出音频
 */

public class Mp4ParseUtil {
    private static final String TAG = "Mp4ParseUtil";

    /**
     * 对Mp4文件集合进行追加合并(按照顺序一个一个拼接起来)
     *
     * @param mp4PathList [输入]Mp4文件路径的集合(支持m4a)(不支持wav)
     * @param outPutPath  [输出]结果文件全部名称包含后缀(比如.mp4)
     * @throws IOException 格式不支持等情况抛出异常
     */
    public static void appendMp4List(List<String> mp4PathList, String outPutPath) throws IOException {
        List<Movie> mp4MovieList = new ArrayList<>();// Movie对象集合[输入]
        for (String mp4Path : mp4PathList) {// 将每个文件路径都构建成一个Movie对象
            mp4MovieList.add(MovieCreator.build(mp4Path));
        }

        List<Track> audioTracks = new LinkedList<>();// 音频通道集合
        List<Track> videoTracks = new LinkedList<>();// 视频通道集合

        for (Movie mp4Movie : mp4MovieList) {// 对Movie对象集合进行循环
            for (Track inMovieTrack : mp4Movie.getTracks()) {
                if ("soun" .equals(inMovieTrack.getHandler())) {// 从Movie对象中取出音频通道
                    audioTracks.add(inMovieTrack);
                }
                if ("vide" .equals(inMovieTrack.getHandler())) {// 从Movie对象中取出视频通道
                    videoTracks.add(inMovieTrack);
                }
            }
        }

        Movie resultMovie = new Movie();// 结果Movie对象[输出]
        if (!audioTracks.isEmpty()) {// 将所有音频通道追加合并
            resultMovie.addTrack(new AppendTrack(audioTracks.toArray(new Track[audioTracks.size()])));
        }
        if (!videoTracks.isEmpty()) {// 将所有视频通道追加合并
            resultMovie.addTrack(new AppendTrack(videoTracks.toArray(new Track[videoTracks.size()])));
        }

        Container outContainer = new DefaultMp4Builder().build(resultMovie);// 将结果Movie对象封装进容器
        FileChannel fileChannel = new RandomAccessFile(String.format(outPutPath), "rw").getChannel();
        outContainer.writeContainer(fileChannel);// 将容器内容写入磁盘
        fileChannel.close();
    }

    /**
     * 分离mp4视频的音频部分，只保留视频部分
     *
     * @param mp4Path .mp4
     * @param outPath .mp4
     */
    public static void splitMp4(String mp4Path, String outPath) {

        try {
            Movie videoMovie = MovieCreator.build(mp4Path);
            Track videoTracks = null;// 获取视频的单纯视频部分
            for (Track videoMovieTrack : videoMovie.getTracks()) {
                if ("vide" .equals(videoMovieTrack.getHandler())) {
                    videoTracks = videoMovieTrack;
                }
            }

            Movie resultMovie = new Movie();
            resultMovie.addTrack(videoTracks);// 视频部分

            Container out = new DefaultMp4Builder().build(resultMovie);
            FileOutputStream fos = new FileOutputStream(new File(outPath));
            out.writeContainer(fos.getChannel());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 将 AAC 和 MP4 进行混合[替换了视频的音轨]
     *
     * @param aacPath .aac
     * @param mp4Path .mp4
     * @param outPath .mp4
     */
    public static boolean muxAacMp4(String aacPath, String mp4Path, String outPath) {
        try {
            Log.d(TAG, "muxAacMp4: aacPath " + aacPath);
            AACTrackImpl aacTrack = new AACTrackImpl(new FileDataSourceImpl(aacPath));
            Movie videoMovie = MovieCreator.build(mp4Path);
            Track videoTracks = null;// 获取视频的单纯视频部分
            for (Track videoMovieTrack : videoMovie.getTracks()) {
                if ("vide" .equals(videoMovieTrack.getHandler())) {
                    videoTracks = videoMovieTrack;
                }
            }

            Movie resultMovie = new Movie();
            resultMovie.addTrack(videoTracks);// 视频部分
            resultMovie.addTrack(aacTrack);// 音频部分

            Container out = new DefaultMp4Builder().build(resultMovie);
            FileOutputStream fos = new FileOutputStream(new File(outPath));
            out.writeContainer(fos.getChannel());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 音视频合成
     *
     * @param audioFile
     * @param videoFile
     * @param outputFile
     * @return
     */
    public static boolean mux(String audioFile, String videoFile, String outputFile) {
        Movie video;
        try {
            video = new MovieCreator().build(videoFile);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        Movie audio;
        try {
            audio = new MovieCreator().build(audioFile);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }

        Track audioTrack = audio.getTracks().get(0);
        video.addTrack(audioTrack);

        Container out = new DefaultMp4Builder().build(video);

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(outputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        try {
            out.writeContainer(fos.getChannel());
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 将 MP4 切割
     *
     * @param mp4Path    .mp4
     * @param fromSample 起始位置
     * @param toSample   结束位置
     * @param outPath    .mp4
     */
    public static void cropMp4(String mp4Path, long fromSample, long toSample, String outPath) throws IOException {
        Movie mp4Movie = MovieCreator.build(mp4Path);
        Track videoTracks = null;// 获取视频的单纯视频部分
        for (Track videoMovieTrack : mp4Movie.getTracks()) {
            if ("vide" .equals(videoMovieTrack.getHandler())) {
                videoTracks = videoMovieTrack;
            }
        }
        Track audioTracks = null;// 获取视频的单纯音频部分
        for (Track audioMovieTrack : mp4Movie.getTracks()) {
            if ("soun" .equals(audioMovieTrack.getHandler())) {
                audioTracks = audioMovieTrack;
            }
        }

        Movie resultMovie = new Movie();
        resultMovie.addTrack(new AppendTrack(new CroppedTrack(videoTracks, fromSample, toSample)));// 视频部分
        resultMovie.addTrack(new AppendTrack(new CroppedTrack(audioTracks, fromSample, toSample)));// 音频部分

        Container out = new DefaultMp4Builder().build(resultMovie);
        FileOutputStream fos = new FileOutputStream(new File(outPath));
        out.writeContainer(fos.getChannel());
        fos.close();
    }

}
