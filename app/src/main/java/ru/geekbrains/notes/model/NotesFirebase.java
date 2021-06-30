package ru.geekbrains.notes.model;

import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ru.geekbrains.notes.R;

public class NotesFirebase implements INotes {
    private static final String NOTES = "Notes";

    private FirebaseFirestore store = FirebaseFirestore.getInstance();
    private CollectionReference collection = store.collection(NOTES);

    private List<Note> notes;

    public NotesFirebase(Resources resources) {
        notes = new ArrayList<>();
    }

    @Override
    public INotes initialize(INotesResponse response) {
        collection.orderBy(NoteMapping.Fields.DATE_OF_CREATION, Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        notes.clear();

                        for (QueryDocumentSnapshot result : task.getResult()) {
                            Map<String, Object> document = result.getData();
                            Note note = NoteMapping.noteByDocument(result.getId(), document);
                            notes.add(note);
                        }

                        response.initialized(NotesFirebase.this);
                    }
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
        collection.add(note.getId()).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference document) {
                note.setId(document.getId());
            }
        });

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
