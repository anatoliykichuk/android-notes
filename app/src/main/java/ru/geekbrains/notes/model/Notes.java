package ru.geekbrains.notes.model;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.notes.R;

public class Notes implements INotes {
    private List<Note> notes;
    private Resources resources;

    public Notes(Resources resources) {
        notes = new ArrayList<>();
        this.resources = resources;
    }

    public Notes initialize(INotesResponse response) {
        String[] notesName = resources.getStringArray(R.array.notes);

        for (int index = 0; index < notesName.length; index++) {
            notes.add(new Note(notesName[index], ""));
        }

        if (response != null) {
            response.initialized(this);
        }

        return this;
    }

    @Override
    public Note getNote(int position) {
        return notes.get(position);
    }

    @Override
    public int getSize() {
        return notes.size();
    }

    @Override
    public int getPosition() {
        return getSize() - 1;
    }

    @Override
    public void add(Note note) {
        notes.add(note);
    }

    @Override
    public void remove(int position) {
        notes.remove(position);
    }

    @Override
    public void update(int position, Note note) {
        notes.set(position, note);
    }

    @Override
    public void clear() {
        notes.clear();
    }
}
