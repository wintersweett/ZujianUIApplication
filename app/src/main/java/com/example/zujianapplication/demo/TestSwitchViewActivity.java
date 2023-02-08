package com.example.zujianapplication.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.zujianuiapplication.MainActivity;
import com.example.zujianuiapplication.R;


public class TestSwitchViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_switch_view);
        SwitchView sv = findViewById(R.id.sv) ;
        sv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isopened = sv.isOpened() ;
            }
        });
        sv.setOnStateChangedListener(new SwitchView.OnStateChangedListener() {
            @Override
            public void toggleToOn(SwitchView view) {
                view.toggleSwitch(true);
            }

            @Override
            public void toggleToOff(SwitchView view) {
                view.toggleSwitch(false);

            }
        });
        Button bt1 = findViewById(R.id.bt1) ;
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sv.isCanTouch()){
                    sv.setCanTouchSwitch(false);
                    sv.setAlpha(0.25f);
                }else {
                    sv.setCanTouchSwitch(true);
                    sv.setAlpha(1);
                }

            }
        });
    }
}