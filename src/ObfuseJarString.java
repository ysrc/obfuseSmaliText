import com.OooOO0OO;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import visitor.ClassVisitorFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by qtfreet on 2017/3/14.
 */
public class ObfuseJarString {
    private static final String encryptFile = OooOO0OO.class.getName().replace(".", "/") + ".class";

    public static void main(String[] args) throws IOException {
        byte b[] = readClass();
        System.out.println("请输入jar包路径:");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.next();
        if (!path.endsWith(".jar")) {
            System.out.println("请输入正确的jar包路径");
            System.exit(0);
        }
        int index = path.lastIndexOf(".jar");
        File jarIn = new File(path);
        File jarOut = new File(path.substring(0, index) + "obfused.jar");
        try {
            processJar(jarIn, jarOut, Charset.forName("UTF-8"), Charset.forName("UTF-8"), b);
        } catch (IllegalArgumentException e) {
            if ("MALFORMED".equals(e.getMessage())) {
                processJar(jarIn, jarOut, Charset.forName("GBK"), Charset.forName("UTF-8"), b);
            } else {
                throw e;
            }
        }
        System.out.println("混淆完成");
    }

    private static byte[] readClass() {
        InputStream in = null;
        try {
            in = OooOO0OO.class.getClassLoader().getResourceAsStream(encryptFile);
            int len = in.available();
            byte[] b = new byte[len];
            in.read(b);
            in.close();
            return b;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private static void processJar(File jarIn, File jarOut, Charset charsetIn, Charset charsetOut, byte[] out) throws IOException {
        ZipInputStream zis = null;
        ZipOutputStream zos = null;
        try {

            zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(jarIn)), charsetIn);
            zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(jarOut)), charsetOut);
            ZipEntry entryIn;
            Map<String, Integer> processedEntryNamesMap = new HashMap<>();
            while ((entryIn = zis.getNextEntry()) != null) {
                final String entryName = entryIn.getName();
                if (!processedEntryNamesMap.containsKey(entryName)) {
                    ZipEntry entryOut = new ZipEntry(entryIn);
                    entryOut.setCompressedSize(-1);
                    zos.putNextEntry(entryOut);
                    if (!entryIn.isDirectory()) {
                        if (entryName.endsWith(".class")) {
                            processClass(zis, zos);
                        } else {
                            copy(zis, zos);
                        }
                    }
                    zos.closeEntry();
                    processedEntryNamesMap.put(entryName, 1);
                }
            }
            ZipEntry eninject = new ZipEntry(encryptFile);
            zos.putNextEntry(eninject);
            zos.write(out);
            zos.closeEntry();

        } finally {
            closeQuietly(zos);
            closeQuietly(zis);
        }
    }


    private static void processClass(InputStream classIn, OutputStream classOut) throws IOException {
        ClassReader cr = new ClassReader(classIn);
        ClassWriter cw = new ClassWriter(1);
        ClassVisitor aia = ClassVisitorFactory.create(cr.getClassName(), cw);
        //   ClassVisitor aia = new TestClassVisitor("", cw);
        cr.accept(aia, 0);

        classOut.write(cw.toByteArray());
        classOut.flush();
    }


    private static void closeQuietly(Closeable target) {
        if (target != null) {
            try {
                target.close();
            } catch (Exception e) {
                // Ignored.
            }
        }
    }

    private static int copy(InputStream in, OutputStream out) throws IOException {
        int total = 0;
        byte[] buffer = new byte[8192];
        int c;
        while ((c = in.read(buffer)) != -1) {
            total += c;
            out.write(buffer, 0, c);
        }
        return total;
    }
}
