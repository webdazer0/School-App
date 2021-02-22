package com.miguel.app.schoolapp.service;

import android.net.http.HttpsConnection;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class HTTPManager {

    public static String key = "09b6b64e2d06cb7763a5e1b869635c0c4b6920a52a346d1eebf02d4fba41ddba";

    private HttpResponse risultato;
    private List<NameValuePair> dati;

    public HTTPManager() {
        dati = new ArrayList<NameValuePair>();
    }

    public void addDati(String k, String v) {
        dati.add(new BasicNameValuePair(k, v));
    }

    public void svuotaDati() {
        dati.clear();
    }

    public void load(List<NameValuePair> d) {
        dati = d;
    }

    public static String get(String url) throws Exception {

        String dati = "";

        URL indirizzo = new URL(url);
        BufferedReader in = new BufferedReader(new InputStreamReader(indirizzo.openStream()));

        String tmp;

        while((tmp = in.readLine()) != null)
            dati += tmp;

        return dati;
    }

    public String post(String url) throws Exception {

        String risultato = "";

        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 30000);
        HttpConnectionParams.setSoTimeout(params, 30000);

        HttpClient client = new DefaultHttpClient(params);
        HttpPost httpPost = new HttpPost(url);

        httpPost.setEntity(new UrlEncodedFormEntity(dati));
        HttpResponse response = client.execute(httpPost);

        BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

        String tmp;

        while((tmp = in.readLine()) != null)
            risultato += tmp;

        return risultato;
    }

}
