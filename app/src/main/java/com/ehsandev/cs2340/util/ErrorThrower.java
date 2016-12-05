package com.ehsandev.cs2340.util;

/**
 * Created by ehsan on 12/5/16.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;



public class ErrorThrower extends DialogFragment {
    public static DialogFragment newInstance(String message, Boolean kill) {
        ErrorThrower frag = new ErrorThrower();
        Bundle args = new Bundle();
        args.putString("message", message);
        args.putBoolean("kill", kill);
        frag.setArguments(args);
        return frag;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String message = getArguments().getString("message");
        final Boolean kill = getArguments().getBoolean("kill");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(message);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (kill) {
                    getActivity().finish();
                } else {
                    dialog.cancel();
                }
            }
        });
        return builder.create();
    }
}