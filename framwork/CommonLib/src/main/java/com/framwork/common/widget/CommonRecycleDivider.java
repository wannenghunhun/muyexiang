package com.framwork.common.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Px;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.framwork.common.utils.DisplayUtil;

/**
 *
 */
public class CommonRecycleDivider extends RecyclerView.ItemDecoration {


    private Paint mPaint;
    private int mDividerHeight = DisplayUtil.dp2px(1);//分割线高度，默认为1dp

    /**
     * 自定义分割线
     *
     * @param dividerHeight 分割线高度
     * @param dividerColor  分割线颜色
     */
    public CommonRecycleDivider(@Px int dividerHeight, int dividerColor) {
        //this(context, orientation);
        mDividerHeight = dividerHeight;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(dividerColor);
        mPaint.setStyle(Paint.Style.FILL);
    }


    /**
     * 自定义分割线
     *
     * @param dividerColor 分割线颜色
     */
    public CommonRecycleDivider(int dividerColor) {
        this(DisplayUtil.dp2px(1), dividerColor);

    }

    //获取分割线尺寸
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(0, 0, 0, mDividerHeight);
    }

    //绘制分割线
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawHorizontal(c, parent);
    }

    //绘制横向 item 分割线
    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getMeasuredWidth() - parent.getPaddingRight();
        final int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + layoutParams.bottomMargin;
            final int bottom = top + mDividerHeight;
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

}
