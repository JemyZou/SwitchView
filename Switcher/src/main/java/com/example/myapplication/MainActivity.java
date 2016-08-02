package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import listener.OnSwitchListener;
import view.SwitcherView;

public class MainActivity extends AppCompatActivity {

    private SwitcherView switchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSwitchView();
    }

    private void initSwitchView() {
        switchView = (SwitcherView) findViewById(R.id.switch_view);
        switchView.setOnSwitchListener(new OnSwitchListener() {
            @Override
            public void onSwitch(boolean isOpened) {
                Toast.makeText(MainActivity.this,isOpened?"打开":"关闭",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
