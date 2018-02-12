package io.gmartin.deofertas.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OfferDBHelper extends SQLiteOpenHelper {

    private static OfferDBHelper self = null;

    private OfferDBHelper(Context context) {
        super(context, OfferContract.DBNAME, null, OfferContract.VERSION);
    }

    public static OfferDBHelper getInstance(Context context){
        if(self == null)
            self = new OfferDBHelper(context);

        return self;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(OfferContract.OfferTable.TABLE_CREATE);
        db.execSQL(OfferContract.OfferImageTable.TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}


}
