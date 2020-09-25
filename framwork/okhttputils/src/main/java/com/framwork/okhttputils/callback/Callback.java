package com.framwork.okhttputils.callback;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public abstract class Callback<T> {
    /**
     * UI Thread
     *
     * @param request
     */
    public void onBefore(Request request) {
    }

    /**
     * UI Thread
     *
     * @param
     */
    public void onAfter() {
    }

    /**
     * UI Thread
     */
    public void inProgress(long bytesWritten, final long totalSize) {

    }

    /**
     * Thread Pool Thread
     *
     * @param response
     */
    public abstract T parseNetworkResponse(Response response) throws IOException, Exception;

    public abstract void onFailure(@Nullable Request request,@Nullable Response response,@Nullable Exception e);

    public abstract void onResponse(T response);


    public static Callback CALLBACK_DEFAULT = new Callback() {
        @Override
        public Object parseNetworkResponse(Response response) throws IOException, Exception {
            return null;
        }

        @Override
        public void onFailure(Request request, Response response, Exception e) {

        }

        @Override
        public void onResponse(Object response) {

        }
    };

}