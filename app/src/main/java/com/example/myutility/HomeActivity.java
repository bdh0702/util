package com.example.myutility;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    public LinearLayout li_profile,li_memo,li_settings,li_calender,li_freeboard,li_paint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        li_profile = findViewById(R.id.li_profile);
        li_memo = findViewById(R.id.li_memo);
        li_settings = findViewById(R.id.li_settings);
        li_calender = findViewById(R.id.li_calender);
        li_freeboard = findViewById(R.id.li_freeboard);
        li_paint = findViewById(R.id.li_paint);
        li_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

        li_memo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,MemoActivity.class);
                startActivity(intent);
            }
        });

        li_calender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,CalenderActivity.class);
                startActivity(intent);
            }
        });

        li_freeboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,FreeBoardActivity.class);
                startActivity(intent);
            }
        });

        li_paint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,PaintActivity.class);
                startActivity(intent);
            }
        });

        li_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });
    }
}
