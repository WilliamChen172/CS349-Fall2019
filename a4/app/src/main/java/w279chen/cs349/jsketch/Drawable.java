package w279chen.cs349.jsketch;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

abstract class Drawable {
    float startx, starty;
    float endx, endy;
    boolean isDrawable;
    int color;

    void setEndPoint(float x, float y) {endx = x; endy = y; isDrawable = true;};

    void setColor(int color) { this.color = color; }
    abstract void translate(float x, float y);
    abstract void draw(Canvas canvas, Paint paint);
    abstract boolean isSelected(float x, float y);
}
