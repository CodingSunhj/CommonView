package com.shj.commonview.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.commonutils.BaseActivity;
import com.commonview.textview.ButtonTextView;
import com.shj.commonview.R;

public class MainActivity extends BaseActivity {
    private ButtonTextView buttonTextView,btnRecyclerView,btnMultiItemRv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonTextView = $(R.id.btnTv);
        btnRecyclerView = $(R.id.btnRecyclerView);
        btnMultiItemRv = $(R.id.BtnMultiItemViewRv);
        buttonTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "click", Toast.LENGTH_SHORT).show();
            }
        });
        btnRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerVewActivity.start(MainActivity.this);
            }
        });
        btnMultiItemRv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiTEMRvActivity.start(MainActivity.this);
            }
        });

    }
}
