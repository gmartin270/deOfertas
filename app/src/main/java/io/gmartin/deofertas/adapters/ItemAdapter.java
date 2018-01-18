package io.gmartin.deofertas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import io.gmartin.deofertas.R;
import io.gmartin.deofertas.controllers.RestClient;
import io.gmartin.deofertas.models.Item;

public class ItemAdapter extends BaseAdapter {

    private List<Item> mItemList;
    private Context mContext;
    private RestClient.Result mResultHandler = null;
    private static ItemAdapter mInstance;

    /*public ItemAdapter (){
        this(null, null);
    }

    public ItemAdapter (Context context){
        this(context, null);
    }

    public ItemAdapter (Context context, List<Item> itemList){
        mContext = context;
        mItemList = itemList;
    }*/

    private ItemAdapter (Context context) {
        mContext = context;
    }

    public static ItemAdapter getInstance(Context context){
        if (mInstance == null) {
            mInstance = new ItemAdapter(context);
        }

        return mInstance;
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
