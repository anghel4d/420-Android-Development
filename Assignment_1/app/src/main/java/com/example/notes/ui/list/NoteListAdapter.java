package com.example.notes.ui.list;
import com.example.notes.R;
import com.example.notes.model.Note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private List<Note> data;

    public NoteListAdapter(List<Note> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_note, parent, false);
        NoteViewHolder holder = new NoteViewHolder(root);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.set(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * Filters and sorting
     */
    public void sort() {

    }

    public void remove() {

    }

    // Creation Date: Earliest to latest

    // Category: Colors

    // Reminder: Closest reminder to furthest, then the ones without reminder at the bottom

    // Title : Alphabetical

    // Last modified: Most recently modified to latest
}
