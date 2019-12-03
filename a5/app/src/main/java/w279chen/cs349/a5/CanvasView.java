package w279chen.cs349.a5;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

public class CanvasView extends View {

    Paint brush;

    Torso torso;
    Head head;
    UpperArm leftUpper;
    UpperArm rightUpper;
    LowerArm leftLower;
    LowerArm rightLower;
    Hand leftHand;
    Hand rightHand;
    UpperLeg leftUpLeg;
    UpperLeg rightUpLeg;
    LowerLeg leftLowLeg;
    LowerLeg rightLowLeg;
    Foot leftFoot;
    Foot rightFoot;

    Drawable selected;

    int touches;
    float[] scalePt1 = new float[2];
    float[] scalePt2 = new float[2];


    public CanvasView(Context context) {
        super(context);
        setup();

    }

    void setup() {
        torso = new Torso();

        head = new Head();

        leftUpper = new UpperArm(850, 1150, 300, 400, 1100, 350);
        rightUpper = new UpperArm(1410, 1710, 300, 400, 1460, 350);
        leftLower = new LowerArm(600, 900, 300, 400, 850, 350);
        rightLower = new LowerArm(1660,1960, 300, 400, 1710, 350);
        leftHand = new Hand(580, 350, 640, 350);
        rightHand = new Hand(1980, 350, 1920, 350);
        leftLower.addChild(leftHand);
        rightLower.addChild(rightHand);
        leftUpper.addChild(leftLower);
        rightUpper.addChild(rightLower);

        leftUpLeg = new UpperLeg(1100, 1200, 750, 1100, 1150, 750);
        rightUpLeg = new UpperLeg(1360, 1460, 750, 1100, 1410, 750);
        leftLowLeg = new LowerLeg(1100, 1200, 1050, 1350, 1150, 1050);
        rightLowLeg = new LowerLeg(1360, 1460, 1050, 1350, 1410, 1050);
        leftFoot = new Foot(1000, 1200, 1300, 1400, 1150, 1350);
        rightFoot = new Foot(1360, 1560, 1300, 1400, 1410, 1350);
        leftLowLeg.addChild(leftFoot);
        rightLowLeg.addChild(rightFoot);
        leftUpLeg.addChild(leftLowLeg);
        rightUpLeg.addChild(rightLowLeg);

        torso.addChild(head);
        torso.addChild(leftUpper);
        torso.addChild(rightUpper);
        torso.addChild(leftUpLeg);
        torso.addChild(rightUpLeg);

        touches = 1;
        brush = new Paint(Paint.ANTI_ALIAS_FLAG);
        brush.setColor(Color.BLACK);
    }

    public void reset() {
        setup();
        invalidate();
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_DOWN:
                if (event.getPointerCount() == 1) {
                    selected = torso.getDrawableHit(touchX, touchY);
                    if (selected == null) {
                        return true;
                    }
                    selected.handleMouseDownEvent(event);
                }
                if (event.getPointerCount() == 2) {
                    selected = torso.getScaleHit(event.getX(0), event.getY(0), event.getX(1), event.getY(1));
                    if (selected == null) {
                        return false;
                    }
                    selected.handleMultiTouchEvent(event);
                    selected.interactionMode = Drawable.InteractionMode.SCALING;
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (selected != null) {
                    selected.handleMouseDragEvent(event);
                }
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
                if (selected != null) {
                    selected.interactionMode = selected.defaultMode;
                }
                selected = null;
                invalidate();
                return true;

            default:
                return true;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        torso.draw(canvas, brush);
    }

    public void setScalePt1(float x, float y) {
        scalePt1[0] = x;
        scalePt1[1] = y;
    }

    public void setScalePt2(float x, float y) {
        scalePt2[0] = x;
        scalePt2[1] = y;
    }
}
