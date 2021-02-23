package com.miguel.app.schoolapp.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.List;

public class ApiRequest extends AsyncTask<List<NameValuePair>, String, String> {

    Context context;
    String method; // Metodo di default: POST  |  (solo metodi "post", "put", "delete" )
    String api_id; // id del studente sul database del server (MONGODB --> NoSQL)
    String API_URL = "https://alunni.herokuapp.com/api/alunni/";

    public ApiRequest() {}
    public ApiRequest(Context context) {
        this(context, "post", "");
    }

    public ApiRequest(Context context, String method, String api_id) {
        this.context = context;
        this.method = method;
        this.api_id = api_id;
    }

    @Override
    protected String doInBackground(List<NameValuePair>... lists) {

        HTTPManager http = new HTTPManager();
        String response = "";

        if(!method.equals("delete")) { http.load(lists[0]); }

        try {
            switch (method) {
                case "post":
                    response = http.post(API_URL);
                    break;
                case "put":
                    Log.i("MITO_DEBUG", "" + API_URL + api_id + "");
                    response = http.update(API_URL + api_id);
                    Log.i("MITO_DEBUG", "PUT PRESENTE");
                    break;
                case "delete":
                    response = http.delete(API_URL + api_id);
                    break;
                default:
            }

            Log.i("MITO_DEBUG", "risultato: " + response);

        } catch (Exception e) {
            for (StackTraceElement ste : e.getStackTrace()) {
                Log.e("MITO_DEBUG", "Errore HTTPManager: " + ste.toString());
            }
        }

        return response;
    }

    @Override
    protected void onPostExecute(String response) {

        try {
            JSONArray json = new JSONArray(response);
            for (int i = 0; i < json.length(); i++) {
                JSONObject obj = json.getJSONObject(i);
                Toast.makeText(context, obj.getString("message"), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("MITO_DEBUG", "JSON errore: " + e.getMessage());
        }

    }
}
