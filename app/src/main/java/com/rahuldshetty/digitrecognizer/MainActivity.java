package com.rahuldshetty.digitrecognizer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SimpleDrawingView simpleDrawingView;
    Button button,predict;
    TextView textView;
    Classifier clf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        simpleDrawingView = findViewById(R.id.view);
        button = findViewById(R.id.clearBtn);
        predict = findViewById(R.id.button3);
        textView = findViewById(R.id.textView);
        clf = new Classifier(getApplicationContext(),MainActivity.this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleDrawingView.clear();
            }
        });
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Result res = clf.classify(simpleDrawingView.getBitmap());
                textView.setText( "Predicted:" + res.getNumber() + "\nProbability:" + res.getProbability() + "\nTime:"+res.getTimeCost());
            }
        });
    }
}
