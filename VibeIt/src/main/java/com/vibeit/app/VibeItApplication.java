package com.vibeit.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import com.vibeit.api.ApiResponse;
import com.vibeit.api.ApiResultReceiver;
import com.vibeit.api.VibeItApiUtils;
import com.vibeit.api.action.AbstractAction;
import com.vibeit.api.model.Category;
import com.vibeit.api.model.User;
import com.vibeit.api.model.UserToken;
import com.vibeit.api.payload.CategoryPayload;
import com.vibeit.api.payload.UserQuestionPayload;
import com.vibeit.api.payload.UserResponsePayload;
import com.vibeit.util.LocalPersistStorage;
import com.vibeit.util.Settings;

/**
 * @author Andrii Kovalov
 */
public class VibeItApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public SharedPreferences getMainSharedPrefs() {
        return getSharedPreferences(Settings.MAIN_PREFS, MODE_PRIVATE);
    }

    public void saveUserInfo(final UserResponsePayload userResponsePayload) {
        LocalPersistStorage.witeObjectToFile(VibeItApplication.this, userResponsePayload, Settings.USER_INFO);
    }

    public UserResponsePayload getSavedUserInfo() {
        return (UserResponsePayload) LocalPersistStorage.readObjectFromFile(this, Settings.USER_INFO);
    }

    public CategoryPayload[] getSavedCategories() {
        return (CategoryPayload[])LocalPersistStorage.readObjectFromFile(this, Settings.CATEGORIES);
    }

    public String getUserToken() {
        String userToken = "";
        UserResponsePayload userResponsePayload = ((UserResponsePayload) LocalPersistStorage.readObjectFromFile(this, Settings.USER_INFO));
        if (userResponsePayload!=null) {
            userToken = userResponsePayload.getUserToken().getToken();
        }
        return userToken;
    }

    public boolean isLoggedIn() {
        return getSavedUserInfo() != null;
    }

}
