package com.framwork.okhttputils.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;

import okhttp3.Response;

public abstract class BitmapCallback extends Callback<Bitmap> {
    @Override
    public Bitmap parseNetworkResponse(Response response) throws IOException {
        return BitmapFactory.decodeStream(response.body().byteStream());
    }
    
}
