package com.example.cadastrodecontatossqlite.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cadastrodecontatossqlite.R;
import com.example.cadastrodecontatossqlite.controller.ControllerContato;
import com.example.cadastrodecontatossqlite.controller.ControllerFoto;
import com.example.cadastrodecontatossqlite.databinding.ActivityCadastrarContatoBinding;
import com.example.cadastrodecontatossqlite.model.Contato;
import com.example.cadastrodecontatossqlite.model.Foto;
import com.example.cadastrodecontatossqlite.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CadastrarContato extends AppCompatActivity {

    ActivityCadastrarContatoBinding binding;
    static final int REQUEST_IMAGE_CODE = 200;
    static Uri uri_foto_perfil = null;

    Contato contatoSelecionado = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastrarContatoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        Bundle extras = getIntent().getExtras();

        if(extras != null) {
            contatoSelecionado = (Contato) getIntent().getExtras().getSerializable("contato");
            fillComponents();
        }

        binding.foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), REQUEST_IMAGE_CODE);

            }
        });

        binding.btnSalvar.setOnClickListener(salvarContato);

        binding.btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    public void fillComponents(){
        binding.foto.setImageBitmap(Utils.convertByteArrayToBitmap(contatoSelecionado.getFoto()));
        binding.nome.setText(contatoSelecionado.getNome());
        binding.celular.setText(contatoSelecionado.getCelular());
        binding.telefone.setText(contatoSelecionado.getTelefone());
        binding.email.setText(contatoSelecionado.getEmail());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_IMAGE_CODE && resultCode == RESULT_OK && data != null) {

            // o caminho até a imagem no dispositivo do usuario
            Uri uri = data.getData();
            uri_foto_perfil = uri;

            if(uri != null) {

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    binding.foto.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

    }

    boolean checkEdits(){
        int count = binding.parentLinearLayout.getChildCount();
        for(int i = 0; i < count; i++){
            View view =  binding.parentLinearLayout.getChildAt(i);
            if(view instanceof TextInputEditText) {
                TextInputEditText edit = (TextInputEditText) view;
                if(edit.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Preencha os campos vazios", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        return true;
    }

    void preencherObjetoSelecionado() throws IOException {

        contatoSelecionado.setNome(binding.nome.getText().toString());
        contatoSelecionado.setCelular(binding.celular.getText().toString());
        contatoSelecionado.setTelefone(binding.telefone.getText().toString());
        contatoSelecionado.setEmail(binding.email.getText().toString());

    }

    Contato preencherObjeto() throws IOException {

        Contato contato = new Contato();

        contato.setNome(binding.nome.getText().toString());
        contato.setCelular(binding.celular.getText().toString());
        contato.setTelefone(binding.telefone.getText().toString());
        contato.setEmail(binding.email.getText().toString());
        contato.setFoto(Utils.convertImageToByteArray(this, uri_foto_perfil));

        return contato;

    }

    View.OnClickListener salvarContato = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(!checkEdits()) return;

            try {

                Contato contato = preencherObjeto();
                Foto foto = new Foto(Utils.convertImageToByteArray(getApplicationContext(), uri_foto_perfil));

                ControllerFoto controllerFoto = new ControllerFoto(getApplicationContext());
                ControllerContato controller = new ControllerContato(getApplicationContext());

                if(contatoSelecionado != null) { // significa que o usuário estará atualizando um contato existente

                    if(uri_foto_perfil != null) {   // só atualiza a foto se o usuário inserir uma nova no lugar da que já esta

                        foto.set_id(contatoSelecionado.getImage_id());
                        foto = controllerFoto.atualizar(foto);
                        contatoSelecionado.setFoto(foto.getData());

                    }

                    preencherObjetoSelecionado();

                    if(controller.update(contatoSelecionado)) {
                        Toast.makeText(getApplicationContext(), "Contato atualizado com sucesso!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                } else {

                    foto = controllerFoto.inserir(foto);
                    if(foto != null) {

                        contato.setImage_id(foto.get_id());
                        if(controller.insert(contato)){
                            Toast.makeText(getApplicationContext(), "Contato salvo com sucesso!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }


                return;

            } catch (IOException e) {
                Log.e("Info_DB", e.getMessage());
            }

        }
    };
}