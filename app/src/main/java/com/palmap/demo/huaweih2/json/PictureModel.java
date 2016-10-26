package com.palmap.demo.huaweih2.json;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aoc on 2016/10/22.
 */

public class PictureModel implements Parcelable {

    private String photo;
    private long updtime;
    private String appendix;
    private String location;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public long getUpdtime() {
        return updtime;
    }

    public void setUpdtime(long updtime) {
        this.updtime = updtime;
    }

    public String getAppendix() {
        return appendix;
    }

    public void setAppendix(String appendix) {
        this.appendix = appendix;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.photo);
        dest.writeLong(this.updtime);
        dest.writeString(this.appendix);
        dest.writeString(this.location);
    }

    public PictureModel() {
    }

    protected PictureModel(Parcel in) {
        this.photo = in.readString();
        this.updtime = in.readLong();
        this.appendix = in.readString();
        this.location = in.readString();
    }

    public static final Parcelable.Creator<PictureModel> CREATOR = new Parcelable.Creator<PictureModel>() {
        @Override
        public PictureModel createFromParcel(Parcel source) {
            return new PictureModel(source);
        }

        @Override
        public PictureModel[] newArray(int size) {
            return new PictureModel[size];
        }
    };
}
