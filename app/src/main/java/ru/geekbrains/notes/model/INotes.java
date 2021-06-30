package ru.geekbrains.notes.model;

public interface INotes {
    INotes initialize(INotesResponse response);
    Note getNote(int position);

    int getSize();
    int getPosition();

    void add(Note note);
    void remove(int position);
    void update(int position, Note note);
    void clear();
}
