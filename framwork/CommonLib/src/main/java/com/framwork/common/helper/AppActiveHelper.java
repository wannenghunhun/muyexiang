package com.framwork.common.helper;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.framwork.common.utils.BroadcastUtil;
import com.framwork.common.utils.SPManager;
import com.framwork.common.widget.DefaultActivityLifecycleCallbacks;

import java.util.Date;


/**
 *
 * app 是否处于活动状态
 */
public class AppActiveHelper {
    private static boolean ismActive = false;
    private static Date time_restart;
    private static Date time_stop;
    private static boolean count_start = false;
    private static int mActivityCount;

    private static final long EXIT_TIME = 60000000 * 1000L;// 毫秒  60S

    public static <T extends Application> void install(T application) {
        registerReceiver(application);
        //前后台监听
        //判断程序是否在前台
        application.registerActivityLifecycleCallbacks(new DefaultActivityLifecycleCallbacks() {
            @Override
            public void onActivityStarted(Activity activity) {
                goGesture(activity);
            }

            @Override
            public void onActivityStopped(Activity activity) {
                onStopChangeState();
            }
        });
    }

    //计算时间差
    public static long timeCount() {
        if (time_restart != null && time_stop != null) {
            return time_restart.getTime() - time_stop.getTime();
        } else {
            return 0;
        }
    }

    public static void clearTime() {
        time_restart = null;
        time_stop = null;
    }

    public static <T extends Application> void registerReceiver(T application) {
        BroadcastUtil.registerGlobalReceiver(application, new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // 锁屏监听
                changeActiveStatus(false);

            }
        }, Intent.ACTION_SCREEN_OFF);
    }

    public static boolean isActive() {
        return ismActive;
    }

    public static void changeActiveStatus(boolean isActive) {
        ismActive = isActive;
    }


    public static void goGesture(Activity activity) {
        if (mActivityCount == 0) {
            if (count_start) {
                count_start = false;
                time_restart = new Date(System.currentTimeMillis());//获取当前时间
                Long c = AppActiveHelper.timeCount();
//                if (LoginUtil.isLogin() && c > EXIT_TIME) {
//                    AppActiveHelper.clearTime();
//                    if (TextUtils.isEmpty(SPManager.getDefaultManager().getString(GlobalTools.SP.GESTURES_PASSWORD))) {
//                        if (activity != null && !(activity.getLocalClassName().contains("GesturesSetActivity"))) {
//                            ARouter.getInstance().build("/app/GesturesSetActivity").navigation();
//                        }
//                    } else {
//                        if(activity != null){
//                            if(activity.getLocalClassName().contains("GesturesVerifyActivity")){
//                                if(((GesturesVerifyActivity)activity).getFrom_type() == GesturesVerifyActivity.MODIFY){//是修改手势密码页
//                                    ARouter.getInstance().build("/app/GesturesVerifyActivity").withInt(GesturesVerifyActivity.FROM_TYPE, GesturesVerifyActivity.BACKGROUND).navigation();
//                                }
//                            }else{
//                                ARouter.getInstance().build("/app/GesturesVerifyActivity").withInt(GesturesVerifyActivity.FROM_TYPE, GesturesVerifyActivity.BACKGROUND).navigation();
//                                EventBus.getDefault().post(BackgroundEvent.EVENT_BACK_TO_FOREGROUND);
//                            }
//                        }
//                    }
//                }
            }
        }
        mActivityCount++;
        changeActiveStatus(true);
    }


    public static void onStopChangeState() {
        mActivityCount--;
        if (mActivityCount == 0) {
            time_stop = new Date(System.currentTimeMillis());//获取当前时间
            count_start = true;
        }

        changeActiveStatus(false);
    }
}
