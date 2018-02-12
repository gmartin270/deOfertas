package io.gmartin.deofertas.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class BaseController implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String PREF_URL = "pref_URL_API";
    private static final String DEFAULT_URL = "http://192.168.0.159:8080/deofertas";
    private SharedPreferences sharedPref;
    protected Context mContext;
    protected String mURL;
    protected BaseControllerListener mListener;
    protected RestClient.Result mResultHandler = null;

    public BaseController(Context context) {
        mContext = context;
        sharedPref = PreferenceManager.getDefaultSharedPreferences(mContext);
        setURL();
        sharedPref.registerOnSharedPreferenceChangeListener(this);

        RestClient.setContext(mContext);
        mResultHandler = new RestClient.Result(){
            @Override
            public void onError(String message){
                mListener.onErrorEvent(message);
            }

            @Override
            public void onResult(Object result) {

            }
        };
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(PREF_URL)) {
            setURL();
        }
    }

    private void setURL() {
        mURL = sharedPref.getString(PREF_URL, DEFAULT_URL);
    }

    public interface BaseControllerListener {
        void onDataReceived(Object data);

        void onErrorEvent(String message);
    }
}
