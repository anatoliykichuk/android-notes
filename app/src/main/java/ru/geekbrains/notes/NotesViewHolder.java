package ru.geekbrains.notes;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NotesViewHolder extends RecyclerView.ViewHolder {
    private TextView notesItem;
    private NotesAdapter.OnItemClickListener onItemClickListener;

    public NotesViewHolder(@NonNull View itemView, NotesAdapter.OnItemClickListener onItemClickListener) {
        super(itemView);
        notesItem = (TextView) itemView;
        this.onItemClickListener = onItemClickListener;

        notesItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, getAdapterPosition());
                }
            }
        });
    }

    public TextView getNotesItem() {
        return notesItem;
    }
}
