package ru.geekbrains.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> {
    private String[] dataSource;
    private OnItemClickListener onItemClickListener;

    public NotesAdapter(String[] dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item, parent, false);
        return new NotesViewHolder(v, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.getNotesItem().setText(dataSource[position]);
    }

    @Override
    public int getItemCount() {
        return dataSource.length;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
