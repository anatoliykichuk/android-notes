package ru.geekbrains.notes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotesFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    private int currentNoteIndex = 0;
    private boolean isLandscape;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeNotes(view);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CURRENT_NOTE, currentNoteIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isLandscape = getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            currentNoteIndex = savedInstanceState.getInt(CURRENT_NOTE, 0);
        }

        showNote(0);
    }

    private void initializeNotes(View view) {
        LinearLayout notesList = (LinearLayout) view;
        String[] notes = getResources().getStringArray(R.array.notes);

        float textSize = getResources().getDimension(R.dimen.note_item_text_size);

        for (int index = 0; index < notes.length; index++) {
            TextView noteItem = new TextView(getContext());
            noteItem.setText(notes[index]);
            noteItem.setTextSize(TypedValue.COMPLEX_UNIT_SP , textSize);

            notesList.addView(noteItem);

            final int noteIndex = index;

            noteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentNoteIndex = noteIndex;
                    showNote(noteIndex);
                }
            });

        }
    }

    private void showNote(int noteIndex) {
        if (isLandscape) {
            showNoteNearby(noteIndex);
        } else {
            showNoteSeparately(noteIndex);
        }
    }

    private void showNoteSeparately(int noteIndex) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), NoteActivity.class);
        intent.putExtra(NoteFragment.INDEX, noteIndex);
        startActivity(intent);
    }

    private void showNoteNearby(int noteIndex) {
        NoteFragment note = NoteFragment.newInstance(noteIndex);

        FragmentManager manager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.note, note);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }
}