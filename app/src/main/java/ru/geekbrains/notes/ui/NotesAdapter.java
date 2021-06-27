package ru.geekbrains.notes.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ru.geekbrains.notes.R;
import ru.geekbrains.notes.data.Notes;

public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> {
    private Notes notes;
    private OnItemClickListener onItemClickListener;

    public NotesAdapter(Notes notes) {
        this.notes = notes;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item, parent, false);
        return new NotesViewHolder(v, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.getNotesItem().setText(notes.getNote(position).getName());
    }

    @Override
    public int getItemCount() {
        return notes.getSize();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
