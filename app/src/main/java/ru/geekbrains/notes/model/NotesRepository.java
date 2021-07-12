package ru.geekbrains.notes.model;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotesRepository implements INotesSource {
    private static final String NOTES = "Notes";

    private final FirebaseFirestore store = FirebaseFirestore.getInstance();
    private final CollectionReference collection = store.collection(NOTES);
    private final List<Note> notes;

    public NotesRepository() {
        notes = new ArrayList<>();
    }

    @Override
    public NotesRepository initialize(INotesResponse response) {
        collection.orderBy(NoteMapping.Fields.DATE_OF_CREATION, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        notes.clear();

                        for (QueryDocumentSnapshot result : task.getResult()) {
                            Map<String, Object> document = result.getData();
                            Note note = NoteMapping.noteByDocument(result.getId(), document);
                            notes.add(note);
                        }

                        response.initialized(NotesRepository.this);
                    }
                });

        return this;
    }

    @Override
    public Note getNote(int position) {
        return notes.get(position);
    }

    @Override
    public int getSize() {
        return notes == null ? 0 : notes.size();
    }

    @Override
    public int getPosition() {
        return getSize() - 1;
    }

    @Override
    public void add(Note note) {
        collection.add(note).addOnSuccessListener(document -> note.setId(document.getId()));
        notes.add(note);
    }

    @Override
    public void remove(int position) {
        collection.document(notes.get(position).getId()).delete();
        notes.remove(position);
    }

    @Override
    public void update(int position, Note note) {
        collection.document(note.getId()).set(NoteMapping.documentByNote(note));
        notes.set(position, note);
    }

    @Override
    public void clear() {
        for (Note note : notes) {
            collection.document(note.getId()).delete();
        }

        notes.clear();
    }
}
