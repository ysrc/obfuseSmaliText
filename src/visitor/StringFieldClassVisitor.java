package visitor;

import com.OooOO0OO;
import org.objectweb.asm.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Visit the class to execute string fog.
 *
 * @author Megatron King
 * @since 2017/3/6 20:37
 */

public class StringFieldClassVisitor extends ClassVisitor {

    private static final String IGNORE_ANNOTATION = "Lcom/qtfreet/lib/annotation/StringIgnore;";
    private static final String Xor_FLAG = OooOO0OO.class.getName().replace('.', '/');

    private boolean isClInitExists;

    private List<ClassStringField> mStaticFinalFields = new ArrayList<>();
    private List<ClassStringField> mStaticFields = new ArrayList<>();
    private List<ClassStringField> mFinalFields = new ArrayList<>();
    private List<ClassStringField> mFields = new ArrayList<>();

    private String mClassName;

    private boolean mIgnoreClass;

    public StringFieldClassVisitor(ClassWriter cw) {
        super(Opcodes.ASM5, cw);
    }

    private void encode(MethodVisitor mv, String str) {
        String mKey = UUID.randomUUID().toString().replace("-", "").trim().substring(0, 6);
        byte[] enc = OooOO0OO.encode(str.getBytes(), mKey);
        int len = enc.length;
        mv.visitIntInsn(Opcodes.SIPUSH, len);
        mv.visitIntInsn(Opcodes.NEWARRAY, Opcodes.T_BYTE);
        for (int i = 0; i < len; i++) {
            mv.visitInsn(Opcodes.DUP);
            mv.visitIntInsn(Opcodes.SIPUSH, i);
            mv.visitIntInsn(Opcodes.BIPUSH, enc[i]);
            mv.visitInsn(Opcodes.BASTORE);
        }
        mv.visitLdcInsn((String) mKey);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, Xor_FLAG, "OooOOoo0oo", "([BLjava/lang/String;)Ljava/lang/String;", false);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.mClassName = name;
//        System.out.println("processClass: " + mClassName);
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        mIgnoreClass = IGNORE_ANNOTATION.equals(desc);
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        if (ClassStringField.STRING_DESC.equals(desc) && name != null && !mIgnoreClass) {
            // static final, in this condition, the value is null or not null.
            if ((access & Opcodes.ACC_STATIC) != 0 && (access & Opcodes.ACC_FINAL) != 0) {
                mStaticFinalFields.add(new ClassStringField(name, (String) value));
                value = null;
            }
            // static, in this condition, the value is null.
            if ((access & Opcodes.ACC_STATIC) != 0 && (access & Opcodes.ACC_FINAL) == 0) {
                mStaticFields.add(new ClassStringField(name, (String) value));
                value = null;
            }

            // final, in this condition, the value is null or not null.
            if ((access & Opcodes.ACC_STATIC) == 0 && (access & Opcodes.ACC_FINAL) != 0) {
                mFinalFields.add(new ClassStringField(name, (String) value));
                value = null;
            }

            // normal, in this condition, the value is null.
            if ((access & Opcodes.ACC_STATIC) != 0 && (access & Opcodes.ACC_FINAL) != 0) {
                mFields.add(new ClassStringField(name, (String) value));
                value = null;
            }
        }
        return super.visitField(access, name, desc, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        if (mv != null && !mIgnoreClass) {
            if ("<clinit>".equals(name)) {
                isClInitExists = true;
                // If clinit exists meaning the static fields (not final) would have be inited here.
                mv = new MethodVisitor(Opcodes.ASM5, mv) {

                    private String lastStashCst;

                    @Override
                    public void visitCode() {
                        super.visitCode();
                        // Here init static final fields.
                        for (ClassStringField field : mStaticFinalFields) {
                            if (field.value == null) {
                                continue;
                            }
                            encode(super.mv, field.value);

                            super.visitFieldInsn(Opcodes.PUTSTATIC, mClassName, field.name, ClassStringField.STRING_DESC);
                        }
                    }

                    @Override
                    public void visitLdcInsn(Object cst) {
                        // Here init static or static final fields, but we must check field name int 'visitFieldInsn'
                        if (cst != null && cst instanceof String && !TextUtils.isEmptyAfterTrim((String) cst)) {
                            lastStashCst = (String) cst;
                            encode(super.mv, lastStashCst);

                        } else {
                            lastStashCst = null;
                            super.visitLdcInsn(cst);
                        }
                    }

                    @Override
                    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                        if (mClassName.equals(owner) && lastStashCst != null) {
                            boolean isContain = false;
                            for (ClassStringField field : mStaticFields) {
                                if (field.name.equals(name)) {
                                    isContain = true;
                                    break;
                                }
                            }
                            if (!isContain) {
                                for (ClassStringField field : mStaticFinalFields) {
                                    if (field.name.equals(name) && field.value == null) {
                                        field.value = lastStashCst;
                                        break;
                                    }
                                }
                            }
                        }
                        lastStashCst = null;
                        super.visitFieldInsn(opcode, owner, name, desc);
                    }
                };

            } else if ("<init>".equals(name)) {
                // Here init final(not static) and normal fields
                mv = new MethodVisitor(Opcodes.ASM5, mv) {
                    @Override
                    public void visitLdcInsn(Object cst) {
                        // We don't care about whether the field is final or normal
                        if (cst != null && cst instanceof String && !TextUtils.isEmptyAfterTrim((String) cst)) {
                            encode(super.mv, (String) cst);
                        } else {
                            super.visitLdcInsn(cst);
                        }
                    }
                };
            } else {
                mv = new MethodVisitor(Opcodes.ASM5, mv) {

                    @Override
                    public void visitLdcInsn(Object cst) {
                        if (cst != null && cst instanceof String && !TextUtils.isEmptyAfterTrim((String) cst)) {
                            // If the value is a static final field
                            for (ClassStringField field : mStaticFinalFields) {
                                if (cst.equals(field.value)) {
                                    super.visitFieldInsn(Opcodes.GETSTATIC, mClassName, field.name, ClassStringField.STRING_DESC);
                                    return;
                                }
                            }
                            // If the value is a final field (not static)
                            for (ClassStringField field : mFinalFields) {
                                // if the value of a final field is null, we ignore it
                                if (cst.equals(field.value)) {
                                    super.visitVarInsn(Opcodes.ALOAD, 0);
                                    super.visitFieldInsn(Opcodes.GETFIELD, mClassName, field.name, "Ljava/lang/String;");
                                    return;
                                }
                            }
                            encode(super.mv, (String) cst);
                            return;
                        }
                        super.visitLdcInsn(cst);
                    }

                };
            }
        }
        return mv;
    }

    @Override
    public void visitEnd() {
        if (!mIgnoreClass && !isClInitExists && !mStaticFinalFields.isEmpty()) {
            MethodVisitor mv = super.visitMethod(Opcodes.ACC_STATIC, "<clinit>", "()V", null, null);
            mv.visitCode();
            // Here init static final fields.
            for (ClassStringField field : mStaticFinalFields) {
                if (field.value == null) {
                    continue; // It could not be happened
                }
                encode(mv, field.value);
                mv.visitFieldInsn(Opcodes.PUTSTATIC, mClassName, field.name, ClassStringField.STRING_DESC);
            }
            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(1, 0);
            mv.visitEnd();
        }
        super.visitEnd();
    }
}