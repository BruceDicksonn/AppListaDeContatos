package com.example.cadastrodecontatossqlite.controller;

import android.content.Context;

import com.example.cadastrodecontatossqlite.dao.FotoDAO;
import com.example.cadastrodecontatossqlite.model.Foto;

public class ControllerFoto {

    FotoDAO dao;

    public ControllerFoto(Context context) {
        dao = new FotoDAO(context);
    }

    public Foto inserir(Foto foto) { // retorna o id da foto adicionada

        if(foto != null) {
            long id = dao.insert(foto);
            if(id >= 0) {
                return dao.find(id);
            }
        }

        return null;

    }

    public Foto atualizar(Foto foto) {

        if(foto != null) {
            if(dao.update(foto)) {
                return dao.find(foto.get_id());
            }
        }

        return null;

    }

}
