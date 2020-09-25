package com.framwork.common.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.framwork.common.R;
import com.framwork.common.utils.StringUtil;


/**
 * 操作成功弹窗
 */
public class SuccessDialog extends Dialog {
    
    private Activity context;
    
    private TextView dialog_success_des, dialog_success_confirm;
    private String msg;
    
    
    public SuccessDialog(Activity context, String msg) {
        super(context);
        this.context = context;
        this.msg = msg;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_success);
        dialog_success_des = findViewById(R.id.dialog_success_des);
        dialog_success_confirm = findViewById(R.id.dialog_success_confirm);
        if(StringUtil.isNotEmpty(msg)) {
            dialog_success_des.setText(msg);
        }
        else {
            dialog_success_des.setText("操作成功！");
        }
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        dialog_success_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    
}
