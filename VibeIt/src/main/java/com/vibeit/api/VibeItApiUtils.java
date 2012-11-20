package com.vibeit.api;

import android.content.Context;
import android.os.ResultReceiver;
import com.vibeit.api.action.*;
import com.vibeit.api.model.Employee;
import com.vibeit.api.model.SurveyChoice;
import com.vibeit.model.AccountInfo;
import com.vibeit.model.SearchArgs;

import java.util.ArrayList;

/**
 * @author Maria Dzyokh
 */
public class VibeItApiUtils {

	public static void sighInWithEmail(Context ctx, ResultReceiver receiver, String email, String password) {
		if (ensureContext(ctx)) {
			ApiIntent intent = new ApiIntent(ctx, receiver, LoginAction.class);
			intent.putExtra(LoginAction.EXTRA_EMAIL, email);
			intent.putExtra(LoginAction.EXTRA_PASSWORD, password);
			ctx.startService(intent);
		}
	}

	public static void signInWithFacebook(Context ctx, ResultReceiver receiver, String token) {
		if (ensureContext(ctx)) {
			ApiIntent intent = new ApiIntent(ctx, receiver, FacebookLoginAction.class);
			intent.putExtra(FacebookLoginAction.EXTRA_TOKEN, token);
			ctx.startService(intent);
		}
	}

	public static void createAccount(Context ctx, ResultReceiver receiver, AccountInfo accountInfo) {
		if (ensureContext(ctx)) {
			ApiIntent intent = new ApiIntent(ctx, receiver, CreateAccountAction.class);
			intent.putExtra(CreateAccountAction.EXTRA_ACCOUNT_INFO, accountInfo);
			ctx.startService(intent);
		}
	}

	public static void forgotPassword(Context ctx, ResultReceiver receiver, String email) {
		if (ensureContext(ctx)) {
			ApiIntent intent = new ApiIntent(ctx, receiver, ForgotPasswordAction.class);
			intent.putExtra(ForgotPasswordAction.EXTRA_EMAIL, email);
			ctx.startService(intent);
		}
	}

	public static void getProfile(Context ctx, ResultReceiver receiver, String userToken) {
		if (ensureContext(ctx)) {
			ApiIntent intent = new ApiIntent(ctx, receiver, GetProfileAction.class);
			intent.putExtra(GetProfileAction.EXTRA_USER_TOKEN, userToken);
			ctx.startService(intent);
		}
	}

	public static void editProfile(Context ctx, ResultReceiver receiver, String firstName, String lastName, String email, String password, String confirmPassword, String user_token) {
		if (ensureContext(ctx)) {
			ApiIntent intent = new ApiIntent(ctx, receiver, EditProfileAction.class);
			intent.putExtra(EditProfileAction.EXTRA_FIRST_NAME, firstName);
			intent.putExtra(EditProfileAction.EXTRA_LAST_NAME, lastName);
			intent.putExtra(EditProfileAction.EXTRA_EMAIL, email);
			intent.putExtra(EditProfileAction.EXTRA_PASSWORD, password);
			intent.putExtra(EditProfileAction.EXTRA_CONFIRM_PASSWORD, confirmPassword);
			intent.putExtra(EditProfileAction.EXTRA_USER_TOKEN, user_token);
			ctx.startService(intent);
		}
	}

	public static void getCategories(Context ctx, ResultReceiver receiver) {
		if (ensureContext(ctx)) {
			ctx.startService(new ApiIntent(ctx, receiver, GetCategoriesAction.class));
		}
	}

    public static void getUserQuestions(Context ctx, ResultReceiver receiver) {
        if (ensureContext(ctx)) {
            ctx.startService(new ApiIntent(ctx, receiver, GetUserQuestionsAction.class));
        }
    }

