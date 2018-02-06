package io.gmartin.deofertas.controllers;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.gmartin.deofertas.models.Store;


public class SearchController {

    private Context mContext;
    private static String mURL = "http://192.168.0.159:8080/deofertas";
    private RestClient.Result mResultHandler = null;
    private SearchControllerListener mSearchListener;

    public SearchController(Context context){
        mContext = context;

        if (context instanceof SearchController.SearchControllerListener) {
            mSearchListener = (SearchController.SearchControllerListener) context;
        }

        RestClient.setContext(context);
        mResultHandler = new RestClient.Result(){
            @Override
            public void onError(String message){
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResult(Object result) {

            }
        };
    }

    public void fetchStores(){
        String endPoint = "/store";
        String query;

        try {
            query = makeQuery();

            RestClient.get(mURL + endPoint + query, new RestClient.Result() {

                @Override
                public void onResult(Object result) {
                    List<Store> storeList = new ArrayList<>();
                    JSONArray jsonArray = (JSONArray) result;
                    JSONObject jsonObject;
                    Gson gson = new Gson();

                    for(int i=0; i < jsonArray.length(); i++) {
                        try {
                            jsonObject = jsonArray.getJSONObject(i);
                            storeList.add(gson.fromJson(jsonObject.toString(), Store.class));
                        } catch (Exception e) {

                        }
                    }

                    mSearchListener.onDataReceived(storeList);
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<String> storeToString(List<Store> stores) {
        List<String> storeString = new ArrayList<>();

        if (stores != null) {
            for (Store store : stores) {
                storeString.add(store.getName());
            }
        }

        return storeString;
    }

    private String makeQuery() {
        String query = "";

        return query;
    }

    public interface SearchControllerListener {
        void onDataReceived(Object data);
    }
}
