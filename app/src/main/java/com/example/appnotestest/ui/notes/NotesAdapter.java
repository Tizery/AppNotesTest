package com.example.appnotestest.ui.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appnotestest.R;
import com.example.appnotestest.domain.Note;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private final ArrayList<Note> data = new ArrayList<>();

    private OnNoteClickedListener listener;

    private OnNoteLongClickedListener longClickListener;

    private final Fragment fragment;

    public NotesAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setNotes(List<Note> toSet) {
        data.clear();
        data.addAll(toSet);
    }

    public void addNote(Note note) {
        data.add(note);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public NotesAdapter.NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_list, parent, false);

        return new NotesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.NotesViewHolder holder, int position) {
        Note note = data.get(position);

        holder.getTitle().setText(note.getTitle());

        Glide.with(holder.getImage()).load(note.getImageUrl()).
                centerCrop().into(holder.getImage());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public OnNoteClickedListener getListener() {
        return listener;
    }

    public void setListener(OnNoteClickedListener listener) {
        this.listener = listener;
    }

    public OnNoteLongClickedListener getLongClickListener() {
        return longClickListener;
    }

    public void setLongClickListener(OnNoteLongClickedListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public int removeNote(Note selectedNote) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).equals(selectedNote)) {
                data.remove(i);
                return i;
            }
        }
        return -1;
    }

    public int updateNote(Note note) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).equals(note)) {
                data.set(i, note);
                return i;
            }
        }
        return -1;
    }

    interface OnNoteClickedListener {
        void onNoteClicked(Note note);
    }

    interface OnNoteLongClickedListener {
        void onNoteLongClicked(Note note);
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;

        private final ImageView image;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            fragment.registerForContextMenu(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (getListener() != null) {
                        getListener().onNoteClicked(data.get(getAdapterPosition()));
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemView.showContextMenu();

                    if (getLongClickListener() != null) {
                        getLongClickListener().onNoteLongClicked(data.get(getAdapterPosition()));
                    }
                    return true;
                }
            });

            title = itemView.findViewById(R.id.note_title);
            image = itemView.findViewById(R.id.note_image);
        }

        public TextView getTitle() {
            return title;
        }

        public ImageView getImage() {
            return image;
        }
    }
}
