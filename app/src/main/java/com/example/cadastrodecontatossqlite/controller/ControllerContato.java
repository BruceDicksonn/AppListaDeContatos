package com.example.cadastrodecontatossqlite.controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.cadastrodecontatossqlite.dao.ContatoDAO;
import com.example.cadastrodecontatossqlite.model.Contato;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ControllerContato implements interfaceMethods{

    ContatoDAO dao;

    public ControllerContato(Context context) {
        this.dao = new ContatoDAO(context);
    }

    @Override
    public boolean insert(Contato contato) {

        if(contato != null) {
            if(dao.insert(contato) != -1) return true;
        }

        return false;

    }

    @Override
    public boolean delete(Contato contato) {

        try {

        } catch (Exception e) {
            Log.e("Info_DB", e.getMessage());
        }

        return false;
    }

    @Override
    public boolean update(Contato contato) {

        try {

            if(contato != null) {

                if(dao.update(contato)) return true;

            }

        } catch (Exception e) {
            Log.e("Info_DB", e.getMessage());
        }

        return false;
    }

    @SuppressLint("Range")
    public Contato find(int id) {

        Contato contato = new Contato();
        Cursor cursor = dao.select(id);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){

            int _id = cursor.getInt( cursor.getColumnIndex("_id") );
            String nome = cursor.getString( cursor.getColumnIndex("nome") );
            String cel = cursor.getString( cursor.getColumnIndex("celular") );
            String fone = cursor.getString( cursor.getColumnIndex("telefone") );
            String email = cursor.getString( cursor.getColumnIndex("email") );
            byte[] blob = cursor.getBlob( cursor.getColumnIndex("data")   );
            int id_foto = cursor.getInt( cursor.getColumnIndex("id_foto")   );

            Contato c = new Contato(id,nome,cel,fone,email,blob, id_foto);
            contato = c;

            cursor.moveToNext();


        }

        return contato;
    }

    @SuppressLint("Range")
    @Override
    public List<Contato> findAll() {

        List<Contato> list = new ArrayList<>();
        Cursor cursor = dao.select();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()){

            int id = cursor.getInt( cursor.getColumnIndex("_id") );
            String nome = cursor.getString( cursor.getColumnIndex("nome") );
            String cel = cursor.getString( cursor.getColumnIndex("celular") );
            String fone = cursor.getString( cursor.getColumnIndex("telefone") );
            String email = cursor.getString( cursor.getColumnIndex("email") );
            byte[] blob = cursor.getBlob( cursor.getColumnIndex("data")   );
            int id_foto = cursor.getInt( cursor.getColumnIndex("id_foto")   );

            Contato contato = new Contato(id,nome,cel,fone,email,blob, id_foto);
            list.add(contato);

            cursor.moveToNext();


        }

        return list;
    }


}
