package com.vibeit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.vibeit.R;

/**
 * @author Maria dzyokh
 */
public class VibeItProgressDialog extends Dialog {

    private LayoutInflater li;
    private TextView messageText;
    private String message;

    public VibeItProgressDialog(Context context, String message) {
        super(context, R.style.VibeitProgressDialog);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        this.li = LayoutInflater.from(context);
        this.message = message;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setCancelable(false);
        View view = li.inflate(R.layout.vibeit_progress_dialog, null);
        messageText = ((TextView) view.findViewById(R.id.message));
        messageText.setText(message);
        setContentView(view);
        super.onCreate(savedInstanceState);
    }

    public void setMessage(String message) {
        if (messageText != null) {
            messageText.setText(message);
        }
    }

}
