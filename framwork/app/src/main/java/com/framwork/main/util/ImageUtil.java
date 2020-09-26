package com.framwork.main.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.bumptech.glide.request.RequestOptions;
import com.framwork.main.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtil {
    
    public static RequestOptions getRequestOptions() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.bg_userface);
        requestOptions.error(R.drawable.bg_userface);
        return requestOptions;
    }
    
    /**
     * bitmap转为base64
     *
     * @param bitmap
     * @return
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if(bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                
                baos.flush();
                baos.close();
                
                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    
    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}