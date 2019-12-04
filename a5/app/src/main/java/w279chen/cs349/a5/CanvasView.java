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

    UpperArm arm3;
    UpperArm arm4;
    UpperArm arm5;
    UpperArm arm6;
    UpperArm arm7;
    UpperArm arm8;
    LowerArm low3;
    LowerArm low4;
    LowerArm low5;
    LowerArm low6;
    LowerArm low7;
    LowerArm low8;
    Hand hand3;
    Hand hand4;
    Hand hand5;
    Hand hand6;
    Hand hand7;
    Hand hand8;

    int touches;
    float[] scalePt1 = new float[2];
    float[] scalePt2 = new float[2];
    boolean isHuman;

    public CanvasView(Context context) {
        super(context);
        isHuman = true;
        setup();

    }

    void setupSecond() {
        torso = new Torso();

        head = new Head();


        leftUpper = new UpperArm(850, 1150, 300, 400, 1100, 350);
        rightUpper = new UpperArm(1410, 1710, 300, 400, 1460, 350);
        leftLower = new LowerArm(600, 900, 300, 400, 850, 350);
        rightLower = new LowerArm(1660,1960, 300, 400, 1710, 350);
        leftHand = new Hand(580, 350, 640, 350);
        rightHand = new Hand(1980, 350, 1920, 350);

        arm3 = new UpperArm(850, 1150, 450, 550, 1100, 500);
        arm4 = new UpperArm(1410, 1710, 450, 550, 1460, 500);
        arm5 = new UpperArm(850, 1150, 600, 700, 1100, 650);
        arm6 = new UpperArm(1410, 1710, 600, 700, 1460, 650);
        arm7 = new UpperArm(850, 1150, 750, 850, 1100, 800);
        arm8 = new UpperArm(1410, 1710, 750, 850, 1460, 800);
        low3 = new LowerArm(600, 900, 450, 550, 850, 500);
        low4 = new LowerArm(1660,1960, 450, 550, 1710, 500);
        low5 = new LowerArm(600, 900, 600, 700, 850, 650);
        low6 = new LowerArm(1660,1960, 600, 700, 1710, 650);
        low7 = new LowerArm(600, 900, 750, 850, 850, 800);
        low8 = new LowerArm(1660,1960, 750, 850, 1710, 800);
        hand3 = new Hand(580, 500, 640, 500);
        hand4 = new Hand(1980, 500, 1920, 500);
        hand5 = new Hand(580, 650, 640, 650);
        hand6 = new Hand(1980, 650, 1920, 650);
        hand7 = new Hand(580, 800, 640, 800);
        hand8 = new Hand(1980, 800, 1920, 800);

        low3.addChild(hand3);
        low4.addChild(hand4);
        arm3.addChild(low3);
        arm4.addChild(low4);


        low5.addChild(hand5);
        low6.addChild(hand6);
        arm5.addChild(low5);
        arm6.addChild(low6);


        low7.addChild(hand7);
        low8.addChild(hand8);
        arm7.addChild(low7);
        arm8.addChild(low8);

        leftLower.addChild(leftHand);
        rightLower.addChild(rightHand);
        leftUpper.addChild(leftLower);
        rightUpper.addChild(rightLower);

        torso.addChild(head);
        torso.addChild(leftUpper);
        torso.addChild(rightUpper);
        torso.addChild(arm3);
        torso.addChild(arm4);

        torso.addChild(arm5);
        torso.addChild(arm6);

        torso.addChild(arm7);
        torso.addChild(arm8);

        touches = 1;
        brush = new Paint(Paint.ANTI_ALIAS_FLAG);
        brush.setColor(Color.BLACK);
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
        if (isHuman) {
            setup();
        } else {
            setupSecond();
        }
        invalidate();
    }

    public void switchDoll() {
        if (isHuman) {
            setupSecond();
            isHuman = false;
            invalidate();
        } else {
            setup();
            isHuman = true;
            invalidate();
        }
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
