package com.framwork.common.utils;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.framwork.common.GlobalContext;

import java.lang.reflect.Field;

/**
 *
 */
public final class ViewUtil {
    /**
     * Find view by id v.
     *
     * @param <V> the type parameter
     * @param ac  the ac
     * @param id  the id
     * @return the v
     * 
     */
    @SuppressWarnings("unchecked")
    public static <V extends View> V findViewById(Activity ac, @IdRes int id) {
        return (V) ac.findViewById(id);
    }
    
    /**
     * Find view by id v.
     *
     * @param <V>  the type parameter
     * @param view the view
     * @param id   the id
     * @return the v
     */
    @SuppressWarnings("unchecked")
    public static <V extends View> V findViewById(View view, @IdRes int id) {
        return (V) view.findViewById(id);
    }
    
    /**
     * Find view by id v.
     *
     * @param <V>  the type parameter
     * @param view the view
     * @param id   the id
     * @return the v
     * 
     */
    @SuppressWarnings("unchecked")
    public static <V extends View> V findViewById(Dialog view, @IdRes int id) {
        return (V) view.findViewById(id);
    }
    
    /**
     * Find view by id v.
     *
     * @param <V>    the type parameter
     * @param window the window
     * @param id     the id
     * @return the v
     */
    @SuppressWarnings("unchecked")
    public static <V extends View> V findViewById(Window window, @IdRes int id) {
        return (V) window.findViewById(id);
    }
    
    
    /**
     * Is visible boolean.
     *
     * @param _view the view
     * @return the boolean
     * 
     */
    public static boolean isVisible(View _view) {
        return _view.getVisibility() == View.VISIBLE;
    }
    
    /**
     * Is gone boolean.
     *
     * @param view the view
     * @return the boolean
     */
    public static boolean isGone(View view) {
        return view.getVisibility() == View.GONE;
    }
    
    /**
     * View gone.
     *
     * @param _view the view
     * 
     */
    public static void setGone(View... _view) {
        setVisibility(View.GONE, _view);
    }

    /**
     * View gone.
     *
     * @param _view the view
     */
    public static void setInvisible(View... _view) {
        setVisibility(View.INVISIBLE, _view);
    }
    
    /**
     * View visible.
     *
     * @param _view the view
     * 
     */
    public static void setVisible(View... _view) {
        setVisibility(View.VISIBLE, _view);
    }
    
    
    /**
     * Sets visibility.
     *
     * @param isVisible the is visible　true View.VISIBLE  false View.GONE
     * @param views     the views
     * @author Created by liuwenjie on 2018/09/05 14:40
     */
    public static void setVisibility(boolean isVisible, View... views) {
        if(isVisible) {
            setVisible(views);
        }
        else {
            setGone(views);
        }
    }
    
    
    public static void setVisibility(int visibility, View... _view) {
        for (View view : _view) {
            if (view != null) {
                if (view instanceof ViewStub || view.getVisibility() != visibility) {
                    view.setVisibility(visibility);
                }

            }
        }
    }
    
    /**
     * New inflate layout inflater.
     *
     * @return the layout inflater
     */
    public static LayoutInflater newInflate() {
        return LayoutInflater.from(GlobalContext.getContext());
    }
    
    /**
     * Inflate view.
     *
     * @param layoutId the layout id
     * @return the view
     * 
     */
    public static View inflate(@LayoutRes int layoutId) {
        return inflate(layoutId, null, false);
    }
    
    /**
     * Inflate view.
     *
     * @param resource     the resource
     * @param root         the root
     * @param attachToRoot the attach to root
     * @return the view
     */
    public static View inflate(@LayoutRes int resource, @Nullable ViewGroup root, boolean attachToRoot) {
        return newInflate().inflate(resource, root, attachToRoot);
    }
    
    /**
     * Inflate view.
     *
     * @param resource the resource
     * @param root     the root
     * @return the view
     * 
     */
    public static View inflate(@LayoutRes int resource, @Nullable ViewGroup root) {
        return newInflate().inflate(resource, root, root != null);
    }
    
    /**
     * Find LinearLayout by id linear layout.
     *
     * @param view the view
     * @param id   the id
     * @return the linear layout
     * 
     */
    public static LinearLayout findLLById(View view, @IdRes int id) {
        return findViewById(view, id);
    }
    
