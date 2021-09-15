package com.example.appnotestest.ui.notes;

import com.example.appnotestest.domain.Note;

import java.util.List;

public interface NotesListView {

    void showNotes(List<Note> notes);

    void showProgress();

    void hideProgress();

    void onNoteAdded(Note note);

    void onNoteRemoved(Note selectedNote);
}
