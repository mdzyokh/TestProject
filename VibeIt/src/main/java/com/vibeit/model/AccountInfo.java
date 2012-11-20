package com.vibeit.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import com.vibeit.api.model.UserQuestionChoice;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author Andrii Kovalov
 */
public class AccountInfo implements Parcelable {
    private String firstName;
    private String lastName;
    private String email;
    private String fbToken;
    private String password;
    private String passwordConfirm;
    private boolean allowContact;
    private ArrayList<UserQuestionChoice> userQuestionAnswers = new ArrayList<UserQuestionChoice>();

    public AccountInfo() {
    }

    public static final Creator<AccountInfo> CREATOR = new Creator<AccountInfo>() {
        @Override
        public AccountInfo createFromParcel(Parcel source) {
            return new AccountInfo(source);
        }

        @Override
        public AccountInfo[] newArray(int size) {
            return new AccountInfo[size];
        }
    };

    public AccountInfo(Parcel source) {
        firstName = source.readString();
        lastName = source.readString();
        email = source.readString();
        fbToken = source.readString();
        allowContact = source.readInt() == 1;
        password = source.readString();
        passwordConfirm = source.readString();
        source.readTypedList(userQuestionAnswers, UserQuestionChoice.CREATOR);
    }

    public static AccountInfo parse(JSONObject json) {
        AccountInfo accountInfo = new AccountInfo();
        accountInfo.firstName = json.optString("first_name");
        accountInfo.lastName = json.optString("last_name");
        accountInfo.email = json.optString("email");
        return accountInfo;
    }

    public String composeQuestionAnswersJson() {
        String result = "";
        try {
            JSONArray array = new JSONArray();
            for (UserQuestionChoice choice : userQuestionAnswers) {
                JSONObject answer = new JSONObject();
                answer.put("user_question_id", choice.getUser_question_id());
                answer.put("user_question_choice_id", choice.getId());
                array.put(answer);
            }
            result = array.toString();
        } catch (JSONException e) {
            Log.e(AccountInfo.class.getSimpleName(), e.getMessage(), e);
        }
        return result;
    }

    public ArrayList<UserQuestionChoice> getUserQuestionAnswers() {
        return userQuestionAnswers;
    }

    public void setUserQuestionAnswers(ArrayList<UserQuestionChoice> userQuestionChoices) {
        this.userQuestionAnswers = userQuestionChoices;
    }

    public void addUserQuestionAnswer(UserQuestionChoice answer) {
        this.userQuestionAnswers.add(answer);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean hasEmail() {
        return !TextUtils.isEmpty(email);
    }

    public String getFbToken() {
        return fbToken;
    }

    public void setFbToken(String fbToken) {
        this.fbToken = fbToken;
    }

    public boolean hasFbToken() {
        return !TextUtils.isEmpty(fbToken);
    }

    public boolean isAllowContact() {
        return allowContact;
    }

    public void setAllowContact(boolean allowContact) {
        this.allowContact = allowContact;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean hasPassword() {
        return !TextUtils.isEmpty(password);
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(email);
        dest.writeString(fbToken);
        dest.writeInt(allowContact ? 1 : 0);
        dest.writeString(password);
        dest.writeString(passwordConfirm);
        dest.writeTypedList(userQuestionAnswers);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}