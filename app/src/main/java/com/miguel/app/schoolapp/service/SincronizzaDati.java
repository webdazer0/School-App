 package com.miguel.app.schoolapp.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.miguel.app.schoolapp.model.DBHelper;
import com.miguel.app.schoolapp.model.Student;
import com.miguel.app.schoolapp.view.adapter.StudentAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SincronizzaDati extends AsyncTask<String, String, List<String>> {

    ArrayList<Student> students;
    StudentAdapter adapter;
    ListView lista;
    Context context;
    ProgressDialog dialog;

    DBHelper dbHelper;

    public SincronizzaDati(ArrayList<Student> students, StudentAdapter adapter, ListView lista, Context context, DBHelper dbHelper) {
        this.students = students;
        this.adapter = adapter;
        this.lista = lista;
        this.context = context;
        this.dbHelper = dbHelper;
        this.dialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Caricamento...");
        dialog.show();
    }

    @Override
    protected ArrayList<String> doInBackground(String... urls) {

        ArrayList<String> dati = new ArrayList<>();
        String tmp = "";

        int k = 0;
        int tot = urls.length;

        try {
            for (String url : urls) {

                tmp = HTTPManager.get(url);
                publishProgress(url, String.valueOf(Math.round(100 * (++k) / tot)) + "%");
                dati.add(tmp);
            }

        } catch (Exception e) {
            for(StackTraceElement ste : e.getStackTrace()) {
                Log.e("MITO_DEBUG", "Errore SincronizzaDati: " + ste.toString());
            }
        }

        return dati;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        //super.onProgressUpdate(values);
        Log.i("MITO_DEBUG","Stato sync: " + values[0]);
        dialog.setMessage("Caricamento " + values[1]);
    }

    @Override
    protected void onPostExecute(List<String> dati) {
        dbHelper.cleanDb(); // CANCELLO LA TABELLA PER RIPOPOLARLA

        for( String dato : dati ) {
            Log.i("MITO_DEBUG", "JSON da API / ENDPOINT: " + dato);

            try {
                JSONArray json = new JSONArray(dato);
                for (int i = 0; i < json.length(); i++) {
                    JSONObject obj = json.getJSONObject(i);

                    dbHelper.insert(obj.getString("nome"), obj.getString("cognome"), obj.getString("data"), obj.getString("_id"));
                }

            } catch (Exception e) {
                Log.e("MITO_DEBUG", "JSON errore: " + e.getMessage());
            }
        }

        if(dialog.isShowing()) {
            dialog.dismiss();
        }

    }
}
