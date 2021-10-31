package com.example.congressionalapp2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LevelSelect extends AppCompatActivity {
    Button returnToMain, switchActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levelselect);
        returnToMain = findViewById(R.id.Back_Button);
        returnToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        switchActivity = findViewById(R.id.Start_Story);
        switchActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchActivties();
            }
        });
    }

    private void switchActivties() {
        Intent newActivity = new Intent(this, StoryMode.class);
        startActivity(newActivity);
    }
}
