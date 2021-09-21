package com.example.appnotestest.domain;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class FireStoreNotesRepository implements NotesRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String NOTES = "notes";
    private static final String TITLE = "title";
    private static final String IMAGE_URL = "imageUrl";
    private static final String CREATED_AT = "createdAt";


    @Override
    public void getNotes(Callback<List<Note>> callback) {
        db.collection(NOTES)
                .orderBy(CREATED_AT, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Note> result = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String title = document.getString(TITLE);
                                String imageUrl = document.getString(IMAGE_URL);
                                long timeStamp = document.getDate(CREATED_AT).getTime();
                                result.add(new Note(document.getId(), title, imageUrl, new Date(timeStamp)));

                            }
                            callback.onSuccess(result);
                        } else {

                        }
                    }

                });
    }

    @Override
    public void addNote(String title, String image, Callback<Note> callback) {
        HashMap<String, Object> data = new HashMap<>();
        Date createdAt = new Date();
        data.put(TITLE, title);
        data.put(IMAGE_URL, image);
        data.put(CREATED_AT, createdAt);

        db.collection(NOTES)
                .add(data)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            String noteId = task.getResult().getId();
                            callback.onSuccess(new Note(noteId, title, image, createdAt));
                        }
                    }
                });

    }

    @Override
    public void removeNote(Note note, Callback<Void> callback) {

        db.collection(NOTES)
                .document(note.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        callback.onSuccess(unused);
                    }
                });

    }
}
