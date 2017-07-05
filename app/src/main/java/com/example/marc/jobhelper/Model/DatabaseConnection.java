package com.example.marc.jobhelper.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
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

    /**
     * Gibt die Verbindung an die Datenbank nach dem Singleton-Pattern zurück.
     * @param context Kontext der App
     * @return DatabaseConnection
     */
    public static DatabaseConnection getInstance(Context context){
        if(connection == null) connection = new DatabaseConnection(context);
        return connection;
    }

    /**
     * Stellt die Datenbankverbindung her.
     * @param context Kontext der App
     */
    private DatabaseConnection(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /**
     * Löscht die Tabelle und erstellt sie neu.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String DeleteCompanyTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DeleteCompanyTable);
        String CreateCompanyTable = "CREATE TABLE " + TABLE_NAME + " ( "
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_BLOB + " BLOB "
                + ")";
        db.execSQL(CreateCompanyTable);
    }

    /**
     * Wenn sich die Version der Datenbank ändert, wird alles gelöscht und neu erstellt.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DeleteCompanyTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(DeleteCompanyTable);
        this.onCreate(db);
    }

    /**
     * Lädt eine Firma mit dem angegebenen Index. Eine Firma wird dabei aus dem Blob in der Datenbank
     * zu einem Json transformiert und anschließend wieder zu einem Objekt.
     * @param index Index der Firma
     * @return Company
     */
    public Company loadCompany(int index){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if(index == DEFAULT_ID) return null;
        try {
            cursor = db.query(TABLE_NAME, COLUMNS, " " + KEY_ID + " = ?", new String[]{String.valueOf(index)}, null, null, null, null);
        }
        catch(android.database.CursorIndexOutOfBoundsException ex){}


        if(cursor != null)
            if(!cursor.moveToFirst()) return null;

        //Aus dem BLOB aus der Datenbank erst ein Json und dann wieder ein Objekt machen.
        byte[] blob = cursor.getBlob(cursor.getColumnIndex(KEY_BLOB));
        byte[] cutBlob = Arrays.copyOfRange(blob, 0,  blob.length-1);
        String json = new String(cutBlob);
        Gson gson = new Gson();
        cursor.close();
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
        Cursor cursor = db.query(TABLE_NAME, COLUMNS, null, null, null, null, null, null);
        if(cursor != null){
            if(!cursor.moveToFirst()) return null;
            do{
                //Aus BLOBs einen nach dem anderen wie oben Objekte machen und in der Liste speichern.
                byte[] blob = cursor.getBlob(cursor.getColumnIndex(KEY_BLOB));
                byte[] cutBlob = Arrays.copyOfRange(blob, 0,  blob.length-1);
                String json = new String(cutBlob);
                Gson gson = new Gson();
                tempCompany = gson.fromJson(json, new TypeToken<Company>(){}.getType()); //FIXME
                companies.add(tempCompany);
            }while(cursor.moveToNext());
        }
        db.close();
        return companies;

    }

    /**
     * Speichert die komplette Liste an Companies.
     * @param companies
     */
    public void saveCompanies(List<Company> companies){
        SQLiteDatabase db = this.getWritableDatabase();
        Gson gson = new Gson();
        ContentValues values = new ContentValues();
        for (Company tempCompany : companies) {
            values.put(KEY_ID, tempCompany.getIndex());
            values.put(KEY_BLOB, gson.toJson(tempCompany).getBytes());
        }

        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    /**
     * Löscht zuerst die Company beim Index ebendieser Company und speichert dann die neue Company an diese Stelle.
     * Beim Speichern wird zuerst aus dem Objekt mittels Gson ein Json gemacht. Dieses Json wird dann als Blob
     * in der Datenbank gespeichert.
     * @param company
     */
    public void addCompany(Company company) {
        this.removeCompanyAtIndex(company.getIndex());
        SQLiteDatabase db = this.getWritableDatabase();
        Gson gson = new Gson();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, company.getIndex());
        values.put(KEY_BLOB, gson.toJson(company)); //aus der Company ein Json machen und das als BLOB in der Datenbank speichern.
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    /**
     * Löscht den eintrag am angegebenen Index.
     * @param index Stelle, die gelöscht werden soll.
     */
    public void removeCompanyAtIndex(int index){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + " = ?", new String[]{String.valueOf(index)});
        db.close();
    }
}




