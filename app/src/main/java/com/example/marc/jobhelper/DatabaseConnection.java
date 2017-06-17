package com.example.marc.jobhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by marc on 17.06.17.
 */

public class DatabaseConnection extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "JobHelperDB";
    private static final String TABLE_NAME = "Companies";
    private static final int DATABASE_VERSION = 4;


    public DatabaseConnection(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CreateCompanyTable = "CREATE TABLE " + TABLE_NAME + " ( " ;//TODO Benötigte Felder Eintragen
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}




