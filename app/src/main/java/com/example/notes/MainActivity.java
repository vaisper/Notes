package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_ADD_NOTE = 1;
    private ImageButton add_Note_Button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        add_Note_Button.setOnClickListener(v -> {
            startActivityForResult(
                    new Intent(getApplicationContext(),CreateNoteActivity.class),REQUEST_CODE_ADD_NOTE
            );
        });
    }

    private void init(){
        add_Note_Button = findViewById(R.id.imageAddNotes);
    }

}