package com.framwork.okhttputils;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.framwork.okhttputils.builder.GetBuilder;
import com.framwork.okhttputils.builder.PostFileBuilder;
import com.framwork.okhttputils.builder.PostFormBuilder;
import com.framwork.okhttputils.builder.PostStringBuilder;
import com.framwork.okhttputils.callback.Callback;
import com.framwork.okhttputils.https.HttpsUtils;
import com.framwork.okhttputils.request.RequestCall;

import java.io.IOException;
import java.io.InputStream;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtils {
    public static final String TAG = "OkHttpUtils";
    public static final long DEFAULT_MILLISECONDS = 60000;
    private static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;

    private OkHttpUtils() {
        mDelivery = new Handler(Looper.getMainLooper());
    }

    private static void setOkHttpClient(OkHttpClient client) {
        OkHttpUtils okHttpUtils = OkHttpUtils.getInstance();
        okHttpUtils.initOkHttpClient(client);
    }

    private void initOkHttpClient(OkHttpClient client) {
        if (client == null) {
            mOkHttpClient = new OkHttpClient();
            return;
        }
        mOkHttpClient = client;
    }

    public static void init(OkHttpClient.Builder builder, boolean verifier) {
        if (verifier) {
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        }
        OkHttpClient client = builder.build();
        init(client);
    }

    public static void init() {
        OkHttpClient client = new OkHttpClient();
        init(client);
    }


    public static void init(OkHttpClient client) {
        setOkHttpClient(client);
    }


    private boolean debug;
    private String tag;

    public OkHttpUtils debug(String tag) {
        debug = true;
        this.tag = tag;
        return this;
    }


    public static OkHttpUtils getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpUtils();
                }
            }
        }
        return mInstance;
    }

    public Handler getDelivery() {
        return mDelivery;
    }

    public OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
        }
        return mOkHttpClient;
    }


    public static GetBuilder get() {
        return new GetBuilder();
    }

    public static PostStringBuilder postString() {
        return new PostStringBuilder();
    }

    public static PostFileBuilder postFile() {
        return new PostFileBuilder();
    }

    public static PostFormBuilder postForm() {
        return new PostFormBuilder();
    }


    public void execute(final RequestCall requestCall, Callback callback) {
        if (debug) {
            if (TextUtils.isEmpty(tag)) {
                tag = TAG;
            }
            Log.d(tag, "{method:" + requestCall.getRequest() + ", detail:" + requestCall.getOkHttpRequest().toString() + "}");
        }
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;

        requestCall.getCall().enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailResultCallback(call.request(), null, e, finalCallback);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.code() == 200) {
                    try {
                        Object o = finalCallback.parseNetworkResponse(response);
                        sendSuccessResultCallback(o, finalCallback);
                    } catch (Exception e) {
                        sendFailResultCallback(response.request(), response, e, finalCallback);
                    }
                } else {
                    try {
                        sendFailResultCallback(requestCall.getRequest(), response, new RuntimeException(response.body().string()), finalCallback);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }


    public void sendFailResultCallback(final Request request, final Response response, final Exception e, final Callback callback) {
        if (callback == null)
            return;

        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onFailure(request, response, e);
                callback.onAfter();
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback) {
        if (callback == null)
            return;
        mDelivery.post(new Runnable() {
            @Override
            public void run() {
                callback.onResponse(object);
                callback.onAfter();
            }
        });
    }


    public void cancelTag(Object tag) {
        //        mOkHttpClient.cancel(tag);

        if (tag == null) {
            return;
        }

        synchronized (mOkHttpClient.dispatcher().getClass()) {
            for (Call call : mOkHttpClient.dispatcher().queuedCalls()) {
                if (tag.equals(call.request().tag()))
                    call.cancel();
            }

            for (Call call : mOkHttpClient.dispatcher().runningCalls()) {
                if (tag.equals(call.request().tag()))
                    call.cancel();
            }
        }

    }

    public void setCertificates(InputStream... certificates) {
        HttpsUtils.setCertificates(getOkHttpClient(), certificates, null, null);
    }


}

