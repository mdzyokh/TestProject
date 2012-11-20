package com.vibeit.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * @author Maria Dzyokh
 */
public class SocialShareUtils {

    public static String getFeedbackMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("Sent from my ");
        sb.append(android.os.Build.MODEL + " (" + android.os.Build.PRODUCT + ")");
        return sb.toString();
    }

    public static void sendEmail(Context ctx, String subject, String message, String to) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SENDTO);
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (to == null) {
            to = "";
        }
        emailIntent.setData(Uri.parse("mailto:" + to));
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);

        ctx.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
    }

    public static void sendFeedback(Context context) {
        sendEmail(context, "Feedback", getFeedbackMessage(), "feedback@vibe-it.com");
    }
}
