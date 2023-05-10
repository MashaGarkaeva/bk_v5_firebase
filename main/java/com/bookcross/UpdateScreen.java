package com.bookcross;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class UpdateScreen extends AppCompatActivity {
    public TextView textUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        textUpdate = findViewById(R.id.text_update);
        String position = getIntent().getExtras().getString("book");

        textUpdate.setText(position);
    }
}