package ru.geekbrains.notes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Note implements Parcelable {
    private int Index;
    private String name;
    private String description;
    private Date dateOfCreation;

    public Note(String name, String description) {
        this.name = name;
        this.description = description;
        this.dateOfCreation = new Date();
    }

    protected Note(Parcel in) {
        Index = in.readInt();
        name = in.readString();
        description = in.readString();
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

    public int getIndex() {
        return Index;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Index);
        dest.writeString(name);
        dest.writeString(description);
    }
}
