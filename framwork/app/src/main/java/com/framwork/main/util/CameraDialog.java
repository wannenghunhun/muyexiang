package com.framwork.main.util;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;

import com.decard.muye.sdk.camera.CameraUtils;
import com.framwork.common.R;


/**
 * 视频认证弹窗
 */
public class CameraDialog extends Dialog {
    
    private Activity context;
    
    private TextureView dialog_camera_textureView;
    
    private ImageView dialog_camera_close;
    
    public CameraDialog(Activity context) {
        super(context);
        this.context = context;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_camera);
        dialog_camera_close = findViewById(R.id.dialog_camera_close);
        dialog_camera_textureView = findViewById(R.id.dialog_camera_textureView);
        
        CameraUtils.getInstance().startCamera(dialog_camera_textureView, context);
        
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        
        dialog_camera_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
//        setOnKeyListener(new OnKeyListener() {
        //            @Override
        //            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        //                if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
        //                    dismiss();
        //                }
        //                return false;
        //            }
        //        });
    }
    
}
