package w279chen.cs349.jsketch;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Rectangle extends Drawable {

    float width, height;


    // Construct a rectangle with the given dimensions
    // The matrix will be used to determine location (defaults to identity matrix)
    // By default, is drawn with the upper-left corner at the origin
    // Assumes: width and height are positive numbers
    Rectangle(float x, float y, int color) {
        startx = x;
        starty = y;
        isDrawable = false;
        this.color = color;
    }

    @Override
    boolean isSelected(float x, float y) {
        boolean inX = false;
        boolean inY = false;
        if (startx > endx && x < startx && x > endx) {
            inX = true;
        } else if (startx < endx && x > startx && x < endx) {
            inX = true;
        }

        if (starty > endy && y < starty && y > endy) {
            inY = true;
        } else if (starty < endy && y > starty && y < endy) {
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

        float tempx, tempy, width, height;

        if (endx < startx) {
            tempx = endx;
            width = startx - endx;
        } else {
            tempx = startx;
            width = endx - startx;
        }

        if (endy < starty) {
            tempy = endy;
            height = starty - endy;
        } else {
            tempy = starty;
            height = endy - starty;
        }
        Matrix oldMatrix = canvas.getMatrix();
        canvas.setMatrix(matrix);
        paint.setColor(color);
        canvas.drawRect(tempx, tempy, tempx + width, tempy + height, paint);
        canvas.setMatrix(oldMatrix);
    }
}
