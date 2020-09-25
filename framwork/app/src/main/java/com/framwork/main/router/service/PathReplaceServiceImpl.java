package com.framwork.main.router.service;

import android.content.Context;
import android.net.Uri;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.PathReplaceService;
import com.framwork.common.BuildConfig;
import com.framwork.main.router.RouterConstants;
import com.framwork.main.router.util.RouterHandler;

/**
 *
 * url 重定向
 */
public class PathReplaceServiceImpl implements PathReplaceService {
    
    @Override
    public String forString(String path) {
        String realPath = RouterConstants.getRealPath(path);
        if(BuildConfig.SHOW_LOG) {
            if(!path.equals(realPath)) {
                RouterHandler.debug("重定向 %s ---> %s", path, realPath);
            }
        }
        return realPath;
    }
    
    @Override
    public Uri forUri(Uri uri) {
        String path = uri.getPath();
        return Uri.parse(forString(path));
    }
    
    @Override
    public void init(Context context) {
        RouterHandler.debug("URL重定向初始化");
    }
}
