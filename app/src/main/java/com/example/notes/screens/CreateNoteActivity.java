package com.example.notes.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notes.R;
import com.example.notes.database.NotesDatabase;
import com.example.notes.entities.Note;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText inputNoteTitle, inputNoteText;
    private TextView textDataTime;
    private ImageButton imageBack, imageSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        init();
        clickBackButton();
        textDataTime.setText(new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a",Locale.getDefault())
                .format(new Date()));
        clickSaveButton();

    }

    private void clickSaveButton() {
        imageSave.setOnClickListener(v -> {
            saveNote();
        });
    }

    private void clickBackButton() {

        imageBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void init() {
        inputNoteText = findViewById(R.id.NoteText);
        inputNoteTitle = findViewById(R.id.NoteTitle);
        textDataTime = findViewById(R.id.text_DataTime);
        imageBack = findViewById(R.id.back_button);
        imageSave = findViewById(R.id.done_button);
    }

    private void saveNote() {
        if (inputNoteTitle.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Note title can`t is empty", Toast.LENGTH_SHORT).show();
            return;
        } else if (inputNoteText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Note text can`t is empty", Toast.LENGTH_LONG).show();
            return;
        }
        final Note note = new Note();
        note.setNoteText(inputNoteText.getText().toString());
        note.setTitle(inputNoteTitle.getText().toString());
        note.setDateTime(textDataTime.getText().toString());

        @SuppressLint("StaticalFieldLeak")
        class NoteSave extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                NotesDatabase.getDatabase(getApplicationContext()).noteDao().insertNote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        new NoteSave().execute();
    }

}