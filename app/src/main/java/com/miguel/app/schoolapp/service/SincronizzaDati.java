package com.miguel.app.schoolapp.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

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
    ListView elenco;
    Context context;
    ProgressDialog dialog;

    public SincronizzaDati(ArrayList<Student> students, StudentAdapter adapter, ListView elenco, Context context) {
        this.students = students;
        this.adapter = adapter;
        this.elenco = elenco;
        this.context = context;
        this.dialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Caricamento...");
        dialog.show();
    }

    @Override
    protected List<String> doInBackground(String... indirizzi) {

        ArrayList<String> dati = new ArrayList<String>();

        String tmp = "";

        int k = 0;
        int tot = indirizzi.length;

        try {

            for (String url : indirizzi) {

                tmp = HTTPManager.get(url);
                publishProgress(url, String.valueOf(Math.round(100 * (++k) / tot)) + "%");
                dati.add(tmp);

            }

        } catch (Exception e) {
            for(StackTraceElement ste : e.getStackTrace()) {
                Log.e("PETAR_DEBUG", "Errore SincronizzaDati: " + ste.toString());
            }
        }

        return dati;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        //super.onProgressUpdate(values);
        Log.i("PETAR_DEBUG","Stato sync: " + values[0]);
        dialog.setMessage("Caricamento " + values[1]);
    }

    @Override
    protected void onPostExecute(List<String> dati) {
        //super.onPostExecute(s);
        for( String dato : dati ) {
            Log.i("PETAR_DEBUG", "JSON da Sincronizza: " + dato);

            try {

                JSONArray json = new JSONArray(dato);
                for (int i = 0; i < json.length(); i++) {
                    JSONObject obj = json.getJSONObject(i);
                    Log.i("PETAR_DEBUG", "nome: " + obj.getString("nome"));
                    students.add(new Student(obj.getString("nome"), obj.getString("cognome"), obj.getString("data"), obj.getInt("_id")));
                }

            } catch (Exception e) {
                Log.e("PETAR_DEBUG", "JSON errore: " + e.getMessage());
            }

        }

        adapter = new StudentAdapter(context, students);

        elenco.setAdapter(adapter);

        if(dialog.isShowing()) {
            dialog.dismiss();
        }

    }
}
