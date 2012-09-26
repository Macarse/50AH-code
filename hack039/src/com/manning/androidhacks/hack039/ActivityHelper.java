/*******************************************************************************
 * Copyright (c) 2012 Manning
 * See the file license.txt for copying permission.
 ******************************************************************************/
package com.manning.androidhacks.hack039;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

public class ActivityHelper {

  public static boolean isLayarAvailable(Context ctx) {
    PackageManager pm = ctx.getPackageManager();

    try {
      pm.getApplicationInfo("com.layar", 0);
      return true;
    } catch (PackageManager.NameNotFoundException e) {
      return false;
    }

  }

  public static AlertDialog showDownloadDialog(final Context ctx) {
    AlertDialog.Builder downloadDialog = new AlertDialog.Builder(ctx);
    downloadDialog.setTitle("Layar is not available");
    downloadDialog
        .setMessage("Do you want to download it from the market?");
    downloadDialog.setPositiveButton("Yes",
        new DialogInterface.OnClickListener() {

          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            Uri uri = Uri.parse("market://details?id=com.layar");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            try {
              ctx.startActivity(intent);
            } catch (ActivityNotFoundException anfe) {
              Toast.makeText(ctx, "Market not installed",
                  Toast.LENGTH_SHORT).show();
            }
          }

        });

    downloadDialog.setNegativeButton("No",
        new DialogInterface.OnClickListener() {

          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
          }

        });

    return downloadDialog.show();
  }
}
