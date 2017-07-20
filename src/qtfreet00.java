/**
 * Created by qtfreet on 2017/2/24.
 */

import java.io.ByteArrayOutputStream;

public class qtfreet00 {
    private static final String hexString = "0123456789ABCDEF";
    private static final String KEY = "qtfreet";

    /**
     * 将字符串编码成16进制数字,适用于所有字符（包括中文）
     */
    public static String encode(String str) {
        //根据默认编码获取字节数组
        byte[] bytes = str.getBytes();
        int len = bytes.length;
        int keyLen = KEY.length();
        for (int i = 0; i < len; i++) {
            //对每个字节进行异或
            bytes[i] = (byte) (bytes[i] ^ KEY.charAt(i % keyLen));
        }
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        //将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
        }
        return sb.toString();
    }

    public static String encode(String str, String key) {
        //根据默认编码获取字节数组
        byte[] bytes = str.getBytes();
        int len = bytes.length;
        int keyLen = key.length();
        for (int i = 0; i < len; i++) {
            //对每个字节进行异或
            bytes[i] = (byte) (bytes[i] ^ key.charAt(i % keyLen));
        }
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        //将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
        }
        return sb.toString();
    }

    /**
     * 将16进制数字解码成字符串,适用于所有字符（包括中文）
     */
    public static String decode(String str) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(str.length() / 2);
        //将每2位16进制整数组装成一个字节
        for (int i = 0; i < str.length(); i += 2)
            baos.write((hexString.indexOf(str.charAt(i)) << 4 | hexString.indexOf(str.charAt(i + 1))));
        byte[] b = baos.toByteArray();
        int len = b.length;
        int keyLen = KEY.length();
        for (int i = 0; i < len; i++) {
            b[i] = (byte) (b[i] ^ KEY.charAt(i % keyLen));
        }
        return new String(b);
    }

}