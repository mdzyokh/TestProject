package com.vibeit.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maria Dzyokh
 */
public class UserQuestionChoice implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("user_question_id")
    private int user_question_id;
    @SerializedName("choice")
    private String choice;
    @SerializedName("created")
    private String created;
    @SerializedName("modified")
    private String modified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_question_id() {
        return user_question_id;
    }

    public void setUser_question_id(int user_question_id) {
        this.user_question_id = user_question_id;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserQuestionChoice that = (UserQuestionChoice) o;

        if (id != that.id) return false;
        if (user_question_id != that.user_question_id) return false;
        if (choice != null ? !choice.equals(that.choice) : that.choice != null) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (modified != null ? !modified.equals(that.modified) : that.modified != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + user_question_id;
        result = 31 * result + (choice != null ? choice.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        return result;
    }

    public static final Creator<UserQuestionChoice> CREATOR = new Creator<UserQuestionChoice>() {
        @Override
        public UserQuestionChoice createFromParcel(Parcel source) {
            UserQuestionChoice userQuestionChoice = new UserQuestionChoice();
            userQuestionChoice.id = source.readInt();
            userQuestionChoice.user_question_id = source.readInt();
            userQuestionChoice.choice = source.readString();
            userQuestionChoice.created = source.readString();
            userQuestionChoice.modified = source.readString();
            return userQuestionChoice;
        }

        @Override
        public UserQuestionChoice[] newArray(int size) {
            return new UserQuestionChoice[size];
        }

    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(user_question_id);
        dest.writeString(choice);
        dest.writeString(created);
        dest.writeString(modified);
    }
}
