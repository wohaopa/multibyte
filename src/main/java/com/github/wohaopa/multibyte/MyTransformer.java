package com.github.wohaopa.multibyte;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.launchwrapper.Launch;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.SortingIndex(9999)
public class MyTransformer implements IClassTransformer {

    static boolean deobfuscatedEnvironment;

    public MyTransformer() {
        deobfuscatedEnvironment = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if (transformedName.equals("net.minecraft.client.gui.FontRenderer")) {

            ClassReader reader = new ClassReader(basicClass);

            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);

            ClassVisitor change = new MyClassVisitor(writer);

            reader.accept(change, ClassReader.EXPAND_FRAMES);

            return writer.toByteArray();

        } else return basicClass;
    }

    static class MyClassVisitor extends ClassVisitor {

        MyClassVisitor(ClassVisitor classVisitor) {
            super(Opcodes.ASM5, classVisitor);
        }

        /**
         * This section is to insert a method at the end of the file.
         * The method looks like this:
         * private float renderMultibyteCharAtPos(char var1, char var2) {
         * int var3 = ((var1 & 1023) << 10 | var2 & 1023) + 65536;
         * int var4 = var3 / 256;
         * this.loadGlyphTexture(var4);
         * byte var5 = 0;
         * byte var6 = 15;
         * float var7 = (float)var5;
         * float var8 = (float)(var6 + 1);
         * float var9 = (float)(var3 % 16 * 16) + var7;
         * float var10 = (float)((var3 & 255) / 16 * 16);
         * float var11 = var8 - var7 - 0.02F;
         * float var12 = this.italicStyle ? 1.0F : 0.0F;
         * GL11.glBegin(5);
         * GL11.glTexCoord2f(var9 / 256.0F, var10 / 256.0F);
         * GL11.glVertex3f(this.posX + var12, this.posY, 0.0F);
         * GL11.glTexCoord2f(var9 / 256.0F, (var10 + 15.98F) / 256.0F);
         * GL11.glVertex3f(this.posX - var12, this.posY + 7.99F, 0.0F);
         * GL11.glTexCoord2f((var9 + var11) / 256.0F, var10 / 256.0F);
         * GL11.glVertex3f(this.posX + var11 / 2.0F + var12, this.posY, 0.0F);
         * GL11.glTexCoord2f((var9 + var11) / 256.0F, (var10 + 15.98F) / 256.0F);
         * GL11.glVertex3f(this.posX + var11 / 2.0F - var12, this.posY + 7.99F, 0.0F);
         * GL11.glEnd();
         * return (var8 - var7) / 2.0F + 1.0F;
         * }
         */
        @Override
        public void visitEnd() {
            super.visitEnd();
            MethodVisitor mv = visitMethod(Opcodes.ACC_PRIVATE, "renderMultibyteCharAtPos", "(CC)F", null, null);
            {

                mv.visitCode();
                mv.visitVarInsn(Opcodes.ILOAD, 1);
                mv.visitIntInsn(Opcodes.SIPUSH, 1023);
                mv.visitInsn(Opcodes.IAND);
                mv.visitIntInsn(Opcodes.BIPUSH, 10);
                mv.visitInsn(Opcodes.ISHL);
                mv.visitVarInsn(Opcodes.ILOAD, 2);
                mv.visitIntInsn(Opcodes.SIPUSH, 1023);
                mv.visitInsn(Opcodes.IAND);
                mv.visitInsn(Opcodes.IOR);
                mv.visitLdcInsn(65536);
                mv.visitInsn(Opcodes.IADD);
                mv.visitVarInsn(Opcodes.ISTORE, 3);
                mv.visitVarInsn(Opcodes.ILOAD, 3);
                mv.visitIntInsn(Opcodes.SIPUSH, 256);
                mv.visitInsn(Opcodes.IDIV);
                mv.visitVarInsn(Opcodes.ISTORE, 4);
                mv.visitVarInsn(Opcodes.ALOAD, 0);
                mv.visitVarInsn(Opcodes.ILOAD, 4);
                mv.visitMethodInsn(
                    Opcodes.INVOKESPECIAL,
                    "net/minecraft/client/gui/FontRenderer",
                    deobfuscatedEnvironment ? "loadGlyphTexture" : "func_78257_a",
                    "(I)V",
                    false);
                mv.visitInsn(Opcodes.ICONST_0);
                mv.visitVarInsn(Opcodes.ISTORE, 5);
                mv.visitIntInsn(Opcodes.BIPUSH, 15);
                mv.visitVarInsn(Opcodes.ISTORE, 6);
                mv.visitVarInsn(Opcodes.ILOAD, 5);
                mv.visitInsn(Opcodes.I2F);
                mv.visitVarInsn(Opcodes.FSTORE, 7);
                mv.visitVarInsn(Opcodes.ILOAD, 6);
                mv.visitInsn(Opcodes.ICONST_1);
                mv.visitInsn(Opcodes.IADD);
                mv.visitInsn(Opcodes.I2F);
                mv.visitVarInsn(Opcodes.FSTORE, 8);
                mv.visitVarInsn(Opcodes.ILOAD, 3);
                mv.visitIntInsn(Opcodes.BIPUSH, 16);
                mv.visitInsn(Opcodes.IREM);
                mv.visitIntInsn(Opcodes.BIPUSH, 16);
                mv.visitInsn(Opcodes.IMUL);
                mv.visitInsn(Opcodes.I2F);
                mv.visitVarInsn(Opcodes.FLOAD, 7);
                mv.visitInsn(Opcodes.FADD);
                mv.visitVarInsn(Opcodes.FSTORE, 9);
                mv.visitVarInsn(Opcodes.ILOAD, 3);
                mv.visitIntInsn(Opcodes.SIPUSH, 255);
                mv.visitInsn(Opcodes.IAND);
                mv.visitIntInsn(Opcodes.BIPUSH, 16);
                mv.visitInsn(Opcodes.IDIV);
                mv.visitIntInsn(Opcodes.BIPUSH, 16);
                mv.visitInsn(Opcodes.IMUL);
                mv.visitInsn(Opcodes.I2F);
                mv.visitVarInsn(Opcodes.FSTORE, 10);
                mv.visitVarInsn(Opcodes.FLOAD, 8);
                mv.visitVarInsn(Opcodes.FLOAD, 7);
                mv.visitInsn(Opcodes.FSUB);
                mv.visitLdcInsn(0.02f);
                mv.visitInsn(Opcodes.FSUB);
                mv.visitVarInsn(Opcodes.FSTORE, 11);
                mv.visitVarInsn(Opcodes.ALOAD, 0);
                mv.visitFieldInsn(
                    Opcodes.GETFIELD,
                    "net/minecraft/client/gui/FontRenderer",
                    deobfuscatedEnvironment ? "italicStyle" : "field_78301_u",
                    "Z");
                Label l0 = new Label();
                mv.visitJumpInsn(Opcodes.IFEQ, l0);
                mv.visitInsn(Opcodes.FCONST_1);
                Label l1 = new Label();
                mv.visitJumpInsn(Opcodes.GOTO, l1);
                mv.visitLabel(l0);
                mv.visitInsn(Opcodes.FCONST_0);
                mv.visitLabel(l1);
                mv.visitVarInsn(Opcodes.FSTORE, 12);
                mv.visitInsn(Opcodes.ICONST_5);
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "org/lwjgl/opengl/GL11", "glBegin", "(I)V", false);
                mv.visitVarInsn(Opcodes.FLOAD, 9);
                mv.visitLdcInsn(256.0f);
                mv.visitInsn(Opcodes.FDIV);
                mv.visitVarInsn(Opcodes.FLOAD, 10);
                mv.visitLdcInsn(256.0f);
                mv.visitInsn(Opcodes.FDIV);
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "org/lwjgl/opengl/GL11", "glTexCoord2f", "(FF)V", false);
                mv.visitVarInsn(Opcodes.ALOAD, 0);
                mv.visitFieldInsn(
                    Opcodes.GETFIELD,
                    "net/minecraft/client/gui/FontRenderer",
                    deobfuscatedEnvironment ? "posX" : "field_78295_j",
                    "F");
                mv.visitVarInsn(Opcodes.FLOAD, 12);
                mv.visitInsn(Opcodes.FADD);
                mv.visitVarInsn(Opcodes.ALOAD, 0);
                mv.visitFieldInsn(
                    Opcodes.GETFIELD,
                    "net/minecraft/client/gui/FontRenderer",
                    deobfuscatedEnvironment ? "posY" : "field_78296_k",
                    "F");
                mv.visitInsn(Opcodes.FCONST_0);
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "org/lwjgl/opengl/GL11", "glVertex3f", "(FFF)V", false);
                mv.visitVarInsn(Opcodes.FLOAD, 9);
                mv.visitLdcInsn(256.0f);
                mv.visitInsn(Opcodes.FDIV);
                mv.visitVarInsn(Opcodes.FLOAD, 10);
                mv.visitLdcInsn(15.98f);
                mv.visitInsn(Opcodes.FADD);
                mv.visitLdcInsn(256.0f);
                mv.visitInsn(Opcodes.FDIV);
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "org/lwjgl/opengl/GL11", "glTexCoord2f", "(FF)V", false);
                mv.visitVarInsn(Opcodes.ALOAD, 0);
                mv.visitFieldInsn(
                    Opcodes.GETFIELD,
                    "net/minecraft/client/gui/FontRenderer",
                    deobfuscatedEnvironment ? "posX" : "field_78295_j",
                    "F");
                mv.visitVarInsn(Opcodes.FLOAD, 12);
                mv.visitInsn(Opcodes.FSUB);
                mv.visitVarInsn(Opcodes.ALOAD, 0);
                mv.visitFieldInsn(
                    Opcodes.GETFIELD,
                    "net/minecraft/client/gui/FontRenderer",
                    deobfuscatedEnvironment ? "posY" : "field_78296_k",
                    "F");
                mv.visitLdcInsn(7.99f);
                mv.visitInsn(Opcodes.FADD);
                mv.visitInsn(Opcodes.FCONST_0);
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "org/lwjgl/opengl/GL11", "glVertex3f", "(FFF)V", false);
                mv.visitVarInsn(Opcodes.FLOAD, 9);
                mv.visitVarInsn(Opcodes.FLOAD, 11);
                mv.visitInsn(Opcodes.FADD);
                mv.visitLdcInsn(256.0f);
                mv.visitInsn(Opcodes.FDIV);
                mv.visitVarInsn(Opcodes.FLOAD, 10);
                mv.visitLdcInsn(256.0f);
                mv.visitInsn(Opcodes.FDIV);
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "org/lwjgl/opengl/GL11", "glTexCoord2f", "(FF)V", false);
                mv.visitVarInsn(Opcodes.ALOAD, 0);
                mv.visitFieldInsn(
                    Opcodes.GETFIELD,
                    "net/minecraft/client/gui/FontRenderer",
                    deobfuscatedEnvironment ? "posX" : "field_78295_j",
                    "F");
                mv.visitVarInsn(Opcodes.FLOAD, 11);
                mv.visitInsn(Opcodes.FCONST_2);
                mv.visitInsn(Opcodes.FDIV);
                mv.visitInsn(Opcodes.FADD);
                mv.visitVarInsn(Opcodes.FLOAD, 12);
                mv.visitInsn(Opcodes.FADD);
                mv.visitVarInsn(Opcodes.ALOAD, 0);
                mv.visitFieldInsn(
                    Opcodes.GETFIELD,
                    "net/minecraft/client/gui/FontRenderer",
                    deobfuscatedEnvironment ? "posY" : "field_78296_k",
                    "F");
                mv.visitInsn(Opcodes.FCONST_0);
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "org/lwjgl/opengl/GL11", "glVertex3f", "(FFF)V", false);
                mv.visitVarInsn(Opcodes.FLOAD, 9);
                mv.visitVarInsn(Opcodes.FLOAD, 11);
                mv.visitInsn(Opcodes.FADD);
                mv.visitLdcInsn(256.0f);
                mv.visitInsn(Opcodes.FDIV);
                mv.visitVarInsn(Opcodes.FLOAD, 10);
                mv.visitLdcInsn(15.98f);
                mv.visitInsn(Opcodes.FADD);
                mv.visitLdcInsn(256.0f);
                mv.visitInsn(Opcodes.FDIV);
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "org/lwjgl/opengl/GL11", "glTexCoord2f", "(FF)V", false);
                mv.visitVarInsn(Opcodes.ALOAD, 0);
                mv.visitFieldInsn(
                    Opcodes.GETFIELD,
                    "net/minecraft/client/gui/FontRenderer",
                    deobfuscatedEnvironment ? "posX" : "field_78295_j",
                    "F");
                mv.visitVarInsn(Opcodes.FLOAD, 11);
                mv.visitInsn(Opcodes.FCONST_2);
                mv.visitInsn(Opcodes.FDIV);
                mv.visitInsn(Opcodes.FADD);
                mv.visitVarInsn(Opcodes.FLOAD, 12);
                mv.visitInsn(Opcodes.FSUB);
                mv.visitVarInsn(Opcodes.ALOAD, 0);
                mv.visitFieldInsn(
                    Opcodes.GETFIELD,
                    "net/minecraft/client/gui/FontRenderer",
                    deobfuscatedEnvironment ? "posY" : "field_78296_k",
                    "F");
                mv.visitLdcInsn(7.99f);
                mv.visitInsn(Opcodes.FADD);
                mv.visitInsn(Opcodes.FCONST_0);
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "org/lwjgl/opengl/GL11", "glVertex3f", "(FFF)V", false);
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "org/lwjgl/opengl/GL11", "glEnd", "()V", false);
                mv.visitVarInsn(Opcodes.FLOAD, 8);
                mv.visitVarInsn(Opcodes.FLOAD, 7);
                mv.visitInsn(Opcodes.FSUB);
                mv.visitInsn(Opcodes.FCONST_2);
                mv.visitInsn(Opcodes.FDIV);
                mv.visitInsn(Opcodes.FCONST_1);
                mv.visitInsn(Opcodes.FADD);
                mv.visitInsn(Opcodes.FRETURN);
                mv.visitMaxs(3, 13);
                mv.visitEnd();
            }

        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor methodVisitor = super.visitMethod(access, name, desc, signature, exceptions);
            if (name.equals("renderStringAtPos") || name.equals("func_78255_a") || name.equals("a")) {
                return new MyAdapter1(Opcodes.ASM5, methodVisitor, access, name, desc);
            } else if (name.equals("<clinit>")) {
                return new MyAdapter2(Opcodes.ASM5, methodVisitor, access, name, desc);
            }

            return methodVisitor;

        }
    }

    /**
     * The Adapter simply increases the size of the unicodePageLocations(field_111274_c) field by a factor of 16 to hold
     * additional fonts.
     * It looks something like this:
     * private static final ResourceLocation[] field_111274_c = new ResourceLocation[256];
     * private static final ResourceLocation[] field_111274_c = new ResourceLocation[4096];
     */
    static class MyAdapter2 extends AdviceAdapter {

        MyAdapter2(int api, MethodVisitor mv, int access, String name, String desc) {
            super(api, mv, access, name, desc);
        }

        @Override
        public void visitIntInsn(int opcode, int operand) {
            if (opcode == SIPUSH && operand == 256) {
                super.visitIntInsn(opcode, 4096);
            } else super.visitIntInsn(opcode, operand);
        }

    }

    /**
     * The Adapter simply increases the size of the unicodePageLocations(field_111274_c) field by a factor of 16 to hold
     * additional fonts.
     * It looks something like this:
     * private static final ResourceLocation[] field_111274_c = new ResourceLocation[256];
     * private static final ResourceLocation[] field_111274_c = new ResourceLocation[4096];
     */
    static class MyAdapter1 extends AdviceAdapter {

        MyAdapter1(int api, MethodVisitor mv, int access, String name, String desc) {
            super(api, mv, access, name, desc);
        }

        private int multibyte = -1;

        private boolean first = true;

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {

            if (Opcodes.INVOKESPECIAL == opcode && "net/minecraft/client/gui/FontRenderer".equals(owner)
                && ("renderCharAtPos".equals(name) || "func_78278_a".equals(name) || "a".equals(name))
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
