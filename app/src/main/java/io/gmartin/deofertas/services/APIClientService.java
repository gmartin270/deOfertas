package io.gmartin.deofertas.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.gmartin.deofertas.adapters.ItemAdapter;
import io.gmartin.deofertas.controllers.RestClient;
import io.gmartin.deofertas.models.Item;

public class APIClientService extends Service {
    APIBinder mBinder = new APIBinder();
    ItemAdapter mAdapter = null;
    public static String mURL = "http://tm5-agmoyano.rhcloud.com/";//"http://192.168.1.18:8080/ws/";
    private Timer timer = new Timer();

    public APIClientService(){}

    public class APIBinder extends Binder{
        //private MainActivity mActivity = null;
        private Context mContext;

        RestClient.Result resultHandler = new RestClient.Result() {
            @Override
            public void onResult(Object result) {
                searchOffers();
            }

            @Override
            public void onError(String message) {
                Toast.makeText(APIClientService.this, message, Toast.LENGTH_LONG);
            }
        };

        /*public void setActivity(MainActivity activity) {
            mActivity = activity;
            RestClient.context = activity;
        }*/

        public void setContext(Context context) {
            mContext = context;
            RestClient.setContext(mContext);
        }

        public void searchOffers(){
            //TODO: remove hardcode and implement a real REST operation
            /*try{
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
            }*/

            List<Item> items = new ArrayList<Item>();

            Item item = new Item();
            item.setId(1);
            item.setDesc("Prueba 1");
            item.setStore("Garbarino");
            item.setPrice(100);

            items.add(item);

            item = new Item();
            item.setId(1);
            item.setDesc("Prueba 2");
            item.setStore("Musimundo");
            item.setPrice(350);

            items.add(item);

            mAdapter = ItemAdapter.getInstance(mContext);
            mAdapter.setItemList(items);
        }
    }

    @Override
    public void onCreate() {
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                mBinder.searchOffers();
            }
        }, 0, 5000);
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
