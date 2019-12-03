package w279chen.cs349.a5;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;

import androidx.annotation.Nullable;

public class Torso extends Drawable {
    private static final float originLeft = 1100;
    private static final float originRight = 1460;
    private static final float originTop = 300;
    private static final float originBottom = 800;

    public Torso() {
        id = idNumber;
        idNumber++;
        interactionMode = InteractionMode.DRAGGING;
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        paint.setColor(Color.BLACK);
        canvas.drawRoundRect(originLeft, originTop, originRight, originBottom, 30, 30, paint);
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

}
