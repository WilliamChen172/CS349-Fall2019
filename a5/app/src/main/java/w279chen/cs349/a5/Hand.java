package w279chen.cs349.a5;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;

public class Hand extends Drawable {
    private float originX;
    private float originY;
    private static final float radius = 60;


    public Hand(float x, float y, int pivotX, int pivotY) {
        id = idNumber;
        idNumber++;
        originX = x;
        originY = y;
        pivot = new Point(pivotX, pivotY);
        rotateLimit = 35;
        interactionMode = InteractionMode.ROTATING;
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        paint.setColor(Color.LTGRAY);
        canvas.drawCircle(originX, originY, radius, paint);
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
        if (vec[0] > originX - radius && vec[0] < originX + radius &&
                vec[1] > originY - radius && vec[1] < originY + radius) {
            return true;
        }
        return false;
    }

}
