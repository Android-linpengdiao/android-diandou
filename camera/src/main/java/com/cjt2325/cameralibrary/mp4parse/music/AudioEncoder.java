package com.cjt2325.cameralibrary.mp4parse.music;

import android.media.AudioFormat;
import android.media.MediaCodec;
import android.media.MediaFormat;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class AudioEncoder {
    private MediaCodec mediaCodec;
    private BufferedOutputStream outputStream;
    private String mediaType = "OMX.google.aac.encoder";
    private File f;

    ByteBuffer[] inputBuffers = null;
    ByteBuffer[] outputBuffers = null;


    // "OMX.qcom.audio.decoder.aac";
    // "audio/mp4a-latm";


    public AudioEncoder(String file) throws IOException {
        f = new File(file);
        touch(f);
        try {

            outputStream = new BufferedOutputStream(new FileOutputStream(f, false));

        } catch (Exception e) {
            Log.e("AudioEncoder", "outputStream initialized");
            e.printStackTrace();
        }
        mediaCodec = MediaCodec.createEncoderByType("audio/mp4a-latm");
        final int kSampleRates[] = {8000, 11025, 16000, 44100, 48000};
        final int kBitRates[] = {32000, 64000, 96000, 128000};
        MediaFormat mediaFormat = new MediaFormat();
        mediaFormat.setString(MediaFormat.KEY_MIME, "audio/mp4a-latm");
        mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, 32000);
        mediaFormat.setInteger(MediaFormat.KEY_CHANNEL_COUNT, 1);
        mediaFormat.setInteger(MediaFormat.KEY_CHANNEL_MASK, AudioFormat.ENCODING_PCM_16BIT);
        mediaFormat.setInteger(MediaFormat.KEY_SAMPLE_RATE, 16000);
        mediaFormat.setInteger(MediaFormat.KEY_AAC_PROFILE, 2);
        mediaFormat.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, 8192 * 2);
                /*MediaFormat mediaFormat = MediaFormat.createAudioFormat(
                "audio/mp4a-latm", 16000, AudioFormat.CHANNEL_CONFIGURATION_MONO);
                mediaFormat.setInteger(MediaFormat.KEY_AAC_PROFILE,
                MediaCodecInfo.CodecProfileLevel.AACObjectLC);
                mediaFormat.setInteger(MediaFormat.KEY_CHANNEL_COUNT, 1);
                mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, kBitRates[0]);
                mediaFormat.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, 8192*2);// It will

*/                // increase
        // capacity
        // of
        // inputBuffers
        mediaCodec.configure(mediaFormat, null, null,
                MediaCodec.CONFIGURE_FLAG_ENCODE);
        mediaCodec.start();
        inputBuffers = mediaCodec.getInputBuffers();
        outputBuffers = mediaCodec.getOutputBuffers();
    }


    public void close() {
        try {
            mediaCodec.stop();
            mediaCodec.release();
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // called AudioRecord's read
    public synchronized void offerEncoder(byte[] input) {
        Log.e("AudioEncoder", input.length + " is coming--------------------------");
        int inputBufferIndex = mediaCodec.dequeueInputBuffer(-1);
        Log.e("AudioEncoder", inputBufferIndex + " is inputBufferIndex------------------------");
        if (inputBufferIndex >= 0) {
            ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];
            inputBuffer.clear();
            inputBuffer.put(input);
            mediaCodec
                    .queueInputBuffer(inputBufferIndex, 0, input.length, 0, 0);
        }


        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        int outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, 0);
        Log.e("AudioEncoder", outputBufferIndex + " is outputBufferIndex====================");
        // //trying to add a ADTS
        while (outputBufferIndex >= 0) {
            int outBitsSize = bufferInfo.size;
            int outPacketSize = outBitsSize + 7; // 7 is ADTS size
            ByteBuffer outputBuffer = outputBuffers[outputBufferIndex];

            outputBuffer.position(bufferInfo.offset);
            outputBuffer.limit(bufferInfo.offset + outBitsSize);


            byte[] outData = new byte[outPacketSize];
            addADTStoPacket(outData, outPacketSize);


            outputBuffer.get(outData, 7, outBitsSize);
            outputBuffer.position(bufferInfo.offset);

            // byte[] outData = new byte[bufferInfo.size];
            try {
                outputStream.write(outData, 0, outData.length);
                outputStream.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            outputBuffer.clear();
            Log.e("AudioEncoder", outData.length + " bytes written===========================================================");
            mediaCodec.releaseOutputBuffer(outputBufferIndex, false);
            outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, 0);
            Log.e("AudioEncoder", outputBufferIndex + " bytes written=====================outputBufferIndex======================================");

        }
    }

    public synchronized void offerEncoder1(byte[] input) {
        Log.e("AudioEncoder", input.length + " is coming--------------------------");
        int inputBufferIndex = mediaCodec.dequeueInputBuffer(-1);
        Log.e("AudioEncoder", inputBufferIndex + " is inputBufferIndex------------------------");
        if (inputBufferIndex >= 0) {
            ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];
            inputBuffer.clear();
            inputBuffer.put(input);
            mediaCodec
                    .queueInputBuffer(inputBufferIndex, 0, input.length, 0, 0);
        }


        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        int outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, 0);
        Log.e("AudioEncoder", outputBufferIndex + " is outputBufferIndex====================");
        // //trying to add a ADTS
        while (outputBufferIndex >= 0) {
            int outBitsSize = bufferInfo.size;
            int outPacketSize = outBitsSize + 7; // 7 is ADTS size
            ByteBuffer outputBuffer = outputBuffers[outputBufferIndex];

            outputBuffer.position(bufferInfo.offset);
            outputBuffer.limit(bufferInfo.offset + outBitsSize);


            byte[] outData = new byte[outPacketSize];
            addADTStoPacket(outData, outPacketSize);


            outputBuffer.get(outData, 7, outBitsSize);
            outputBuffer.position(bufferInfo.offset);
            try {
                outputStream.write(outData, 0, outData.length);
                outputStream.flush();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            outputBuffer.clear();
            Log.e("AudioEncoder", outData.length + " bytes written===========================================================");
            mediaCodec.releaseOutputBuffer(outputBufferIndex, false);
            outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, 0);
            Log.e("AudioEncoder", outputBufferIndex + " bytes written=====================outputBufferIndex======================================");
            Log.e("AudioEncoder", f.length() + " ===========================================================f.length()");
        }
        Log.e("AudioEncoder", " =====================ok======================================");


    }

    // Without ADTS header
    /*
     * while (outputBufferIndex >= 0) { ByteBuffer outputBuffer =
     * outputBuffers[outputBufferIndex]; byte[] outData = new
     * byte[bufferInfo.size];
     *
     * outputBuffer.get(outData); try { outputStream.write(outData, 0,
     * outData.length); } catch (IOException e) { // TODO Auto-generated
     * catch block e.printStackTrace(); } Log.e("AudioEncoder",
     * outData.length + " bytes written");
     *
     * mediaCodec.releaseOutputBuffer(outputBufferIndex, false);
     * outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, 0);
     *
     * }
     */
    //}


    /**
     * Add ADTS header at the beginning of each and every AAC packet. This is
     * needed as MediaCodec encoder generates a packet of raw AAC data.
     * <p>
     * Note the packetLen must count in the ADTS header itself.
     **/
    public void addADTStoPacket(byte[] packet, int packetLen) {
        int profile = 2; // AAC LC
        // 39=MediaCodecInfo.CodecProfileLevel.AACObjectELD;
        int freqIdx = 8; // 16000 采样率
        int chanCfg = 1; // 1 单声道
        // fill in ADTS data
        packet[0] = (byte) 0xFF;
        packet[1] = (byte) 0xF9;
        packet[2] = (byte) (((profile - 1) << 6) + (freqIdx << 2) + (chanCfg >> 2));
        packet[3] = (byte) (((chanCfg & 3) << 6) + (packetLen >> 11));
        packet[4] = (byte) ((packetLen & 0x7FF) >> 3);
        packet[5] = (byte) (((packetLen & 7) << 5) + 0x1F);
        packet[6] = (byte) 0xFC;
        Log.e("AudioEncoder", packetLen + "packetlen");
    }

    public void touch(File f) {
        try {
            if (!f.exists()) {
                f.createNewFile();
            } else {
                f.delete();
                f.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
