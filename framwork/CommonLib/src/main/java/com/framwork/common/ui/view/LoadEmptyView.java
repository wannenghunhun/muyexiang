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
import com.framwork.common.widget.excpetion.base.ILoadEmpty;

/**
 *
 */
public class LoadEmptyView extends LinearLayout implements ILoadEmpty {


    Context context;
    private TextView btn_empty_again;
    private TextView tv_empty_txt;

    public LoadEmptyView(Context context) {
        this(context, null);
    }

    public LoadEmptyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public LoadEmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    protected void init() {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        inflate(context, getLayoutId(), this);
        initView();
    }

    protected int getLayoutId() {
        return R.layout.layout_empty;
    }

    protected void initView() {
        btn_empty_again = ViewUtil.findTVById(this, R.id.btn_empty_again);
        tv_empty_txt = ViewUtil.findTVById(this, R.id.tv_empty_txt);
    }


    @Override
    public View getEmptyView() {
        return this;
    }

    @Override
    public void showEmpty(String tv) {
        showEmptyView();
        tv_empty_txt.setText(tv);
        btn_empty_again.setVisibility(GONE);
    }

    @Override
    public void showEmpty(String tv, String btn) {
        showEmptyView();
        tv_empty_txt.setText(tv);
        btn_empty_again.setText(btn);
    }

    @Override
    public void setLoadEmptyOnClickListener(OnClickListener onClickListener) {
        btn_empty_again.setOnClickListener(onClickListener);
    }

    @Override
    public void hideEmptyView() {
        setVisibility(GONE);
    }

    @Override
    public void showEmptyView() {
        setVisibility(VISIBLE);
    }
}
