package io.gmartin.deofertas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import io.gmartin.deofertas.models.Offer;

public class OfferDBHelper extends SQLiteOpenHelper {

    private static OfferDBHelper self = null;

    private OfferDBHelper(Context context) {
        super(context, DeOfertasContract.DBNAME, null, DeOfertasContract.VERSION);
    }

    public static OfferDBHelper getInstance(Context context){
        if(self == null)
            self = new OfferDBHelper(context);

        return self;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DeOfertasContract.OfferTable.TABLE_CREATE);
        //db.execSQL(DeOfertasContract.OfferImageTable.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}

    public void insertOffer(Offer offer) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DeOfertasContract.OfferTable._ID, offer.getId());
        values.put(DeOfertasContract.OfferTable.HASH_ID, offer.getHashId());
        values.put(DeOfertasContract.OfferTable.TITLE, offer.getTitle());
        values.put(DeOfertasContract.OfferTable.DESC, offer.getDesc());
        values.put(DeOfertasContract.OfferTable.STORE_ID, offer.getStoreId());
        values.put(DeOfertasContract.OfferTable.STORE_NAME, offer.getStoreName());
        values.put(DeOfertasContract.OfferTable.PRICE, offer.getPrice());
        values.put(DeOfertasContract.OfferTable.IMAGE, offer.getImage());
        values.put(DeOfertasContract.OfferTable.LINK, offer.getLink());

        db.insert(DeOfertasContract.OfferTable.TABLE_NAME, null, values);
        db.close();
    }

    public void deleteOffer(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = DeOfertasContract.OfferTable._ID+" = ?";
        String[] args = {id.toString()};

        db.delete(DeOfertasContract.OfferTable.TABLE_NAME, selection, args);
        db.close();
    }

    public List<Offer> readAllOffers() throws Exception {
        List<Offer> offers = null;
        Offer offer;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(
                "select * from " +
                        DeOfertasContract.OfferTable.TABLE_NAME +
                        " order by " +
                        DeOfertasContract.OfferTable._ID, null);

        if(cursor.getCount() > 0) {
            if (offers == null) {
                offers = new ArrayList<>();
            }
            cursor.moveToFirst();

            //TODO: iterate the cursor to get all the records.
            for (int i = 0; i < cursor.getCount(); i++) {
                offer = rowToOffer(cursor);
                offers.add(offer);
                cursor.moveToNext();
            }
        }

        db.close();
        return offers;
    }

    public Offer getOffer(Long id) throws Exception {
        Offer offer = null;
        SQLiteDatabase db = this.getWritableDatabase();
        String[] args = {String.valueOf(id)};
        Cursor cursor = db.rawQuery(
                "select * from " +
                        DeOfertasContract.OfferTable.TABLE_NAME +
                        " where "+DeOfertasContract.OfferTable._ID+" = ?", args);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            offer = rowToOffer(cursor);
        }
        db.close();
        return offer;
    }

    protected Offer rowToOffer(Cursor cursor) throws Exception{
        Offer offer = new Offer();
        try {
            offer.setId(cursor.getLong(cursor.getColumnIndex(DeOfertasContract.OfferTable._ID)));
            offer.setHashId(cursor.getString(cursor.getColumnIndex(DeOfertasContract.OfferTable.HASH_ID)));
            offer.setTitle(cursor.getString(cursor.getColumnIndex(DeOfertasContract.OfferTable.TITLE)));
            offer.setDesc(cursor.getString(cursor.getColumnIndex(DeOfertasContract.OfferTable.DESC)));
            offer.setStoreId(cursor.getLong(cursor.getColumnIndex(DeOfertasContract.OfferTable.STORE_ID)));
            offer.setStoreName(cursor.getString(cursor.getColumnIndex(DeOfertasContract.OfferTable.STORE_NAME)));
            offer.setPrice(cursor.getDouble(cursor.getColumnIndex(DeOfertasContract.OfferTable.PRICE)));
            offer.setImageBlob(cursor.getBlob(cursor.getColumnIndex(DeOfertasContract.OfferTable.IMAGE)));
            offer.setLink(cursor.getString(cursor.getColumnIndex(DeOfertasContract.OfferTable.LINK)));
            offer.setFavorite(true);

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return offer;
    }
}
