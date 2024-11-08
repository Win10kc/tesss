package com.example.flickr1;

import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;

public class DbBitmapUtility {

    // Convert from bitmap to byte array
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
}
