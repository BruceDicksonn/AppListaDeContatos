package com.example.cadastrodecontatossqlite.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.cadastrodecontatossqlite.utils.Utils;

import java.io.Serializable;

public class Contato implements Serializable {

    int _id;
    byte[] foto;
    String nome;
    String celular = "";
    String telefone = "";
    String email = "";
    int image_id;

    public Contato(){}

    public Contato(String nome, String telefone, byte[] blob){
        this.nome = nome;
        this.telefone = telefone;
        this.foto = blob;
    }

    public Contato(int id, String nome, String celular, String telefone, String email ,byte[] foto, int id_foto){
        this._id = id;
        this.nome = nome;
        this.celular = celular;
        this.telefone = telefone;
        this.email = email;
        this.foto = foto;
        this.image_id = id_foto;
    }

    public byte[] getFoto() {return foto;}

    public void setFoto(byte[] foto) {this.foto = foto;}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
