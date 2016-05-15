package com.gilazovdeveloper.vkbesedas.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by ruslan on 13.03.16.
 */
public class ImageLoader {
   public static void load(Context ctx, String pictureSrc, ImageView view, boolean hideIfEmpty)    {
       if (!pictureSrc.equals("")) {
           Glide
                   .with(ctx)
                   .load(pictureSrc)
                   .skipMemoryCache(true)
                   .into(view);
       }else if (hideIfEmpty){
           view.setVisibility(View.GONE);
       }
   }
}
