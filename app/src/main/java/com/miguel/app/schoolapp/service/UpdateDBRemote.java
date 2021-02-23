package com.miguel.app.schoolapp.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;

import java.util.List;

public class UpdateDBRemote extends AsyncTask<List<NameValuePair>, String, String> {

    Context context;
    String api_id;

    public UpdateDBRemote(Context context, String api_id) {
        this.context = context;
        this.api_id = api_id;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(List<NameValuePair>... lists) {
        HTTPManager http = new HTTPManager();
        http.load(lists[0]);

        try {
            String r = http.update("https://alunni.herokuapp.com/api/alunni/" + api_id);
            Log.i("MITO_DEBUG","risultato: " + r);

            return r;

        } catch (Exception e) {
            for(StackTraceElement ste : e.getStackTrace()) {
                Log.e("MITO_DEBUG", "Errore HTTPManager: " + ste.toString());
            }
        }

        return "Errore aggiornamento dati studente";
    }
}
