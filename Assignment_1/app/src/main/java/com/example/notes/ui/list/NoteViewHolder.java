package com.example.notes.ui.list;
import com.example.notes.R;

import android.content.res.Resources;
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

    private final View itemView;
    private Note note;

    private final LinearLayout container;
    private final TextView titleTextView;
    private final TextView contentTextView;
    private final TextView dateTextView;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;

        container = itemView.findViewById(R.id.noteContainerLinearLayout);
        titleTextView = itemView.findViewById(R.id.title_TextView);
        contentTextView = itemView.findViewById(R.id.content_TextView);
        dateTextView = itemView.findViewById(R.id.date_TextView);
    }

    public void set(Note note) {
        this.note = note;
        titleTextView.setText((note.getTitle()));
        contentTextView.setText(note.getBody());
        if(note.getReminder() != null)
            dateTextView.setText(FORMATTER.format(note.getReminder()));
        else
            dateTextView.setText(null);
        changeColors();
    }

    private void changeColors() {
        Resources res = itemView.getResources();
        int colorCode = 0;
        if (note.getCategory() != null)
            colorCode = note.getCategory().getColorId();
        container.setBackgroundResource(colorCode);
        dateTextView.setBackgroundResource(colorCode);
    }
}
