package io.gmartin.deofertas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.controllers.OffersController;
import io.gmartin.deofertas.controllers.RestClient;
import io.gmartin.deofertas.models.Item;

public class ItemAdapter extends BaseAdapter {

    private List<Item> mItemList;
    private Context mContext;
    private RestClient.Result mResultHandler = null;
    private static String mURL = "http://192.168.0.159:8080/deofertas/offer";

    public ItemAdapter (){
        this(null);
    }

    public ItemAdapter (Context context){
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
                    //usrs = (JSONArray) result;

                    JSONArray offers = new JSONArray();
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

    public void setItemList(List<Item> mItemList) {
        this.mItemList = mItemList;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        int count = 0;

        if (mItemList != null) {
            count = mItemList.size();
        }

        return count;
    }

    @Override
    public Object getItem(int i) {
        return mItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItemList.get(i).getId();
    }

    public String getItemHash(int i) {
        return mItemList.get(i).getHashId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Item item = (Item)getItem(i);

        if (view == null) {
            LayoutInflater li = LayoutInflater.from(mContext);
            view = li.inflate(R.layout.item_layout, null);
        }

        TextView store = (TextView)view.findViewById(R.id.txtStore);
        TextView price = (TextView)view.findViewById(R.id.txtPrice);
        TextView desc = (TextView)view.findViewById(R.id.txtDesc);

        store.setText(item.getStore());
        desc.setText(item.getDesc());
        price.setText(String.format("$%.2f",item.getPrice()));

        return view;
    }
}
