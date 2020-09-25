package com.framwork.main.http.interceptor;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.RealResponseBody;
import okio.GzipSource;
import okio.Okio;

/**
 *
 * 解压，fix 拦截器解密时乱码
 * 解压数据，并改变 Gzip header
 */
public class ResponseGzipInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        Response response = chain.proceed(request);
        Response.Builder responseBuilder = response.newBuilder().request(request);
        if ("gzip".equalsIgnoreCase(response.header("Content-Encoding"))
                && HttpHeaders.hasBody(response)) {

            GzipSource responseBody = new GzipSource(response.body().source());
            Headers strippedHeaders = response.headers().newBuilder()
                    .removeAll("Content-Encoding")
                    .removeAll("Content-Length")
                    .build();
            responseBuilder.headers(strippedHeaders);
            String contentType = response.header("Content-Type");
            responseBuilder.body(new RealResponseBody(contentType, -1L, Okio.buffer(responseBody)));

        }

        return responseBuilder.build();
    }
}
