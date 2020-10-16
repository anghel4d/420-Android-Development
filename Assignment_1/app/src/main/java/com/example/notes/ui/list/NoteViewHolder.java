package com.example.notes.ui.list;
import com.example.notes.R;

import android.content.res.Resources;
import android.graphics.Rect;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.model.Note;

import java.text.SimpleDateFormat;

public class NoteViewHolder extends RecyclerView.ViewHolder {

    private static final SimpleDateFormat FORMATTER;

    static {
        FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    }

    private final NoteListAdapter noteAdapter;

    private final LinearLayout container;
    private final TextView titleTextView;
    private final TextView contentTextView;
    private final TextView dateTextView;

    public NoteViewHolder(@NonNull final View itemView, NoteListAdapter adapter) {
        super(itemView);
        noteAdapter = adapter;

        // Set variables from viewIds
        container = itemView.findViewById(R.id.noteContainerLinearLayout);
        titleTextView = itemView.findViewById(R.id.title_TextView);
        contentTextView = itemView.findViewById(R.id.content_TextView);
        dateTextView = itemView.findViewById(R.id.date_TextView);

        // Create Action Mode event listener
        itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(final View view) {
                view.getRootView().startActionMode(new ActionMode.Callback2() {
                    @Override
                    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                        MenuInflater inflater = actionMode.getMenuInflater();
                        inflater.inflate(R.menu.menu_action_mode, menu);
                        return true;
                    }

                    @Override
                    public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                        return false;
                    }

                    @Override
                    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.action_reminderMenuItem:
                                // Some datetime shit idk
                                break;
                            case R.id.action_trashMenuItem:
                                noteAdapter.remove(view.getContext(), getAdapterPosition());
                                break;
                            case R.id.action_closeMenuItem:
                                break;
                        }
                        actionMode.finish();
                        return true;
                    }

                    @Override
                    public void onDestroyActionMode(ActionMode actionMode) {
                    }
                });

                return false;
            }
        });
    }

    public void set(Note note) {
        titleTextView.setText((note.getTitle()));
        contentTextView.setText(note.getBody());
        if(note.isHasReminder())
            dateTextView.setText(FORMATTER.format(note.getReminder()));
        changeColors(note);
    }

    private void changeColors(Note note) {
        int colorCode;
        colorCode = note.getCategory().getColorId();
        container.setBackgroundResource(colorCode);
        dateTextView.setBackgroundResource(colorCode);
    }
}
