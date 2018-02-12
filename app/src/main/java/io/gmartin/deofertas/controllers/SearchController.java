package io.gmartin.deofertas.controllers;

import android.app.Fragment;
import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.gmartin.deofertas.models.Store;


public class SearchController extends BaseController {

    private Fragment mFragment;

    public SearchController(Context context, Fragment fragment){
        super(context);
        mFragment = fragment;

        if (mFragment instanceof BaseControllerListener) {
            mListener = (BaseControllerListener) mFragment;
        }
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

                    mListener.onDataReceived(storeList);
                }

                @Override
                public void onError(String message) {
                    mListener.onErrorEvent(message);
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
}
