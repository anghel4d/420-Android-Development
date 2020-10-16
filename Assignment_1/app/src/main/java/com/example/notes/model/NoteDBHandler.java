package com.example.notes.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.notes.sqlite.DatabaseException;
import com.example.notes.sqlite.Table;
import com.example.notes.sqlite.TableFactory;

import java.util.List;

public class NoteDBHandler extends SQLiteOpenHelper {

    /**
     * Filename to store on the local database (in device storage).
     */
    private static final String DATABASE_FILE_NAME = "note.db";

    /**
     * Field to update with every structural change of the database.
     */
    private static final int DATABASE_VERSION = 2;

    // Fields
    private Table<Note> noteTable;

    public NoteDBHandler(@Nullable Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        noteTable = TableFactory.makeFactory(this, Note.class)
                .withSeedData(SampleData.generateNotes())
                .build();
    }

    public Table<Note> getNoteTable() {
        return noteTable;
    }

    public void delete(Note note) {
        try {
            List<Note> temp1 = noteTable.readAll();
            noteTable.delete(note);
            List<Note> temp2 = noteTable.readAll();
            Log.d("LMAO", "XDDDD");
        } catch (DatabaseException e){
            Log.d("DB ERROR", "Failed to delete element: " + e.getMessage());
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        noteTable.createTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        noteTable.dropTable(sqLiteDatabase);
        noteTable.createTable(sqLiteDatabase);
    }
}