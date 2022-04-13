package com.hero.mvcdemo.helper;

import com.orhanobut.logger.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Helper {
    private static final String TAG = Md5Helper.class.getSimpleName();

    /*
        * 使用MD5算法对传入的key进行加密并返回。
        */
    private static MessageDigest mDigest = null;

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 1000; i++) {
            System.out.println("main-----------------------" + StringtoMD5Final("http:\\/\\/test-1253492636.image.myqcloud.com\\/case\\/20170923\\/dnFGASoXVtqIEGnM.png"));
        }
    }

    static {
        try {
            mDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对key进行MD5加密，如果无MD5加密算法，则直接使用key对应的hash值。</br>
     *
     * @param key
     * @return
     */
    public static String StringtoMD5(String key) {
        String cacheKey;
        //获取MD5算法失败时，直接使用key对应的hash值
        if (mDigest == null) {
            return String.valueOf(key.hashCode());
        }
        mDigest.update(key.getBytes());
        cacheKey = bytesToHexString(mDigest.digest());
        return cacheKey;
    }

    public static String StringtoMD5Final(String key) {
        String s = StringtoMD5(key);
        if (s != null) {
            Logger.t(TAG).v("StringtoMD5Final------s-----------------" + s);
            return s;
        } else {
            int i = 0;
            while (i < 4) {
                String value = StringtoMD5(key);
                if (value != null) {
                    Logger.t(TAG).v("StringtoMD5Final------value-----------------" + s);
                    return value;
                } else {
                    Logger.t(TAG).v("StringtoMD5Final------i++-----------------" + i);
                    i++;
                }
            }
            return null;
        }
    }

    /**
     * @param bytes
     * @return
     */
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    //对md5的字符串进行加密
    public static String MD5tobetter(String md5) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }
}