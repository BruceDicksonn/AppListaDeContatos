package com.example.cadastrodecontatossqlite.config;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "db_contatos";
    static final int DB_VERSION = 4;
    private static DbHelper db = null;

    // singleton
    public static synchronized DbHelper getInstance(Context context) {
        if(db == null) {
            db = new DbHelper(context);
        }
        return db;
    }

    private DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {

           ControleDatabase controleDatabase = new ControleDatabase(DB_VERSION, DB_VERSION);
           controleDatabase.execCreate(db);

        } catch (Exception e) {
            Log.e("DbHelper.onCreate", "O Script de criacao do banco de dados falhou: " + e.getMessage());
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {

            ControleDatabase controleDatabase = new ControleDatabase(oldVersion, newVersion);
            controleDatabase.execUpdate(db);

        } catch (Exception e) {
            Log.e("DbHelper.onUpdate", "O Script de atualização do banco de dados falhou: " + e.getMessage());
        }
    }
}
