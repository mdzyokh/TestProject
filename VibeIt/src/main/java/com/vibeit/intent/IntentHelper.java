package com.vibeit.intent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.vibeit.R;
import com.vibeit.api.model.Location;
import com.vibeit.api.payload.LocationDetailsPayload;
import com.vibeit.api.payload.LocationPayload;
import com.vibeit.api.payload.SurveyPayload;
import com.vibeit.model.AccountInfo;
import com.vibeit.screen.*;
import com.vibeit.util.Extras;

/**
 * @author Maria Dzyokh
 */
public class IntentHelper {

    public static void signIn(Activity ctx) {
        if (ensureContext(ctx)) {
            ctx.startActivity(new Intent(ctx, SignInScreen.class));
        }
    }

    public static void signInWithEmail(Context ctx) {
        if (ensureContext(ctx)) {
            ctx.startActivity(new Intent(ctx, SignInWithEmailScreen.class));
        }
    }

    public static void createAccount(Context ctx) {
        if (ensureContext(ctx)) {
            ctx.startActivity(new Intent(ctx, CreateAccountScreen.class));
        }
    }

    public static void createAccountStep2(Context ctx, AccountInfo accountInfo) {
        if (ensureContext(ctx)) {
            Intent intent = new Intent(ctx, CreateAccountStep2Screen.class);
            intent.putExtra(CreateAccountScreen.EXTRA_ACCOUNT_INFO, accountInfo);
            ctx.startActivity(intent);
        }
    }

    public static void createFacebookAccount(Context ctx, AccountInfo accountInfo) {
        if (ensureContext(ctx)) {
            Intent intent = new Intent(ctx, CreateAccountScreen.class);
            intent.putExtra(CreateAccountScreen.EXTRA_ACCOUNT_INFO, accountInfo);
            ctx.startActivity(intent);
        }
    }

    public static void homeScreen(Activity ctx) {
        if (ensureContext(ctx)) {
            ctx.startActivity(new Intent(ctx, HomeScreen.class));
            ctx.finish();
        }
    }

    public static void forgotPasswordScreen(Context ctx) {
        if (ensureContext(ctx)) {
            ctx.startActivity(new Intent(ctx, ForgotPasswordScreen.class));
        }
    }

    public static void locationDetailsScreen(Context ctx, LocationDetailsPayload locationDetailsPayload) {
        if (ensureContext(ctx)) {
            Intent intent = new Intent(ctx, LocationDetailsScreen.class);
            intent.putExtra(Extras.EXTRA_LOCATION, locationDetailsPayload);
            ctx.startActivity(intent);
        }
    }

    public static void ratingScreen(Context ctx, LocationDetailsPayload locationDetailsPayload) {
        if (ensureContext(ctx)) {
            Intent intent = new Intent(ctx, RatingStep1Screen.class);
            intent.putExtra(Extras.EXTRA_LOCATION, locationDetailsPayload);
            ctx.startActivity(intent);
        }
    }

    public static void ratingStep2Screen(Context ctx, LocationDetailsPayload locationDetailsPayload) {
        if (ensureContext(ctx)) {
            Intent intent = new Intent(ctx, RatingStep2Screen.class);
            intent.putExtra(Extras.EXTRA_LOCATION, locationDetailsPayload);
            ctx.startActivity(intent);
        }
    }

    public static void employeeRatingScreen(Context ctx, LocationDetailsPayload locationDetailsPayload) {
        if (ensureContext(ctx)) {
            Intent intent = new Intent(ctx, EmployeeRatingScreen.class);
            intent.putExtra(Extras.EXTRA_LOCATION, locationDetailsPayload);
            ctx.startActivity(intent);
        }
    }

    public static void surveyScreen(Context ctx, LocationDetailsPayload location, SurveyPayload survey) {
        if (ensureContext(ctx)) {
            Intent intent = new Intent(ctx, SurveyScreen.class);
            intent.putExtra(Extras.EXTRA_LOCATION, location);
            intent.putExtra(Extras.EXTRA_SURVEY, survey);
            ctx.startActivity(intent);
        }
    }

    private static boolean ensureContext(Context ctx) {
        return ctx != null;
    }
}
