package com.example.cadastrodecontatossqlite.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.cadastrodecontatossqlite.R;
import com.example.cadastrodecontatossqlite.controller.ControllerContato;
import com.example.cadastrodecontatossqlite.databinding.ActivityVisualizarContatoBinding;
import com.example.cadastrodecontatossqlite.model.Contato;
import com.example.cadastrodecontatossqlite.utils.Utils;

public class VisualizarContato extends AppCompatActivity {

    Contato contatoSelecionado = null;
    ActivityVisualizarContatoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVisualizarContatoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            contatoSelecionado = (Contato) extras.getSerializable("contato");
        }

    }

    @Override
    protected void onStart() {

        ControllerContato controller = new ControllerContato(getApplicationContext());
        contatoSelecionado = controller.find(contatoSelecionado.get_id());
        fillComponents();

        super.onStart();
    }

    public void fillComponents() {

        binding.perfil.setImageBitmap(Utils.convertByteArrayToBitmap(contatoSelecionado.getFoto()));
        binding.tvNome.setText(contatoSelecionado.getNome());
        binding.tvEmail.setText(contatoSelecionado.getEmail());

        if(!contatoSelecionado.getCelular().isEmpty()) binding.tvFone.setText(contatoSelecionado.getCelular());
        else binding.tvFone.setText(contatoSelecionado.getTelefone());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.editar:

                Intent intent = new Intent(getApplicationContext(), CadastrarContato.class);

                if(contatoSelecionado != null) {
                    intent.putExtra("contato", contatoSelecionado);
                }

                startActivity(intent);

            break;
        }

        return super.onOptionsItemSelected(item);
    }
}