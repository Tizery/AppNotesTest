package com.example.appnotestest.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.appnotestest.R;
import com.example.appnotestest.domain.Note;

public class NoteDetailFragment extends Fragment {

    public NoteDetailFragment() {
        // Required empty public constructor
    }

    public static NoteDetailFragment newInstance(Note note) {
        NoteDetailFragment fragment = new NoteDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_detail, container, false);
    }
}