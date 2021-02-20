package com.miguel.app.schoolapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.miguel.app.schoolapp.model.DBHelper;
import com.miguel.app.schoolapp.model.ListDB;
import com.miguel.app.schoolapp.view.adapter.StudentAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Context context;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        try {
            dbHelper = new DBHelper(context);

            showToolbar("", false, false);
            inserToSQL();

        } catch (Exception error) {
            Log.e("MITO_TAG", "err: " + error.getMessage());
        }
    }

    private void inserToSQL() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ListDB.Data.COL_NAME, "Miguel");
        values.put(ListDB.Data.COL_LASTNAME, "Cruzado");
        values.put(ListDB.Data.COL_DATE, "13/02/2019");

        long lastId = db.insert(ListDB.Data.TABLE_NAME, null, values);

        Log.i("MITO_TAG", "onCreate: " + lastId);
    }


//    private void loadList() {
//        ListView lista = (ListView) findViewById(R.id.listStudent);
//        StudentAdapter adapter = new StudentAdapter(context, studentSAMPLE);
//        lista.setAdapter(adapter);
//    }

    // Creo un arraylist<String> per popolare la listview tramite l'adapter
    public void sampleList() {
        ArrayList<String> studentSAMPLE = new ArrayList<>();
        studentSAMPLE.add("Maria");
        studentSAMPLE.add("Pedro");
        studentSAMPLE.add("Ramon");
    }

    public void showToolbar(String title, boolean upButton, boolean hasTitle) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // Supporto toolbar per versioni prima di LOLLIPOP
        if (hasTitle) {
            getSupportActionBar().setTitle(title);
        } else {
            getSupportActionBar().setTitle(getResources().getString(R.string.app_name)); // Se hasTitle è false, mostra il nome del'app nella Toolbar
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
}