package ru.geekbrains.notes.observer;

import java.util.ArrayList;
import java.util.List;

import ru.geekbrains.notes.model.Note;

public class Publisher {
    private List<Observer> observers;

    public Publisher() {
        this.observers = new ArrayList<>();
    }

    public void subscribe(Observer observer) {
        observers.add(observer);
    }

    public void unsubscribe(Observer observer) {
        observers.remove(observer);
    }

    public void notify(Note note) {
        for (Observer observer : observers) {
            observer.update(note);
        }
    }
}
