package com.example.cadastrodecontatossqlite.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cadastrodecontatossqlite.R;
import com.example.cadastrodecontatossqlite.adapter.AdapterContato;
import com.example.cadastrodecontatossqlite.controller.ControllerContato;
import com.example.cadastrodecontatossqlite.databinding.ActivityMainBinding;
import com.example.cadastrodecontatossqlite.listeners.OnItemRecyclerViewClickListener;
import com.example.cadastrodecontatossqlite.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    List<Contato> contatos = new ArrayList<>();
    AdapterContato adapterContato = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), CadastrarContato.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarListaContatos();
    }

    void carregarListaContatos(){

        ControllerContato controller = new ControllerContato(this);
        contatos = controller.findAll();

        if(adapterContato == null) initComponents();
        else adapterContato.setListaContato(contatos);

    }

    private void initComponents() {

        adapterContato = new AdapterContato(contatos);

        binding.listaContatos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.listaContatos.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        binding.listaContatos.setAdapter(adapterContato);
        binding.listaContatos.addOnItemTouchListener(new OnItemRecyclerViewClickListener(
                this,
                binding.listaContatos,
                new OnItemRecyclerViewClickListener.OnClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(getApplicationContext(), VisualizarContato.class);

                        Contato contato = contatos.get(position);
                        intent.putExtra("contato", contato);
                        startActivity(intent);

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Toast.makeText(getApplicationContext(), "LongClick", Toast.LENGTH_SHORT).show();
                    }
                }
        ));


    }
}