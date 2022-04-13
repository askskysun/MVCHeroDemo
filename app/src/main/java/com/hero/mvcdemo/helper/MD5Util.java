package com.hero.mvcdemo.helper;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class MD5Util {

    public static void main(String[] args) throws Exception {
        System.out.println(getDateMd5());
    }

    public static String getDateMd5() throws NoSuchAlgorithmException {
        return getMD5Hex(String.valueOf(new Date().getTime()));
    }

    public static String getMD5Hex(final String inputString) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(inputString.getBytes());

        byte[] digest = md.digest();

        return convertByteToHex(digest);
    }

    private static String convertByteToHex(byte[] byteData) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    /**
     * 32位 小写
     * 参阅 https://blog.csdn.net/mixiu888/article/details/78232458?locationNum=6&fps=1
     *
     * @param sSecret
     * @return
     */
    public static String getMd5Value32snall(String sSecret) {
        if (TextUtils.isEmpty(sSecret)) {
            //默认密码  防止出错
            return "666";
        }

        try {
            MessageDigest bmd5 = MessageDigest.getInstance("MD5");
            bmd5.update(sSecret.getBytes());
            int i;
            StringBuffer buf = new StringBuffer();
            byte[] b = bmd5.digest();
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sSecret;
    }
}