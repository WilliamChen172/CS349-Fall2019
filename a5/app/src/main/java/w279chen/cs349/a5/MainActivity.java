package w279chen.cs349.a5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
    Button reset;
    Button about;

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
    }

    private void setup() {
        LinearLayout viewGroup = findViewById(R.id.main_region);
        LinearLayout toolbar = findViewById(R.id.main_toolbar);
        canvasView = new CanvasView(this.getBaseContext());
        reset = findViewById(R.id.reset);
        about = findViewById(R.id.about);
        toolbar.setLayoutParams(new LinearLayout.LayoutParams(matchParent, 188));
        canvasView.setLayoutParams(new LinearLayout.LayoutParams(matchParent, 1500));
        reset.setLayoutParams(new LinearLayout.LayoutParams(300, matchParent));
        about.setLayoutParams(new LinearLayout.LayoutParams(300, matchParent));
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        viewGroup.addView(canvasView);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvasView.reset();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.showAbout();
            }
        });
    }

    public void showAbout()
    {
        AlertDialog ad = new AlertDialog.Builder(MainActivity.this).create();
        ad.setCancelable(true);
        ad.setTitle("Scene Graph");
        ad.setMessage("William Chen\n20679103");
        ad.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            } });
        ad.show();
    }
}

