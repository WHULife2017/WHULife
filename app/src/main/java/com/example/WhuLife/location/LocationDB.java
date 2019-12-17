package com.example.WhuLife.location;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class LocationDB extends SQLiteOpenHelper {

    public static final String CREATE_PACKAGE = "create table package ("
            + "id integer primary key autoincrement, "
            + "package_location text, "
            + "package_company text, "
            + "latitude real, "
            + "longtitude real, "
            + "inform_time text)";

    private Context mContext;

    public LocationDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_PACKAGE);
        Toast.makeText(mContext, "create success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
//        db.execSQL("drop table if exists package");
//        onCreate(db);
//        Toast.makeText(mContext, "update success", Toast.LENGTH_SHORT).show();
    }
}