    public static void getLocalVibes(Context ctx, ResultReceiver receiver, String userToken, double latitude, double longitude, float distance, int avg_overall, String order, SearchArgs searchArgs, int category, int page, String nextPageToken) {
       if (ensureContext(ctx)) {
           ApiIntent intent = new ApiIntent(ctx, receiver, GetLocalVibesAction.class);
           intent.putExtra(GetLocalVibesAction.EXTRA_AVG_OVERALL, avg_overall);
           intent.putExtra(GetLocalVibesAction.EXTRA_DISTANCE, distance);
           intent.putExtra(GetLocalVibesAction.EXTRA_LATITUDE, latitude);
           intent.putExtra(GetLocalVibesAction.EXTRA_LONGITUDE, longitude);
           intent.putExtra(GetLocalVibesAction.EXTRA_ORDER, order);
           intent.putExtra(GetLocalVibesAction.EXTRA_SEARCH, searchArgs);
           intent.putExtra(GetLocalVibesAction.EXTRA_USER_TOKEN, userToken);
           intent.putExtra(GetLocalVibesAction.EXTRA_PAGE, page);
           intent.putExtra(GetLocalVibesAction.EXTRA_CATEGORY, category);
           intent.putExtra(GetLocalVibesAction.EXTRA_NEXT_PAGE_TOKEN, nextPageToken);
           ctx.startService(intent);
       }
    }

    public static void getTopPerformers(Context ctx, ResultReceiver receiver, String userToken, double latitude, double longitude, float distance, int categoty_id,  SearchArgs searchArgs, int page, String nextPageToken) {
        if (ensureContext(ctx)) {
            ApiIntent intent = new ApiIntent(ctx, receiver, GetTopPerformersAction.class);
            intent.putExtra(GetTopPerformersAction.EXTRA_CATEGORY, categoty_id);
            intent.putExtra(GetTopPerformersAction.EXTRA_DISTANCE, distance);
            intent.putExtra(GetTopPerformersAction.EXTRA_LATITUDE, latitude);
            intent.putExtra(GetTopPerformersAction.EXTRA_LONGITUDE, longitude);
            intent.putExtra(GetTopPerformersAction.EXTRA_SEARCH, searchArgs);
            intent.putExtra(GetTopPerformersAction.EXTRA_USER_TOKEN, userToken);
            intent.putExtra(GetTopPerformersAction.EXTRA_PAGE, page);
            intent.putExtra(GetTopPerformersAction.EXTRA_NEXT_PAGE_TOKEN, nextPageToken);
            ctx.startService(intent);
        }
    }

    public static void getRisingStars(Context ctx, ResultReceiver receiver, String userToken, double latitude, double longitude, float distance, int categoty_id, SearchArgs searchArgs, int page, String nextPageToken) {
        if (ensureContext(ctx)) {
            ApiIntent intent = new ApiIntent(ctx, receiver, GetRisingStarsAction.class);
            intent.putExtra(GetRisingStarsAction.EXTRA_CATEGORY, categoty_id);
            intent.putExtra(GetRisingStarsAction.EXTRA_DISTANCE, distance);
            intent.putExtra(GetRisingStarsAction.EXTRA_LATITUDE, latitude);
            intent.putExtra(GetRisingStarsAction.EXTRA_LONGITUDE, longitude);
            intent.putExtra(GetRisingStarsAction.EXTRA_SEARCH, searchArgs);
            intent.putExtra(GetRisingStarsAction.EXTRA_USER_TOKEN, userToken);
            intent.putExtra(GetRisingStarsAction.EXTRA_PAGE, page);
            intent.putExtra(GetRisingStarsAction.EXTRA_NEXT_PAGE_TOKEN, nextPageToken);
            ctx.startService(intent);
        }
    }

