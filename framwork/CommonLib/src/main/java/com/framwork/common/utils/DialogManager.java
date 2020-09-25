package com.framwork.common.utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DialogManager {
    private List<DialogBean> dialogList = new ArrayList<>();
    private Integer[] priorityArray;
    private static DialogManager mInstance;
    
    // 优先级排序
    public static int UPDATE_DIALOG = 100;//更新弹框
    public static int DOWNLOAD_DIALOG = 99;//下载弹框
    public static int PRIVATE_PROTOCOL_DIALOG = 98;//个人隐私协议弹框
    public static int OPEN_ACCOUNT_DIALOG = 97;//开户绑卡弹框
    
    public DialogManager() {
    }
    
    public static DialogManager getInstance() {
        if(mInstance == null) {
            synchronized(DialogManager.class) {
                if(mInstance == null) {
                    mInstance = new DialogManager();
                }
            }
        }
        return mInstance;
    }
    
    public void clearDialog() {
        if(dialogList != null) {
            dialogList.clear();
        }
        
    }
    
    
    /**
     * 添加dialog，显示dialog交给DialogManage来管理
     *
     * @param priority 优先级
     */
    public void addDialog(Dialog dialog, int priority) {
        DialogBean dialogBean = new DialogBean();
        dialogBean.setDialog(dialog);
        dialogBean.setDialogView(dialog.getWindow().getDecorView());
        dialogBean.setPriority(priority);
        setDialog(dialogBean);
    }
    
    private void setDialog(DialogBean currentDialog) {
        //设置监听
        currentDialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialogList.remove(currentDialog);//关闭后从显示列表上删除
                if(dialogList.size() > 0) {
                    sortDialog();
                }
            }
        });
        addToList(currentDialog);
    }
    
    //添加进入序列
    private void addToList(DialogBean dialogBean) {
        if(dialogList.size() == 0) {
            dialogList.add(dialogBean);
        }
        else {
            boolean isSingle = true;
            for(int i = 0; i < dialogList.size(); i++) {
                if(dialogBean.getPriority() == dialogList.get(i).getPriority()) {
                    isSingle = false;
                }
            }
            if(isSingle) {
                dialogList.add(dialogBean);
            }
        }
        sortDialog();
    }
    
    
    //取出优先级进行排序,显示
    private void sortDialog() {
        //初始化
        priorityArray = new Integer[dialogList.size()];
        //赋值
        for(int i = 0; i < dialogList.size(); i++) {
            priorityArray[i] = dialogList.get(i).getPriority();
        }
        //排序
        int maxElement = priorityArray[0];
        for(int x = 0; x < priorityArray.length; x++) {
            if(priorityArray[x] > maxElement) {
                maxElement = priorityArray[x];
            }
        }
        //显示
        for(int i = 0; i < dialogList.size(); i++) {
            if(dialogList.get(i).getPriority() == maxElement) {
                dialogList.get(i).getDialog().show();
            }
        }
    }
    
    
    public static class DialogBean {
        private Dialog dialog;
        private View dialogView;//dialog的view
        private int priority;//优先级
        
        public Dialog getDialog() {
            return dialog;
        }
        
        public void setDialog(Dialog dialog) {
            this.dialog = dialog;
        }
        
        public View getDialogView() {
            return dialogView;
        }
        
        public void setDialogView(View dialogView) {
            this.dialogView = dialogView;
        }
        
        public int getPriority() {
            return priority;
        }
        
        public void setPriority(int priority) {
            this.priority = priority;
        }
    }
}
