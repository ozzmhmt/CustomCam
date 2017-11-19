package com.ozzmhmt.customcamera.Manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * Created by mehmet on 19.11.2017.
 */

public class BitmapManager {
    private HashMap<String, SoftReference<Bitmap>> bitmapBuffer;
    private int width;
    private int height;

    public BitmapManager() {
        bitmapBuffer = new HashMap<String, SoftReference<Bitmap>>();
        width = -1;
        height = -1;
    }
    public BitmapManager(int width, int height) {
        bitmapBuffer = new HashMap<String, SoftReference<Bitmap>>();
        this.width = width;
        this.height = height;
    }

    public Bitmap getBitmap(String path) {
        if(path == null)
            return null;

        SoftReference<Bitmap> reference = bitmapBuffer.get(path);
        Bitmap bitmap = null;
        if(reference != null)
            bitmap = reference.get();

        if(bitmap == null || bitmap.isRecycled()) {
            if(width < 0 && height < 0) {
                bitmap = BitmapFactory.decodeFile(path);
                if(bitmap == null)
                    return null;
            }
            else {
                Bitmap bitmap0 = BitmapFactory.decodeFile(path);
                if(bitmap0 == null)
                    return null;

                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

                float scale, transX, transY;
                if(width < 0) {
                    scale = (float)height / bitmap0.getHeight();
                    transX = 0;
                    transY = 0;
                }
                else if(height < 0) {
                    scale = (float)width / bitmap0.getWidth();
                    transX = 0;
                    transY = 0;
                }
                else {
                    scale = Math.min((float)width / bitmap0.getWidth(),
                            (float)height / bitmap0.getHeight());
                    transX = (width - bitmap0.getWidth() * scale) / 2;
                    transY = (height - bitmap0.getHeight() * scale) / 2;
                }

                Matrix matrix = new Matrix();
                matrix.postScale(scale, scale);
                matrix.postTranslate(transX, transY);

                Canvas canvas = new Canvas(bitmap);
                canvas.drawBitmap(bitmap0, matrix, null);

                if(bitmap0 != null && !bitmap0.isRecycled()) {
                    bitmap0.recycle();
                    bitmap0 = null;
                }
            }
            bitmapBuffer.put(path, new SoftReference<Bitmap>(bitmap));
            Log.i("BitmapBuffer", path + " is loaded!");
        }

        return bitmap;
    }

    public Bitmap getBitmapFromAssets(Context context, String path){
        if(path == null)
            return null;

        String newpath = "assets/" + path;
        SoftReference<Bitmap> reference = bitmapBuffer.get(newpath);
        Bitmap bitmap = null;
        if(reference != null)
            bitmap = reference.get();

        if(bitmap == null || bitmap.isRecycled()) {
            if(width < 0 && height < 0) {
                try {
                    InputStream is = context.getAssets().open(path);
                    byte[] buffer = new byte[is.available()];
                    while (is.read(buffer) != -1);
                    bitmap = (Bitmap)BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if(bitmap == null)
                    return null;
            }
            else {
                Bitmap bitmap0 = null;
                InputStream is;
                try {
                    is = context.getAssets().open(path);
                    byte[] buffer = new byte[is.available()];
                    while (is.read(buffer) != -1);
                    bitmap0 = (Bitmap)BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if(bitmap0 == null)
                    return null;

                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

                float scale, transX, transY;
                if(width < 0) {
                    scale = (float)height / bitmap0.getHeight();
                    transX = 0;
                    transY = 0;
                }
                else if(height < 0) {
                    scale = (float)width / bitmap0.getWidth();
                    transX = 0;
                    transY = 0;
                }
                else {
                    scale = Math.min((float)width / bitmap0.getWidth(),
                            (float)height / bitmap0.getHeight());
                    transX = (width - bitmap0.getWidth() * scale) / 2;
                    transY = (height - bitmap0.getHeight() * scale) / 2;
                }

                Matrix matrix = new Matrix();
                matrix.postScale(scale, scale);
                matrix.postTranslate(transX, transY);

                Canvas canvas = new Canvas(bitmap);
                canvas.drawBitmap(bitmap0, matrix, null);

                if(bitmap0 != null && !bitmap0.isRecycled()) {
                    bitmap0.recycle();
                    bitmap0 = null;
                }
            }
            bitmapBuffer.put(newpath, new SoftReference<Bitmap>(bitmap));
            Log.i("BitmapBuffer", newpath + " is loaded!");
        }

        return bitmap;
    }

    public void recycleAll() {
        for(SoftReference<Bitmap> reference : bitmapBuffer.values()) {
            if(reference != null) {
                Bitmap bitmap = reference.get();
                if(bitmap != null && !bitmap.isRecycled())
                    bitmap.recycle();
            }
        }
    }
}
