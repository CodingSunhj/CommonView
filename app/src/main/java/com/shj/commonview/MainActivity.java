package com.shj.commonview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.view.shj.commonview.ButtonTextView;

public class MainActivity extends AppCompatActivity {
    private ButtonTextView buttonTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonTextView = (ButtonTextView) this.findViewById(R.id.btnTv);
        buttonTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
