package com.example.cadastrodecontatossqlite.config;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;

public class ControleDatabase {

    private ArrayList<String[]> sql;
    int oldVersion, newVersion;

    public ControleDatabase(int oldVersion, int newVersion) {

        this.oldVersion = oldVersion;
        this.newVersion = newVersion;

        // a lista interna é iniciada com o limite da versão
        sql = new ArrayList<String[]>(newVersion + 1);

        // inicializa a lista com todos os valores nulos
        for(int i=1; i <= newVersion; i++){sql.add(null);};

        // preenche a lista
        initSqlUpdate();
    }

    public void execUpdate(SQLiteDatabase db)
    {
        for (int i=1; i <= newVersion; i++)
        {
            if (i > oldVersion && i <= newVersion)
            {
                if (sql.get(i) != null)
                {
                    String[] sqlArray = sql.get(i);

                    for (String comando : sqlArray)
                        execSQL(db, comando);
                }
            }
        }
    }

    public void execSQL(SQLiteDatabase db, String comando){
        try {
            db.execSQL(comando);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initSqlUpdate() {

        sql.add(4, new String[] {
                "ALTER TABLE contatos RENAME COLUMN fone TO celular",
                "ALTER TABLE contatos ADD COLUMN email VARCHAR(80)",
                "ALTER TABLE contatos ADD COLUMN telefone VARCHAR(80)"
        });


    }

    public void execCreate(SQLiteDatabase db)
    {
        try {

            // cada comando do array de strings é executado um por um
            for(String comando : sqlCreator()) {
                db.execSQL(comando);
            }

        } catch (Exception ex) {
            Log.e("Info_DB", "Erro ao criar banco de dados: " + ex.getMessage());
        }
    }


    public String[] sqlCreator() {
        return new String[] {
                "CREATE TABLE fotos(_id INTEGER PRIMARY KEY AUTOINCREMENT, data BLOB);",

                "CREATE TABLE contatos(" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " nome VARCHAR(80) NOT NULL," +
                        " fone VARCHAR(20) NOT NULL," +
                        " id_foto INTEGER, " +
                        "FOREIGN KEY(id_foto) REFERENCES fotos(_id) ON DELETE RESTRICT" +
                ");"
        };
    }

}
