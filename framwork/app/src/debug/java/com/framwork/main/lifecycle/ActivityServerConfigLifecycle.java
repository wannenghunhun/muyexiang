package com.framwork.main.lifecycle;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.framwork.common.helper.ServerHelper;
import com.framwork.common.ui.lifecycle.IActivityLifecycle;
import com.framwork.common.utils.ColorUtil;
import com.framwork.common.utils.DisplayUtil;
import com.framwork.common.utils.SPManager;
import com.framwork.main.R;
import com.framwork.main.helper.DebugServerConfig;
import com.framwork.main.view.ServerConfigView;



public class ActivityServerConfigLifecycle implements IActivityLifecycle {


    public static final String LEFT_MARGIN = "LEFT_MARGIN";
    public static final String RIGHT_MARGIN = "RIGHT_MARGIN";
    public static final String TOP_MARGIN = "TOP_MARGIN";
    public static final String BOTTOM_MARGIN = "BOTTOM_MARGIN";


    public ActivityServerConfigLifecycle() {
        init();
    }

    public static void saveMargin(int l, int t, int r, int b) {
        SPManager.getManager(DebugServerConfig.SERVER_TYPE).commitInt(LEFT_MARGIN, l);
        SPManager.getManager(DebugServerConfig.SERVER_TYPE).commitInt(TOP_MARGIN, t);
        SPManager.getManager(DebugServerConfig.SERVER_TYPE).commitInt(RIGHT_MARGIN, r);
        SPManager.getManager(DebugServerConfig.SERVER_TYPE).commitInt(BOTTOM_MARGIN, b);
    }

    public static int leftMargin = -1;
    public static int rightMargin = -1;
    public static int bottomMargin = -1;
    public static int topMargin = -1;


    public static void init() {
        leftMargin = SPManager.getManager(DebugServerConfig.SERVER_TYPE).getInt(LEFT_MARGIN, 0);
        topMargin = SPManager.getManager(DebugServerConfig.SERVER_TYPE).getInt(TOP_MARGIN, 0);
        rightMargin = SPManager.getManager(DebugServerConfig.SERVER_TYPE).getInt(RIGHT_MARGIN, 10);
        bottomMargin = SPManager.getManager(DebugServerConfig.SERVER_TYPE).getInt(BOTTOM_MARGIN, 30);
    }


    @Override
    public void onResume(Activity activity) {
        View view = activity.findViewById(R.id.server_config_view);
        if (view != null) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.leftMargin = leftMargin;
            params.rightMargin = rightMargin;
            params.topMargin = topMargin;
            params.bottomMargin = bottomMargin;
            view.setLayoutParams(params);
            return;
        }
        FrameLayout frameLayout = activity.findViewById(android.R.id.content);
        ServerConfigView configView = new ServerConfigView(activity);
        configView.setTextSize(12);
        configView.setTextColor(Color.WHITE);
        configView.setText(ServerHelper.currentServerType);
        configView.setMaxEms(3);
        configView.setLines(1);
        configView.setGravity(Gravity.CENTER);
        configView.setId(R.id.server_config_view);
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(ColorUtil.argb(150,Color.GRAY));
        drawable.setShape(GradientDrawable.OVAL);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(DisplayUtil.dp2px(40), DisplayUtil.dp2px(40));
        params.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        configView.setBackground(drawable);
        params.leftMargin = leftMargin;
        params.rightMargin = rightMargin;
        params.topMargin = topMargin;
        params.bottomMargin = bottomMargin;
        frameLayout.addView(configView, params);
        configView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerHelper.startConfig(activity);
            }
        });
    }

    @Override
    public void onPause(Activity activity) {
        View view = activity.findViewById(R.id.server_config_view);
        if (view != null) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            leftMargin = params.leftMargin;
            rightMargin = params.rightMargin;
            topMargin = params.topMargin;
            bottomMargin = params.bottomMargin;
            saveMargin(leftMargin, topMargin, rightMargin, bottomMargin);
            return;
        }
    }
}
