package io.gmartin.deofertas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.controllers.RestClient;
import io.gmartin.deofertas.models.Offer;

public class OfferAdapter extends BaseAdapter {

    private List<Offer> mOfferList;
    private Context mContext;
    private RestClient.Result mResultHandler = null;
    private static String mURL = "http://192.168.2.103:8080/deofertas/offer";

    public OfferAdapter(){
        this(null);
    }

    public OfferAdapter(Context context){
        mContext = context;
        RestClient.setContext(context);
        mResultHandler = new RestClient.Result(){
            @Override
            public void onError(String message){
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResult(Object result) {
                fetchOffers();
            }
        };

        fetchOffers();
    }

    public void fetchOffers(){
        try {
            RestClient.get(mURL, new RestClient.Result() {

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

                    notifyDataSetChanged();
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

    /*public void setItemList(List<Offer> mItemList) {
        this.mOfferList = mItemList;
    }*/

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        int count = 0;

        if (mOfferList != null && mOfferList != null) {
            count = mOfferList.size();
        }

        return count;
    }

    @Override
    public Object getItem(int i) {
        return mOfferList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mOfferList.get(i).getId();
    }

    public String getItemHash(int i) {
        return mOfferList.get(i).getHashId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Offer offer = (Offer)getItem(i);

        if (view == null) {
            LayoutInflater li = LayoutInflater.from(mContext);
            view = li.inflate(R.layout.offer_layout, null);
        }

        TextView store = view.findViewById(R.id.txtStore);
        TextView price = view.findViewById(R.id.txtPrice);
        TextView desc = view.findViewById(R.id.txtDesc);

        store.setText(offer.getStoreName());
        desc.setText(offer.getDesc());
        price.setText(String.format("$%.2f", offer.getPrice()));

        return view;
    }
}
