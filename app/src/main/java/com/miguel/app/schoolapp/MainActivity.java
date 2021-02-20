package com.miguel.app.schoolapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.miguel.app.schoolapp.model.DBHelper;
import com.miguel.app.schoolapp.model.Student;
import com.miguel.app.schoolapp.model.StudentDB;
import com.miguel.app.schoolapp.view.DetailsActivity;
import com.miguel.app.schoolapp.view.adapter.StudentAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Context context;
    ArrayList<Student> studentSAMPLE;
    DBHelper dbHelper;
    FloatingActionButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        studentSAMPLE = new ArrayList<>();

        try {
            dbHelper = new DBHelper(context);
            showToolbar("", false, false);
            sampleList(); // Popola arrayList di prova

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            selectSQL(db);

            loadList();

            addBtn = findViewById(R.id.fab);
            addBtn.setOnClickListener(addBtnEvent);

        } catch (Exception error) {
            Log.e("MITO_TAG", "err: " + error.getMessage());
        }


    }

    private void inserToSQL(SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(StudentDB.Data.COL_NAME, "Miguel");
        values.put(StudentDB.Data.COL_LASTNAME, "Cruzado");
        values.put(StudentDB.Data.COL_DATE, "13/02/2019");

        long lastId = db.insert(StudentDB.Data.TABLE_NAME, null, values);
        Log.i("MITO_TAG", "onCreate: " + lastId);
    }

    private void selectSQL(SQLiteDatabase db) {

        String customQuery = "SELECT * FROM " + StudentDB.Data.TABLE_NAME;

        Cursor cursor = db.rawQuery(customQuery, null);

        while (cursor.moveToNext()) {
            String tmpNome = cursor.getString(cursor.getColumnIndex(StudentDB.Data.COL_NAME));
            String tmpCognome = cursor.getString(cursor.getColumnIndex(StudentDB.Data.COL_LASTNAME));
            String tmpData = cursor.getString(cursor.getColumnIndex(StudentDB.Data.COL_DATE));

            studentSAMPLE.add(new Student(tmpNome, tmpCognome, tmpData));
        }
        cursor.close();
    }


    private void deleteSQL(SQLiteDatabase db) {
//        DELETE FROM student  WHERE _id = 4
        String customQuery = "DELETE FROM " + StudentDB.Data.TABLE_NAME + " WHERE " + StudentDB.Data._ID + "=2";
        boolean result = db.rawQuery(customQuery, null).moveToFirst();
        Log.i("MITO_TAG", "onCreate: " + result);
    }

    private void loadList() {
        ListView lista = (ListView) findViewById(R.id.listStudent);
        StudentAdapter adapter = new StudentAdapter(context, studentSAMPLE);
        lista.setAdapter(adapter);
    }

    // Creo un arraylist<String> per popolare la listview tramite l'adapter
    public void sampleList() {
        studentSAMPLE.add(new Student("Lionel", "Messi", "13/05/2021"));
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

//    EVENTS: CAMBIO DI ACTIVITY (Da MainActivity a DetailsActivity)

    private View.OnClickListener addBtnEvent = v -> {
        Intent intent = new Intent(context, DetailsActivity.class);
        startActivity(intent);
    };
}