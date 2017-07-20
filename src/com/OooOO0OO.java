package com;

/**
 * Created by qtfreet on 2017/2/24.
 */


public class OooOO0OO {

    /**
     * 将字符串编码成16进制数字,适用于所有字符（包括中文）
     */
    public static byte[] encode(byte[] bytes, String key) {
        //根据默认编码获取字节数组

        int len = bytes.length;
        int keyLen = key.length();
        for (int i = 0; i < len; i++) {
            //对每个字节进行异或
            bytes[i] = (byte) (bytes[i] ^ key.charAt(i % keyLen));
        }

        return bytes;
    }

    /**
     * 将16进制数字解码成字符串,适用于所有字符（包括中文）
     */
    public static String OooOOoo0oo(byte[] bytes, String key) {
        int len = bytes.length;
        int keyLen = key.length();
        for (int i = 0; i < len; i++) {
            //对每个字节进行异或
            bytes[i] = (byte) (bytes[i] ^ key.charAt(i % keyLen));
        }

        return new String(bytes);
    }
}