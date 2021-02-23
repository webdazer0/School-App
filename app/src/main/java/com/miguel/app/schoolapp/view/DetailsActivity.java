package com.miguel.app.schoolapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.miguel.app.schoolapp.R;
import com.miguel.app.schoolapp.model.DBHelper;
import com.miguel.app.schoolapp.model.StudentDB;
import com.miguel.app.schoolapp.service.ApiRequest;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    DBHelper dbHelper;
    Context context;
    int id;
    String api_id;

    Button save;
    String name;
    String lastname;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        id = (int) intent.getLongExtra("sqlID", 0);

        save = findViewById(R.id.add_btn_save);
        Log.i("MITO_TAG", "id: " + id);

        context = this;

        try {
            dbHelper = new DBHelper(context);

            if (id > 0) {
                api_id = dbHelper.getApiID(id);
                getDataById(id);
                showToolbar("", true, 3); // setTitle = nome
                save.setText("Aggiorna");
            } else {
                showToolbar("Aggiungi studente", true, 2);
            }

            save.setOnClickListener(saveStudentEvent);

        } catch (Exception error) {
            Log.e("MITO_TAG", "err: " + error.getMessage());
        }
    }


    View.OnClickListener saveStudentEvent = v -> {
        name = ((EditText) findViewById(R.id.add_name)).getText().toString();
        lastname = ((EditText) findViewById(R.id.add_lastname)).getText().toString();
        date = ((EditText) findViewById(R.id.add_date)).getText().toString();

        if(!name.equals("") && !lastname.equals("") && !date.equals("")) {
            if (id > 0 && api_id.length() > 0) {
                updateStudentAPI(name, lastname, date);
            } else {
                createStudentAPI(name, lastname, date);
            }
        } else {
            Toast.makeText(context, "Devi compilare tutti i campi!", Toast.LENGTH_SHORT).show();
        }

    };

    private void createStudentAPI(String name, String lastname, String date) {
//      salvataggio online
        ApiRequest request = new ApiRequest(context);

        List<NameValuePair> values = new ArrayList<>();
        values.add(new BasicNameValuePair("nome", name));
        values.add(new BasicNameValuePair("cognome", lastname));
        values.add(new BasicNameValuePair("data", date));

        request.execute(values);
    }

    private void updateStudentAPI(String name, String lastname, String date) {
//      aggiornamento dati online
        ApiRequest request = new ApiRequest(context, "put", api_id);

        List<NameValuePair> values = new ArrayList<>();
        values.add(new BasicNameValuePair("nome", name));
        values.add(new BasicNameValuePair("cognome", lastname));
        values.add(new BasicNameValuePair("data", date));

        request.execute(values);
    }




    private void getDataById(int id) {
        Cursor cursor = dbHelper.selectById(id);

        while (cursor.moveToNext()) {
            String tmpNome = cursor.getString(cursor.getColumnIndex(StudentDB.Data.COL_NAME));
            String tmpCognome = cursor.getString(cursor.getColumnIndex(StudentDB.Data.COL_LASTNAME));
            String tmpData = cursor.getString(cursor.getColumnIndex(StudentDB.Data.COL_DATE));

            ((TextView) findViewById(R.id.add_name)).setText(tmpNome);
            ((TextView) findViewById(R.id.add_lastname)).setText(tmpCognome);
            ((TextView) findViewById(R.id.add_date)).setText(tmpData);
        }
        cursor.close();
    }



    public void showToolbar(String title, boolean upButton, int hasTitle) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // Supporto toolbar per versioni prima di LOLLIPOP

        switch (hasTitle) { // number 2 to Choose the name Toolbar
            case 1:
                getSupportActionBar().setTitle(getResources().getString(R.string.app_name)); // Se hasTitle è false, mostra il nome del'app nella Toolbar
                break;
            case 2:
                getSupportActionBar().setTitle(title);
                break;
            case 3:
                getSupportActionBar().setTitle(((EditText) findViewById(R.id.add_name)).getText().toString());
                break;
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
}