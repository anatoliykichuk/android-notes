package ru.geekbrains.notes.view;

import android.view.View;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;

public class NotesViewHolder extends RecyclerView.ViewHolder {

    private final CardView notesItem;

    public NotesViewHolder(@NonNull View itemView, NotesAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        notesItem = (CardView) itemView;

        notesItem.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, getAdapterPosition());
            }
        });
    }

    public CardView getNotesItem() {
        return notesItem;
    }
}
