package br.com.tcc.felip.sistema;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import br.com.tcc.felip.sistema.Controllers.Parametro;
import br.com.tcc.felip.sistema.Model.SqliteParametroBean;
import br.com.tcc.felip.sistema.Model.SqliteParametroDao;

public class Principal extends AppCompatActivity implements View.OnClickListener {

    Button btn_Loga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        btn_Loga = (Button) findViewById(R.id.btnLogar);
        btn_Loga.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnLogar) {
            // passa parametros para que possa ser realizado login no sistema.
        }
    }
}

