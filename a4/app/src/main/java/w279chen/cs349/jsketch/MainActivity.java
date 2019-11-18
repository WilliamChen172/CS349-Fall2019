package w279chen.cs349.jsketch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.LinearLayout;
import android.content.res.Configuration;
import android.content.res.Resources;

public class MainActivity extends AppCompatActivity {

    private ImageButton selectButton;
    private ImageButton eraseButton;
    private ImageButton rectButton;
    private ImageButton circleButton;
    private ImageButton lineButton;
    private ImageButton colourButton1;
    private ImageButton colourButton2;
    private ImageButton colourButton3;
    private Button colourButton4;

    private boolean isEraseEnabled;
    private boolean isShapeEnabled;

    DrawingView drawingView;
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
        model = Model.getInstance();
        model.addActivity(this);
        isShapeEnabled = true;
        isEraseEnabled = false;
        setup();

    }

    private void setup() {
        selectButton = findViewById(R.id.select_button);
        eraseButton = findViewById(R.id.erase_button);
        rectButton = findViewById(R.id.rect_button);
        circleButton = findViewById(R.id.circle_button);
        lineButton = findViewById(R.id.line_button);
        colourButton1 = findViewById(R.id.colour_button1);
        colourButton2 = findViewById(R.id.colour_button2);
        colourButton3 = findViewById(R.id.colour_button3);
        colourButton4 = findViewById(R.id.colour_button4);

        LinearLayout viewGroup = findViewById(R.id.main_region);
        LinearLayout toolbar = findViewById(R.id.main_toolbar);
        drawingView = new DrawingView(this.getBaseContext());
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            viewGroup.setOrientation(LinearLayout.HORIZONTAL);
            toolbar.setOrientation(LinearLayout.VERTICAL);
            toolbar.setLayoutParams(new LinearLayout.LayoutParams(getScreenWidth()-getScreenHeight()-112, matchParent));
            selectButton.setLayoutParams(new LinearLayout.LayoutParams(matchParent, 200));
            eraseButton.setLayoutParams(new LinearLayout.LayoutParams(matchParent, 200));
            colourButton4.setLayoutParams(new LinearLayout.LayoutParams(matchParent, 150));
            rectButton.setLayoutParams(new LinearLayout.LayoutParams(matchParent, 200));
            circleButton.setLayoutParams(new LinearLayout.LayoutParams(matchParent, 200));
            lineButton.setLayoutParams(new LinearLayout.LayoutParams(matchParent, 200));
            colourButton1.setLayoutParams(new LinearLayout.LayoutParams(matchParent, 150));
            colourButton2.setLayoutParams(new LinearLayout.LayoutParams(matchParent, 150));
            colourButton3.setLayoutParams(new LinearLayout.LayoutParams(matchParent, 150));
            drawingView.setLayoutParams(new LinearLayout.LayoutParams(getScreenHeight() + 112, matchParent));
            System.out.println("Height: " + getScreenHeight() + " Width: " + getScreenWidth());
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        } else {
            // In portrait
            viewGroup.setOrientation(LinearLayout.VERTICAL);
            toolbar.setOrientation(LinearLayout.HORIZONTAL);
            toolbar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getScreenHeight() - getScreenWidth() + 112));
            selectButton.setLayoutParams(new LinearLayout.LayoutParams(200, matchParent));
            eraseButton.setLayoutParams(new LinearLayout.LayoutParams(200, matchParent));
            colourButton4.setLayoutParams(new LinearLayout.LayoutParams(200, matchParent));
            rectButton.setLayoutParams(new LinearLayout.LayoutParams(200, matchParent));
            circleButton.setLayoutParams(new LinearLayout.LayoutParams(200, matchParent));
            lineButton.setLayoutParams(new LinearLayout.LayoutParams(200, matchParent));
            colourButton1.setLayoutParams(new LinearLayout.LayoutParams(200, matchParent));
            colourButton2.setLayoutParams(new LinearLayout.LayoutParams(200, matchParent));
            colourButton3.setLayoutParams(new LinearLayout.LayoutParams(200, matchParent));
            drawingView.setLayoutParams(new LinearLayout.LayoutParams(matchParent, getScreenWidth() - 112));
            System.out.println("Height: " + getScreenHeight() + " Width: " + getScreenWidth());
            View decorView = getWindow().getDecorView();
            // Hide the status bar.
            int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setSelected(DrawingView.Tool.SELECT);
                deselectAll();
                selectButton.setImageResource(R.drawable.select_selected);
            }
        });
        eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEraseEnabled) {
                    drawingView.setSelected(DrawingView.Tool.ERASE);
                    drawingView.eraseSelected();
                }
            }
        });
        rectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShapeEnabled) {
                    drawingView.setSelected(DrawingView.Tool.RECT);
                    deselectAll();
                    rectButton.setImageResource(R.drawable.rect_selected);
                }
            }
        });
        circleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShapeEnabled) {
                    drawingView.setSelected(DrawingView.Tool.CIRCLE);
                    deselectAll();
                    circleButton.setImageResource(R.drawable.circle_selected);
                }
            }
        });
        lineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShapeEnabled) {
                    drawingView.setSelected(DrawingView.Tool.LINE);
                    deselectAll();
                    lineButton.setImageResource(R.drawable.line_selected);
                }
            }
        });
        colourButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setBrush(getColor(R.color.paletteColour1));
            }
        });
        colourButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setBrush(getColor(R.color.paletteColour2));
            }
        });
        colourButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawingView.setBrush(getColor(R.color.paletteColour3));
            }
        });

        viewGroup.addView(drawingView);
    }

    private void deselectAll() {
        selectButton.setImageResource(R.drawable.select_enabled);
        eraseButton.setImageResource(R.drawable.delete_disabled);
        rectButton.setImageResource(R.drawable.rect_enabled);
        circleButton.setImageResource(R.drawable.circle_enabled);
        lineButton.setImageResource(R.drawable.line_enabled);
    }

    void shapeIsSelected() {
        eraseButton.setImageResource(R.drawable.delete_enabled);
        isEraseEnabled = true;
        rectButton.setImageResource(R.drawable.rect_disabled);
        circleButton.setImageResource(R.drawable.circle_disabled);
        lineButton.setImageResource(R.drawable.line_disabled);
        isShapeEnabled = false;
    }

    void shapeIsDeselected() {
        eraseButton.setImageResource(R.drawable.delete_disabled);
        isEraseEnabled = false;
        rectButton.setImageResource(R.drawable.rect_enabled);
        circleButton.setImageResource(R.drawable.circle_enabled);
        lineButton.setImageResource(R.drawable.line_enabled);
        isShapeEnabled = true;
    }

    void updateCurrentColor(int color) {
        colourButton4.setBackgroundColor(color);
    }
}
