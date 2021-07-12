package ru.geekbrains.notes.view;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import ru.geekbrains.notes.model.Keys;
import ru.geekbrains.notes.model.Note;
import ru.geekbrains.notes.model.NotesRepository;

public class NotesFragment extends Fragment {

    private Note currentNote;
    private NotesRepository notes;
    private NotesAdapter adapter;
    private RecyclerView notesItems;
    private int notesPosition;

    private boolean isLandscape;

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notes = new NotesRepository().initialize(new INotesResponse() {
            @Override
            public void initialized(NotesRepository notes) {
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (currentNote != null) {
            notes.update(notesPosition, currentNote);
        }

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
        outState.putParcelable(Keys.CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isLandscape = getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(Keys.CURRENT_NOTE);

        } else if (notes != null && notes.getSize() > 0) {
            currentNote = notes.getNote(0);
        }

        if (isLandscape) {
            showNote();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
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
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        adapter = new NotesAdapter(this);
        adapter.setNotes(notes);

        notesItems.setHasFixedSize(true);
        notesItems.setLayoutManager(manager);
        notesItems.setAdapter(adapter);

        setSeparator();

        adapter.setOnItemClickListener((view, position) -> {
            Activity context  = requireActivity();
            PopupMenu popupMenu = new PopupMenu(context, view);
            context.getMenuInflater().inflate(R.menu.notes_popup_menu, popupMenu.getMenu());

            notesPosition = position;

            popupMenu.setOnMenuItemClickListener(onPopupMenuItemClickListener);
            popupMenu.show();
        });
    }

    private void setSeparator() {
        DividerItemDecoration itemDecoration = new DividerItemDecoration(
                getContext(),  LinearLayoutManager.VERTICAL);

        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator, null));
        notesItems.addItemDecoration(itemDecoration);
    }

    private PopupMenu.OnMenuItemClickListener onPopupMenuItemClickListener = (MenuItem item) ->  {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.popup_menu_edit:
                currentNote = notes.getNote(notesPosition);
                showNote();
                return true;

            case R.id.popup_menu_remove:
                askQuestion();

                return true;
        }
        return true;
    };

    private void askQuestion() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext())
                .setTitle(R.string.note_remove_dialog_title)
                .setCancelable(false)
                .setPositiveButton(R.string.note_dialog_positive_button, (dialog, which) -> {
                            notes.remove(notesPosition);
                            adapter.notifyDataSetChanged();

                        })

                .setNegativeButton(R.string.note_dialog_negative_button, (dialog, which) -> {

                        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    private void showNote() {
        int containerId = isLandscape ? R.id.fragment_containerIfLandscape : R.id.fragment_container;

        requireActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, NoteFragment.newInstance(currentNote))
                .addToBackStack(null)
                .commit();
    }

    private void showMessage(String message) {
        Toast toast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }
}