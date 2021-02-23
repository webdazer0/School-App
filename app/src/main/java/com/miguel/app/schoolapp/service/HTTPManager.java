package com.miguel.app.schoolapp.service;

import android.net.http.HttpsConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class HTTPManager {


    private HttpResponse response;
    private List<NameValuePair> dati;

    public HTTPManager() {
        dati = new ArrayList<NameValuePair>();
    }

    public void addDati(String key, String value) {
        dati.add(new BasicNameValuePair(key, value));
    }

    public void svuotaDati() {
        dati.clear();
    }

    public void load(List<NameValuePair> d) {
        dati = d;
    }

    public static String get(String url) throws Exception {

        String dati = "";

        URL myUrl = new URL(url);
        BufferedReader in = new BufferedReader(new InputStreamReader(myUrl.openStream()));

        String tmp;
        while((tmp = in.readLine()) != null)
            dati += tmp;

        return dati;
    }

    public String post(String url) throws Exception {

        String result = "";
        HttpClient client = loadClient();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(dati));
        HttpResponse response = client.execute(httpPost);

        result = getResponse(response, result);
        return result;
    }



    public String delete(String url) throws Exception {

        String result = "";
        HttpClient client = loadClient();

        HttpDelete request = new HttpDelete(url);
        HttpResponse response = client.execute(request);

        result = getResponse(response, result);
        return result;
    }

    public String update(String url) throws Exception {

        String result = "";
        HttpClient client = loadClient();

        HttpPut request = new HttpPut(url);
        request.setEntity(new UrlEncodedFormEntity(dati));
        HttpResponse response = client.execute(request);

        result = getResponse(response, result);
        return result;
    }

    private HttpClient loadClient() {
        HttpParams params = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(params, 15000);
        HttpConnectionParams.setSoTimeout(params, 15000);
        HttpClient client = new DefaultHttpClient(params);
        return client;
    }

    private String getResponse(HttpResponse httpResponse, String result) throws IOException {

        String tmp;

        BufferedReader in = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
        while((tmp = in.readLine()) != null)
            result += tmp;

        result= "[" + result + "]";

        return result;
    }

}
