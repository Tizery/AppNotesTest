package com.example.appnotestest.domain;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class NotesRepositoryImpl implements NotesRepository {

    public static final NotesRepository INSTANCE = new NotesRepositoryImpl();
    private final ArrayList<Note> res = new ArrayList<>();
    private final Handler handler = new Handler(Looper.getMainLooper());

    public NotesRepositoryImpl() {
        res.add(new Note("idApple", "Яблоко", "https://upload.wikimedia.org/wikipedia/commons/1/15/Red_Apple.jpg"));
        res.add(new Note("idOrange", "Апельсин", "https://upload.wikimedia.org/wikipedia/commons/c/c4/Orange-Fruit-Pieces.jpg"));
        res.add(new Note("idBanana", "Банан", "https://upload.wikimedia.org/wikipedia/commons/6/69/Banana_%28white_background%29.jpg"));
        res.add(new Note("idKiwi", "Киви", "https://upload.wikimedia.org/wikipedia/commons/d/d3/Kiwi_aka.jpg"));
        res.add(new Note("idPeach", "Персик", "https://upload.wikimedia.org/wikipedia/commons/f/f5/Cross_sections_of_peach.jpg"));
    }
//    https://images.freeimages.com/images/large-previews/241/night-fog-1521028.jpg

    @Override
    public void getNotes(Callback<List<Note>> callback) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(res);
                    }
                });
            }
        }).start();
    }

    @Override
    public void addNote(String title, String image, Callback<Note> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Note result = new Note(UUID.randomUUID().toString(), title, image);

                res.add(result);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(result);
                    }
                });
            }
        }).start();
    }

    @Override
    public void removeNote(Note note, Callback<Void> callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                res.remove(note);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(null);
                    }
                });
            }
        }).start();
    }
}
