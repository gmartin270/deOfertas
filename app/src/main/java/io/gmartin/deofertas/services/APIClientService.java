package io.gmartin.deofertas.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import io.gmartin.deofertas.controllers.RestClient;
import io.gmartin.deofertas.models.Offer;

public class APIClientService extends Service {
    APIBinder mBinder = new APIBinder();
    //OfferAdapter mAdapter = null;
    public static String mURL = "http://tm5-agmoyano.rhcloud.com/";//"http://192.168.1.18:8080/ws/";
    private Timer timer = new Timer();

    public APIClientService(){}

    public class APIBinder extends Binder{
        private Context mContext;

        /*RestClient.Result resultHandler = new RestClient.Result() {
            @Override
            public void onResult(Object result) {
                searchOffers();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(APIClientService.this, message, Toast.LENGTH_LONG);
            }
        };

        public void setContext(Context context) {
            mContext = context;
            RestClient.setContext(mContext);
        }*/

        /*public List<Offer> searchOffers(){
            //TODO: remove hardcode and implement a real REST operation
            try{
                if(mContext != null) {
                    RestClient.get(mURL, new RestClient.Result() {
                        @Override
                        public void onResult(Object result) {
                            mAdapter = UserAdapter.getInstance(mActivity);

                            mAdapter.setList((JSONArray) result);
                        }

                        @Override
                        public void onError(String message) {
                            Toast.makeText(APIClientService.this, message, Toast.LENGTH_SHORT);
                        }
                    });
                }
            }catch(IOException e){
                Toast.makeText(APIClientService.this, e.getMessage(), Toast.LENGTH_SHORT);
            }


            return offers;
        }*/
    }

    @Override
    public void onCreate() {
        /*timer.schedule(new TimerTask(){
            @Override
            public void run() {
                mBinder.searchOffers();
            }
        }, 0, 5000);*/
    }

    @Override
    public void onDestroy() {
        timer.cancel();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
}