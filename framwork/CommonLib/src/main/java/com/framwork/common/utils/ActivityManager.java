package com.framwork.common.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * activity 管理
 *
 * @Description:
 */
public class ActivityManager {
    
    private static Stack<Activity> stack = new Stack<Activity>();// Activity栈
    
    /**
     * 移除所有activity
     *
     * @Description:
     */
    public static void popAll() {
        while(!stack.isEmpty()) {
            pop();
        }
    }
    
    public static int stackSize() {
        return stack.size();
    }
    
    /**
     * 移除位于栈顶的activity
     *
     * @Description:
     */
    public static void pop() {
        Activity activity = stack.pop();
        if(activity != null && !activity.isFinishing()) {
            activity.finish();
        }
    }
    
    /**
     * 移除指定activity
     *
     * @param activity
     * @Description:
     */
    public static void pop(Activity activity) {
        if(!activity.isFinishing()) {
            activity.finish();
        }
        stack.remove(activity);
    }
    
    /**
     * 移除并关闭指定某一类的activity
     *
     * @param cls
     * @Description:
     */
    public static void popClass(Class<? extends Activity> cls) {
        Stack<Activity> newStack = new Stack<Activity>();
        for(Activity a : stack) {
            if(a.getClass().equals(cls)) {
                if(!a.isFinishing()) {
                    a.finish();
                }
            }
            else {
                newStack.push(a);
            }
        }
        stack = newStack;
    }
    
    /**
     * 获取在栈顶的activity
     *
     * @return
     * @Description:
     */
    public static Activity current() {
        if(stack.isEmpty()) {
            return null;
        }
        LogUtil.e("class=" + stack.toString() + "-----" + stack.peek().getLocalClassName());
        return stack.peek();
    }
    
    /**
     * 添加activity到栈中
     *
     * @param activity
     * @Description:
     */
    public static void push(Activity activity) {
        stack.push(activity);
    }
    
    /**
     * 保留某一类的activity，其它的都关闭并移出栈
     *
     * @param cls
     * @Description:
     */
    public static void retain(Class<? extends Activity> cls) {
        Stack<Activity> newStack = new Stack<Activity>();
        for(Activity a : stack) {
            if(a.getClass().equals(cls)) {
                newStack.push(a);
            }
            else {
                a.finish();
            }
        }
        stack = newStack;
    }
}