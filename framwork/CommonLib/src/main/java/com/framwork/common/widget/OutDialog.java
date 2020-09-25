package com.framwork.common.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.framwork.common.R;


/**
 * 操作失败弹窗
 */
public class OutDialog extends Dialog {
    
    private Context context;
    
    private TextView setting_btn_cancel, setting_btn_confirm;
    
    private OnConfirmClickListener onConfirmClickListener;
    
    public OutDialog(Context context, OnConfirmClickListener onConfirmClickListener) {
        super(context);
        this.context = context;
        this.onConfirmClickListener = onConfirmClickListener;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_out);
        setting_btn_cancel = findViewById(R.id.setting_btn_cancel);
        setting_btn_confirm = findViewById(R.id.setting_btn_confirm);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        setting_btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setting_btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onConfirmClickListener.onConfirmClick();
                dismiss();
            }
        });
        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    dismiss();
                }
                return false;
            }
        });
        
    }
    
    public abstract static class OnConfirmClickListener {
        public abstract void onConfirmClick();
    }
    
}
