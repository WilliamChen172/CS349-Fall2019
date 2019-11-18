package w279chen.cs349.jsketch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {

    enum Tool {
        SELECT,
        ERASE,
        RECT,
        CIRCLE,
        LINE,
        COLOUR1,
        COLOUR2,
        COLOUR3
    }

    Drawable drawable;
    Tool selected;
    Paint brush;
    int selectedColor;


    Drawable selectFrame;
    Paint select_brush;
    float selectedX;
    float selectedY;

    Model model;

    public DrawingView(Context context) {
        super(context);
        model = Model.getInstance();
        selected = Tool.SELECT;

        brush = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectedColor = getResources().getColor(R.color.paletteColour1, null);
        brush.setColor(selectedColor);

        select_brush = new Paint(Paint.ANTI_ALIAS_FLAG);
        select_brush.setColor(Color.BLACK);
        select_brush.setStyle(Paint.Style.STROKE);
        select_brush.setStrokeWidth(5);
    }

    public void setSelected(Tool selected) {
        this.selected = selected;
    }

    public void setBrush(int color) {
        selectedColor = color;
        brush.setColor(selectedColor);
        if (drawable != null) {
            model.drawables.get(model.drawables.indexOf(model.selected)).setColor(selectedColor);
            invalidate();
        }
    }

    public void eraseSelected() {
        if (drawable != null) {
            model.drawables.remove(model.selected);
            model.selected = null;
            selectFrame = null;
            drawable = null;
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

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                switch (selected) {
                    case SELECT:
                        model.setSelected(touchX, touchY);
                        if (model.selected != null) {
                            model.shapeIsSelected();
                            model.updateCurrentColor(model.selected.color);
                            selectedX = touchX;
                            selectedY = touchY;
                            drawable = model.selected;
                            if (drawable instanceof Circle) {
                                selectFrame = new Rectangle(drawable.startx - ((Circle) drawable).radius,
                                        drawable.starty - ((Circle) drawable).radius, Color.BLACK);
                                selectFrame.setEndPoint(drawable.startx + ((Circle) drawable).radius,
                                        drawable.starty + ((Circle) drawable).radius);
                            } else {
                                selectFrame = new Rectangle(drawable.startx, drawable.starty, Color.BLACK);
                                selectFrame.setEndPoint(drawable.endx, drawable.endy);
                            }
                        } else {
                            selectFrame = null;
                            drawable = null;
                            model.shapeIsDeselected();
                            model.updateCurrentColor(Color.TRANSPARENT);
                        }
                        invalidate();
                        break;
                    case RECT:
                        drawable = new Rectangle(touchX, touchY, selectedColor);
                        break;
                    case CIRCLE:
                        drawable = new Circle(touchX, touchY, selectedColor);
                        break;
                    case LINE:
                        drawable = new Line(touchX, touchY, selectedColor);
                        break;
                    default:
                        break;
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                if (selected == Tool.RECT || selected == Tool.CIRCLE || selected == Tool.LINE) {
                    drawable.setEndPoint(touchX, touchY);
                    invalidate();
                } else if (selected == Tool.SELECT) {
                    if (selectFrame != null) {
                        float difx = touchX-selectedX;
                        float dify = touchY-selectedY;
                        model.drawables.get(model.drawables.indexOf(model.selected)).translate(difx, dify);
                        selectFrame.translate(difx, dify);
                        selectedX = touchX;
                        selectedY = touchY;
                        invalidate();
                    }
                }
                return true;
            case MotionEvent.ACTION_UP:
                if (selected == Tool.RECT || selected == Tool.CIRCLE || selected == Tool.LINE) {
                    drawable.setEndPoint(touchX, touchY);
                    model.addDrawable(drawable);
                    drawable = null;
                    invalidate();
                } else if (selected == Tool.SELECT) {
                    if (selectFrame != null) {
                        float difx = touchX-selectedX;
                        float dify = touchY-selectedY;
                        model.drawables.get(model.drawables.indexOf(model.selected)).translate(difx, dify);
                        selectFrame.translate(difx, dify);
                        selectedX = touchX;
                        selectedY = touchY;
                        invalidate();
                    }
                }
                return true;
            default:
                return true;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Drawable drawable: model.drawables) {
            drawable.draw(canvas, brush);
        }
        if (drawable != null) {
            drawable.draw(canvas, brush);
        }
        if (selectFrame != null) {
            selectFrame.draw(canvas, select_brush);
        }
    }
}
