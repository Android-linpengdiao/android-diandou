package com.baselibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.baselibrary.R;
import com.baselibrary.view.GlideRoundTransform;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;

public class GlideLoader {

    public final static String domain = "http://admin.udiandou.com/";

    public static void LoderImage(Context context, String url, ImageView view) {
        LoderImage(context, url, view, 0);
    }

    public static void LoderImage(Context context, String url, ImageView view, int round) {
        Glide.with(context)
                .load(domain + url)
                .centerCrop()
                .transform(new GlideRoundTransform(context, round))
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    public static void LoderClipImage(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .fitCenter()
                .thumbnail(0.1f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    public static void LoderImageOrGif(Context context, String url, ImageView view) {
        if (url.endsWith("gif")) {
            Glide.with(context)
                    .asGif()
                    .load(url)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(view);
        } else {
            Glide.with(context)
                    .load(url)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(view);
        }
    }

    public static void LoderGalleryImage(Context context, String url, ImageView view) {
        if (!CommonUtil.isBlank(url)) {
            LoderGalleryImage(context, url.startsWith("http") ? url : "file://" + url, view, 0);
        }
    }

    public static void LoderGalleryImage(Context context, String url, ImageView view, int round) {
        Glide.with(context)
                .load(url)
                .fitCenter()
                .thumbnail(0.1f)
                .placeholder(R.drawable.web_ic_picture_default)
                .error(R.drawable.web_ic_picture_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    public static void LoderMedia(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .transform(new GlideRoundTransform(context, 0))
                .placeholder(R.color.media_color)
                .error(R.color.media_color)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    public static void LoderLoadImageType(Context context, String url, ImageView view) {
        Glide.with(context)
                .load("file://" + url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    public static void LoderLoadImage(Context context, String url, ImageView view) {
        Glide.with(context)
                .load("file://" + url)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    public static void LoderDrawable(Context context, int drawable, ImageView view, int round) {
        Glide.with(context)
                .load(drawable)
                .fitCenter()
                .transform(new GlideRoundTransform(context, round))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);
    }

    public static Bitmap load(Context context, String url) {
        try {
            return Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
