package com.framwork.main.crash;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.framwork.common.adapter.pager.SimpleFragmentPageAdapter;
import com.framwork.common.utils.ViewUtil;
import com.framwork.main.R;

import org.acra.config.CoreConfiguration;
import org.acra.dialog.BaseCrashReportDialog;
import org.acra.interaction.DialogInteraction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;




/**
 *
 */
@SuppressWarnings("Registered")
public class CrashActivity extends BaseCrashReportDialog {


    public TabLayout tab_layout;
    public ViewPager viewpager;
    String crashReportData;

    @Override
    protected void init(@Nullable Bundle savedInstanceState) {
        super.init(savedInstanceState);
        setContentView(R.layout.activity_crash);
        tab_layout = ViewUtil.findViewById(this, R.id.tab_layout);
        viewpager = ViewUtil.findViewById(this, R.id.viewpager);
        initData();
    }

    private void initData() {
        final Serializable sConfig = getIntent().getSerializableExtra(DialogInteraction.EXTRA_REPORT_CONFIG);
        final Serializable sReportFile = getIntent().getSerializableExtra(DialogInteraction.EXTRA_REPORT_FILE);

        if((sConfig instanceof CoreConfiguration) && (sReportFile instanceof File)) {
            File reportFile = (File) sReportFile;
            try {
                crashReportData = readFile(reportFile.getAbsolutePath());
                cancelReports();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        SimpleFragmentPageAdapter<Fragment> pageAdapter = new SimpleFragmentPageAdapter<>(getSupportFragmentManager());

        pageAdapter.addItem(CrashReportFragment.newInstance(crashReportData), "崩溃日志");
//        pageAdapter.addItem(new app.crash.SvnInfoFragment(), "提交记录");
        pageAdapter.addItem(LogCatFragment.newInstance(crashReportData), "APP-LOG");
        viewpager.setAdapter(pageAdapter);
        tab_layout.setupWithViewPager(viewpager);
    }

    /**
     * 将文本文件中的内容读入到buffer中
     *
     * @param buffer   buffer
     * @param filePath 文件路径
     * @throws IOException 异常
     * @author cn.outofmemory
     * @date 2013-1-7
     */
    public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while(line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
    }

    /**
     * 读取文本文件内容
     *
     * @param filePath 文件所在路径
     * @return 文本内容
     * @throws IOException 异常
     * @author cn.outofmemory
     * @date 2013-1-7
     */
    public static String readFile(String filePath) throws IOException {
        StringBuffer sb = new StringBuffer();
        readToBuffer(sb, filePath);
        return sb.toString();
    }
}
