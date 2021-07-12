package ru.geekbrains.notes.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import ru.geekbrains.notes.R;
import ru.geekbrains.notes.model.Note;
import ru.geekbrains.notes.model.NotesRepository;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;

public class NotesAdapter extends RecyclerView.Adapter<NotesViewHolder> {
    private NotesRepository notes;
    private final Fragment fragment;
    private OnItemClickListener onItemClickListener;

    public NotesAdapter(Fragment fragment){
        this.fragment = fragment;
    }

    public void setNotes(NotesRepository notes) {
        this.notes = notes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_item, parent, false);
        return new NotesViewHolder(v, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        Note note = notes.getNote(position);

        CardView notesItem = holder.getNotesItem();

        TextView noteNameView = notesItem.findViewById(R.id.note_name);
        noteNameView.setText(note.getName());

        TextView noteDateOfCreationView = notesItem.findViewById(R.id.note_date_of_creation);
        noteDateOfCreationView.setText(new SimpleDateFormat("dd.MM.yyyy").format(note.getDateOfCreation()));
    }

    @Override
    public int getItemCount() {
        return notes == null ? 0 : notes.getSize();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
