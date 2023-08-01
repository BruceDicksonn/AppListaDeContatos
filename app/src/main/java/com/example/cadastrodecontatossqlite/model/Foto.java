package com.example.cadastrodecontatossqlite.model;

import java.io.Serializable;

public class Foto implements Serializable {

    int _id;
    byte[] data;

    public Foto(){}

    public Foto(int _id, byte[] data) {this._id = _id;this.data = data;}

    public Foto(byte[] data) {this.data = data;}

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
