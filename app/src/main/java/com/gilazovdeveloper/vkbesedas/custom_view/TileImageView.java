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
import com.gilazovdeveloper.vkbesedas.R;

import java.util.LinkedList;
import java.util.List;

/*
* получаем список картинок,загружаем их в list используя glide
* далее в зависимости от количества картинок склеиваем их и setImage
*
*
* */
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

        if (images.size()==0) {
            setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_enhance_black_24dp));
        }

        for (int i = 0; i < picturesCount; i++) {

            Glide
                    .with((ctx instanceof Activity) ? ctx : ctx.getApplicationContext()) // запросы отправляются пока жив context
                    .load(images.get(i))
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>(picturesSize, picturesSize) {
                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                            Log.d("TAG", "onLoadFailed: "+e.toString());
                            setImageDrawable(getResources().getDrawable(R.drawable.ic_camera_enhance_black_24dp));
                        }

                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                            Log.d("TAG", "onResourceReady: ");
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
        bitmaps.clear();
    }

    public Bitmap buildBitmap(List<Bitmap> bitmaps, int bitmapSize, boolean alpha) {

        Bitmap finalBitmap = Bitmap.createBitmap( bitmapSize , bitmapSize, (alpha) ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Log.d("TAG", "buildBitmap: black bitmap. Bitmap List Size = " +bitmaps.size());
        switch (bitmaps.size()) {
            case 1:
                Log.d("TAG", "buildBitmap:1 ");
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
                Log.d("TAG", "buildBitmap:2");
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
                Log.d("TAG", "buildBitmap:3");
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
                Log.d("TAG", "buildBitmap:4");
                return finalBitmap;

            }
        }

        return finalBitmap;
    }

}
