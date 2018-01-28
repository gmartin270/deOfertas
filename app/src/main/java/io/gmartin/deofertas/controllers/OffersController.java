package io.gmartin.deofertas.controllers;

import android.content.Context;

public class OffersController {

    private static OffersController mInstance = null;

    private Context mContext;
    private static String mURL = "http://tm5-agmoyano.rhcloud.com/";

    private OffersController(Context context){
        mContext = context;
    }

    public static OffersController getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new OffersController(context);
        }

        return mInstance;
    }


}
