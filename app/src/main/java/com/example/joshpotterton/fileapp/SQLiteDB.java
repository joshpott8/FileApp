package com.example.joshpotterton.fileapp;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class SQLiteDB extends SQLiteOpenHelper{

    public SQLiteDB(Context context){
        super(context, "textEntries.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createCmd = "CREATE TABLE Entries (id TEXT PRIMARY KEY, User TEXT, Entry TEXT)";

        try {
            db.execSQL(createCmd);
            db.close();
        }
        catch(SQLException e){
            Log.v("Error", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addEntry(String user, String text){

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd hh:mm:ss");
        dateFormat.setLenient(false);
        Date today = new Date();
        String date = dateFormat.format(today);

        //String insertQuery = "INSERT Entries(id, User, Entry) VALUES('" + user + "/" + date +
         //       "','" + user + "'" + "'" + text + "')";

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            values.put("id", user + "/" + date);
            values.put("User", user);
            values.put("Entry", text);

            db.insert("Entries", null, values);
            db.close();

            //db.execSQL(insertQuery);

        }
        catch(SQLException e){
            Log.v("Error", e.getMessage());
        }
    }

    public ArrayList<HashMap<String, String>> getEntries(){
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();

        String selectQuery = "SELECT * FROM Entries";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("id", cursor.getString(0));
                map.put("User", cursor.getString(1));
                map.put("Text", cursor.getString(2));

                arrayList.add(map);
            } while(cursor.moveToNext());
        }
        return arrayList;
    }

}
