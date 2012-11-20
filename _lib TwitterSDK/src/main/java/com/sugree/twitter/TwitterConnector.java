package com.sugree.twitter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

/**
 * @author Maria Dzyokh
 */
public class TwitterConnector {

    private static TwitterConnector instance;

    private Twitter mTwitter;
    private String mAccessToken;
    private String mAccessSecret;
    private SharedPreferences mSharedPreferences;

    public static final String KEY_TWITTER_ACCESS_TOKEN = "TWITTER_ACCESS_TOKEN";
    public static final String KEY_TWITTER_ACCESS_SECRET = "TWITTER_ACCESS_SECRET";
    public static final String KEY_TWITTER_SCREEN_NAME = "TWITTER_SCREEN_NAME";

    private String apiKey;
    private String apiSecret;

    private TwitterConnector(Context context, int icon, String apiKey, String apiSecret)
    {
        mTwitter = new Twitter(icon);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        mAccessToken = mSharedPreferences.getString(KEY_TWITTER_ACCESS_TOKEN, null);
        mAccessSecret = mSharedPreferences.getString(KEY_TWITTER_ACCESS_SECRET, null);

        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    public static TwitterConnector getInstance(Context context, int icon, String apiKey, String apiSecret)
    {
        if (instance == null)
        {
            instance = new TwitterConnector(context, icon, apiKey, apiSecret);
        }
        return instance;
    }

    public String getTwitterScreenName() {
        return mSharedPreferences.getString(KEY_TWITTER_SCREEN_NAME, "");
    }

    public boolean isAuthorized() {
       return mAccessToken!=null&mAccessSecret!=null;
    }

    public void authorize(final Activity activity, Twitter.DialogListener listener)
    {
        mTwitter.authorize(activity, new Handler(), apiKey, apiSecret, listener);
    }

    public void tweetWithAuthorizing(final Activity activity, final String text)
    {
        if (mAccessToken == null || mAccessSecret == null)
        {
            authorize(activity, new Twitter.DialogListener()
            {

                @Override
                public void onTwitterError(TwitterError e)
                {
                }

                @Override
                public void onError(DialogError e)
                {
                }

                @Override
                public void onComplete(Bundle values)
                {
                    getAuthorizeDialogListener().onComplete(values);
                    tweet(activity, text);
                }

                @Override
                public void onCancel()
                {
                }

                @Override
                public void onDismiss() {

                }
            });

        } else
        {
            tweetInBackground(activity, text);
        }
    }


    public Twitter.DialogListener getAuthorizeDialogListener()
    {
        return new Twitter.DialogListener()
        {

            @Override
            public void onTwitterError(TwitterError e)
            {
            }

            @Override
            public void onError(DialogError e)
            {
            }

            @Override
            public void onComplete(Bundle values)
            {
                mAccessToken = mTwitter.getAccessToken();
                mAccessSecret = mTwitter.getSecretToken();

                twitter4j.Twitter twitter = new TwitterFactory().getInstance();
                twitter.setOAuthConsumer(apiKey, apiSecret);
                AccessToken accessTokenObject = new AccessToken(mAccessToken, mAccessSecret);
                twitter.setOAuthAccessToken(accessTokenObject);
                try
                {
                    String screenName = twitter.getScreenName();
                    mSharedPreferences.edit().putString(KEY_TWITTER_ACCESS_TOKEN, mAccessToken).putString(KEY_TWITTER_ACCESS_SECRET, mAccessSecret)
                            .putString(KEY_TWITTER_SCREEN_NAME, screenName).commit();
                } catch (IllegalStateException e)
                {
                    e.printStackTrace();
                } catch (TwitterException e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancel()
            {
            }

            @Override
            public void onDismiss() {

            }
        };
    }

    private void tweet(final Activity activity, final String text)
    {
        activity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                AlertDialog.Builder builder = getTweetDialogBuilder(activity, text);
                builder.create().show();
            }
        });
    }

    private void tweetInBackground(final Activity activity, final String text) {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                twitter4j.Twitter twitter = new TwitterFactory().getInstance();
                twitter.setOAuthConsumer(apiKey, apiSecret);
                AccessToken accessTokenObject = new AccessToken(mAccessToken, mAccessSecret);
                twitter.setOAuthAccessToken(accessTokenObject);
                try
                {
                    twitter.updateStatus(text);
                } catch (TwitterException e) { e.printStackTrace();}
            }
        }).start();
    }

    private AlertDialog.Builder getTweetDialogBuilder(final Activity activity, final String updateText)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Share message");
        //builder.setIcon(R.drawable.social_icon_twitter);
        final EditText editView = new EditText(activity);
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(140);
        editView.setFilters(FilterArray);
        editView.setText(updateText);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        editView.setLayoutParams(params);
        builder.setView(editView);
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        twitter4j.Twitter twitter = new TwitterFactory().getInstance();
                        twitter.setOAuthConsumer(apiKey, apiSecret);
                        AccessToken accessTokenObject = new AccessToken(mAccessToken, mAccessSecret);
                        twitter.setOAuthAccessToken(accessTokenObject);
                        try
                        {
                            twitter.updateStatus(editView.getText().toString());
                            activity.runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    Toast.makeText(activity, "Sent", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (TwitterException e)
                        {
                            activity.runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    Toast.makeText(activity, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        return builder;
    }

    public void disconnect()
    {
        mAccessToken = null;
        mAccessSecret = null;

        mSharedPreferences.edit().putString(KEY_TWITTER_ACCESS_TOKEN, null).putString(KEY_TWITTER_ACCESS_SECRET, null)
                .putString(KEY_TWITTER_SCREEN_NAME, null).commit();
    }
}

