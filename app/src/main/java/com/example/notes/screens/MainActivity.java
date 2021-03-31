package com.example.notes.screens;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import com.example.notes.R;
import com.example.notes.adapters.NoteAdapter;
import com.example.notes.database.NotesDatabase;
import com.example.notes.entities.Note;
import com.example.notes.listeners.NotesListeners;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NotesListeners {

    public static final String TAG = "Notes";
    public static final int REQUEST_CODE_ADD_NOTE = 1;
    public static final int REQUEST_CODE_UPDATE_NOTE = 2;
    public static final int REQUEST_CODE_SHOW_NOTE = 3;
    private ImageButton add_Note_Button;
    private NoteAdapter noteAdapter;
    private RecyclerView notesrecyclerView;
    private List<Note> notesList;

    private int noteClickedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        add_Note_Button.setOnClickListener(v -> startActivityForResult(
                new Intent(getApplicationContext(), CreateNoteActivity.class), REQUEST_CODE_ADD_NOTE
        ));
        notesList = new ArrayList<>();
        noteAdapter = new NoteAdapter(notesList,this);
        notesrecyclerView.setAdapter(noteAdapter);
        getAllNotes(REQUEST_CODE_SHOW_NOTE, false);

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

    private void getAllNotes(final int request_code, final boolean isNoteDeleted) {

        @SuppressLint("StaticFieldLeak")
        class GetNotes extends AsyncTask<Void, Void, List<Note>> {

            @Override
            protected void onPostExecute(List<Note> notes) {
                super.onPostExecute(notes);
                if (request_code == REQUEST_CODE_SHOW_NOTE){
                    notesList.addAll(notes);
                    noteAdapter.notifyDataSetChanged();
                } else if (request_code == REQUEST_CODE_ADD_NOTE) {
                    notesList.add(0, notes.get(0));
                    noteAdapter.notifyItemInserted(0);
                    notesrecyclerView.smoothScrollToPosition(0);
                } else if (request_code == REQUEST_CODE_UPDATE_NOTE){
                    notesList.remove(noteClickedPosition);
                    if(isNoteDeleted) {
                        noteAdapter.notifyItemRemoved(noteClickedPosition);
                    }else {
                        notesList.add(noteClickedPosition,notes.get(noteClickedPosition));
                        noteAdapter.notifyItemChanged(noteClickedPosition);
                    }
                }
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
            getAllNotes(REQUEST_CODE_ADD_NOTE,false);
        } else if (requestCode == REQUEST_CODE_UPDATE_NOTE && resultCode ==RESULT_OK){
            if (data!= null){
                getAllNotes(REQUEST_CODE_UPDATE_NOTE,data.getBooleanExtra("isNoteDeleted",false));
            }

        }
    }

    @Override
    public void onNoteClicked(Note note, int position) {
        noteClickedPosition = position;
        Intent intent = new Intent(getApplicationContext(),CreateNoteActivity.class);
        intent.putExtra("isViewOrUpdate",true);
        intent.putExtra("note",note);
        startActivityForResult(intent,REQUEST_CODE_UPDATE_NOTE);
    }
}