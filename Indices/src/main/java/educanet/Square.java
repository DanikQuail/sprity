package educanet;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL33;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Square {
    private float updateNumero = 6;
    private FloatBuffer tb = BufferUtils.createFloatBuffer(8);
    private float x;
    private float y;
    private float z;

    public float[] vertices;

    private float[] colors = {
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f, 1.0f,
    };

    float[] textures = {
            0.16f, 0.0f,
            0.16f, 1.0f,
            0.0f, 1.0f,
            0.0f, 0.0f,
    };
    float[] textures2 = {
            0.32f, 0.0f,
            0.32f, 1.0f,
            0.16f, 1.0f,
            0.16f, 0.0f,
    };
    float[] textures3 = {
            0.48f, 0.0f,
            0.48f, 1.0f,
            0.32f, 1.0f,
            0.32f, 0.0f,
    };
    float[] textures4 = {
            0.64f, 0.0f,
            0.64f, 1.0f,
            0.48f, 1.0f,
            0.48f, 0.0f,
    };
    float[] textures5 = {
            0.80f, 0.0f,
            0.80f, 1.0f,
            0.64f, 1.0f,
            0.64f, 0.0f,
    };
    float[] textures6 = {
            0.96f, 0.0f,
            0.96f, 1.0f,
            0.80f, 1.0f,
            0.80f, 0.0f,
    };



    private final int[] indices = {
            0, 1, 3, // First triangle
            1, 2, 3 // Second triangle
    };

    private int squareVaoId;
    private int squareVboId;
    private int squareEboId;
    private int squareColorId;
    private FloatBuffer cb;
    private static int uniformMatrixLocation;
    public Matrix4f matrix;
    public FloatBuffer matrixFloatBuffer;
    private static int textureIndicesId;
    private static int textureLoL;

    public Square(float x, float y, float size) {
        this.x = x;
        this.y = y;
        this.z = size;
        float[] vertices = {
                x + size, y, 0.0f, // 0 -> Top right
                x + size, y - size, 0.0f, // 1 -> Bottom right
                x, y - size, 0.0f, // 2 -> Bottom left
                x, y, 0.0f, // 3 -> Top left
        };
        matrix = new Matrix4f()
                .identity();
        // 4x4 -> FloatBuffer of size 16
        matrixFloatBuffer = BufferUtils.createFloatBuffer(16);

        this.vertices = vertices;
        cb = BufferUtils.createFloatBuffer(colors.length).put(colors).flip();

        squareVaoId = GL33.glGenVertexArrays();
        squareVboId = GL33.glGenBuffers();
        squareEboId = GL33.glGenBuffers();
        squareColorId = GL33.glGenBuffers();
        textureIndicesId = GL33.glGenBuffers();

        textureLoL = GL33.glGenTextures();
        obrazekPog();

        uniformMatrixLocation = GL33.glGetUniformLocation(Shaders.shaderProgramId, "matrix");

        GL33.glBindVertexArray(squareVaoId);

        GL33.glBindBuffer(GL33.GL_ELEMENT_ARRAY_BUFFER, squareEboId);
        IntBuffer ib = BufferUtils.createIntBuffer(indices.length)
                .put(indices)
                .flip();
        GL33.glBufferData(GL33.GL_ELEMENT_ARRAY_BUFFER, ib, GL33.GL_STATIC_DRAW);

        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, squareColorId);
        FloatBuffer cb = BufferUtils.createFloatBuffer(colors.length)
                .put(colors)
                .flip();

        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, cb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(1, 3, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(1);


        GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, squareVboId);
        FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length)
                .put(vertices)
                .flip();

        GL33.glBufferData(GL33.GL_ARRAY_BUFFER, fb, GL33.GL_STATIC_DRAW);
        GL33.glVertexAttribPointer(0, 3, GL33.GL_FLOAT, false, 0, 0);
        GL33.glEnableVertexAttribArray(0);

        GL33.glUseProgram(educanet.Shaders.shaderProgramId);

        matrix.get(matrixFloatBuffer);
        GL33.glUniformMatrix4fv(uniformMatrixLocation, false, matrixFloatBuffer);

        MemoryUtil.memFree(fb);
        MemoryUtil.memFree(tb);
        MemoryUtil.memFree(cb);
        MemoryUtil.memFree(ib);
    }



    public void render() {
        GL33.glUseProgram(Shaders.shaderProgramId);
        matrix.get(matrixFloatBuffer);
        GL33.glUniformMatrix4fv(uniformMatrixLocation, false, matrixFloatBuffer);
        GL33.glBindTexture(GL33.GL_TEXTURE_2D, textureLoL);
        GL33.glBindVertexArray(squareVaoId);
        GL33.glDrawElements(GL33.GL_TRIANGLES, indices.length, GL33.GL_UNSIGNED_INT, 0);
    }

    public void update(long window) {
        if ((int) updateNumero == 6){
            GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, textureIndicesId);
            tb
                    .put(textures2)
                    .flip();


            // Send the buffer (positions) to the GPU
            GL33.glBufferData(GL33.GL_ARRAY_BUFFER, tb, GL33.GL_STATIC_DRAW);
            GL33.glVertexAttribPointer(2, 2, GL33.GL_FLOAT, false, 0, 0);
            GL33.glEnableVertexAttribArray(2);
            updateNumero -= 0.1;
            System.out.println("6");
        }
        if (updateNumero >= 5){
            GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, textureIndicesId);
            tb.clear()
                    .put(textures3)
                    .flip();


            // Send the buffer (positions) to the GPU
            GL33.glBufferData(GL33.GL_ARRAY_BUFFER, tb, GL33.GL_STATIC_DRAW);
            GL33.glVertexAttribPointer(2, 2, GL33.GL_FLOAT, false, 0, 0);
            GL33.glEnableVertexAttribArray(2);
            updateNumero -= 0.1;
            System.out.println("5");
        }
        if ((int)updateNumero == 4){
            GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, textureIndicesId);
            tb.clear()
                    .put(textures4)
                    .flip();


            // Send the buffer (positions) to the GPU
            GL33.glBufferData(GL33.GL_ARRAY_BUFFER, tb, GL33.GL_STATIC_DRAW);
            GL33.glVertexAttribPointer(2, 2, GL33.GL_FLOAT, false, 0, 0);
            GL33.glEnableVertexAttribArray(2);
            updateNumero -= 0.1;
            System.out.println("4");
        }
        if ((int)updateNumero == 3){
            GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, textureIndicesId);
            tb.clear()
                    .put(textures5)
                    .flip();


            // Send the buffer (positions) to the GPU
            GL33.glBufferData(GL33.GL_ARRAY_BUFFER, tb, GL33.GL_STATIC_DRAW);
            GL33.glVertexAttribPointer(2, 2, GL33.GL_FLOAT, false, 0, 0);
            GL33.glEnableVertexAttribArray(2);
            updateNumero -= 0.1;
            System.out.println("3");

        }
        if ((int)updateNumero == 2){
            GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, textureIndicesId);
            tb.clear()
                    .put(textures6)
                    .flip();


            // Send the buffer (positions) to the GPU
            GL33.glBufferData(GL33.GL_ARRAY_BUFFER, tb, GL33.GL_STATIC_DRAW);
            GL33.glVertexAttribPointer(2, 2, GL33.GL_FLOAT, false, 0, 0);
            GL33.glEnableVertexAttribArray(2);
            updateNumero -= 0.1;
            System.out.println("2");

        }
        if ((int)updateNumero == 1){
            GL33.glBindBuffer(GL33.GL_ARRAY_BUFFER, textureIndicesId);
            tb.clear()
                    .put(textures)
                    .flip();


            // Send the buffer (positions) to the GPU
            GL33.glBufferData(GL33.GL_ARRAY_BUFFER, tb, GL33.GL_STATIC_DRAW);
            GL33.glVertexAttribPointer(2, 2, GL33.GL_FLOAT, false, 0, 0);
            GL33.glEnableVertexAttribArray(2);
            System.out.println("1");
            updateNumero = 6;
        }


        matrix.get(matrixFloatBuffer);
        GL33.glUniformMatrix4fv(uniformMatrixLocation, false, matrixFloatBuffer);
    }



    private static void obrazekPog() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);

            ByteBuffer obrazek = STBImage.stbi_load("image/pog.png", w, h, comp, 3);
            if (obrazek != null) {
                obrazek.flip();

                GL33.glBindTexture(GL33.GL_TEXTURE_2D, textureLoL);
                GL33.glTexImage2D(GL33.GL_TEXTURE_2D, 0, GL33.GL_RGB, w.get(), h.get(), 0, GL33.GL_RGB, GL33.GL_UNSIGNED_BYTE, obrazek);
                GL33.glGenerateMipmap(GL33.GL_TEXTURE_2D);

                STBImage.stbi_image_free(obrazek);
            }
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}