package com.example.appnotestest.ui.notes;

import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appnotestest.R;
import com.example.appnotestest.domain.Note;
import com.example.appnotestest.domain.NotesRepositoryImpl;
import com.example.appnotestest.ui.Router;
import com.example.appnotestest.ui.RouterHolder;
import com.example.appnotestest.ui.edit.EditNoteFragment;

import java.util.Collections;
import java.util.List;
import java.util.UUID;


public class NotesListFragment extends Fragment implements NotesListView {

    private NotesListPresenter presenter;

    private NotesAdapter adapter;

    private ProgressBar progressBar;

    private RecyclerView notesList;

    private Note selectedNote;

    private Router router;

    private boolean wasNotesRequested;

    public NotesListFragment() {
        super(R.layout.fragment_notes_list);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof RouterHolder) {
            router = ((RouterHolder) context).getRouter();
        } else if (getParentFragment() instanceof RouterHolder) {
            router = ((RouterHolder) getParentFragment()).getRouter();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new NotesListPresenter(this, NotesRepositoryImpl.INSTANCE);

        adapter = new NotesAdapter(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getParentFragmentManager().setFragmentResultListener(EditNoteFragment.KEY_NOTE_RESULT, getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                Note note = result.getParcelable(EditNoteFragment.ARG_NOTE);

                int index = adapter.updateNote(note);

                adapter.notifyItemChanged(index);
            }
        });

        adapter.setListener(new NotesAdapter.OnNoteClickedListener() {
            @Override
            public void onNoteClicked(Note note) {

                if (router != null) {
                    router.showNoteDetails(note);
                }
            }
        });

        adapter.setLongClickListener(new NotesAdapter.OnNoteLongClickedListener() {
            @Override
            public void onNoteLongClicked(Note note) {
                selectedNote = note;
            }
        });

        progressBar = view.findViewById(R.id.progress);

        notesList = view.findViewById(R.id.notes_list);
        notesList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        notesList.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.bg_separator));
        notesList.addItemDecoration(itemDecoration);

        DefaultItemAnimator animator = new DefaultItemAnimator();

        notesList.setItemAnimator(animator);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_delete_all) {
                    adapter.setNotes(Collections.emptyList());
                    adapter.notifyDataSetChanged();
                    return true;
                }

                if (item.getItemId() == R.id.action_add) {
                    presenter.addNote("Фрукт", "https://upload.wikimedia.org/wikipedia/commons/2/2f/Culinary_fruits_front_view.jpg");
                    return true;
                }
                return false;
            }
        });

        if (!wasNotesRequested) {
            presenter.requestNotes();
            wasNotesRequested = true;
        }
    }

    @Override
    public void showNotes(List<Note> notes) {

        adapter.setNotes(notes);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onNoteAdded(Note note) {
        adapter.addNote(note);

        adapter.notifyItemInserted(adapter.getItemCount() - 1);

        notesList.smoothScrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    public void onNoteRemoved(Note selectedNote) {
        int index = adapter.removeNote(selectedNote);
        adapter.notifyItemRemoved(index);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.menu_notes_list_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            presenter.removeNote(selectedNote);
            return true;
        }

        if (item.getItemId() == R.id.action_update) {
            if (router != null) {
                router.showEditNote(selectedNote);
            }
            return true;
        }
        return super.onContextItemSelected(item);
    }
}