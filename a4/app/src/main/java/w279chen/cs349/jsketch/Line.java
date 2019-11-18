package w279chen.cs349.jsketch;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Line extends Drawable {

    Line(float x, float y, int color) {
        startx = x;
        starty = y;
        isDrawable = false;
        this.color = color;
    }

    @Override
    boolean isSelected(float x, float y) {
        boolean inX = false;
        boolean inY = false;

        if (Math.abs(startx - endx) < 20) {

            if (startx > endx && x < startx + 20 && x > endx - 20) {
                inX = true;
            } else if (startx < endx && x > startx - 20 && x < endx + 20) {
                inX = true;
            }

        } else {

            if (startx > endx && x < startx && x > endx) {
                inX = true;
            } else if (startx < endx && x > startx && x < endx) {
                inX = true;
            }
        }
        if (Math.abs(starty - endy) < 20) {
            if (starty > endy && y < starty + 20 && y > endy - 20) {
                inY = true;
            } else if (starty < endy && y > starty - 20 && y < endy + 20) {
                inY = true;
            }
        } else {
            if (starty > endy && y < starty && y > endy) {
                inY = true;
            } else if (starty < endy && y > starty && y < endy) {
                inY = true;
            }
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
        Matrix oldMatrix = canvas.getMatrix();
        canvas.setMatrix(matrix);
        paint.setStrokeWidth(10);
        paint.setColor(color);
        canvas.drawLine(startx, starty, endx, endy, paint);
        canvas.setMatrix(oldMatrix);
    }
}