    /**
     * Find RelativeLayout by id relative layout.
     *
     * @param view the view
     * @param id   the id
     * @return the relative layout
     * 
     */
    public static RelativeLayout findRLById(View view, @IdRes int id) {
        return findViewById(view, id);
    }
    
    
    /**
     * Find FrameLayout by id frame layout.
     *
     * @param view the view
     * @param id   the id
     * @return the frame layout
     * 
     */
    public static FrameLayout findFLById(View view, @IdRes int id) {
        return findViewById(view, id);
    }
    
    /**
     * Find TextView by id text view.
     *
     * @param view the view
     * @param id   the id
     * @return the text view
     * 
     */
    public static TextView findTVById(View view, @IdRes int id) {
        
        return findViewById(view, id);
    }
    
    /**
     * Find button by id button.
     *
     * @param view the view
     * @param id   the id
     * @return the button
     * 
     */
    public static Button findBtnById(View view, @IdRes int id) {
        
        return findViewById(view, id);
    }
    
    /**
     * Find ImageView by id image view.
     *
     * @param view the view
     * @param id   the id
     * @return the image view
     * 
     */
    public static ImageView findIVById(View view, @IdRes int id) {
        return findViewById(view, id);
    }
    
    /**
     * Find check box by id check box.
     *
     * @param view the view
     * @param id   the id
     * @return the check box
     * 
     */
    public static CheckBox findCheckBoxById(View view, @IdRes int id) {
        return findViewById(view, id);
    }
    
    /**
     * Find linear layout by id linear layout.
     *
     * @param activity the activity
     * @param id       the id
     * @return the linear layout
     * 
     */
    public static LinearLayout findLinearLayoutById(Activity activity, @IdRes int id) {
        return findViewById(activity, id);
    }
    
    /**
     * Find relative layout by id relative layout.
     *
     * @param activity the activity
     * @param id       the id
     * @return the relative layout
     * 
     */
    public static RelativeLayout findRelativeLayoutById(Activity activity, @IdRes int id) {
        return findViewById(activity, id);
    }
    
    
    /**
     * Find frame layout by id frame layout.
     *
     * @param activity the activity
     * @param id       the id
     * @return the frame layout
     * 
     */
    public static FrameLayout findFrameLayoutById(Activity activity, @IdRes int id) {
        return findViewById(activity, id);
    }
    
    /**
     * Find text view by id text view.
     *
     * @param activity the activity
     * @param id       the id
     * @return the text view
     * 
     */
    public static TextView findTextViewById(Activity activity, @IdRes int id) {
        
        return findViewById(activity, id);
    }
    
    /**
     * Sets tv text.
     *
     * @param activity the activity
     * @param id       the id
     * @param text     the text
     * @return the tv text
     * 
     */
    public static TextView setTvText(Activity activity, @IdRes int id, String text) {
        TextView textView = findTextViewById(activity, id);
        textView.setText(text);
        return textView;
    }
    
    
    /**
     * Sets tv text.
     *
     * @param view the view
     * @param id   the id
     * @param text the text
     * @return the tv text
     * 
     */
    public static TextView setTvText(View view, @IdRes int id, String text) {
        TextView textView = findTVById(view, id);
        textView.setText(text);
        return textView;
    }
    
    /**
     * Sets btn text.
     *
     * @param activity the activity
     * @param id       the id
     * @param text     the text
     * @return the btn text
     * 
     */
    public static Button setBtnText(Activity activity, @IdRes int id, String text) {
        Button button = findButtonById(activity, id);
        button.setText(text);
        return button;
    }
    
    /**
     * Find button by id button.
     *
     * @param activity the activity
     * @param id       the id
     * @return the button
     * 
     */
    public static Button findButtonById(Activity activity, @IdRes int id) {
        
        return findViewById(activity, id);
    }
    
    /**
     * Find image view by id image view.
     *
     * @param activity the activity
     * @param id       the id
     * @return the image view
     * 
     */
    public static ImageView findImageViewById(Activity activity, @IdRes int id) {
        return findViewById(activity, id);
    }
    
