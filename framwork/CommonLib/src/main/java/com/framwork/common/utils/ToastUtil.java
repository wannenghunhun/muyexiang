package com.framwork.common.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.framwork.common.GlobalContext;
import com.framwork.common.R;
import com.framwork.common.widget.ErrorView;


public class ToastUtil extends ErrorView {
    
    private static Toast mToast;
    
    public static void showToast(Context mContext, String text, int duration) {
        if (StringUtil.isNull(text))
            return;
        if (mToast != null) {
            ((TextView) mToast.getView().findViewById(R.id.tv_toast_content)).setText(text);
        } else {
            if (mContext != null) {
                mToast = new Toast(mContext.getApplicationContext());
                View view = View.inflate(mContext.getApplicationContext(), R.layout.layout_toast, null);
                TextView tv = (TextView) view.findViewById(R.id.tv_toast_content);
                tv.setText(text);
                mToast.setGravity(Gravity.CENTER, 0, 0);
                mToast.setDuration(duration);
                mToast.setView(view);
            }
        }
        if (mToast != null) {
            mToast.show();
        }
        
    }
    
    
    public static void showToast(String text) {
        showToast(text, Toast.LENGTH_SHORT);
    }
    
    public static void showToast(String text, int duration) {
        showToast(GlobalContext.getContext(), text, duration);
    }
    
    public static void showToast(int resId, int duration) {
        showToast(GlobalContext.getContext(), ResUtil.getString(resId), duration);
    }
    
    public static void showToast(int resId) {
        showToast(resId, Toast.LENGTH_SHORT);
    }
    
    
}
