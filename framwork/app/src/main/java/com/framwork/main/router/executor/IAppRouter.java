package com.framwork.main.router.executor;

import android.content.Context;

import java.util.Map;

/**
 *
 */
public interface IAppRouter {


    default void navigation(String url, Map<String, String> params) {
        // Nothing to do
    }

    /**
     * Navigation to the route with path in postcard.
     *
     * @param context Activity and so on.
     */
    default void navigation(Context context, String url, Map<String, String> params) {
        // Nothing to do
    }


}
