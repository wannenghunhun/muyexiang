package com.framwork.main.util;

import com.bumptech.glide.request.RequestOptions;
import com.framwork.main.R;

public class ImageUtil {
    
    public static RequestOptions getRequestOptions() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.bg_userface);
        requestOptions.error(R.drawable.bg_userface);
        return requestOptions;
    }
}