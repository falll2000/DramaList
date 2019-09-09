package idv.jerry.dramalist.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class GlideUtils {

    public static void loadImages(String url, ImageView imageView){
        GlideApp.with(imageView.getContext())
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(imageView);
    }
}
