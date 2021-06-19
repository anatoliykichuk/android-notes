package ru.geekbrains.notes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NotesFragment extends Fragment {

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
                    showNote(noteIndex);
                }
            });

        }
    }

    private void showNote(int noteIndex) {

    }
}