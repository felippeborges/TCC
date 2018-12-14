package br.com.tcc.felip.sistema;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.tcc.felip.sistema.Controllers.Parametro;
import br.com.tcc.felip.sistema.Model.SqliteParametroBean;
import br.com.tcc.felip.sistema.Model.SqliteParametroDao;

public class Principal extends AppCompatActivity implements View.OnClickListener {

    Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);

        btn_add = (Button) findViewById(R.id.button6);
        btn_add.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button6) {
            SqliteParametroBean parBean = new SqliteParametroBean();
            SqliteParametroDao parDao = new SqliteParametroDao(getBaseContext());

            parBean.setP_usu_codigo(1);
            parBean.setP_importar_todos_clientes("todos");
            parBean.setP_desconto_do_vendedor(10);
            parBean.setP_trabalhar_com_estoque_negativo("s");
            parBean.setP_end_ip_local("htpps://10.7.1.172/www.teste");
            parBean.setP_end_ip_remoto("htpps://www.teste.com");

            parDao.gravar_parametro(parBean);
            Intent it = new Intent(getBaseContext(), Parametro.class);
            startActivity(it);
        }
    }
}

