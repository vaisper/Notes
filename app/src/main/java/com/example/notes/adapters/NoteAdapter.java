package com.example.notes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.entities.Note;
import com.example.notes.listeners.NotesListeners;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

     private List<Note> notes;
     private NotesListeners notesListeners;

    public NoteAdapter(List<Note> notes,NotesListeners notesListeners) {
        this.notes = notes;
        this.notesListeners = notesListeners;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_conteiner_note,
                        parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.setNote(notes.get(position));
        holder.layoutNote.setOnClickListener(v -> notesListeners.onNoteClicked(notes.get(position),position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }



    static class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle, textDataTime;
        LinearLayout layoutNote;

         NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            init();
        }

        private void init() {
            textTitle = itemView.findViewById(R.id.textTitle);
            textDataTime = itemView.findViewById(R.id.textDataTime);
            layoutNote = itemView.findViewById(R.id.layoutNote);
        }

        void setNote(Note note) {
            textTitle.setText(note.getTitle());
            textDataTime.setText(note.getDateTime());

        }


    }


}
