package br.com.tcc.felip.sistema;

import android.app.DownloadManager;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.com.tcc.felip.sistema.Controllers.Parametro;
import br.com.tcc.felip.sistema.Model.SqliteParametroBean;
import br.com.tcc.felip.sistema.Model.SqliteParametroDao;
import br.com.tcc.felip.sistema.Util.Util;
import br.com.tcc.felip.sistema.Util.CustomJsonObjectRequest;

public class Principal extends AppCompatActivity implements View.OnClickListener {

    private Map<String, String> params;

    Button btn_Loga;
    EditText txt_usuario;
    EditText txt_senha;

    private RequestQueue rq;
    private static final String TAG_SUCESSO = "sucesso";
    private static final String TAG_MENSAGEM = "mensagem";
    private static final String TAG_REQUEST = "tag";
    private static final String TAG_USUARIO_JSON_ARRAY = "usuario_array";
    private static final String CODIGO_USUARIO = "usu_codigo";
    private static final String DESCONTO_USUARIO = "usu_desconto";
    private static final String USUARIO = "usu_usuario";
    private static final String SENHA = "usu_senha";

    private SqliteParametroDao parDao;
    private SqliteParametroBean parBean;
    private String URL_REGISTRO = "10.7.1.172/Sistemacomercialweb/json/registrar/registrar_usuario.php";

    private JSONArray usuario_array = null;

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

        parBean = new SqliteParametroBean();
        parDao = new SqliteParametroDao(getBaseContext());
    }

    public boolean valida_campos() {
        declaraObjetos();

        if (txt_usuario.getText().toString().length() <= 4) {
            txt_usuario.setError("Seu nome de usuário está menor que o tamanho permitido");
            txt_usuario.requestFocus();
            return false;
        } else if (txt_senha.getText().toString().trim().length() <= 4) {
            txt_senha.setError("Sua senha está menor que o tamanho permitido");
            txt_senha.requestFocus();
        }


        return false;
    }

    public void valida_usuario() {
        declaraObjetos();

        String usuario_digitado = txt_usuario.getText().toString();
        String senha_digitado = txt_senha.getText().toString();

        if (usuario_digitado.trim().equals(parBean.getP_usuario()) && senha_digitado.trim().equals(parBean.getP_senha())) {
            //REALIZA VERIFICAÇÃO PARA ENTÃO PODER EFETUAR ACESSO AO SISTEMA.

        } else {
            // MENSAGEM CASO LOGIN OU SENHA ESTEJA ERRADO.
        }
    }


    public void registra_usuario_web() {//metodo responsavel por registrar o usuario no sistema WEB caso não possua um registro
        params = new HashMap<String, String>();

        params.put("usu_usuario", txt_usuario.getText().toString());
        params.put("usu_senha", txt_senha.getText().toString());

        CustomJsonObjectRequest request = new CustomJsonObjectRequest(Request.Method.POST,
                URL_REGISTRO,
                params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//OBTEM RESPOSTA DO PHP
                        try {
                            int sucesso = (Integer) response.get(TAG_SUCESSO);

                            switch (sucesso) {

                                case 1:
                                    usuario_array = response.getJSONArray(TAG_USUARIO_JSON_ARRAY);

                                    for (int i = 0; i < usuario_array.length(); i++) {
                                        JSONObject usuarioObj = usuario_array.getJSONObject(i);
                                        parBean.setP_usu_codigo(usuarioObj.getInt(CODIGO_USUARIO));
                                        parBean.setP_desconto_do_vendedor(usuarioObj.getInt(DESCONTO_USUARIO));
                                        parBean.setP_usuario(usuarioObj.getString(USUARIO));
                                        parBean.setP_senha(usuarioObj.getString(SENHA));

                                        parDao.gravar_parametro(parBean);

                                        Log.i("Script",CODIGO_USUARIO);
                                        Log.i("Script",DESCONTO_USUARIO);
                                        Log.i("Script",USUARIO);
                                        Log.i("Script",SENHA);
                                    }
                                    break;
                            }

                        } catch (JSONException e) {
                            Log.i("Script", e.getMessage());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        request.setTag(TAG_REQUEST);
        rq.add(request);
    }

    @Override
    public void onStop() {
        super.onStop();
        rq.cancelAll(TAG_REQUEST);
    }

    @Override
    public void onClick(View v) {

        if (valida_campos()) {

            parBean = parDao.busca_parametros();

            if (parBean != null) {
                valida_usuario();
            } else {
                if (v.getId() == R.id.btnLogar) {

                    if (Util.chegarConexaoCelular(getBaseContext())) {
                        //Toast.makeText(getBaseContext(), "CONEXÃO REALIZADA COM SUCESSO", Toast.LENGTH_LONG).show();
                        registra_usuario_web();

                    } else {
                        Intent i = new Intent(Settings.ACTION_WIFI_SETTINGS);//VERIFICA QUE O WIFI NÃO ESTA LIGADO, E LEVA O USUARIO PARA AS CONFIGURAÇÕES DO CELULAR NA AREA DO WIFI.
                        startActivity(i);
                        Toast.makeText(getBaseContext(), "SEM CONEXÃO COM A INTERNET", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }
}


