package com.kangbc.kbcapplication1.binding;

import android.databinding.BindingAdapter;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.kangbc.kbcapplication1.common.util.ImageUtil;

/**
 * Created by TedPark on 2017. 2. 16..
 */

public class BindingAdapters {

    @BindingAdapter({"bind:font"})
    public static void setFont(TextView textView, String fontName) {
        textView.setTypeface(Typeface.createFromAsset(textView.getContext().getAssets(), "fonts/" + fontName));
    }

    @BindingAdapter({"bind:imageUrl", "bind:error"})
    public static void loadImage(ImageView imageView, String url, Drawable errorDrawable) {
        ImageUtil.loadImage(imageView, url, errorDrawable);
    }


}
