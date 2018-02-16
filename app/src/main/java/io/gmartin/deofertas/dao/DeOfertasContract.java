package io.gmartin.deofertas.dao;

import android.provider.BaseColumns;

public class DeOfertasContract {

    public static final String DBNAME = "deOfertasDB4";
    public static final Integer VERSION = 1;

    public DeOfertasContract(){};

    public static abstract class OfferTable implements BaseColumns{
        public static final String TABLE_NAME = "offer";
        public static final String HASH_ID = "hash_id";
        public static final String TITLE = "title";
        public static final String DESC = "desc";
        public static final String STORE_ID = "store_id";
        public static final String STORE_NAME = "store_name";
        public static final String PRICE = "price";
        public static final String FAVORITE = "favorite";

        public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
                _ID + " INTEGER PRIMARY KEY, " +
                HASH_ID + " TEXT, " +
                TITLE + " TEXT, " +
                DESC + " TEXT, " +
                STORE_ID + " INTEGER, " +
                STORE_NAME + " TEXT, " +
                PRICE + " REAL, " +
                FAVORITE + " INTEGER );";
    }

    public static abstract class OfferImageTable implements BaseColumns{
        public static final String TABLE_NAME = "offer_images";
        public static final String OFFER_ID = "offer_id";
        public static final String IMAGE_NAME = "image_name";

        public static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
                _ID + " INTEGER PRIMARY KEY, " +
                OFFER_ID + " INTEGER, " +
                IMAGE_NAME + " TEXT );";
    }
}
