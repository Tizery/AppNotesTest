package com.example.appnotestest.ui.notes;

import com.example.appnotestest.domain.Callback;
import com.example.appnotestest.domain.Note;
import com.example.appnotestest.domain.NotesRepository;

import java.util.List;

public class NotesListPresenter {

    private NotesListView view;

    private NotesRepository repository;

    public NotesListPresenter(NotesListView view, NotesRepository repository) {
        this.view = view;
        this.repository = repository;
    }

    public void requestNotes() {
        view.showProgress();

        repository.getNotes(new Callback<List<Note>>() {
            @Override
            public void onSuccess(List<Note> data) {
                view.showNotes(data);

                view.hideProgress();
            }
        });
    }

    public void addNote(String title, String imageUrl) {
        view.showProgress();

        repository.addNote(title, imageUrl, new Callback<Note>() {
            @Override
            public void onSuccess(Note data) {
                view.hideProgress();
                view.onNoteAdded(data);
            }
        });
    }

    public void removeNote(Note selectedNote) {
        view.showProgress();

        repository.removeNote(selectedNote, new Callback<Void>() {
            @Override
            public void onSuccess(Void data) {
                view.hideProgress();
                view.onNoteRemoved(selectedNote);
            }
        });
    }
}
