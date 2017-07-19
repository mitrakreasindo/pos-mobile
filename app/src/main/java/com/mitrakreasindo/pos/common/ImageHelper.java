package com.mitrakreasindo.pos.common;

import android.graphics.Bitmap;

/**
 * Created by hendric on 2017-07-17.
 */

public class ImageHelper
{
  public static Bitmap getResizedBitmap(Bitmap image, int maxSize)
  {
    int width = image.getWidth();
    int height = image.getHeight();

    float bitmapRatio = (float) width / (float) height;
    if (bitmapRatio > 1)
    {
      width = maxSize;
      height = (int) (width / bitmapRatio);
    }
    else
    {
      height = maxSize;
      width = (int) (height * bitmapRatio);
    }
    return Bitmap.createScaledBitmap(image, width, height, true);
  }
}
