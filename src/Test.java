import javax.crypto.KeyGenerator;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Created by qtfreet00 on 2017/3/14.
 */
public class Test {

    public static void main(String[] args) throws Exception {
        //    OooOO0OO.createDecodeFunction();
//        String s = OooOO0OO.OooOOoo0oo(new byte[]{(byte) 88, (byte) 89, (byte) 65, (byte) 10, (byte) 20, (byte) 83, (byte) 27, (byte) 84, (byte) 92, (byte) 1}, "685cb6");
//        System.out.println(s);
//        System.out.println(UUID.randomUUID().toString().toUpperCase());
        //System.out.println(THIS_IS_DEMO_VERSION_NOT_FOR_COMMERCIAL_USE("^RaPzYt\u0017_XpVarEzVq[vD3Xu\u0017w^uQvEvYg\u0017`^iR,\u0016,\u0017UE|Z3SzQuRaR}C3ZvC{XwD3Xa\u0017d_rC,\u0016,"));
        // StackTraceElement stackTraceElement = new CloneNotSupportedException().getStackTrace()[1];
        //String string = new StringBuffer(stackTraceElement.getClassName()).append(stackTraceElement.getMethodName()).toString();
        //  System.out.println(stackTraceElement.getClassName()+stackTraceElement.getMethodName());
        //  test();
        //  System.out.println(decode2("}f\u001d\u001a\u0014s\u0000\f\u0004a\u0017\u001b\f\u0002\u001f\u0000\u0004= \rt\u0006\u0015\u000e"));
        //  System.out.println(toString("\u0001'9>*+',94<'t#7;-<){:2,", 104));
        //  System.out.println(insert(83, "\u0017=&&;9 z8303>.%b/-+#g ,&;"));
//        FileInputStream fis = new FileInputStream("C:\\Users\\qtfreet00\\Desktop\\jadx\\jadx-gui\\src\\main\\resources\\i18n\\Messages_zh_CN2.properties");
//        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line = br.readLine()) != null) {
//            sb.append(StringEscapeUtils.escapeJava(line)).append("\n");
//        }
//        FileOutputStream fos = new FileOutputStream("C:\\Users\\qtfreet00\\Desktop\\jadx\\jadx-gui\\src\\main\\resources\\i18n\\Messages_zh_CN.properties");
//        fos.write(sb.toString().getBytes());
        KeyGenerator v4 = KeyGenerator.getInstance("AES");
        v4.init(128, new SecureRandom("6666".getBytes()));
        System.out.println(Arrays.toString(v4.generateKey().getEncoded()));
    }

    private static String z(char[] var0) {
        int var10000 = var0.length;
        int var1 = 0;
        char[] var10001 = var0;
        char[] var10002;
        int var10003;
        if (var10000 <= 1) {
            var10002 = var0;
            var10003 = var1;
        } else {
            var10001 = var0;
            if (var10000 <= var1) {
                return (new String(var0)).intern();
            }

            var10002 = var0;
            var10003 = var1;
        }

        while (true) {
            char var10004 = var10002[var10003];
            byte var10005;
            switch (var1 % 5) {
                case 0:
                    var10005 = 55;
                    break;
                case 1:
                    var10005 = 103;
                    break;
                case 2:
                    var10005 = 95;
                    break;
                case 3:
                    var10005 = 115;
                    break;
                default:
                    var10005 = 40;
            }

            var10002[var10003] = (char) (var10004 ^ var10005);
            ++var1;
            if (var10000 == 0) {
                var10003 = var10000;
                var10002 = var10001;
            } else {
                if (var10000 <= var1) {
                    return (new String(var10001)).intern();
                }

                var10002 = var10001;
                var10003 = var1;
            }
        }
    }

    private static char[] z(String var0) {
        char[] var10000 = var0.toCharArray();
        char[] var10001 = var10000;

        while (true) {
            int var10002 = var10001.length;
            var10001 = var10000;
            int var1 = var10002;
            if (var10002 >= 2) {
                break;
            }

            char[] var4 = var10001;
            int var3 = var1;
            var10000 = var4;
            char[] var10003 = var4;
            var10002 = var3;
            var10001 = var10003;
            if (var10002 != 0) {
                var10001 = var10000;
                boolean var2 = false;
                var10003[0] = (char) (var10003[0] ^ 40);
                break;
            }
        }

        return var10001;
    }


