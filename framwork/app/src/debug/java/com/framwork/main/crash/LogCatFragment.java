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
 *
 */
public class LogCatFragment extends Fragment {

    private String crashReportData;

    public static final String CRASHREPORTDATA_KEY = "CrashReportData";


    private TextView tv_logcat;


    public static LogCatFragment newInstance(String crashReportData) {
        Bundle bundle = new Bundle();
        bundle.putString(CRASHREPORTDATA_KEY, crashReportData);
        LogCatFragment fragment = new LogCatFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = ViewUtil.inflate(R.layout.fragment_crash_logcat);
        tv_logcat = ViewUtil.findViewById(view, R.id.tv_logcat);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        crashReportData = getArguments().getString(CRASHREPORTDATA_KEY);
        try {
            CrashReportData data = new CrashReportData(crashReportData);
            tv_logcat.setText(data.getString(ReportField.LOGCAT));
        } catch(Exception e) {

        }

    }
}
