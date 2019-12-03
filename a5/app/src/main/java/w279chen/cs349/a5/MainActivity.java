package w279chen.cs349.a5;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.LinearLayout;
import android.content.res.Configuration;
import android.content.res.Resources;

public class MainActivity extends AppCompatActivity {

    CanvasView canvasView;
    Model model;

    private static final int matchParent = LinearLayout.LayoutParams.MATCH_PARENT;

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setup();
        model = Model.getInstance();
        model.addActivity(this);
    }

    private void setup() {
        LinearLayout viewGroup = findViewById(R.id.main_region);
        LinearLayout toolbar = findViewById(R.id.main_toolbar);
        canvasView = new CanvasView(this.getBaseContext());
        toolbar.setLayoutParams(new LinearLayout.LayoutParams(matchParent, 188));
        canvasView.setLayoutParams(new LinearLayout.LayoutParams(matchParent, 1500));
        System.out.println("Height: " + getScreenHeight() + " Width: " + getScreenWidth());
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        viewGroup.addView(canvasView);
    }
}

