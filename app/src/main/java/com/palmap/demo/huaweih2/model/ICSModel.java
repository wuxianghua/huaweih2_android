package com.palmap.demo.huaweih2.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wtm on 2016/11/14.
 */

public class ICSModel implements Parcelable {

    private String title;
    private int resId;
    private String description;


    public ICSModel() {
    }

    public ICSModel(String title, int resId, String description) {
        this.title = title;
        this.resId = resId;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.resId);
        dest.writeString(this.description);
    }

    private ICSModel(Parcel in) {
        this.title = in.readString();
        this.resId = in.readInt();
        this.description = in.readString();
    }

    public static final Creator<ICSModel> CREATOR = new Creator<ICSModel>() {
        public ICSModel createFromParcel(Parcel source) {
            return new ICSModel(source);
        }

        public ICSModel[] newArray(int size) {
            return new ICSModel[size];
        }
    };
}
