package ru.geekbrains.notes;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesViewHolder extends RecyclerView.ViewHolder {
    private TextView notesItem;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);
        notesItem = (TextView) itemView;
    }

    public TextView getNotesItem() {
        return notesItem;
    }
}
