package com.miguel.app.schoolapp.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class DeleteDBRemote extends AsyncTask<String, Void, String> {

    Context context;
    String api_id;

    public DeleteDBRemote(Context context, String api_id) {
        this.context = context;
        this.api_id = api_id;
    }

    @Override
    protected void onPostExecute(String risultato) {
        Toast.makeText(context, risultato, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected String doInBackground(String... strings) {

        HTTPManager http = new HTTPManager();

        try {
            String r = http.delete("https://alunni.herokuapp.com/api/alunni/" + api_id);
            Log.i("MITO_TAG","risultato delete: " + r);
            return r;

        } catch (Exception e) {
            for(StackTraceElement ste : e.getStackTrace()) {
                Log.e("MITO_TAG", "Errore delete HTTPManager: " + ste.toString());
            }
        }

        return "Errore eliminazione";
    }
}
