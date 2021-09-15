package com.example.appnotestest.domain;

import java.util.List;

public interface NotesRepository {

    void getNotes(Callback<List<Note>> callback);

    void addNote(String title, String image, Callback<Note> callback);

    void removeNote(Note note, Callback<Void> callback);
}