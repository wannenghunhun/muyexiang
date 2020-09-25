package com.framwork.common.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.framwork.common.R;
import com.framwork.common.utils.DisplayUtil;
import com.framwork.common.utils.ResUtil;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.internal.ArrowDrawable;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * @author liyong
 * @date 2018/11/26 16:28
 * @description 下拉刷新头布局
 */
public class DefaultRefreshFooter extends RelativeLayout implements RefreshFooter {
    private ImageView mArrowView;       //下拉箭头
    private TextView mTitleText;        //下拉头部文字
    private LinearLayout mCenterLayout;
    protected RefreshKernel mRefreshKernel;
    protected int mBackgroundColor;
    protected int mPaddingTop = 20;
    protected int mPaddingBottom = 20;
    protected int mFinishDuration = 500;
    protected boolean mNoMoreData = false;

    public DefaultRefreshFooter(Context context) {
        this(context, null);
    }

    public DefaultRefreshFooter(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultRefreshFooter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mArrowView = new ImageView(context);
        mTitleText = new TextView(context);
        mTitleText.setTextColor(ResUtil.getColor(R.color.gray_FF9496A3));

        ArrowDrawable mArrowDrawable = new ArrowDrawable();
        mArrowDrawable.setColor(ResUtil.getColor(R.color.gray_FF9496A3));
        mArrowView.setImageDrawable(mArrowDrawable);

        mCenterLayout = new LinearLayout(context);
        mCenterLayout.setGravity(Gravity.CENTER_VERTICAL);
        mCenterLayout.setOrientation(LinearLayout.HORIZONTAL);

        final View thisView = this;
        final ViewGroup thisGroup = this;
        final View arrowView = mArrowView;
        final View titleView = mTitleText;
        final ViewGroup centerLayout = mCenterLayout;

        LayoutParams lpArrow = new LayoutParams(DisplayUtil.dp2px(20), DisplayUtil.dp2px(20));
        centerLayout.addView(arrowView, lpArrow);

        LayoutParams lpHeaderText = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        titleView.setPadding(DisplayUtil.dp2px(8), 0, 0, 0);
        centerLayout.addView(titleView, lpHeaderText);

        LayoutParams lpHeaderLayout = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpHeaderLayout.addRule(CENTER_IN_PARENT);
        thisGroup.addView(centerLayout, lpHeaderLayout);

        if (thisView.getPaddingTop() == 0) {
            if (thisView.getPaddingBottom() == 0) {
                thisView.setPadding(thisView.getPaddingLeft(), mPaddingTop = DisplayUtil.dp2px(20), thisView.getPaddingRight(), mPaddingBottom = DisplayUtil.dp2px(20));
            } else {
                thisView.setPadding(thisView.getPaddingLeft(), mPaddingTop = DisplayUtil.dp2px(20), thisView.getPaddingRight(), mPaddingBottom = thisView.getPaddingBottom());
            }
        } else {
            if (thisView.getPaddingBottom() == 0) {
                thisView.setPadding(thisView.getPaddingLeft(), mPaddingTop = thisView.getPaddingTop(), thisView.getPaddingRight(), mPaddingBottom = DisplayUtil.dp2px(20));
            } else {
                mPaddingTop = thisView.getPaddingTop();
                mPaddingBottom = thisView.getPaddingBottom();
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final View thisView = this;
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.EXACTLY) {
            thisView.setPadding(thisView.getPaddingLeft(), 0, thisView.getPaddingRight(), 0);
        } else {
            thisView.setPadding(thisView.getPaddingLeft(), mPaddingTop, thisView.getPaddingRight(), mPaddingBottom);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {
        if (colors.length > 0) {
            final View thisView = this;
            if (!(thisView.getBackground() instanceof BitmapDrawable)) {
                setPrimaryColor(colors[0]);
            }

            if (colors.length > 1) {
                setAccentColor(colors[1]);
//                } else {
//                    setAccentColor(colors[0] == 0xffffffff ? 0xff666666 : 0xffffffff);
            }
        }
    }

    public void setPrimaryColor(@ColorInt int primaryColor) {
        mBackgroundColor = primaryColor;
        if (mRefreshKernel != null) {
            mRefreshKernel.requestDrawBackgroundFor(this, mBackgroundColor);
//            if (this instanceof RefreshHeader) {
//                mRefreshKernel.requestDrawBackgroundForHeader(mPrimaryColor);
//            } else if (this instanceof RefreshFooter) {
//                mRefreshKernel.requestDrawBackgroundForFooter(mPrimaryColor);
//            }
        }
    }

    public void setAccentColor(@ColorInt int accentColor) {
        mTitleText.setTextColor(accentColor);
        if (mArrowView != null) {
            ((ArrowDrawable) mArrowView.getDrawable()).setColor(accentColor);
        }
    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {
        mRefreshKernel = kernel;
        mRefreshKernel.requestDrawBackgroundFor(this, mBackgroundColor);
    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {
    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        if (!mNoMoreData) {
            mTitleText.setText(success ? "加载完成" : "加载失败");
        }else{
            mTitleText.setText("没有更多数据了");
        }
        return mFinishDuration;//延迟500毫秒之后再弹回
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {
        if (!mNoMoreData) {
            switch (newState) {
                case None:
                    mArrowView.setVisibility(VISIBLE);
                    mTitleText.setVisibility(View.VISIBLE);
                case PullUpToLoad:
                    mTitleText.setText("上拉获取更多");
                    mTitleText.setVisibility(View.VISIBLE);
                    mArrowView.setVisibility(VISIBLE);
                    mArrowView.animate().rotation(180);
                    break;
                case ReleaseToLoad:
                    mTitleText.setText("释放立即加载");
                    mTitleText.setVisibility(View.VISIBLE);
                    mArrowView.setVisibility(VISIBLE);
                    mArrowView.animate().rotation(0);
                    break;
                case Loading:
                case LoadReleased:
                    mTitleText.setText("拼命获取中...");
                    mTitleText.setVisibility(View.VISIBLE);
                    mArrowView.setVisibility(View.GONE);
                    break;
            }
        }
    }

    @Override
    public boolean setNoMoreData(boolean noMoreData) {
        if (mNoMoreData != noMoreData) {
            mNoMoreData = noMoreData;
            final View arrowView = mArrowView;
            if (noMoreData) {
                mTitleText.setText("没有更多数据了");
                arrowView.setVisibility(GONE);
            } else {
                mTitleText.setText("上拉加载更多");
                arrowView.setVisibility(VISIBLE);
            }
        }
        return true;
    }
}