    public static String toString(String string, int n) {
        try {
            throw new Exception("findhook");
        } catch (Exception e) {
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                System.out.println(stackTraceElement.getClassName() + "  " + stackTraceElement.getMethodName() + " " + stackTraceElement.getLineNumber());
            }
        }
        int n2 = 2 + 2;
        char[] arrc = string.toCharArray();
        int n3 = arrc.length;
        char[] arrc2 = arrc;
        int n4 = 0;
        int n5 = (n2 << 1 + n2) + -1 ^ 32;
        do {
            char[] arrc3 = arrc2;
            if (n4 == n3) {
                String string2 = String.valueOf(arrc3, 0, n3).intern();
                return string2;
            }
            int n6 = n4++;
            int n7 = arrc3[n6] ^ n & n5;
            ++n;
            arrc3[n6] = (char) n7;
        } while (true);
    }

    public static String insert(int n, String string) {
        String string2;
        try {
            char[] arrc;
            int n2 = 2 + 2;
            char[] arrc2 = string.toCharArray();
            int n3 = arrc2.length;
            char[] arrc3 = arrc2;
            int n4 = 0;
            int n5 = (n2 << 1 + n2) - 1 ^ 32;
            do {
                arrc = arrc3;
                if (n4 == n3) break;
                int n6 = n4++;
                int n7 = arrc[n6] ^ n & n5;
                ++n;
                arrc[n6] = (char) n7;
            } while (true);
            string2 = String.valueOf(arrc, 0, n3).intern();
        } catch (Exception g2) {
            string2 = null;
        }
        String string3 = string2;
        return string3;
    }

    public static /* synthetic */ String decode2(String iiIiIIIIiI2) {
        int n;
        String string = "com.allatori.iIIIIIIiIITHIS_IS_DEMO_VERSION_NOT_FOR_COMMERCIAL_USE";
        int n2 = iiIiIIIIiI2.length();
        int n3 = n2 - 1;
        char[] arrc = new char[n2];
        int n4 = (2 ^ 5) << 3 ^ 2;
        int n5 = 4 << 3 ^ (2 ^ 5);
        int n6 = n = string.length() - 1;
        String string2 = string;
        while (n3 >= 0) {
            int n7 = n3--;
            arrc[n7] = (char) (n5 ^ (iiIiIIIIiI2.charAt(n7) ^ string2.charAt(n)));
            if (n3 < 0) break;

            if (--n < 0) {
                n = n6;
            }

        }
        return new String(arrc);
    }


    private static void test() throws Exception {
        Class clazz = Class.forName("com.allatori.IiIIiiIIii");
        Method rfc = clazz.getDeclaredMethod("THIS_IS_DEMO_VERSION_NOT_FOR_COMMERCIAL_USE", String.class);
        rfc.setAccessible(true);
        String res = (String) rfc.invoke(null, "#U,\u001d\b\u0013a\u0019\u0017\u001b\u0007\u0010\u000b\u0015f\u0001\u0015u\t\u0004\u0019/'\u0014\u0014\u0000t\u001b\r\t\u0003b\u0010\u001d\u0004\f\u0017\f\u0013\u001a\u001c\rc");
        System.out.println(res);
    }

    public static String OooOOoo0oo(byte[] str, String key) {
        return "";
    }

    public static final String DEFAULT_KEY = "qtfreet";
    private static final String hexString = "0123456789ABCDEF";

    public static String OooOOoo0oo(String str) {
        int i;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(str.length() / 2);
        for (i = 0; i < str.length(); i += 2) {
            baos.write((hexString.indexOf(str.charAt(i)) << 4) | hexString.indexOf(str.charAt(i + 1)));
        }
        byte[] b = baos.toByteArray();
        int len = b.length;
        int keyLen = DEFAULT_KEY.length();
        for (i = 0; i < len; i++) {
            b[i] = (byte) (b[i] ^ DEFAULT_KEY.charAt(i % keyLen));
        }
        return new String(b);
    }


    public static /* synthetic */ String THIS_IS_DEMO_VERSION_NOT_FOR_COMMERCIAL_USE(String iiIiIIIIiI2) {
        int n = iiIiIIIIiI2.length();
        int n2 = n - 1;
        char[] arrc = new char[n];
        int n3 = (3 ^ 5) << 3 ^ (2 ^ 5);
        int n4 = n2;
        int n5 = 2 << 3 ^ 3;
        while (n4 >= 0) {
            int n6 = n2--;
            arrc[n6] = (char) (iiIiIIIIiI2.charAt(n6) ^ n5);
            if (n2 < 0) break;
            int n7 = n2--;
            arrc[n7] = (char) (iiIiIIIIiI2.charAt(n7) ^ n3);
            n4 = n2;
        }
        return new String(arrc);
    }

}
