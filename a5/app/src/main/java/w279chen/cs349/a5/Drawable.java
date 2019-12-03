package w279chen.cs349.a5;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import java.lang.Math;

import androidx.annotation.Nullable;

import java.util.Vector;

public abstract class Drawable {

    enum InteractionMode {
        DRAGGING,
        SCALING,
        ROTATING
    }

    static int idNumber = 0;

    Drawable parent = null;
    Vector<Drawable> children = new Vector<Drawable>();
    float prevX;
    float prevY;
    int rotateLimit;
    float rotateCurrent;
    Point pivot = null;
    InteractionMode interactionMode;
    InteractionMode defaultMode;

    int id;
    boolean canScale;
    double distance = 0;

    Matrix matrix = new Matrix(); // identity matrix
    Matrix scaleMatrix = new Matrix();
    Matrix noScaleMatrix = new Matrix();

    public Drawable() {
    }

    public Drawable(Drawable parent) {
        if (parent != null) {
            parent.addChild(this);
        }
    }

    public void addChild(Drawable s) {
        children.add(s);
        s.setParent(this);
    }
    public Drawable getParent() {
        return parent;
    }
    private void setParent(Drawable s) {
        this.parent = s;
    }

    public int getId() {
        return id;
    }

    // Translate by dx, dy
    // Appends to the current matrix, so operations are cumulative
    void translate(float dx, float dy) {
        matrix.preTranslate(dx, dy);
        noScaleMatrix.preTranslate(dx, dy);
    }

    // Scale by sx, sy
    // Appends to the current matrix, so operations are cumulative
    void scale(float sx, float sy) {
        if (canScale) {
            matrix.preScale(sx, sy, pivot.x, pivot.y);
            scaleMatrix.preScale(sx, sy, pivot.x, pivot.y);
        }
    }

    void rotate(float degrees) {
        if (pivot != null) {
            float actualDegree = degrees;
            if (rotateCurrent + degrees > rotateLimit) {
            } else if (rotateCurrent + degrees < Math.negateExact(rotateLimit)) {
            } else {
                rotateCurrent += degrees;
                matrix.postRotate(actualDegree, pivot.x, pivot.y);
                noScaleMatrix.postRotate(actualDegree, pivot.x, pivot.y);
            }

        }
    }

    public abstract boolean pointInside(float x, float y);


    protected void handleMultiTouchEvent(MotionEvent e) {
        float diffx = e.getX(0) - e.getX(1);
        float diffy = e.getY(0) - e.getY(1);
        distance = Math.sqrt(diffx * diffx + diffy * diffy);
    }

    protected void handleMouseDownEvent(MotionEvent e) {
        prevX = e.getX();
        prevY = e.getY();
    }

    protected void handleMouseDragEvent(MotionEvent e) {
        if (e.getPointerCount() == 2) {
            float diffx = e.getX(0) - e.getX(1);
            float diffy = e.getY(0) - e.getY(1);
            double newDistance = Math.sqrt(diffx * diffx + diffy * diffy);
            if (newDistance < distance) {
                scale(1, (float)0.98);
                distance = newDistance;
            } else if (newDistance > distance) {
                scale(1, (float)1.05);
                distance = newDistance;
            }
            return;
        }
        switch (interactionMode) {
            case DRAGGING:
                float x_diff = e.getX() - prevX;
                float y_diff = e.getY() - prevY;
                translate(x_diff, y_diff);
                break;
            case ROTATING:
                float angle = (float)getRotationDegree(e.getX(), e.getY(), prevX, prevY, pivot.x, pivot.y);
                rotate(angle);
                break;
            case SCALING:
                float diffx = e.getX(0) - e.getX(1);
                float diffy = e.getY(0) - e.getY(1);
                double newDistance = Math.sqrt(diffx * diffx + diffy * diffy);
                if (newDistance < distance) {
                    scale(1, (float)0.98);
                    distance = newDistance;
                } else if (newDistance > distance) {
                    scale(1, (float)1.05);
                    distance = newDistance;
                }
                break;
        }
        // Save our last point, if it's needed next time around
        prevX = e.getX();
        prevY = e.getY();
    }

    Drawable getDrawableHit(float x, float y) {
        for (Drawable drawable : children) {
            Drawable s = drawable.getDrawableHit(x, y);
            if (s != null) {
                return s;
            }
        }
        if (this.pointInside(x, y)) {
            return this;
        }
        return null;
    }


    Drawable getScaleHit(float x0, float y0, float x1, float y1) {
        float midx = (x0 + x1)/2;
        float midy = (y0 + y1)/2;
        return getDrawableHit(midx, midy);
    }

    Matrix getFullMatrix() {
        Matrix returnMatrix = new Matrix();
        Drawable cur = this;
        while (cur != null) {
            returnMatrix.postConcat(cur.getLocalMatrix());
            cur = cur.getParent();
        }
        return returnMatrix;
    }

    Matrix getFullScaleMatrix() {
        Matrix returnMatrix = new Matrix();
        Drawable cur = this;
        while (cur != null) {
            returnMatrix.postConcat(cur.getScaleMatrix());
            cur = cur.getParent();
        }
        return returnMatrix;
    }

    Matrix getFullNoScaleMatrix() {
        Matrix returnMatrix = new Matrix();
        Drawable cur = this;
        while (cur != null) {
            returnMatrix.postConcat(cur.getNoScaleMatrix());
            cur = cur.getParent();
        }
        return returnMatrix;
    }

    Matrix getScaleMatrix() {
        Matrix temp = this.scaleMatrix;
        return temp;
    }

    Matrix getLocalMatrix() {
        Matrix temp = this.matrix;
        return temp;
    }

    Matrix getNoScaleMatrix() {
        Matrix temp = this.noScaleMatrix;
        return temp;
    }
    // Draw using the current matrix
    void draw(Canvas canvas, Paint paint) {

        for (Drawable drawable: children) {
            drawable.draw(canvas, paint);
        }

        Matrix oldMatrix = canvas.getMatrix();

        Matrix fullMatrix = getFullMatrix();

        Matrix scaleMatrix = this.scaleMatrix;
        //System.out.println(id + ": " + fullMatrix);

        scaleMatrix.postConcat(fullMatrix);


        canvas.setMatrix(fullMatrix);
        drawSelf(canvas, paint);
        canvas.setMatrix(oldMatrix);

    }


    public abstract void drawSelf(Canvas canvas, Paint paint);


    public double getRotationDegree(float newX, float newY, float oldX, float oldY, float centerX, float centerY) {
        Matrix fullMatrix = getFullMatrix();
        Matrix inverseMatrix = new Matrix();
        if (!fullMatrix.invert(inverseMatrix)) {
            return 0;
        }
        float oldPoint[] = new float[2];
        float newPoint[] = new float[2];
        oldPoint[0] = oldX;
        oldPoint[1] = oldY;
        newPoint[0] = newX;
        newPoint[1] = newY;
        inverseMatrix.mapPoints(oldPoint);
        inverseMatrix.mapPoints(newPoint);


        double newAngle = Math.atan2(newPoint[0] - centerX, newPoint[1] - centerY) * 180 / Math.PI;
        double oldAngle = Math.atan2(oldPoint[0] - centerX, oldPoint[1] - centerY) * 180 / Math.PI;
        return oldAngle - newAngle;
    }

}