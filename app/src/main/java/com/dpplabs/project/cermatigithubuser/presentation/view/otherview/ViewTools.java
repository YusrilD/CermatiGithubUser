package com.dpplabs.project.cermatigithubuser.presentation.view.otherview;

import android.content.Context;
import android.util.DisplayMetrics;

public class ViewTools {

    public static int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}
