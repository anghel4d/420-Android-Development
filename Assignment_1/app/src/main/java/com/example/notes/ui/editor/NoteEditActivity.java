package com.example.notes.ui.editor;
import java.util.Stack;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.notes.R;
import com.example.notes.model.Category;
import com.example.notes.model.Note;


public class NoteEditActivity extends AppCompatActivity implements View.OnClickListener {

    // Fields
    Stack<Note> history;
    Note currentNote;
    boolean isUndoCall;

    // UI references
    private EditText title;
    private EditText body;
    private EditText reminder;
    // private CircleView color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_note_edit);

        // Initializing fields
        history = new Stack<>();
        currentNote = new Note();

        // Assigning variables from View
        title = (EditText) findViewById(R.id.title_EditText);
        body = (EditText) findViewById(R.id.body_EditText);
        reminder = (EditText) findViewById(R.id.reminder_EditText);

        // Create event listeners
        body.addTextChangedListener(new NoteTextWatcher());
        title.addTextChangedListener(new NoteTextWatcher());

        // Undo button listener
        ImageView undo = findViewById(R.id.undo_Button);
        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undo();
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        /*
        switch (id) {
            case
        }
        */
    }
    private class NoteTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (isUndoCall)
                return;
            history.push(currentNote);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            update();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    /**
     * This function saves the previous Note state to the stack and updates the current one.
     */
    private void update() {
        if (isUndoCall)
            return;
        currentNote = currentNote.clone(); // Don't change the one in stack.
        currentNote.setTitle(title.getText().toString());
        currentNote.setBody(body.getText().toString());
        // currentNote.setReminder();
        currentNote.setCategory(Category.BROWN);
    }

    /**
     * This function reverts the Note to the previous page.
     */
    private void undo() {
        if (history.isEmpty())
            return;

        currentNote = history.pop();
        isUndoCall = true;
        title.setText(currentNote.getTitle());
        body.setText(currentNote.getBody());
        // Color thing
        isUndoCall = false;
    }
}