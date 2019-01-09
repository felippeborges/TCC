package br.com.tcc.felip.sistema.Util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Util extends Activity {

    public static boolean chegarConexaoCelular(Context ctx) {

        boolean conectado = false;

        try {
            final ConnectivityManager connMgr = (ConnectivityManager) ctx.getSystemService(ctx.CONNECTIVITY_SERVICE);
            final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if (mobile.getState() == NetworkInfo.State.CONNECTED) {

                conectado = true;
            } else if (wifi.getState() == NetworkInfo.State.CONNECTED) {

                conectado = true;
            }
            
        } catch (Exception e) {

            conectado = false;
        }

        return conectado;
    }
}
