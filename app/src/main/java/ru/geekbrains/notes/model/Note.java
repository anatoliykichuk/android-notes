package ru.geekbrains.notes.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;

public class Note implements Parcelable {
    private String id;
    private String name;
    private String description;
    private Date dateOfCreation;

    public Note(String name, String description) {
        this.name = name;
        this.description = description;
        this.dateOfCreation = Calendar.getInstance().getTime();
    }

    public Note(String name, String description, Date dateOfCreation) {
        this.name = name;
        this.description = description;
        this.dateOfCreation = dateOfCreation;
    }

    protected Note(Parcel in) {
        name = in.readString();
        description = in.readString();
        dateOfCreation = new Date(in.readLong());
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void update(String name, String description, Date dateOfCreation) {
        this.name = name;
        this.description = description;
        this.dateOfCreation = dateOfCreation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeLong(dateOfCreation.getTime());
    }
}
