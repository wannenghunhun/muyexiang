package com.framwork.common.widget;

import android.support.annotation.DrawableRes;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.framwork.common.GlobalContext;
import com.framwork.common.R;
import com.framwork.common.utils.ResUtil;
import com.framwork.common.utils.ViewUtil;

/**
 *
 */
public class ErrorView {


    private static Toast noNetWorkToast = null;

    /**
     * 显示无网络提示
     */
    public static void showNetError(String errorMsg) {
        View view = null;
        if (noNetWorkToast == null) {
            noNetWorkToast = new Toast(GlobalContext.getContext());
            noNetWorkToast.setGravity(Gravity.CENTER, 0, 0);
            view = ViewUtil.inflate(R.layout.toast_no_net_work);
            noNetWorkToast.setView(view);
            noNetWorkToast.setDuration(Toast.LENGTH_SHORT);
        } else {
            view = noNetWorkToast.getView();
        }
        setData(R.drawable.ic_gantanhao, errorMsg, view);
        noNetWorkToast.show();
    }

    private static void setData(@DrawableRes int drawableResId, String errorMsg, View view) {
        TextView tv_prompt_msg = ViewUtil.findViewById(view, R.id.tv_prompt_msg);
        ImageView iv_prompt_icon = ViewUtil.findViewById(view, R.id.iv_prompt_icon);
        tv_prompt_msg.setText(errorMsg);
        iv_prompt_icon.setImageResource(drawableResId);
    }

    public static void showNetError() {
        showNetError(ResUtil.getString(R.string.no_net_error));
    }

}
