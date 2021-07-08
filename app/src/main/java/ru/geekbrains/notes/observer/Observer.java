package ru.geekbrains.notes.observer;

import ru.geekbrains.notes.model.Note;

public interface Observer {
    void update(Note note);
}
