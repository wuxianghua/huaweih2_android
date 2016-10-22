package com.palmap.demo.huaweih2.json;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aoc on 2016/10/22.
 */

public class PictureModel implements Parcelable {

    private String url;
    private long time;
    private String details;
    private String locationStr;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLocationStr() {
        return locationStr;
    }

    public void setLocationStr(String locationStr) {
        this.locationStr = locationStr;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeLong(this.time);
        dest.writeString(this.details);
        dest.writeString(this.locationStr);
    }

    public PictureModel() {
    }

    protected PictureModel(Parcel in) {
        this.url = in.readString();
        this.time = in.readLong();
        this.details = in.readString();
        this.locationStr = in.readString();
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
