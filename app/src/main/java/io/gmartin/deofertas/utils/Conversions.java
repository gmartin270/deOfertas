package io.gmartin.deofertas.utils;

import android.content.Context;

/**
 * Created by gmartin on 20/02/18.
 */

public class Conversions {

    public static int dpToPx(float dp, Context context){
        float density = context.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round(dp * density);
    }
}
