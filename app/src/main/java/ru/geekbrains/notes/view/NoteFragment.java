package ru.geekbrains.notes.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;

import ru.geekbrains.notes.R;
import ru.geekbrains.notes.model.Keys;
import ru.geekbrains.notes.model.Note;
import ru.geekbrains.notes.observer.Publisher;

public class NoteFragment extends Fragment {

    private static Note currentNote;

    private EditText nameView;
    private EditText descriptionView;
    private DatePicker dateOfCreationView;

    private Publisher publisher;

    public static NoteFragment newInstance(Note currentNote) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(Keys.CURRENT_NOTE, currentNote);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        publisher = ((MainActivity) context).getPublisher();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentNote = getArguments().getParcelable(Keys.CURRENT_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note, container, false);

        nameView = view.findViewById(R.id.name);
        nameView.setText(currentNote.getName());

        descriptionView = view.findViewById(R.id.description);
        descriptionView.setText(currentNote.getDescription());

        dateOfCreationView = view.findViewById(R.id.date_of_creation);
        initializeDateOfCreationView();

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull  Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.note_menu, menu);
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.menu_save:

                if (isModified()) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext())
                            .setTitle(R.string.note_save_dialog_title)
                            .setCancelable(false)
                            .setPositiveButton(
                                    R.string.note_dialog_positive_button,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                            .setNegativeButton(
                                    R.string.note_dialog_negative_button,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            getActivity().onBackPressed();
                                        }
                                    })
                            .setNeutralButton(
                                    R.string.note_dialog_cancel_button,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                    AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                } else {
                    getActivity().onBackPressed();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notify(currentNote);
    }

    private void initializeDateOfCreationView() {
        Calendar dateOfCreation = dateOfCreationAsCalendar();

        dateOfCreationView.updateDate(
                dateOfCreation.get(Calendar.YEAR),
                dateOfCreation.get(Calendar.MONTH),
                dateOfCreation.get(Calendar.DAY_OF_MONTH));
    }

    private boolean isModified() {
        return !nameView.getText().equals(currentNote.getName())
                || !descriptionView.getText().equals(currentNote.getDescription())
                || isDateOfCreationModified();
    }

    private boolean isDateOfCreationModified() {
        Calendar dateOfCreation = dateOfCreationAsCalendar();

        return dateOfCreationView.getYear() != dateOfCreation.get(Calendar.YEAR)
                || dateOfCreationView.getMonth() != dateOfCreation.get(Calendar.MONTH)
                || dateOfCreationView.getDayOfMonth() != dateOfCreation.get(Calendar.DAY_OF_MONTH);
    }

    private Calendar dateOfCreationAsCalendar() {
        Date dateOfCreation = currentNote.getDateOfCreation();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateOfCreation);

        return calendar;
    }
}