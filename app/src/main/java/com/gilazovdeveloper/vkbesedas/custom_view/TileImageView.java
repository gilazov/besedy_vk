package com.gilazovdeveloper.vkbesedas.custom_view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.LinkedList;
import java.util.List;


public class TileImageView extends ImageView
{
    List<String> images;
    List<Bitmap> bitmaps = new LinkedList<>();
    int picturesCount;//количество картинок
    volatile int leftPicturesCount; // количество оставшихся для загрузки картинок
    Context ctx;

    public TileImageView(Context context) {
        super(context);
       ctx = context;
    }

    public TileImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ctx = context;
    }

    public TileImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ctx = context;
   }

    public void setImage(List<String> images, final int picturesSize){
        this.images = images;

        picturesCount = images.size();
        leftPicturesCount = picturesCount;

        for (int i = 0; i < picturesCount; i++) {

            Glide
                    .with((ctx instanceof Activity) ? ctx : ctx.getApplicationContext()) // запросы отправляются пока жив context
                    .load(images.get(i))
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>(picturesSize, picturesSize) {
                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                            Log.d("NAT", "onLoadFailed: "+e.toString());
                        }

                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                            Log.d("NAT", "onResourceReady: ");
                            bitmaps.add(bitmap);
                            leftPicturesCount--;
                            if (leftPicturesCount == 0){
                                showBitmap(picturesSize);
                            }
                        }
                    });
        }

    }

    public void showBitmap(int pictureSize){
        setImageBitmap(buildBitmap(bitmaps, pictureSize, false));
    }

    public Bitmap buildBitmap(List<Bitmap> bitmaps, int bitmapSize, boolean alpha) {

        Bitmap finalBitmap = Bitmap.createBitmap( bitmapSize , bitmapSize, (alpha) ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);

        switch (bitmaps.size()) {
            case 1:
                return bitmaps.get(0);
            case 2: {

                Canvas canvas = new Canvas(finalBitmap);
                Bitmap tmpBitmap = bitmaps.get(0);

                //вырезаем половину изоражения в середине изображения
                Bitmap croppedBitmap = Bitmap.createBitmap(tmpBitmap, bitmapSize / 4, 0,
                        bitmapSize * 3 / 4, bitmapSize);

                canvas.drawBitmap(croppedBitmap, 0, 0, new Paint());

                tmpBitmap = bitmaps.get(1);

                //вырезаем половину изоражения в середине изображения
                croppedBitmap = Bitmap.createBitmap(tmpBitmap, bitmapSize / 4, 0,
                        bitmapSize * 3 / 4, bitmapSize);

                canvas.drawBitmap(croppedBitmap, bitmapSize / 2, 0, new Paint());

                return finalBitmap;
            }

            case 3:
            {
                Canvas canvas = new Canvas(finalBitmap);
                Bitmap tmpBitmap = bitmaps.get(0);
                //вырезаем половину изоражения в середине изображения
                Bitmap croppedBitmap = Bitmap.createBitmap(tmpBitmap, (bitmapSize / 4), 0,
                        (bitmapSize / 2), bitmapSize);

                canvas.drawBitmap(croppedBitmap, 0, 0, new Paint());


                tmpBitmap = bitmaps.get(1);
                croppedBitmap = Bitmap.createScaledBitmap(tmpBitmap, bitmapSize / 2, bitmapSize / 2, true);
                canvas.drawBitmap(croppedBitmap, bitmapSize / 2, 0, new Paint());

                tmpBitmap = bitmaps.get(2);
                croppedBitmap = Bitmap.createScaledBitmap(tmpBitmap, bitmapSize / 2, bitmapSize / 2, true);
                canvas.drawBitmap(croppedBitmap, bitmapSize / 2, bitmapSize / 2, new Paint());

                return finalBitmap;
            }

            case 4:

            {
                Canvas canvas = new Canvas(finalBitmap);

                Bitmap tmpBitmap = bitmaps.get(0);
                Bitmap croppedBitmap = Bitmap.createScaledBitmap(tmpBitmap, bitmapSize / 2, bitmapSize / 2, true);
                canvas.drawBitmap(croppedBitmap, 0, 0, new Paint());

                tmpBitmap = bitmaps.get(1);
                croppedBitmap = Bitmap.createScaledBitmap(tmpBitmap, bitmapSize / 2, bitmapSize / 2, true);
                canvas.drawBitmap(croppedBitmap, bitmapSize / 2, 0, new Paint());

                tmpBitmap = bitmaps.get(2);
                croppedBitmap = Bitmap.createScaledBitmap(tmpBitmap, bitmapSize / 2, bitmapSize / 2, false);
                canvas.drawBitmap(croppedBitmap, bitmapSize / 2, bitmapSize / 2, new Paint());

                tmpBitmap = bitmaps.get(3);
                croppedBitmap = Bitmap.createScaledBitmap(tmpBitmap, bitmapSize / 2, bitmapSize / 2, false);
                canvas.drawBitmap(croppedBitmap, 0, bitmapSize / 2, new Paint());

                return finalBitmap;

            }
        }

        return finalBitmap;
    }

}
