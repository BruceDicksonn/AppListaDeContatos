package com.example.cadastrodecontatossqlite.controller;

import com.example.cadastrodecontatossqlite.model.Contato;
import java.util.List;

public interface interfaceMethods {
    boolean insert(Contato contato);
    boolean delete(Contato contato);
    boolean update(Contato contato);
    List<Contato> findAll();
}
