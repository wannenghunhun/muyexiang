package com.framwork.common.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
    private static AES mHttpAes; // 接口请求加密
    private static AES mWebAes; // H5数据加密
    private IvParameterSpec ivParameterSpec;
    private SecretKeySpec secretKeySpec;
    private Cipher mCipherEncrypt, mCipherDecrypt;

    private AES(String key, String ivStr, String wayStr) {
        // 这里的 key 不可以使用 KeyGenerator、SecureRandom、SecretKey 生成
        byte[] enCodeFormat = key.getBytes();
        byte[] initParam = ivStr.getBytes();
        ivParameterSpec = new IvParameterSpec(initParam);
        // 指定加密的算法、工作模式和填充方式
        secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
        try {
            mCipherEncrypt = Cipher.getInstance(wayStr);
            mCipherDecrypt = Cipher.getInstance(wayStr);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public static AES getHttpAes(String key, String ivStr, String wayStr) {
        if (mHttpAes == null) {
            synchronized (AES.class) {
                if (mHttpAes == null) {
                    mHttpAes = new AES(key, ivStr, wayStr);
                }
            }
        }
        return mHttpAes;
    }

    public static AES getWebAes(String key, String ivStr, String wayStr) {
        if (mWebAes == null) {
            synchronized (AES.class) {
                if (mWebAes == null) {
                    mWebAes = new AES(key, ivStr, wayStr);
                }
            }
        }
        return mWebAes;
    }

    public String encryptAES(String content) {
        byte[] encryptedBytes = new byte[0];
        try {
            byte[] byteContent = content.getBytes("utf-8");
            mCipherEncrypt.init(Cipher.ENCRYPT_MODE, secretKeySpec,
                    ivParameterSpec);
            encryptedBytes = mCipherEncrypt.doFinal(byteContent);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        // 同样对加密后数据进行 base64 编码
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
    }
 
    public String decryptAES(String content) {
        // base64 解码
        byte[] encryptedBytes = Base64.decode(content, Base64.DEFAULT);
        byte[] result = new byte[0];
        try {
            mCipherDecrypt.init(Cipher.DECRYPT_MODE, secretKeySpec,
                    ivParameterSpec);
            result = mCipherDecrypt.doFinal(encryptedBytes);
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        String re = new String(result, Charset.forName("utf-8"));
        return re;
    }


}