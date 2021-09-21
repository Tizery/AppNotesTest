package com.example.appnotestest.ui;

import androidx.fragment.app.FragmentManager;

import com.example.appnotestest.R;
import com.example.appnotestest.domain.Note;
import com.example.appnotestest.ui.detail.NoteDetailFragment;
import com.example.appnotestest.ui.edit.EditNoteFragment;
import com.example.appnotestest.ui.info.InfoFragment;
import com.example.appnotestest.ui.notes.NotesListFragment;

public class Router {

    private final FragmentManager fragmentManager;

    public Router(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void showNotesList() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, new NotesListFragment())
                .commit();
    }

    public void showAuth() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, new AuthFragment())
                .commit();
    }

    public void showInfo() {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, new InfoFragment())
                .commit();
    }

    public void showNoteDetails(Note note) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, NoteDetailFragment.newInstance(note))
                .addToBackStack(null)
                .commit();
    }

    public void showEditNote(Note note) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.container, EditNoteFragment.newInstance(note))
                .addToBackStack(null)
                .commit();
    }
}
