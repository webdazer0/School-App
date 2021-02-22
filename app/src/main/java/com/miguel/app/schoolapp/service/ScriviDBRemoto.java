package com.miguel.app.schoolapp.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;

import java.util.List;

public class ScriviDBRemoto extends AsyncTask<List<NameValuePair>, String, String> {

    Context context;

    public ScriviDBRemoto(Context c) {
        context = c;
    }

    @Override
    protected String doInBackground(List<NameValuePair>... coppiedidati) {

        HTTPManager http = new HTTPManager();
        /*http.addDati("nome","Luigi");
        http.addDati("cognome","Neri");
        http.addDati("data","18/02/2021");*/
        http.load(coppiedidati[0]);

        try {

//            String r = http.post("https://torregatti.com/dati.php?opzione=salva&key=" + HTTPManager.key);
            String r = http.post("https://alunni.herokuapp.com/api/alunni");
            Log.i("PETAR_DEBUG","risultato: " + r);

            return r;

        } catch (Exception e) {
            for(StackTraceElement ste : e.getStackTrace()) {
                Log.e("PETAR_DEBUG", "Errore HTTPManager: " + ste.toString());
            }
        }

        return "Errore salvataggio";
    }

    @Override
    protected void onPostExecute(String risultato) {

        Toast.makeText(context, risultato, Toast.LENGTH_SHORT).show();

    }
}
