package com.vibeit.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchArgs implements Parcelable {
    private String query;
    private String address;

    public static final Creator<SearchArgs> CREATOR = new Creator<SearchArgs>() {
        @Override
        public SearchArgs createFromParcel(Parcel source) {
            return new SearchArgs(source.readString(), source.readString());
        }

        @Override
        public SearchArgs[] newArray(int size) {
            return new SearchArgs[size];
        }
    };

    public SearchArgs() {
    }

    public SearchArgs(String query, String address) {
        this.query = query;
        this.address = address;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(query);
        dest.writeString(address);
    }
}
