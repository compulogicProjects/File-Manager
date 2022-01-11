package com.smart.android.file.manager.tool;

import android.os.Parcel;
import android.os.Parcelable;

public class Music_Model implements Parcelable {
    String id;
    String path;
    String title;
    String duration;

    public Music_Model(String id, String path, String title, String duration) {
        this.id = id;
        this.path = path;
        this.title = title;
        this.duration = duration;
    }

    protected Music_Model(Parcel in) {
        id = in.readString();
        path = in.readString();
        title = in.readString();
        duration = in.readString();
    }

    public static final Creator<Music_Model> CREATOR = new Creator<Music_Model>() {
        @Override
        public Music_Model createFromParcel(Parcel in) {
            return new Music_Model(in);
        }

        @Override
        public Music_Model[] newArray(int size) {
            return new Music_Model[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(path);
        dest.writeString(title);
        dest.writeString(duration);
    }
}
