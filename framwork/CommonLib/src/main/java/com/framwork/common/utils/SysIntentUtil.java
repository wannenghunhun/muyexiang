package com.framwork.common.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 系统界面管理
 */
public class SysIntentUtil {

    /**
     * 打开网络设置界面
     */
    public static void openNetSetting(Context context) {
        Intent intent = null;
        if (OSUtils.hasHoneycomb_11()) {
            // 3.0 以上
            intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        } else {
            intent = new Intent();
            ComponentName componentName = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
            intent.setComponent(componentName);
            intent.setAction(Intent.ACTION_VIEW);
        }
        context.startActivity(intent);
    }

    /**
     * 打开默认浏览器
     */
    public static void openDefBrowser(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        context.startActivity(intent);
    }

    /**
     * 打开安卓自带浏览器
     */
    public static void openSysBrowser(Context context, String url) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri content_url = Uri.parse(url);
        intent.setData(content_url);
        intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");

        context.startActivity(intent);
    }

    /**
     * 打开电话界面  但是 不播出
     *
     * @param context
     * @param telNum
     */
    public static void openTel(Context context, String telNum) {
        ActivityUtil.startActivity(context, Intent.ACTION_DIAL, "tel:" + telNum);
    }

    /**
     * 打开电话界面  直接播出了
     *
     * @param context
     * @param telNum
     */
    @SuppressWarnings("MissingPermission")
    public static void callTel(Context context, String telNum) {
        ActivityUtil.startActivity(context, Intent.ACTION_CALL, "tel:" + telNum);
    }

    /**
     * 打开设置界面
     *
     * @param context
     */
    public static void openSetting(Context context) {
        ActivityUtil.startActivity(context, Settings.ACTION_SETTINGS);
    }

    /**
     * 打开相机
     *
     * @param context
     */

    public static void openCamera(Context context) {
        ActivityUtil.startActivity(context, "android.media.action.STILL_IMAGE_CAMERA");
    }

    /**
     * 打开联系人界面
     *
     * @param context
     */
    public static void openContacts(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        context.startActivity(intent);
    }

    /**
     * 打开mp3播放器
     *
     * @param context
     */

    public static void openMP3(Context context) {
        Intent intent = new Intent("android.intent.action.MUSIC_PLAYER");
        context.startActivity(intent);
    }

    public static void playMP3(Context context, String filePath) {
        browserFile(context, filePath, "audio/mp3");
    }


    public static void browserFile(Context context, String filePath, String type) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(filePath), type);
        ActivityUtil.startActivity(context, intent);
    }


    public static void openGPS(Context context) {
        ActivityUtil.startActivity(context, Settings.ACTION_LOCATION_SOURCE_SETTINGS);
    }


    public static void openGPS(Activity activity, int reqCode) {
        ActivityUtil.startActivityForResult(activity, Settings.ACTION_LOCATION_SOURCE_SETTINGS, reqCode);
    }


    /**
     * 选择本地视频
     *
     * @param activity
     * @param requestCode
     */
    public static void selectVideo(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);//视频
        intent.setType("video/*");
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 选择本地图片
     *
     * @param activity
     * @param requestCode
     */
    public static void selectImage(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);//图片
        intent.setType("image/*");
        activity.startActivityForResult(intent, requestCode);
    }


    /**
     * 拍摄视频
     *
     * @param activity
     * @param requestCode
     */
    public static void shotVideo(Activity activity, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);//拍摄视频
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 拍摄照片
     *
     * @param activity
     * @param requestCode
     */
    public static void shotImage(Activity activity, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//拍摄照片
        String cameraPicName = System.currentTimeMillis() + ".jpg";
        File photofile = new File(Environment.getExternalStorageDirectory().getPath() + cameraPicName);
        Uri uri1 = Uri.fromFile(photofile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri1);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * home  键
     *
     * @param context
     */
    public static void goHome(Context context) {
        Intent homeIntent = new Intent();
        homeIntent.setAction(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        context.startActivity(homeIntent);
    }


    /**
     * 打开应用市场的app
     *
     * @param context
     * @param packageName 所要打开的app简介
     * @throws Exception
     */
    public static void gotoMarket(Context context, String packageName) throws Exception {
        String strUrl = "market://details?id=" + packageName;
        Uri uri = Uri.parse(strUrl);
        Intent it = new Intent("android.intent.action.VIEW", uri);
        context.startActivity(it);
    }


    /**
     * 更新  手机相册 显示最新图片
     *
     * @param context context
     * @param file    this file update
     */
    public static void updateGallery(Context context, File file) {
        Intent intent = new Intent();
        Uri uri = Uri.fromFile(file);
        intent.setAction("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    public static void updateGallery(Context context, String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            updateGallery(context, file);
            return;
        }
        LogUtil.w("%s isn't exist", filePath);
    }

    public static void sendSms(Context context, String phone, String content) {
        if (content == null) {
            content = "";
        }
        Uri smsToUri = Uri.parse("smsto:" + phone);
        Intent intent = new Intent(Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", content);
        context.startActivity(intent);
    }

    public static boolean sendEmail(Context context, String email) {
        // 必须明确使用mailto前缀来修饰邮件地址,如果使用
        Uri uri = Uri.parse("mailto:" + email);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra(Intent.EXTRA_SUBJECT, ""); // 主题
        intent.putExtra(Intent.EXTRA_TEXT, ""); // 正文
        if (context != null && intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(Intent.createChooser(intent, "请选择邮件类应用"));
            return true;
        } else {
            if (context != null) {
                return false;
            }
        }
        return true;
    }

    /**
     * 安装apk
     *
     * @param file
     */
    public static void installApk(File file, Context context) {
        if (!file.exists()) {
            return;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //兼容7.0及以上版本，外部应用访问本应用文件的权限问题，判断是否是Android7.0以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri apkURI = FileProvider.getUriForFile(context, AppUtil.getPackageName() + ".provider", file);
            intent.setDataAndType(apkURI, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ActivityManager.popAll();
                System.exit(0);
            }
        }, 500);

    }


    public static void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        context.startActivity(intent);
    }
}
