package com.example.zujianapplication.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.zujianuiapplication.MainActivity;
import com.example.zujianuiapplication.R;


public class TestSwitchViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_switch_view);
        SwitchView view = findViewById(R.id.sv) ;
       // view.setCanTouchSwitch(false);
        Button button = findViewById(R.id.test) ;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestSwitchViewActivity.this,MainActivity.class) ;
                startActivity(intent);
            }
        });
    }
}