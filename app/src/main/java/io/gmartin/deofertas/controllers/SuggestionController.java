package io.gmartin.deofertas.controllers;

import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.gmartin.deofertas.models.SuggestedOffer;


public class SuggestionController extends BaseController {

    private SuggestionControllerListener mSuggestionControllerListener;

    public interface SuggestionControllerListener {
        void onSuggestionDataReceived(List<SuggestedOffer> suggestedOffers);
        void onLastSuggestionDataReceived(SuggestedOffer suggestedOffer);
    }

    public SuggestionController(Context context){
        super(context);

        if (mContext instanceof BaseControllerListener) {
            mListener = (BaseControllerListener) mContext;
        }

        if (mContext instanceof SuggestionControllerListener) {
            mSuggestionControllerListener = (SuggestionControllerListener) mContext;
        }
    }

    public void fetchSuggestions(){
        String endPoint = "/suggestion";

        try {
            RestClient.get(mURL + endPoint, new RestClient.Result() {

                @Override
                public void onResult(Object result) {
                    List<SuggestedOffer> suggestionsList = new ArrayList<>();
                    JSONArray jsonArray = (JSONArray) result;
                    JSONObject jsonObject;
                    Gson gson = new Gson();

                    for(int i=0; i < jsonArray.length(); i++) {
                        try {
                            jsonObject = jsonArray.getJSONObject(i);
                            suggestionsList.add(gson.fromJson(jsonObject.toString(), SuggestedOffer.class));
                        } catch (Exception e) {

                        }
                    }

                    mSuggestionControllerListener.onSuggestionDataReceived(suggestionsList);
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

    public void fetchLastSuggestion(){
        String endPoint = "/suggestion/last?suggestion_id=11";

        try {
            RestClient.get(mURL + endPoint, new RestClient.Result() {

                @Override
                public void onResult(Object result) {
                    SuggestedOffer suggestion = null;
                    JSONObject jsonObject = (JSONObject) result;
                    Gson gson = new Gson();

                    try {
                        suggestion = gson.fromJson(jsonObject.toString(), SuggestedOffer.class);
                    } catch (Exception e) {

                    }

                    mSuggestionControllerListener.onLastSuggestionDataReceived(suggestion);
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
}
