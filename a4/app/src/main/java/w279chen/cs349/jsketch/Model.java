package w279chen.cs349.jsketch;


import java.util.ArrayList;

class Model {
    // Create static instance of this mModel
    private static final Model ourInstance = new Model();

    static Model getInstance() {
        return ourInstance;
    }

    MainActivity activity;

    ArrayList<Drawable> drawables;
    Drawable selected;

    Model() {
        drawables = new ArrayList<>();
    }

    public void addDrawable(Drawable drawable) {
        drawables.add(drawable);
    }

    public void setSelected(float x, float y) {
        Drawable found = null;
        for (int i = drawables.size() - 1; i >= 0; i--) {
            Drawable candidate = drawables.get(i);
            if (candidate.isSelected(x, y)) {
                found = candidate;
            }
        }
        selected = found;
    }

    public void shapeIsSelected() {
        activity.shapeIsSelected();
    }

    public void shapeIsDeselected() {
        activity.shapeIsDeselected();
    }

    public void updateCurrentColor(int color) {
        activity.updateCurrentColor(color);
    }

    public void addActivity(MainActivity mainActivity) {
        activity = mainActivity;
    }
}