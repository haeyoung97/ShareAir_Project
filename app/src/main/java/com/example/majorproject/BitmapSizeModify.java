package com.example.majorproject;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class BitmapSizeModify extends Thread {

    private String filepath;
    private Bitmap resizeBitmap;

    public BitmapSizeModify(String filepath, Bitmap resizeBitmap) {
        this.filepath = filepath;
        this.resizeBitmap = resizeBitmap;
    }


    @Override
    public void run() {
        Log.d("bitmapresize : ", filepath);
        //resizeBitmap = decodeSampleBitmapFromResource(filepath, 128, 128);
        resizeBitmap = cropBitmap(filepath, 256, 256);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
    {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if(height > reqHeight || width > reqHeight){
            final int halfHeight = height/2;
            final int halfWidth = width/2;

            while((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth)
            {
                inSampleSize += 2;
            }
        }
        return inSampleSize;
    }
    public static Bitmap cropBitmap(String filepath, int reqWidth, int reqHeight)
    {
        Bitmap original = BitmapFactory.decodeFile(filepath);
        Log.d("BitmapSize : ", Integer.toString(original.getWidth()));
        Log.d("BitmapSize : ", Integer.toString(original.getHeight()));
        int reqSize;
        if(original.getWidth() > original.getHeight()){
            reqSize = original.getHeight()/2;
        }
        else{
            reqSize = original.getWidth()/2;
        }
        Bitmap result = Bitmap.createBitmap(original, original.getWidth() / 4, original.getHeight() / 4, reqSize, reqSize);
        if(result != original)
        {
            original.recycle();
        }
        return result;
    }
    public static Bitmap decodeSampleBitmapFromResource(String filepath, int reqWidth, int reqHeight)
    {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        BitmapFactory.decodeFile(filepath);
        options.inJustDecodeBounds = true;

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        Log.d("inSampleSize : ", Integer.toString(options.inSampleSize));
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filepath, options);
    }
    public Bitmap getResizeBitmap(){
        return resizeBitmap;
    }

}
