package com.vibeit.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Maria Dzyokh
 */
public class Employee implements Serializable, Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("location_id")
    private int locationId;
    @SerializedName("status")
    private boolean status;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("avg_rating")
    private float avgRating;
    @SerializedName("total_rating")
    private float totalRating;
    @SerializedName("rates_count")
    private int ratesCount;

    private float userRating;
    private String userComment;

    public Employee() {
    }

    public static final Creator<Employee> CREATOR = new Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel source) {
            return new Employee(source);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    public Employee(Parcel source) {
        id = source.readInt();
        locationId = source.readInt();
        status = source.readInt() == 1;
        firstName = source.readString();
        lastName = source.readString();
        avgRating = source.readFloat();
        totalRating = source.readFloat();
        ratesCount = source.readInt();
        userRating = source.readFloat();
        userComment = source.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(locationId);
        parcel.writeInt(status ? 1 : 0);
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeFloat(avgRating);
        parcel.writeFloat(totalRating);
        parcel.writeInt(ratesCount);
        parcel.writeFloat(userRating);
        parcel.writeString(userComment);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }

    public float getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(float totalRating) {
        this.totalRating = totalRating;
    }

    public int getRatesCount() {
        return ratesCount;
    }

    public void setRatesCount(int ratesCount) {
        this.ratesCount = ratesCount;
    }

    public float getUserRating() {
        return userRating;
    }

    public void setUserRating(float userRating) {
        this.userRating = userRating;
    }

    public String getUserComment() {
        return userComment!=null?userComment:"";
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (Float.compare(employee.avgRating, avgRating) != 0) return false;
        if (id != employee.id) return false;
        if (locationId != employee.locationId) return false;
        if (ratesCount != employee.ratesCount) return false;
        if (status != employee.status) return false;
        if (Float.compare(employee.totalRating, totalRating) != 0) return false;
        if (Float.compare(employee.userRating, userRating) != 0) return false;
        if (firstName != null ? !firstName.equals(employee.firstName) : employee.firstName != null) return false;
        if (lastName != null ? !lastName.equals(employee.lastName) : employee.lastName != null) return false;
        if (userComment != null ? !userComment.equals(employee.userComment) : employee.userComment != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + locationId;
        result = 31 * result + (status ? 1 : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (avgRating != +0.0f ? Float.floatToIntBits(avgRating) : 0);
        result = 31 * result + (totalRating != +0.0f ? Float.floatToIntBits(totalRating) : 0);
        result = 31 * result + ratesCount;
        result = 31 * result + (userRating != +0.0f ? Float.floatToIntBits(userRating) : 0);
        result = 31 * result + (userComment != null ? userComment.hashCode() : 0);
        return result;
    }

}
