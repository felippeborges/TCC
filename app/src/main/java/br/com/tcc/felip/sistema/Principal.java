package br.com.tcc.felip.sistema;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.Map;

import br.com.tcc.felip.sistema.Controllers.Parametro;
import br.com.tcc.felip.sistema.Model.SqliteParametroBean;
import br.com.tcc.felip.sistema.Model.SqliteParametroDao;
import br.com.tcc.felip.sistema.Util.Util;

public class Principal extends AppCompatActivity implements View.OnClickListener {

    private Map<String, String> params;

    Button btn_Loga;
    EditText txt_usuario;
    EditText txt_senha;

    private RequestQueue rq;
    private static final String TAG_SUCESSO = "sucesso";
    private static final String TAG_MENSAGEM = "mensagem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
        declaraObjetos();

        btn_Loga.setOnClickListener(this);
    }


    public void declaraObjetos() {
        txt_usuario = (EditText) findViewById(R.id.txt_usuario);
        txt_senha = (EditText) findViewById(R.id.txt_senha);
        btn_Loga = (Button) findViewById(R.id.btnLogar);

        rq = Volley.newRequestQueue(Principal.this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnLogar) {

            if (Util.chegarConexaoCelular(getBaseContext())) {
                Toast.makeText(getBaseContext(),"CONEXÃO REALIZADA COM SUCESSO", Toast.LENGTH_LONG).show();

            } else {
                Intent i = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivity(i);
                Toast.makeText(getBaseContext(), "Sem conexão com internet", Toast.LENGTH_LONG).show();
            }
        }
    }
}

