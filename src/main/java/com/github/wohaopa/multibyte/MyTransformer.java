package com.github.wohaopa.multibyte;

import static org.objectweb.asm.Opcodes.*;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

public class MyTransformer implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("net.minecraft.client.gui.FontRenderer")) {

            ClassReader reader = new ClassReader(basicClass);

            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);

            ClassVisitor change = new ChangeVisitor(writer);

            reader.accept(change, ClassReader.EXPAND_FRAMES);

            return writer.toByteArray();

        } else return basicClass;
    }

    public static class ChangeVisitor extends ClassVisitor {

        ChangeVisitor(ClassVisitor classVisitor) {
            super(Opcodes.ASM5, classVisitor);
        }

        @Override
        public void visitEnd() {
            super.visitEnd();
            MethodVisitor mv = visitMethod(ACC_PRIVATE, "renderMultibyteCharAtPos", "(CC)F", null, null);
            {

                mv.visitCode();
                mv.visitVarInsn(ILOAD, 1);
                mv.visitIntInsn(SIPUSH, 1023);
                mv.visitInsn(IAND);
                mv.visitIntInsn(BIPUSH, 10);
                mv.visitInsn(ISHL);
                mv.visitVarInsn(ILOAD, 2);
                mv.visitIntInsn(SIPUSH, 1023);
                mv.visitInsn(IAND);
                mv.visitInsn(IOR);
                mv.visitLdcInsn(65536);
                mv.visitInsn(IADD);
                mv.visitVarInsn(ISTORE, 3);
                mv.visitVarInsn(ILOAD, 3);
                mv.visitIntInsn(SIPUSH, 256);
                mv.visitInsn(IDIV);
                mv.visitVarInsn(ISTORE, 4);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitVarInsn(ILOAD, 4);
                mv.visitMethodInsn(
                    INVOKESPECIAL,
                    "net/minecraft/client/gui/FontRenderer",
                    "loadGlyphTexture",
                    "(I)V",
                    false);
                mv.visitInsn(ICONST_0);
                mv.visitVarInsn(ISTORE, 5);
                mv.visitIntInsn(BIPUSH, 15);
                mv.visitVarInsn(ISTORE, 6);
                mv.visitVarInsn(ILOAD, 5);
                mv.visitInsn(I2F);
                mv.visitVarInsn(FSTORE, 7);
                mv.visitVarInsn(ILOAD, 6);
                mv.visitInsn(ICONST_1);
                mv.visitInsn(IADD);
                mv.visitInsn(I2F);
                mv.visitVarInsn(FSTORE, 8);
                mv.visitVarInsn(ILOAD, 3);
                mv.visitIntInsn(BIPUSH, 16);
                mv.visitInsn(IREM);
                mv.visitIntInsn(BIPUSH, 16);
                mv.visitInsn(IMUL);
                mv.visitInsn(I2F);
                mv.visitVarInsn(FLOAD, 7);
                mv.visitInsn(FADD);
                mv.visitVarInsn(FSTORE, 9);
                mv.visitVarInsn(ILOAD, 3);
                mv.visitIntInsn(SIPUSH, 255);
                mv.visitInsn(IAND);
                mv.visitIntInsn(BIPUSH, 16);
                mv.visitInsn(IDIV);
                mv.visitIntInsn(BIPUSH, 16);
                mv.visitInsn(IMUL);
                mv.visitInsn(I2F);
                mv.visitVarInsn(FSTORE, 10);
                mv.visitVarInsn(FLOAD, 8);
                mv.visitVarInsn(FLOAD, 7);
                mv.visitInsn(FSUB);
                mv.visitLdcInsn(0.02f);
                mv.visitInsn(FSUB);
                mv.visitVarInsn(FSTORE, 11);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitFieldInsn(GETFIELD, "net/minecraft/client/gui/FontRenderer", "italicStyle", "Z");
                Label l0 = new Label();
                mv.visitJumpInsn(IFEQ, l0);
                mv.visitInsn(FCONST_1);
                Label l1 = new Label();
                mv.visitJumpInsn(GOTO, l1);
                mv.visitLabel(l0);
                mv.visitInsn(FCONST_0);
                mv.visitLabel(l1);
                mv.visitVarInsn(FSTORE, 12);
                mv.visitInsn(ICONST_5);
                mv.visitMethodInsn(INVOKESTATIC, "org/lwjgl/opengl/GL11", "glBegin", "(I)V", false);
                mv.visitVarInsn(FLOAD, 9);
                mv.visitLdcInsn(256.0f);
                mv.visitInsn(FDIV);
                mv.visitVarInsn(FLOAD, 10);
                mv.visitLdcInsn(256.0f);
                mv.visitInsn(FDIV);
                mv.visitMethodInsn(INVOKESTATIC, "org/lwjgl/opengl/GL11", "glTexCoord2f", "(FF)V", false);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitFieldInsn(GETFIELD, "net/minecraft/client/gui/FontRenderer", "posX", "F");
                mv.visitVarInsn(FLOAD, 12);
                mv.visitInsn(FADD);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitFieldInsn(GETFIELD, "net/minecraft/client/gui/FontRenderer", "posY", "F");
                mv.visitInsn(FCONST_0);
                mv.visitMethodInsn(INVOKESTATIC, "org/lwjgl/opengl/GL11", "glVertex3f", "(FFF)V", false);
                mv.visitVarInsn(FLOAD, 9);
                mv.visitLdcInsn(256.0f);
                mv.visitInsn(FDIV);
                mv.visitVarInsn(FLOAD, 10);
                mv.visitLdcInsn(15.98f);
                mv.visitInsn(FADD);
                mv.visitLdcInsn(256.0f);
                mv.visitInsn(FDIV);
                mv.visitMethodInsn(INVOKESTATIC, "org/lwjgl/opengl/GL11", "glTexCoord2f", "(FF)V", false);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitFieldInsn(GETFIELD, "net/minecraft/client/gui/FontRenderer", "posX", "F");
                mv.visitVarInsn(FLOAD, 12);
                mv.visitInsn(FSUB);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitFieldInsn(GETFIELD, "net/minecraft/client/gui/FontRenderer", "posY", "F");
                mv.visitLdcInsn(7.99f);
                mv.visitInsn(FADD);
                mv.visitInsn(FCONST_0);
                mv.visitMethodInsn(INVOKESTATIC, "org/lwjgl/opengl/GL11", "glVertex3f", "(FFF)V", false);
                mv.visitVarInsn(FLOAD, 9);
                mv.visitVarInsn(FLOAD, 11);
                mv.visitInsn(FADD);
                mv.visitLdcInsn(256.0f);
                mv.visitInsn(FDIV);
                mv.visitVarInsn(FLOAD, 10);
                mv.visitLdcInsn(256.0f);
                mv.visitInsn(FDIV);
                mv.visitMethodInsn(INVOKESTATIC, "org/lwjgl/opengl/GL11", "glTexCoord2f", "(FF)V", false);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitFieldInsn(GETFIELD, "net/minecraft/client/gui/FontRenderer", "posX", "F");
                mv.visitVarInsn(FLOAD, 11);
                mv.visitInsn(FCONST_2);
                mv.visitInsn(FDIV);
                mv.visitInsn(FADD);
                mv.visitVarInsn(FLOAD, 12);
                mv.visitInsn(FADD);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitFieldInsn(GETFIELD, "net/minecraft/client/gui/FontRenderer", "posY", "F");
                mv.visitInsn(FCONST_0);
                mv.visitMethodInsn(INVOKESTATIC, "org/lwjgl/opengl/GL11", "glVertex3f", "(FFF)V", false);
                mv.visitVarInsn(FLOAD, 9);
                mv.visitVarInsn(FLOAD, 11);
                mv.visitInsn(FADD);
                mv.visitLdcInsn(256.0f);
                mv.visitInsn(FDIV);
                mv.visitVarInsn(FLOAD, 10);
                mv.visitLdcInsn(15.98f);
                mv.visitInsn(FADD);
                mv.visitLdcInsn(256.0f);
                mv.visitInsn(FDIV);
                mv.visitMethodInsn(INVOKESTATIC, "org/lwjgl/opengl/GL11", "glTexCoord2f", "(FF)V", false);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitFieldInsn(GETFIELD, "net/minecraft/client/gui/FontRenderer", "posX", "F");
                mv.visitVarInsn(FLOAD, 11);
                mv.visitInsn(FCONST_2);
                mv.visitInsn(FDIV);
                mv.visitInsn(FADD);
                mv.visitVarInsn(FLOAD, 12);
                mv.visitInsn(FSUB);
                mv.visitVarInsn(ALOAD, 0);
                mv.visitFieldInsn(GETFIELD, "net/minecraft/client/gui/FontRenderer", "posY", "F");
                mv.visitLdcInsn(7.99f);
                mv.visitInsn(FADD);
                mv.visitInsn(FCONST_0);
                mv.visitMethodInsn(INVOKESTATIC, "org/lwjgl/opengl/GL11", "glVertex3f", "(FFF)V", false);
                mv.visitMethodInsn(INVOKESTATIC, "org/lwjgl/opengl/GL11", "glEnd", "()V", false);
                mv.visitVarInsn(FLOAD, 8);
                mv.visitVarInsn(FLOAD, 7);
                mv.visitInsn(FSUB);
                mv.visitInsn(FCONST_2);
                mv.visitInsn(FDIV);
                mv.visitInsn(FCONST_1);
                mv.visitInsn(FADD);
                mv.visitInsn(FRETURN);
                mv.visitMaxs(3, 13);
                mv.visitEnd();
            }

        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
            if (name.equals("renderStringAtPos")) {
                return new MyAdapter1(Opcodes.ASM5, methodVisitor, access, name, desc);
            } else if (name.equals("<clinit>")) {
                return new MyAdapter2(Opcodes.ASM5, methodVisitor, access, name, desc);
            }

            return methodVisitor;

        }
    }

    static class MyAdapter2 extends AdviceAdapter {

        MyAdapter2(int api, MethodVisitor mv, int access, String name, String desc) {
            super(api, mv, access, name, desc);
        }

        @Override
        public void visitIntInsn(int opcode, int operand) {
            if (opcode == Opcodes.SIPUSH && operand == 256) {
                super.visitIntInsn(opcode, 4096);
            } else super.visitIntInsn(opcode, operand);
        }

    }

    static class MyAdapter1 extends AdviceAdapter {

        MyAdapter1(int api, MethodVisitor mv, int access, String name, String desc) {
            super(api, mv, access, name, desc);
        }

        private int multibyte = -1;
        // private int char1 = -1;

        private boolean first = true;

        @Override
        protected void onMethodEnter() {
            super.onMethodEnter();

        }

        // @Override
        // protected void updateNewLocals(Object[] newLocals) {
        // if(newLocals.length > char1){
        // newLocals[char1] = INTEGER;
        // }
        // super.updateNewLocals(newLocals);
        // }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {

            if (Opcodes.INVOKESPECIAL == opcode && "net/minecraft/client/gui/FontRenderer".equals(owner)
                && "renderCharAtPos".equals(name)
                && "(ICZ)F".equals(desc)) {

                int var3 = super.newLocal(Type.BOOLEAN_TYPE);
                super.visitVarInsn(Opcodes.ISTORE, var3);
                super.pop();
                super.pop();
                super.pop();

                Label label1 = super.newLabel();
                Label label2 = super.newLabel();

                if (first) {
                    multibyte = super.newLocal(Type.BOOLEAN_TYPE);
                    // char1 = super.newLocal(Type.CHAR_TYPE);

                    Label label3 = super.newLabel();

                    super.visitVarInsn(Opcodes.ILOAD, 3); // i
                    super.visitInsn(Opcodes.ICONST_1); // 字面量1
                    super.visitInsn(Opcodes.IADD); // i+1
                    super.visitVarInsn(Opcodes.ALOAD, 1); // 字符串
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "length", "()I", false); // 调用方法
                    super.visitJumpInsn(Opcodes.IF_ICMPGE, label3);

                    super.visitVarInsn(Opcodes.ILOAD, 4); // c0

                    super.visitVarInsn(Opcodes.ALOAD, 1); // 字符串
                    super.visitVarInsn(Opcodes.ILOAD, 3); // i
                    super.visitInsn(Opcodes.ICONST_1); // 字面量1
                    super.visitInsn(Opcodes.IADD); // i+1
                    super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "charAt", "(I)C", false); // 调用方法
                    // super.visitInsn(Opcodes.DUP);
                    // super.visitVarInsn(Opcodes.ISTORE, char1);
                    // super.visitVarInsn(Opcodes.ILOAD, char1);

                    super.visitMethodInsn(
                        Opcodes.INVOKESTATIC,
                        "java/lang/Character",
                        "isSurrogatePair",
                        "(CC)Z",
                        false); // 调用方法
                    super.visitJumpInsn(Opcodes.IFEQ, label3);
                    super.visitInsn(Opcodes.ICONST_1); // true
                    Label label4 = super.newLabel();
                    super.visitJumpInsn(Opcodes.GOTO, label4);

                    super.visitLabel(label3);
                    super.visitInsn(Opcodes.ICONST_0); // false

                    super.visitLabel(label4);
                    super.visitVarInsn(Opcodes.ISTORE, multibyte);
                }

                super.visitVarInsn(Opcodes.ILOAD, multibyte);
                super.visitJumpInsn(Opcodes.IFEQ, label1);

                if (first) {
                    super.visitIincInsn(3, 1); // i++
                    first = false;
                }
                super.visitVarInsn(Opcodes.ALOAD, 0);
                super.visitVarInsn(Opcodes.ILOAD, 4); // c0

                // super.visitVarInsn(Opcodes.ILOAD, char1); // char 01

                super.visitVarInsn(Opcodes.ALOAD, 1); // 字符串
                super.visitVarInsn(Opcodes.ILOAD, 3); // i
                // super.visitInsn(Opcodes.ICONST_1); // 字面量1
                // super.visitInsn(Opcodes.IADD); // i+1
                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/String", "charAt", "(I)C", false); // 调用方法

                super.visitMethodInsn(
                    Opcodes.INVOKESPECIAL,
                    "net/minecraft/client/gui/FontRenderer",
                    "renderMultibyteCharAtPos",
                    "(CC)F",
                    false); // 调用方法

                super.visitJumpInsn(Opcodes.GOTO, label2);
                super.visitLabel(label1);

                super.visitVarInsn(Opcodes.ALOAD, 0);
                super.visitVarInsn(Opcodes.ILOAD, 5);
                super.visitVarInsn(Opcodes.ILOAD, 4);
                super.visitVarInsn(Opcodes.ILOAD, var3);

                super.visitMethodInsn(opcode, owner, name, desc, itf);

                super.visitLabel(label2);
            } else super.visitMethodInsn(opcode, owner, name, desc, itf);
        }

        @Override
        protected void onMethodExit(int opcode) {
            super.onMethodExit(opcode);
            super.visitMaxs(0, 0);
        }
    }
}
