package w279chen.cs349.a5;


import java.util.ArrayList;

class Model {
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

    public void addActivity(MainActivity mainActivity) {
        activity = mainActivity;
    }
}
