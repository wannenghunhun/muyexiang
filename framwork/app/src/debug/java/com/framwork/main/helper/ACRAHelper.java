package com.framwork.main.helper;

import android.app.Application;

import com.framwork.main.crash.CrashActivity;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.config.CoreConfigurationBuilder;
import org.acra.config.DialogConfigurationBuilder;
import org.acra.data.StringFormat;

/**
 *
 */
public class ACRAHelper {

    public static <T extends Application> void install(T application) {
        CoreConfigurationBuilder builder = new CoreConfigurationBuilder(application);
        builder.setReportFormat(StringFormat.JSON);
        builder.setReportField(ReportField.APP_VERSION_CODE, true)
                .setReportField(ReportField.PHONE_MODEL, true)
                .setReportField(ReportField.APP_VERSION_NAME, true)
                .setReportField(ReportField.ANDROID_VERSION, true)
                .setReportField(ReportField.CUSTOM_DATA, true)
                .setReportField(ReportField.STACK_TRACE, true)
                .setReportField(ReportField.APPLICATION_LOG, true)
                .setReportField(ReportField.PACKAGE_NAME, true);

        builder.getPluginConfigurationBuilder(DialogConfigurationBuilder.class)
                .setReportDialogClass(CrashActivity.class)
                .setEnabled(true);

        builder.setDeleteOldUnsentReportsOnApplicationStart(true);
        builder.setDeleteUnapprovedReportsOnApplicationStart(true);
        //        builder.setLogcatArguments("com.hengyirong:W","com.hengyirong:D","com.hengyirong:E","com.hengyirong:I");// 只打印项目日志
        ACRA.init(application, builder, true);

    }

}
