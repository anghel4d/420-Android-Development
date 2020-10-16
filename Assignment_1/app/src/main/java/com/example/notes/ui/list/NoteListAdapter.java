package com.example.notes.ui.list;
import com.example.notes.R;
import com.example.notes.model.Note;
import com.example.notes.model.NoteDBHandler;
import com.example.notes.sqlite.DatabaseException;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

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
        NoteViewHolder holder = new NoteViewHolder(root, this);
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

    /***
     * Handles sorting of Note items given various different modes.
     * @param sortingMode
     */
    public void sort(String sortingMode) {
        switch (sortingMode) {
            case "Title":
                sortNotesByTitle();
                break;
            case "Creation Date":
                sortNotesByCreationDate();
                break;
            case "Last Modified":
                sortNotesByLastModified();
                break;
            case "Reminder":
                sortNotesByReminder();
                break;
            case "Category":
                sortNotesByCategory();
                break;
        }

        this.notifyDataSetChanged();
    }

    /***
     * Sorts by title in alphabetical order.
     */
    private void sortNotesByTitle() {
        Collections.sort(data, new Comparator<Note>() {
            @Override
            public int compare(Note note1, Note note2) {
                return note1.getTitle().compareTo(note2.getTitle());
            }
        });
    }

    /***
     * Sorts notes from most earliest to latest.
     */
    private void sortNotesByCreationDate() {
        Collections.sort(data, new Comparator<Note>() {
            @Override
            public int compare(Note note1, Note note2) {
                return note1.getCreated().compareTo(note2.getCreated());
            }
        });
    }

    /***
     * Sorts notes by most recent to oldest edit.
     */
    private void sortNotesByLastModified() {
        Collections.sort(data, new Comparator<Note>() {
            @Override
            public int compare(Note note1, Note note2) {
                return note2.getModified().compareTo(note1.getModified());
            }
        });
    }

    /***
     * Sorts notes by reminder, with the most recent ones first and all nulls at the end.
     */
    private void sortNotesByReminder() {
        Collections.sort(data, new Comparator<Note>() {
            @Override
            public int compare(Note note1, Note note2) {

                if (note1.getReminder() == null)
                    return (note2.getReminder() == null) ? 0 : 1;

                if (note2.getReminder() == null)
                    return -1;

                return note2.getReminder().compareTo(note1.getReminder());
            }
        });
    }

    /***
     * Arbitrarily sort based off of category color's integer value.
     */
    private void sortNotesByCategory() {
        Collections.sort(data, new Comparator<Note>() {
            @Override
            public int compare(Note note1, Note note2) {
                return note1.getCategory().compareTo(note2.getCategory());
            }
        });
    }

    /***
     * Removes the Note element specified by pos from Adapter memory and from sqlite Database.
     * @param context
     * @param pos
     */
    public void remove(Context context, int pos) {
        // Remove data item from the database.
        Note temp = data.get(pos);
        NoteDBHandler handler = new NoteDBHandler(context);
        handler.delete(temp);

        // Remove data item from memory.
        data.remove(pos);

        // Update display and stop showing removed item.
        this.notifyItemRemoved(pos);
    }
}
