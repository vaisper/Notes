package com.example.notes.screens;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.example.notes.R;
import com.example.notes.adapters.NoteAdapter;
import com.example.notes.database.NotesDatabase;
import com.example.notes.entities.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Notes";
    public static final int REQUEST_CODE_ADD_NOTE = 1;
    private ImageButton add_Note_Button;
    private NoteAdapter noteAdapter;
    private RecyclerView notesrecyclerView;
    private List<Note> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        add_Note_Button.setOnClickListener(v -> startActivityForResult(
                new Intent(getApplicationContext(), CreateNoteActivity.class), REQUEST_CODE_ADD_NOTE
        ));
        notesList = new ArrayList<>();
        noteAdapter = new NoteAdapter(notesList);
        notesrecyclerView.setAdapter(noteAdapter);
        getAllNotes();

    }

    private void init() {

        add_Note_Button = findViewById(R.id.imageAddNotes);
        notesrecyclerView = findViewById(R.id.NoteRecycleView);
        setrecycleManager();
    }

     private void setrecycleManager(){
        notesrecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        );
         Log.d(TAG, "RecyclerView init finish");
     }

    private void getAllNotes() {
        class GetNotes extends AsyncTask<Void, Void, List<Note>> {

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                if (notesList.size() == 0){
                    notesList.addAll(notes);
                    noteAdapter.notifyDataSetChanged();
                }
                else{ notesList.add(0,notes.get(0));
                noteAdapter.notifyItemInserted(0);
                }
                notesrecyclerView.smoothScrollToPosition(0);
            }

            @Override
            protected List<Note> doInBackground(Void... voids) {
                return NotesDatabase.getDatabase(getApplicationContext()).noteDao().getAllNotes();
            }
        }
        new GetNotes().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK){
            getAllNotes();
        }
    }

}