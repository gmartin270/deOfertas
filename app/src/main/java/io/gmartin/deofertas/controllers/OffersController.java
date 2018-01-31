package io.gmartin.deofertas.controllers;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.gmartin.deofertas.models.Offer;
import io.gmartin.deofertas.models.Search;

public class OffersController {

    private Context mContext;
    private static String mURL = "http://192.168.0.159:8080/deofertas";
    private List<Offer> mOfferList;
    private RestClient.Result mResultHandler = null;
    private OfferControllerListener mOfferListener;

    public OffersController(Context context){
        mContext = context;

        if (context instanceof OffersController.OfferControllerListener) {
            mOfferListener = (OffersController.OfferControllerListener) context;
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

    public void fetchOffers(Search search){
        String endPoint = "/offer";
        String query;

        try {
            query = makeQuery(search);

            RestClient.get(mURL + endPoint + query, new RestClient.Result() {

                @Override
                public void onResult(Object result) {
                    mOfferList = new ArrayList<>();
                    JSONArray offersJSON = (JSONArray) result;
                    JSONObject offerJSON;
                    Gson gson = new Gson();

                    for(int i=0; i < offersJSON.length(); i++) {
                        try {
                            offerJSON = offersJSON.getJSONObject(i);
                            mOfferList.add(gson.fromJson(offerJSON.toString(), Offer.class));
                        } catch (Exception e) {

                        }
                    }

                    mOfferListener.onDataReceived(mOfferList);
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

    private String makeQuery(Search search) {
        List<String> params = new ArrayList<>();
        String query = "";

        if (search != null) {
            if (search.getText() != null && search.getText().length() > 0) {
                params.add("desc=" + search.getText());
            }
        }

        if (params.size() > 0) {
            for (String param: params) {
                if (query.length() == 0) {
                    query = "?" + param;
                } else {
                    query += "&" + param;
                }
            }
        }

        return query;
    }

    public interface OfferControllerListener {
        void onDataReceived(Object data);
    }
}
