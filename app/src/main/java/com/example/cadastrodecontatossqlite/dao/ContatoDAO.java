package com.example.cadastrodecontatossqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.cadastrodecontatossqlite.config.DbHelper;
import com.example.cadastrodecontatossqlite.model.Contato;

import java.util.List;

public class ContatoDAO {

    String table = "contatos";
    String columns[] = null;
    String where = "";
    String having = "";
    String orderBy = "";

    SQLiteDatabase write;
    SQLiteDatabase read;

    public ContatoDAO(Context context) {
        this.write = DbHelper.getInstance(context).getWritableDatabase();
        this.read = DbHelper.getInstance(context).getReadableDatabase();
    }


    public long insert(Contato contato) {

        try{

            ContentValues cv = new ContentValues();

            cv.put("nome", contato.getNome());
            cv.put("celular", contato.getCelular());
            cv.put("telefone", contato.getTelefone());
            cv.put("email", contato.getEmail());
            cv.put("id_foto", contato.getImage_id());

            return write.insert(table, null, cv);


        } catch(Exception e){
            e.printStackTrace();
        }

        return -1;
    }

    public boolean delete(Contato contato) {

        try{

        } catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public boolean update(Contato contato) {

        String where = "_id = ?";
        String[] whereArgs = new String[]{String.valueOf(contato.get_id())};

        try{

            ContentValues cv = new ContentValues();

            cv.put("nome", contato.getNome());
            cv.put("celular", contato.getCelular());
            cv.put("telefone", contato.getTelefone());
            cv.put("email", contato.getEmail());

            write.update(table, cv, where, whereArgs);

            return true;


        } catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }

    public Cursor select(int id) {

        table = "contatos LEFT JOIN fotos ON contatos.id_foto = fotos._id";
        columns = new String[]{"contatos.*","fotos.data"};
        where = "contatos._id = ?";
        String[] whereArgs = new String[] {String.valueOf(id)};

        try{

            Cursor cursor = read.query(table, columns, where, whereArgs, null, null, null);
            if(cursor != null) {
                return cursor;
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public Cursor select() {

        table = "contatos LEFT JOIN fotos ON contatos.id_foto = fotos._id";
        columns = new String[]{"contatos.*","fotos.data"};

        try{

            Cursor cursor = read.query(table, columns, null, null, null, null, null);

            if(cursor != null) {
                return cursor;
            }

        } catch(Exception e){
            Log.e("Info_DB", e.getMessage());
        }

        return null;
    }
}
