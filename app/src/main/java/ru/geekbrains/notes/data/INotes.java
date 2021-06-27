package ru.geekbrains.notes.data;

public interface INotes {
    Note getNote(int position);

    int getSize();
    int getPosition();

    void add(Note note);
    void remove(int position);
    void update(int position, Note note);
    void clear();
}
