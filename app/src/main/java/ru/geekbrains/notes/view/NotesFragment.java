package ru.geekbrains.notes.view;

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

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import ru.geekbrains.notes.R;
import ru.geekbrains.notes.model.INotesResponse;
import ru.geekbrains.notes.model.Note;
import ru.geekbrains.notes.model.Notes;

public class NotesFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    private Note currentNote;
    private Notes notes;
    private NotesAdapter adapter;
    private RecyclerView notesItems;
    private boolean isLandscape;

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        notesItems = view.findViewById(R.id.notes_items);

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

        } else if (notes != null && notes.getSize() > 0) {
            // TODO: удалить
            //currentNote = new Note(getResources().getStringArray(R.array.notes)[0], "");
            currentNote = notes.getNote(0);
        }

        if (isLandscape) {
            showNoteNearby(currentNote);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.notes_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.menu_add:
                notes.add(new Note("<Пустая заметка>", ""));
                adapter.notifyItemInserted(notes.getPosition());
                notesItems.scrollToPosition(notes.getPosition());
                return true;

            case R.id.menu_clear:
                notes.clear();
                adapter.notifyDataSetChanged();
                return true;

            case R.id.menu_about:
                // TODO: Реализовать вывод информации о приложении.
                showMessage("Отображение информации о приложении");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeNotes(RecyclerView notesItems) {
        notes = new Notes().initialize(new INotesResponse() {
            @Override
            public void initialized(Notes notes) {
                adapter.notifyDataSetChanged();
            }
        });

        notesItems.setHasFixedSize(true);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        notesItems.setLayoutManager(manager);

        adapter = new NotesAdapter(this);
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
                                currentNote = notes.getNote(position);
                                showNote(currentNote);
                                return true;

                            case R.id.popup_menu_remove:
                                notes.remove(position);
                                adapter.notifyDataSetChanged();
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

    private void showMessage(String message) {
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}