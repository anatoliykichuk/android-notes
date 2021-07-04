package ru.geekbrains.notes.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ru.geekbrains.notes.R;
import ru.geekbrains.notes.model.Note;

public class NoteFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    private static Note currentNote;

    public static NoteFragment newInstance(Note currentNote) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(CURRENT_NOTE, currentNote);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentNote = getArguments().getParcelable(CURRENT_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note, container, false);

        TextView nameView = view.findViewById(R.id.name);
        nameView.setText(currentNote.getName());

        EditText descriptionView = view.findViewById(R.id.description);
        descriptionView.setText(currentNote.getDescription());

        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.menu_save:
                // TODO: Реализовать выбор и установку цвет азаливки.
                showMessage("Изменение заливки");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMessage(String message) {
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}