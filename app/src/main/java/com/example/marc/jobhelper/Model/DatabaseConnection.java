package com.example.marc.jobhelper.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Stellt den Zugriffspunkt in die Datenbank für persistente Datenspeicherung dar.
 * Erstellt von Marc am 17.06.17.
 */

public class DatabaseConnection extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "JobHelperDB";
    private static final String TABLE_NAME = "Companies";
    private static final int DATABASE_VERSION = 4;

    private static final String KEY_ID = "id";
    private static final String KEY_BLOB = "blob";
    public static final int DEFAULT_ID = -1;

    private static final String[] COLUMNS = {KEY_ID, KEY_BLOB};

    private static DatabaseConnection connection;

    public static DatabaseConnection getInstance(Context context){
        if(connection == null) connection = new DatabaseConnection(context);
        return connection;
    }

    private DatabaseConnection(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CreateCompanyTable = "CREATE TABLE " + TABLE_NAME + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_BLOB + " BLOB "
                + ")";
        db.execSQL(CreateCompanyTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DeleteCompanyTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DeleteCompanyTable);
        this.onCreate(db);
    }


    public Company loadCompany(int index){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, " " + KEY_ID + " = ?", new String[]{String.valueOf(index)}, null, null, null, null);

        if(cursor != null)
            cursor.moveToFirst();
        byte[] blob = cursor.getBlob(cursor.getColumnIndex(KEY_BLOB));
        String json = new String(blob);
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<Company>() {}.getType());
    }
    /**
     * Alle Companies komplett aus der Datenbank holen, um hohe Reaktionsgeschwindigeit zu erhalten.
     * @return Liste die alle Objekte vom Typ Company beinhaltet.
     */

    public List<Company> loadAllCompaines(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Company> companies = new ArrayList<>();
        Company tempCompany;
        boolean nextCompAvail;
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
            nextCompAvail = true;
            while(nextCompAvail){
                byte[] blob = cursor.getBlob(cursor.getColumnIndex(KEY_BLOB));
                String json = new String(blob);
                Gson gson = new Gson();
                tempCompany = gson.fromJson(json, new TypeToken<Company>() {}.getType());
                companies.add(tempCompany);
                nextCompAvail = cursor.moveToNext();
            }
        }
        //TODO Alle Companies in die ArrayList laden.

        db.close();
        return companies;

    }

    public void saveCompanies(List<Company> companies){
        SQLiteDatabase db = this.getWritableDatabase();
        Gson gson = new Gson();
        ContentValues values = new ContentValues();
        for (Company tempCompany : companies) {
            values.put(KEY_BLOB, gson.toJson(tempCompany).getBytes());
        }

        db.insert(TABLE_NAME, null, values);
        db.close();

    }
}




