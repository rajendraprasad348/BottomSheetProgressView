package com.rajendra_prasad.advanced_progress;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


public class ThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
