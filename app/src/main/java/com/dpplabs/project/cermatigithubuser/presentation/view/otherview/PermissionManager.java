package com.dpplabs.project.cermatigithubuser.presentation.view.otherview;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;
import com.dpplabs.project.cermatigithubuser.R;
import com.dpplabs.project.cermatigithubuser.net.util.Function;

import static android.Manifest.permission.INTERNET;

public class PermissionManager {

    private static final int USE_INTERNET = 1;

    private Activity act;
    private Function onOKFunction;
    private Function lastPermission;

    public PermissionManager(Activity act){
        this.act=act;
    }

    public void requestBasePermissions(final Activity act, final Function<Void> doAfter){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M&&!useInternetPermGranted(act)){
            new AlertDialog.Builder(act)
                    .setMessage("Need Internet Permission")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            ActivityCompat.requestPermissions(act, new String[]{Manifest.permission.INTERNET}, USE_INTERNET);
                            onOKFunction = doAfter;
                            lastPermission = new Function<Void>() {
                                @Override
                                public void run(Void... params) {
                                    requestBasePermissions(act, doAfter);
                                }
                            };
                        }
                    })
                    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                            act.finish();
                        }
                    })
                    .setCancelable(false)
                    .create()
                    .show();
        } else {
            doAfter.run();
        }
    }

    private static boolean useInternetPermGranted(Context context) {
        return (context.checkCallingOrSelfPermission(INTERNET) == PackageManager.PERMISSION_GRANTED);
    }

    public void onPermissionCallback(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case USE_INTERNET: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(onOKFunction==null) return;
                    onOKFunction.run();
                    onOKFunction =null;
                } else {
                    Toast.makeText(act, "Permission Denied", Toast.LENGTH_SHORT).show();
                    if(lastPermission==null) return;
                    lastPermission.run();
                    lastPermission=null;
                }
            }
        }
    }
}