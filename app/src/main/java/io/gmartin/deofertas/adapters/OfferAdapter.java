package io.gmartin.deofertas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
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

    public OfferAdapter(Context context){
        mContext = context;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        int count = 0;

        if (mOfferList != null) {
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
        ImageView favoriteImage = view.findViewById(R.id.favorite_image);

        store.setText(offer.getStoreName());
        desc.setText(offer.getDesc());
        price.setText(String.format("$%.2f", offer.getPrice()));

        return view;
    }

    public void updateList(List<Offer> offers) {
        mOfferList = offers;
        notifyDataSetChanged();
    }
}
