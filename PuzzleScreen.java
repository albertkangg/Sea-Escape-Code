package com.example.congressionalapp2021;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PuzzleScreen extends AppCompatActivity {
    private float x, y, dx, dy;
    final int FREEPLAY = 0;
    TextView coorTouch;
    RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle_layout);
        layout = findViewById(R.id.grid_layout);
        Intent intent = getIntent();
        int level = intent.getIntExtra("Level", 0);
        if(level == FREEPLAY) {

        }
        else {
            ArrayList<String>levels = turtlestorymode.generateLevels();
            String level_board = levels.get(level);
            Set<String> visitedBlocks = new HashSet<>();
            for(String space: level_board.split("")) {
                if(!visitedBlocks.contains(space)) {
                    if (turtlestorymode.odds.contains(space) && turtlestorymode.piece2.contains(space)) {

                    } else if (turtlestorymode.odds.contains(space) && turtlestorymode.piece3.contains(space)) {

                    } else if (turtlestorymode.evens.contains(space) && turtlestorymode.piece2.contains(space)) {

                    } else if (turtlestorymode.evens.contains(space) && turtlestorymode.piece3.contains(space)) {

                    }
                    visitedBlocks.add(space);
                }
            }
        }





        ImageView turtle = findViewById(R.id.turtle);
        coorTouch = findViewById(R.id.coors);
        final ImageView grid = findViewById(R.id.grid);
        int[] gridlocation = new int[2];
        grid.getLocationOnScreen(gridlocation);
        final int gridx = gridlocation[0];
        final int gridy = gridlocation[1];

        turtle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        System.out.println(gridx);
                        x = event.getRawX();
                        //y = event.getRawY();
                        dx = x-view.getX();
                        //dy = y-view.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        System.out.println(event.getRawX()-dx);
                        if(event.getRawX()-dx > gridx && event.getRawX()-dx < 1000) {
                            view.setX(event.getRawX() - dx);
                        }
                        //view.setY(event.getRawY()-dy);
                        break;
                    case MotionEvent.ACTION_UP:
                    {

                    }
                }
                return true;
            }
        });
        turtlestorymode game = new turtlestorymode();
        ArrayList<String> levels = game.generateLevels();
        for(String level : levels) {

        }
        RelativeLayout relativeLayout = findViewById(R.id.grid_layout);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        for (int i = 1; i <= 2; i++) {
//            ImageView imageview = new ImageView(this);
//            imageview.setImageResource();
//            imageview.setLayoutParams(params);
//            imageview.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//            relativeLayout.addView(imageview);
//        }

    }
    
    private boolean viewsOverlap(View v1, View v2) {
        int[] v1_coords = new int[2];
        v1.getLocationOnScreen(v1_coords);
        int v1_w = v1.getWidth();
        int v1_h = v1.getHeight();
        Rect v1_rect = new Rect(v1_coords[0], v1_coords[1], v1_coords[0] + v1_w, v1_coords[1] + v1_h);

        int[] v2_coords = new int[2];
        v2.getLocationOnScreen(v1_coords);
        int v2_w = v2.getWidth();
        int v2_h = v2.getHeight();
        Rect v2_rect = new Rect(v2_coords[0], v2_coords[1], v2_coords[0] + v2_w, v2_coords[1] + v2_h);

        return v1_rect.intersect(v2_rect) || v1_rect.contains(v2_rect) || v2_rect.contains(v1_rect);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int x = (int)event.getX();
        int y = (int)event.getY();
        coorTouch.setText("x: " + x + ", y: " + y);
        return false;
    }
}
