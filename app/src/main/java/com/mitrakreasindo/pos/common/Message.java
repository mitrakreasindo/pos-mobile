package com.mitrakreasindo.pos.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.mitrakreasindo.pos.main.R;

/**
 * Created by lisa on 31/08/17.
 */

public class Message
{

  public static final String ACTIVE = "SHOW";
  public static final String INACTIVE = "DISMISS";

  public static void error(String message, Context context)
  {

    new AlertDialog.Builder(context)
      .setTitle(R.string.failed)
      .setMessage(message)
      .setIcon(R.drawable.ic_error_outline_black)
      .setPositiveButton(R.string.Okay, null).show();
  }

  public static void progress(Context context, String action)
  {
    final ProgressDialog progressDialog = new ProgressDialog(context);
    progressDialog.setMessage(context.getString(R.string.progress_message));
    progressDialog.setCancelable(false);
    switch (action)
    {
      case ACTIVE:
        progressDialog.show();
        break;

      case INACTIVE:
        progressDialog.dismiss();
        break;
    }
  }
}
