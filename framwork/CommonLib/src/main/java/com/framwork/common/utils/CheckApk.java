package com.framwork.common.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import com.framwork.common.GlobalContext;

public class CheckApk {

    public static boolean checkSign(String singInfoKey) {
        String s = CheckApk.getSign();
        if (StringUtil.isEmpty(s) || !s.equals(singInfoKey)) {
            return false;
        }
        return true;
    }

    public static String getSign() {
        Signature[] signs = getRawSignature();
        if ((signs == null) || (signs.length == 0)) {
            return null;
        } else {
            Signature sign = signs[0];
            String signMd5 = MD5Util.getMD5String(sign.toByteArray());
            return signMd5;
        }
    }

    public static Signature[] getRawSignature() {
        PackageManager pkgMgr = GlobalContext.getContext().getPackageManager();
        String packageName = GlobalContext.getContext().getPackageName();
        if ((packageName == null) || (packageName.length() == 0)) {
            return null;
        }
        PackageInfo info = null;
        try {
            info = pkgMgr.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
        if (info == null) {
            return null;
        }
        return info.signatures;
    }

}
