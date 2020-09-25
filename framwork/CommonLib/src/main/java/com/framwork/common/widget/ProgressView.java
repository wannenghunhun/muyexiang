package com.framwork.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.ImageView;

import com.framwork.common.R;


@SuppressLint("AppCompatCustomView")
public class ProgressView extends ImageView {
    private AttributeSet attrs;
    
    public ProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.attrs = attrs;
        setAnimation(attrs);
    }
    
    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.attrs = attrs;
        setAnimation(attrs);
    }
    
    public ProgressView(Context context) {
        super(context);
    }
    
    private void setAnimation(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressView);
        int frameCount = a.getInt(R.styleable.ProgressView_frameCount, 12);
        int duration = a.getInt(R.styleable.ProgressView_duration, 1000);
        a.recycle();
        
        setAnimation(frameCount, duration);
    }
    
    public void setAnimation(final int frameCount, final int duration) {
        Animation a = AnimationUtils.loadAnimation(getContext(), R.anim.anim_loading);
        a.setDuration(duration);
        a.setInterpolator(new Interpolator() {
            
            @Override
            public float getInterpolation(float input) {
                return (float) Math.floor(input * frameCount) / frameCount;
            }
        });
        startAnimation(a);
    }
    
    /**
     * 设置进度动画
     */
    public void setAnimation() {
        setAnimation(attrs);
    }
}
