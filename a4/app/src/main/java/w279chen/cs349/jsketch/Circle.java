package w279chen.cs349.jsketch;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Circle extends Drawable{
    float radius;
    float difx, dify;

    // Construct a circle with the given dimensions
    // The matrix will be used to determine location (defaults to identity matrix)
    // By default, is drawn centred at the origin
    // Assumes: positive radius
    Circle(float x, float y, int color) {
        startx = x;
        starty = y;
        isDrawable = false;
        this.color = color;
    }

    @Override
    boolean isSelected(float x, float y) {
        boolean inX = false;
        boolean inY = false;

        float largex = startx + radius;
        float largey = starty + radius;
        float smallx = startx - radius;
        float smally = starty - radius;

        if (x < largex && x > smallx) {
            inX = true;
        }

        if (y < largey && y > smally) {
            inY = true;
        }

        return inX && inY;
    }

    @Override
    void translate(float x, float y) {
        startx += x;
        starty += y;
        endx += x;
        endy += y;
    }

    @Override
    void draw(Canvas canvas, Paint paint) {
        if (!isDrawable) {
            return;
        }
        difx = endx - startx;
        dify = endy - starty;
        radius = (float)Math.sqrt(difx*difx + dify*dify);

        Matrix oldMatrix = canvas.getMatrix();
        canvas.setMatrix(matrix);
        paint.setColor(color);
        canvas.drawCircle(startx, starty, radius, paint);
        canvas.setMatrix(oldMatrix);
    }
}