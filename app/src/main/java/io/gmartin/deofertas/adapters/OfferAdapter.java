package io.gmartin.deofertas.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import io.gmartin.deofertas.R;
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
        TextView title = view.findViewById(R.id.txtTitle);
        ImageView favoriteImage = view.findViewById(R.id.favorite_image);
        ImageView offerImage = view.findViewById(R.id.offer_image);

        if (offer.isFavorite()) {
            favoriteImage.setVisibility(View.VISIBLE);
        }

        store.setText(offer.getStoreName());
        title.setText(offer.getTitle());
        price.setText(String.format("$%.2f", offer.getPrice()));

        if (mOfferList.get(i).getImageStr() != null && mOfferList.get(i).getImageStr().length() > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(mOfferList.get(i).getImage(), 0, mOfferList.get(i).getImage().length);
            offerImage.setImageBitmap(bitmap);
        } else {
            offerImage.setImageResource(R.mipmap.ic_no_image_available);
        }

        return view;
    }

    public void updateList(List<Offer> offers) {
        mOfferList = offers;
        notifyDataSetChanged();
    }
}
/*
package io.gmartin.deofertas.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.OfferViewHolder> {

    public interface ItemClickListener {
        void onClick(View view, int position);
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView mOfferCardView;
        ImageView mOfferImage;
        ImageView mFavoriteImage;
        AppCompatTextView mTitle;
        AppCompatTextView mPrice;
        AppCompatTextView mStore;

        public OfferViewHolder(View itemView) {
            super(itemView);
            mOfferCardView = itemView.findViewById(R.id.offer_card_view);
            mOfferImage = itemView.findViewById(R.id.offer_image);
            mFavoriteImage = itemView.findViewById(R.id.favorite_image);
            mTitle = itemView.findViewById(R.id.txtTitle);
            mPrice = itemView.findViewById(R.id.txtPrice);
            mStore = itemView.findViewById(R.id.txtStore);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }

    private List<Offer> mOfferList;
    private Context mContext;
    private ItemClickListener clickListener;

    public OfferAdapter(Context context){
        mContext = context;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        int count = 0;

        if (mOfferList != null) {
            count = mOfferList.size();
        }

        return count;
    }

    @Override
    public OfferViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.offer_layout, viewGroup, false);
        OfferViewHolder offerViewHolder = new OfferViewHolder(view);
        return offerViewHolder;
    }

    @Override
    public void onBindViewHolder(OfferViewHolder offerViewHolder, int i) {
        offerViewHolder.mTitle.setText(mOfferList.get(i).getTitle());
        offerViewHolder.mPrice.setText(String.format("$%.2f", mOfferList.get(i).getPrice()));
        offerViewHolder.mStore.setText(mOfferList.get(i).getStoreName());

        if (mOfferList.get(i).isFavorite()) {
            offerViewHolder.mFavoriteImage.setVisibility(View.VISIBLE);
        }


        if (mOfferList.get(i).getImageStr() != null && mOfferList.get(i).getImageStr().length() > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(mOfferList.get(i).getImage(), 0, mOfferList.get(i).getImage().length);
            offerViewHolder.mOfferImage.setImageBitmap(bitmap);
        } else {
            offerViewHolder.mOfferImage.setImageResource(R.mipmap.ic_no_image_available);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public Object getItem(int i) {
        return mOfferList.get(i);
    }

    */
/*@Override
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

        TextView desc = view.findViewById(R.id.txtTitle);
        TextView price = view.findViewById(R.id.txtPrice);
        TextView store = view.findViewById(R.id.txtStore);
        ImageView offerImage = view.findViewById(R.id.offer_image);
        ImageView favoriteImage = view.findViewById(R.id.favorite_image);

        if (offer.isFavorite()) {
            favoriteImage.setVisibility(View.VISIBLE);
        }

        desc.setText(offer.getTitle());
        price.setText(String.format("$%.2f", offer.getPrice()));
        store.setText(offer.getStoreName());

        if (offer.getImageStr() != null && offer.getImageStr().length() > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(offer.getImage(), 0, offer.getImage().length);
            offerImage.setImageBitmap(bitmap);
        } else {
            offerImage.setImageResource(R.mipmap.ic_no_image_available);
        }

        return view;
    }*//*


    public void updateList(List<Offer> offers) {
        mOfferList = offers;
        notifyDataSetChanged();
    }
}
*/
