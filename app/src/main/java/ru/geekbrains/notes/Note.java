package ru.geekbrains.notes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Note implements Parcelable {
    private int index;
    private String name;
    private String description;
    private Date dateOfCreation;

    public Note(int index, String name, String description) {
        this.index = index;
        this.name = name;
        this.description = description;
        this.dateOfCreation = new Date();
    }

    protected Note(Parcel in) {
        index = in.readInt();
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
        return index;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(index);
        dest.writeString(name);
        dest.writeString(description);
    }
}
