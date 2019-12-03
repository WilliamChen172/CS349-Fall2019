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
    Model model;
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
    Hand leftFoot;
    Hand rightFoot;
    Drawable selected;


    public CanvasView(Context context) {
        super(context);
        model = Model.getInstance();
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
        leftFoot = new Hand(1150, 1370, 1150, 1320);
        rightFoot = new Hand(1410, 1370, 1410, 1320);
        leftLowLeg.addChild(leftFoot);
        rightLowLeg.addChild(rightFoot);
        leftUpLeg.addChild(leftLowLeg);
        rightUpLeg.addChild(rightLowLeg);
        torso.addChild(head);
        torso.addChild(leftUpper);
        torso.addChild(rightUpper);
        torso.addChild(leftUpLeg);
        torso.addChild(rightUpLeg);
        brush = new Paint(Paint.ANTI_ALIAS_FLAG);
        brush.setColor(Color.BLACK);

    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        super.setOnTouchListener(l);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                selected = torso.getDrawableHit(touchX, touchY);
                if (selected == null) {
                    return false;
                }
                selected.handleMouseDownEvent(event);
                return true;
            case MotionEvent.ACTION_MOVE:
                selected.handleMouseDragEvent(event);
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
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
}
