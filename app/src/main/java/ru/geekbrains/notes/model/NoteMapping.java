package ru.geekbrains.notes.model;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NoteMapping {

    public static class Fields {
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
        public static final String  DATE_OF_CREATION = "dateOfCreation";
    }

    public static Map<String, Object> documentByNote(Note note) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.NAME, note.getName());
        answer.put(Fields.DESCRIPTION, note.getDescription());
        answer.put(Fields.DATE_OF_CREATION, note.getDateOfCreation());

        return answer;
    }

    public static Note noteByDocument(String id, Map<String, Object> document) {
        Timestamp timestamp = (Timestamp)document.get(Fields.DATE_OF_CREATION);

        Note answer = new Note(
                (String) document.get(Fields.NAME),
                (String) document.get(Fields.DESCRIPTION),
                timestamp.toDate());

        answer.setId(id);

        return answer;
    }
}
