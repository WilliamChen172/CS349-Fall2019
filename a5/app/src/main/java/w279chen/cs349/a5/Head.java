package w279chen.cs349.a5;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Matrix;
import android.graphics.Point;

public class Head extends Drawable {
    private static final float originX = 1280;
    private static final float originY = 200;
    private static final float radius = 100;

    public Head() {
        id = idNumber;
        idNumber++;
        pivot = new Point(1280, 300);
        rotateLimit = 50;
        interactionMode = InteractionMode.ROTATING;
    }

    @Override
    public void drawSelf(Canvas canvas, Paint paint) {
        paint.setColor(Color.DKGRAY);
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
//        System.out.println(fullMatrix);
//        System.out.println(inverseMatrix);
//        System.out.println("X: " + vec[0] + " Y: " + vec[1]);
        if (vec[0] > originX - radius && vec[0] < originX + radius &&
            vec[1] > originY - radius && vec[1] < originY + radius) {//&&
            //vec[0] + vec[1] > originX + originY - 2*radius &&
            //vec[0] + vec[1] < originX + originY + 2*radius) {
            //System.out.println("Hit test passed");
            return true;
        }
        return false;
    }

}
