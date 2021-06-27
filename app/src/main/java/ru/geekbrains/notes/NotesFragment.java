package ru.geekbrains.notes;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;

public class NotesFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    private Note currentNote;
    private boolean isLandscape;

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        RecyclerView notesItems = view.findViewById(R.id.notes_items);

        initializeNotes(notesItems);
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isLandscape = getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            currentNote = new Note(0, getResources().getStringArray(R.array.notes)[0], "");
        }

        if (isLandscape) {
            showNoteNearby(currentNote);
        }
    }

    private void initializeNotes(RecyclerView notesItems) {
        String[] notes = getResources().getStringArray(R.array.notes);

        notesItems.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        notesItems.setLayoutManager(manager);

        NotesAdapter adapter = new NotesAdapter(notes);
        notesItems.setAdapter(adapter);

        adapter.setOnItemClickListener(new NotesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Activity context  = requireActivity();
                PopupMenu popupMenu = new PopupMenu(context, view);
                context.getMenuInflater().inflate(R.menu.notes_popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();

                        switch (itemId) {
                            case R.id.popup_menu_edit:
                                // TODO: Реализовать активизацию первой строки.
                                currentNote = new Note(position, notes[position], "");
                                showNote(currentNote);
                                return true;
                            case R.id.popup_menu_remove:
                                // TODO: Реализовать активизацию последней строки.

                                return true;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    private void showNote(Note currentNote) {
        if (isLandscape) {
            showNoteNearby(currentNote);
        } else {
            showNoteSeparately(currentNote);
        }
    }

    private void showNoteSeparately(Note currentNote) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), NoteActivity.class);
        intent.putExtra(NoteFragment.CURRENT_NOTE, currentNote);
        startActivity(intent);
    }

    private void showNoteNearby(Note currentNote) {
        NoteFragment note = NoteFragment.newInstance(currentNote);

        FragmentManager manager = requireActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.note, note);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }
}