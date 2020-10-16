package com.example.notes.ui.list;
import com.example.notes.R;
import com.example.notes.model.NoteDBHandler;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.model.Note;
import com.example.notes.model.SampleData;
import com.example.notes.sqlite.DatabaseException;

import java.util.ArrayList;
import java.util.List;

public class NoteListFragment extends Fragment {

    private List<Note> allData;
    private NoteListAdapter noteListAdapter;
    private Spinner userChoiceSpinner;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initializing spinner items
        userChoiceSpinner = (Spinner) view.findViewById(R.id.filterSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.filter_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userChoiceSpinner.setAdapter(adapter);

        // Setting up spinner Event Listener
        userChoiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String userChoice = parent.getItemAtPosition(pos).toString();
                noteListAdapter.sort(userChoice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Nothing to do.
            }
        });

        // Get database data into memory
        allData = new ArrayList<>();
        try {
            NoteDBHandler dbh = new NoteDBHandler(getContext());
            allData.addAll(dbh.getNoteTable().readAll());
        } catch (DatabaseException e) {
            Log.d("DB ERROR", "Couldn't load data:" + e.getMessage());
        }

        // Initialize the recyclerview
        RecyclerView rv = view.findViewById(R.id.noteListRecyclerView);
        noteListAdapter = new NoteListAdapter(allData);
        rv.setAdapter(noteListAdapter);
        GridLayoutManager glm = new GridLayoutManager(getContext(), 2);
        rv.setLayoutManager(glm);
    }
}