    /**
     * Find check box by id check box.
     *
     * @param activity the activity
     * @param id       the id
     * @return the check box
     * 
     */
    public static CheckBox findCheckBoxById(Activity activity, @IdRes int id) {
        return findViewById(activity, id);
    }
    
    
    /**
     * Sets compound drawables.
     *
     * @param <T>       the type parameter
     * @param view      the view
     * @param leftRes   the left res
     * @param topRes    the top res
     * @param rightRes  the right res
     * @param bottomRes the bottom res
     * 
     */
    public static <T extends TextView> void setCompoundDrawables(T view, @Nullable Drawable leftRes, @Nullable Drawable topRes, @Nullable Drawable rightRes, @Nullable Drawable bottomRes) {
        DrawableUtil.setCompoundDrawables(view, leftRes, topRes, rightRes, bottomRes);
    }





    
    /**
     * Sets compound drawables.
     *
     * @param <T>       the type parameter
     * @param view      the view
     * @param leftRes   the left res
     * @param topRes    the top res
     * @param rightRes  the right res
     * @param bottomRes the bottom res
     * 
     */
    public static <T extends TextView> void setCompoundDrawables(T view, int leftRes, int topRes, int rightRes, int bottomRes) {
        
        DrawableUtil.setCompoundDrawables(view, leftRes, topRes, rightRes, bottomRes);
        
    }
    
    
    /**
     * Sets left drawable.
     *
     * @param <T>  the type parameter
     * @param view the view
     * @param id   the id
     * 
     */
    public static <T extends TextView> void setLeftDrawable(T view, @DrawableRes int id) {
        DrawableUtil.setLeftDrawable(view, id);
    }
    
    /**
     * Sets top drawable.
     *
     * @param <T>  the type parameter
     * @param view the view
     * @param id   the id
     * 
     */
    public static <T extends TextView> void setTopDrawable(T view, @DrawableRes int id) {
        DrawableUtil.setTopDrawable(view, id);
    }
    
    /**
     * Sets right drawable.
     *
     * @param <T>  the type parameter
     * @param view the view
     * @param id   the id
     * 
     */
    public static <T extends TextView> void setRightDrawable(T view, @DrawableRes int id) {
        DrawableUtil.setRightDrawable(view, id);
    }
    
    /**
     * Sets bottom drawable.
     *
     * @param <T>  the type parameter
     * @param view the view
     * @param id   the id
     * 
     */
    public static <T extends TextView> void setBottomDrawable(T view, @DrawableRes int id) {
        DrawableUtil.setBottomDrawable(view, id);
    }
    
    /**
     * Sets left drawable.
     *
     * @param <T>      the type parameter
     * @param view     the view
     * @param drawable the drawable
     * 
     */
    public static <T extends TextView> void setLeftDrawable(T view, Drawable drawable) {
        DrawableUtil.setLeftDrawable(view, drawable);
    }
    
    /**
     * Sets top drawable.
     *
     * @param <T>      the type parameter
     * @param view     the view
     * @param drawable the drawable
     * 
     */
    public static <T extends TextView> void setTopDrawable(T view, Drawable drawable) {
        DrawableUtil.setTopDrawable(view, drawable);
    }
    
    /**
     * Sets right drawable.
     *
     * @param <T>      the type parameter
     * @param view     the view
     * @param drawable the drawable
     * 
     */
    public static <T extends TextView> void setRightDrawable(T view, Drawable drawable) {
        DrawableUtil.setRightDrawable(view, drawable);
    }
    
    /**
     * Sets bottom drawable.
     *
     * @param <T>      the type parameter
     * @param view     the view
     * @param drawable the drawable
     * 
     */
    public static <T extends TextView> void setBottomDrawable(T view, Drawable drawable) {
        DrawableUtil.setBottomDrawable(view, drawable);
    }
    
    /**
     * 清除图片
     *
     * @param <T>  the type parameter
     * @param view the view
     * 
     */
    public static <T extends TextView> void clearCompoundDrawables(T view) {
        DrawableUtil.clearCompoundDrawables(view);
    }
    
    
    public static void setDivider(LinearLayout layout, Drawable drawable, @LinearLayoutCompat.DividerMode int showDividers) {
        layout.setDividerDrawable(drawable);
        layout.setShowDividers(showDividers);
    }

    public static void setTabWidth(final TabLayout tabLayout, final int padding){
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);



                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距 注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width ;
                        params.leftMargin = padding;
                        params.rightMargin = padding;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }



    public static void setTabLineWidth(final TabLayout tabLayout){
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);



                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 50;
//                        width = mTextView.getWidth();
//                        if (width == 0) {
//                            mTextView.measure(0, 0);
//                            width = mTextView.getMeasuredWidth();
//                        }

                        //设置tab左右间距 注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width ;
//                        params.leftMargin = padding;
//                        params.rightMargin = padding;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    
}
