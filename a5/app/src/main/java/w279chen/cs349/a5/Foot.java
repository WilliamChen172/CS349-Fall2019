package w279chen.cs349.a5;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;

public class Foot extends Drawable {
    private float originLeft;
    private float originRight;
    private float originTop;
    private float originBottom;

    public Foot(float left, float right, float top, float bottom, int pivotX, int pivotY) {
        id = idNumber;
        idNumber++;
        originLeft = left;
        originRight = right;
        originTop = top;
        originBottom = bottom;
        pivot = new Point(pivotX, pivotY);
        rotateLimit = 35;
        interactionMode = InteractionMode.ROTATING;
        defaultMode = InteractionMode.ROTATING;
        canScale = false;
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        paint.setColor(Color.LTGRAY);
        canvas.drawRoundRect(originLeft, originTop, originRight, originBottom, 50, 50, paint);
    }

    @Override
    public boolean pointInside(float x, float y) {
        Matrix fullMatrix = getFullMatrix();
        Matrix inverseMatrix = new Matrix();
        if (!fullMatrix.invert(inverseMatrix)) {
            return false;
        }
        float vec[] = new float[2];
        vec[0] = x;
        vec[1] = y;
        inverseMatrix.mapPoints(vec);

        if (vec[0] > originLeft && vec[0] < originRight
                && vec[1] > originTop && vec[1] < originBottom) {
            return true;
        }
        return false;
    }

    @Override
    void draw(Canvas canvas, Paint paint) {

        for (Drawable drawable: children) {
            drawable.draw(canvas, paint);
        }

        Matrix oldMatrix = canvas.getMatrix();

        Matrix fullMatrix = getFullMatrix();

        Matrix noScaleMatrix = getFullNoScaleMatrix();
        float[] fullValues = new float[9];
        float[] noScaleValues = new float[9];
        fullMatrix.getValues(fullValues);
        noScaleMatrix.getValues(noScaleValues);

        float translatex, translatey;
        if (noScaleValues[4] == 0 || fullValues[4] == 0) {
            translatey = 0;
        } else {
            translatey = noScaleValues[5] / noScaleValues[4] - fullValues[5] / fullValues[4];
        }
        if (noScaleValues[1] == 0 || fullValues[1] == 0) {
            translatex = 0;
        } else {
            translatex = noScaleValues[2] / noScaleValues[1] - fullValues[2] / fullValues[1];
        }

            noScaleMatrix.preTranslate(translatex, translatey);



        canvas.setMatrix(noScaleMatrix);
        drawSelf(canvas, paint);
        canvas.setMatrix(oldMatrix);

    }
}