    public static void getMyVibes(Context ctx, ResultReceiver receiver, String userToken, SearchArgs searchArgs, int page, String nextPageToken) {
        if (ensureContext(ctx)) {
            ApiIntent intent = new ApiIntent(ctx, receiver, GetMyVibesAction.class);
            intent.putExtra(GetMyVibesAction.EXTRA_USER_TOKEN, userToken);
            intent.putExtra(GetMyVibesAction.EXTRA_SEARCH, searchArgs);
            intent.putExtra(GetMyVibesAction.EXTRA_PAGE, page);
            intent.putExtra(GetMyVibesAction.EXTRA_NEXT_PAGE_TOKEN, nextPageToken);
            ctx.startService(intent);
        }
    }

    public static void getLocationDetails(Context ctx, ResultReceiver receiver, String serviceHash, String userToken) {
         if (ensureContext(ctx)) {
             ApiIntent intent = new ApiIntent(ctx, receiver, GetLocationDetailsAction.class);
             intent.putExtra(GetLocationDetailsAction.EXTRA_USER_TOKEN, userToken);
             intent.putExtra(GetLocationDetailsAction.EXTRA_SERVICE_HASH, serviceHash);
             ctx.startService(intent);
         }
    }

    public static void submitVibe(Context ctx, ResultReceiver receiver, String userToken, int locationId, float speed, float professionalism, float cleanliness, float value, String comment) {
        if (ensureContext(ctx)) {
            ApiIntent intent = new ApiIntent(ctx, receiver, SubmitVibeAction.class);
            intent.putExtra(SubmitVibeAction.EXTRA_USER_TOKEN, userToken);
            intent.putExtra(SubmitVibeAction.EXTRA_LOCATION_ID, locationId);
            intent.putExtra(SubmitVibeAction.EXTRA_SPEED, speed);
            intent.putExtra(SubmitVibeAction.EXTRA_PROFESSIONALISM, professionalism);
            intent.putExtra(SubmitVibeAction.EXTRA_CLEANLINESS, cleanliness);
            intent.putExtra(SubmitVibeAction.EXTRA_VALUE, value);
            intent.putExtra(SubmitVibeAction.EXTRA_COMMENT, comment);
            ctx.startService(intent);
        }
    }

    public static void rateEmployee(Context ctx, ResultReceiver receiver, String userToken, int locationId,Employee[] employees) {
        if (ensureContext(ctx)) {
            ApiIntent intent = new ApiIntent(ctx, receiver, RateEmployeeAction.class);
            intent.putExtra(RateEmployeeAction.EXTRA_USER_TOKEN, userToken);
            intent.putExtra(RateEmployeeAction.EXTRA_LOCATION_ID, locationId);
            intent.putExtra(RateEmployeeAction.EXTRA_EMPLOYEES, employees);
            ctx.startService(intent);
        }
    }

    public static void getSurveyDetails(Context ctx, ResultReceiver receiver, String userToken, int surveyId) {
         if (ensureContext(ctx)) {
             ApiIntent intent = new ApiIntent(ctx, receiver, GetSurveyDetailsAction.class);
             intent.putExtra(GetSurveyDetailsAction.EXTRA_USER_TOKEN, userToken);
             intent.putExtra(GetSurveyDetailsAction.EXTRA_SURVEY_ID, surveyId);
             ctx.startService(intent);
         }
    }

    public static void takeSurvey(Context ctx, ResultReceiver receiver, String userToken, int locationId, int surveyId, ArrayList<SurveyChoice> answers) {
          if (ensureContext(ctx)) {
              ApiIntent intent = new ApiIntent(ctx, receiver, TakeSurveyAction.class);
              intent.putExtra(TakeSurveyAction.EXTRA_USER_TOKEN, userToken);
              intent.putExtra(TakeSurveyAction.EXTRA_LOCATION_ID, locationId);
              intent.putExtra(TakeSurveyAction.EXTRA_SURVEY_ID, surveyId);
              intent.putExtra(TakeSurveyAction.EXTRA_ANSWERS, answers);
              ctx.startService(intent);
          }
    }

	private static boolean ensureContext(Context ctx) {
		return ctx != null;
	}
}