package com.example.notes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.entities.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

     private List<Note> notes;

    public NoteAdapter(List<Note> notes) {
        this.notes = notes;
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

         NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            init();
        }

        private void init() {
            textTitle = itemView.findViewById(R.id.textTitle);
            textDataTime = itemView.findViewById(R.id.textDataTime);
        }

        void setNote(Note note) {
            textTitle.setText(note.getTitle());
            textDataTime.setText(note.getDateTime());

        }


    }


}
