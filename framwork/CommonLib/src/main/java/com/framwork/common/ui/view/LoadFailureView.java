package com.framwork.common.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framwork.common.R;
import com.framwork.common.utils.ViewUtil;
import com.framwork.common.widget.excpetion.base.ILoadFailure;

/**
 *
 */
public class LoadFailureView extends LinearLayout implements ILoadFailure {

    Context context;

    private TextView tv_failure_txt;
    private TextView btn_failure_again;

    public LoadFailureView(Context context) {
        this(context, null);
    }

    public LoadFailureView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LoadFailureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }


    private void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        inflate(context, getLayoutId(), this);
        initView();
    }

    protected int getLayoutId() {
        return R.layout.layout_failure;
    }

    protected void initView() {
        tv_failure_txt = ViewUtil.findTVById(this, R.id.tv_failure_txt);
        btn_failure_again = ViewUtil.findTVById(this, R.id.btn_failure_again);
    }


    @Override
    public View getLoadFailureView() {
        return this;
    }


    @Override
    public void showLoadFailure(String tv, String btn) {
        showLoadFailureView();
        tv_failure_txt.setText(tv);
        btn_failure_again.setText(btn);
    }

    @Override
    public void setLoadFailureOnClickListener(OnClickListener onClickListener) {
        btn_failure_again.setOnClickListener(onClickListener);
    }

    @Override
    public void hideLoadFailureView() {
        setVisibility(GONE);
    }

    @Override
    public void showLoadFailureView() {
        setVisibility(VISIBLE);
    }
}
