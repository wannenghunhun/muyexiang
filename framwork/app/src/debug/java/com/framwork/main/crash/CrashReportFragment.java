package com.framwork.main.crash;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.framwork.common.utils.ViewUtil;
import com.framwork.main.R;

import org.acra.ReportField;
import org.acra.data.CrashReportData;

/**
 * 崩溃页面
 */
public class CrashReportFragment extends Fragment {


    private String crashReportData;

    public static final String CRASHREPORTDATA_KEY = "CrashReportData";


    private TextView tv_stack;
    private TextView tv_app_version;
    private TextView tv_os_version;
    private TextView tv_os_model;
    private TextView tv_packageName;


    public static CrashReportFragment newInstance(String crashReportData) {
        Bundle bundle = new Bundle();
        bundle.putString(CRASHREPORTDATA_KEY, crashReportData);
        CrashReportFragment fragment = new CrashReportFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = ViewUtil.inflate(R.layout.fragment_crash_info);
        tv_stack = ViewUtil.findViewById(view, R.id.tv_stack);
        tv_os_version = ViewUtil.findViewById(view, R.id.tv_os_version);
        tv_app_version = ViewUtil.findViewById(view, R.id.tv_app_version);
        tv_os_model = ViewUtil.findViewById(view, R.id.tv_os_model);
        tv_packageName = ViewUtil.findViewById(view, R.id.tv_packageName);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        crashReportData = getArguments().getString(CRASHREPORTDATA_KEY);
        try {
            CrashReportData data = new CrashReportData(crashReportData);
            tv_stack.setText(data.getString(ReportField.STACK_TRACE));
            tv_os_version.setText(data.getString(ReportField.ANDROID_VERSION));
            tv_app_version.setText(data.getString(ReportField.APP_VERSION_NAME));
            tv_os_model.setText(data.getString(ReportField.PHONE_MODEL));
            tv_packageName.setText(data.getString(ReportField.PACKAGE_NAME));
        } catch(Exception e) {

        }

    }
}
