package com.mushroom.midnight.common.util;

import javax.vecmath.AxisAngle4d;
import javax.vecmath.Matrix4d;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.function.Consumer;

public class MatrixStack {
    private final Stack<Matrix4d> matrixStack;
    private final Stack<Matrix4d> matrixPool;

    public MatrixStack(int initPoolSize) {
        this.matrixPool = new Stack<>();
        for (int i = 0; i < initPoolSize; i++) {
            Matrix4d matrix = new Matrix4d();
            this.matrixPool.push(matrix);
        }

        this.matrixStack = new Stack<>();
        Matrix4d matrix = new Matrix4d();
        matrix.setIdentity();
        this.matrixStack.push(matrix);
    }

    public void identity() {
        while (this.matrixStack.size() > 1) {
            this.matrixPool.push(this.matrixStack.pop());
        }
        this.matrixStack.peek().setIdentity();
    }

    public void push() {
        Matrix4d matrix = this.takePool();
        matrix.mul(this.matrixStack.peek());
        this.matrixStack.push(matrix);
    }

    public void pop() {
        if (this.matrixStack.size() <= 1) {
            throw new EmptyStackException();
        }
        this.matrixPool.push(this.matrixStack.pop());
    }

    public void translate(double x, double y, double z) {
        Matrix4d matrix = this.matrixStack.peek();
        this.useMatrix(translation -> {
            translation.setTranslation(new Vector3d(x, y, z));
            matrix.mul(translation);
        });
    }

    public void rotate(double angle, double x, double y, double z) {
        Matrix4d matrix = this.matrixStack.peek();
        this.useMatrix(rotation -> {
            rotation.setRotation(new AxisAngle4d(x, y, z, Math.toRadians(angle)));
            matrix.mul(rotation);
        });
    }

    public void transform(Point3d point) {
        Matrix4d matrix = this.matrixStack.peek();
        matrix.transform(point);
    }

    public void transform(Point3f point) {
        Matrix4d matrix = this.matrixStack.peek();
        matrix.transform(point);
    }

    public void transform(Vector3d vector) {
        Matrix4d matrix = this.matrixStack.peek();
        matrix.transform(vector);
    }

    private void useMatrix(Consumer<Matrix4d> handler) {
        Matrix4d matrix = this.takePool();
        handler.accept(matrix);
        this.matrixPool.push(matrix);
    }

    private Matrix4d takePool() {
        if (this.matrixPool.isEmpty()) {
            Matrix4d matrix = new Matrix4d();
            matrix.setIdentity();
            return matrix;
        }
        Matrix4d matrix = this.matrixPool.pop();
        matrix.setIdentity();
        return matrix;
    }
}
