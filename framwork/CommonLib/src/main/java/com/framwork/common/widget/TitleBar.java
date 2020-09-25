package com.framwork.common.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.Px;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.framwork.common.R;
import com.framwork.common.utils.ResUtil;



/**
 *
 */
public class TitleBar extends ConstraintLayout {
    @ColorInt
    private int titleTextColor;
    private CharSequence titleText;
    @Px
    private int titleTextSize;
    private Drawable titleBackIcon;
    private boolean titleDividerVisible;
    @ColorInt
    private int titleDividerColor;
    @ColorInt
    private int defaultTextColor = Color.WHITE;
    @Px
    private int defaultTitleTextSize;
    @ColorInt
    private int defaultTitleBackground = Color.BLACK;
    private Drawable defaultTitleBackIcon;
    private boolean defaultTitleDividerVisible = false;

    private boolean titleBackIconVisible = true;
    @ColorInt
    private int defaultTitleDividerColor = Color.GRAY;
    @ColorInt
    private int titleBackground;

    private int rightLayout = -1;
    private int leftLayout = -1;
    private int centerLayout = -1;

    private ImageView iv_back;
    private TextView tv_title;
    private LinearLayout ll_left;
    private LinearLayout ll_right;
    private View v_divider;
    private FrameLayout fl_center_layout;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        defaultTitleBackIcon = ContextCompat.getDrawable(context, R.drawable.icon_back);
        defaultTitleTextSize = sp2px(18);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TitleBar, defStyleAttr, 0);
        titleText = a.getString(R.styleable.TitleBar_titleText);
        if (TextUtils.isEmpty(titleText)) {
            titleText = "";
        }
        titleTextColor = a.getColor(R.styleable.TitleBar_titleTextColor, defaultTextColor);
        titleTextSize = (int) a.getDimensionPixelSize(R.styleable.TitleBar_titleTextSize, defaultTitleTextSize);
        titleBackground = a.getColor(R.styleable.TitleBar_titleBackground, defaultTitleBackground);
        titleBackIcon = a.getDrawable(R.styleable.TitleBar_titleBackIcon);
        if (titleBackIcon == null) {
            titleBackIcon = defaultTitleBackIcon;
        }
        titleDividerVisible = a.getBoolean(R.styleable.TitleBar_titleDividerVisible, defaultTitleDividerVisible);
        titleBackIconVisible = a.getBoolean(R.styleable.TitleBar_titleBackIconVisible, true);
        titleDividerColor = a.getColor(R.styleable.TitleBar_titleDividerColor, defaultTitleDividerColor);
        rightLayout = a.getResourceId(R.styleable.TitleBar_rightLayout, -1);
        centerLayout = a.getResourceId(R.styleable.TitleBar_centerLayout, -1);
        leftLayout = a.getResourceId(R.styleable.TitleBar_leftLayout, -1);
        a.recycle();
        View view = inflate(context, R.layout.title_bar, this);
        iv_back = view.findViewById(R.id.iv_back);
        tv_title = view.findViewById(R.id.tv_title);
        ll_left = view.findViewById(R.id.ll_left);
        ll_right = view.findViewById(R.id.ll_right);
        v_divider = view.findViewById(R.id.v_divider);
        fl_center_layout = view.findViewById(R.id.fl_center_layout);

        setTitleTextColor(titleTextColor);
        setTitleText(titleText);
        setTitleTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize);
        setTitleBackIcon(titleBackIcon);
        setTitleDividerColor(titleDividerColor);
        setTitleDividerVisible(titleDividerVisible);
        setTitleBackground(titleBackground);
        setLeftBackVisible(titleBackIconVisible);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) context).finish();
            }
        });


        if (leftLayout > 0) {
            addLeftView(leftLayout);
        }
        if (rightLayout > 0) {
            addRightView(rightLayout);
        }
        if (centerLayout > 0) {
            addCenterView(centerLayout);
        }

    }

    public void setTitleTextColor(@ColorInt int titleTextColor) {
        this.titleTextColor = titleTextColor;
        tv_title.setTextColor(titleTextColor);
    }

    public void setTitleText(CharSequence titleText) {
        this.titleText = titleText;
        tv_title.setText(titleText);
    }

    public void setTitleText(@StringRes int titleText) {
        setTitleText(ResUtil.getString(titleText));
    }

    public void setTitleTextSize(int unit, int titleTextSize) {
        this.titleTextSize = titleTextSize;
        tv_title.setTextSize(unit, titleTextSize);
    }

    public void setTitleTextSize(int titleTextSize) {
        this.titleTextSize = titleTextSize;
        tv_title.setTextSize(titleTextSize);
    }

    public void setTitleBackIcon(Drawable titleBackIcon) {
        this.titleBackIcon = titleBackIcon;
        iv_back.setImageDrawable(titleBackIcon);
    }

    public void setTitleDividerVisible(boolean titleDividerVisible) {
        this.titleDividerVisible = titleDividerVisible;
        if (titleDividerVisible) {
            v_divider.setVisibility(VISIBLE);
        } else {
            v_divider.setVisibility(GONE);
        }
    }

    public void setTitleDividerColor(@ColorInt int titleDividerColor) {
        this.titleDividerColor = titleDividerColor;
        v_divider.setBackgroundColor(titleDividerColor);
    }

    public void setTitleBackground(@ColorInt int titleBackground) {
        this.titleBackground = titleBackground;
        setBackgroundColor(titleBackground);
    }


    public void setLeftBackVisible(boolean leftBackVisible) {
        titleBackIconVisible = leftBackVisible;
        if (titleBackIconVisible) {
            iv_back.setVisibility(VISIBLE);
        } else {
            iv_back.setVisibility(GONE);
        }
    }

    public void setLeftBackClickListener(View.OnClickListener onClickListener) {
        iv_back.setOnClickListener(onClickListener);
    }


    public void addLeftView(View view) {
        ll_left.addView(view);
    }

    public View addLeftView(@LayoutRes int layoutId) {
        View leftView = View.inflate(getContext(), layoutId, null);
        addLeftView(leftView);
        return leftView;
    }

    public void addLeftView(View view, int index) {
        ll_left.addView(view, index);
    }

    public void addRightView(View view) {
        ll_right.addView(view);
    }

    public void addRightView(View view, int index) {
        ll_right.addView(view, index);
    }


    public View addRightView(@LayoutRes int layoutId) {
        View rightView = View.inflate(getContext(), layoutId, null);
        ll_right.addView(rightView);
        return rightView;
    }


    public void addCenterView(View view, int index) {
        fl_center_layout.addView(view, index);
    }


    public View addCenterView(@LayoutRes int layoutId) {
        View rightView = View.inflate(getContext(), layoutId, null);
        fl_center_layout.addView(rightView);
        return rightView;
    }


    public int sp2px(float sp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        return (int) (sp * displayMetrics.scaledDensity + 0.5f);
    }


}
