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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.miguel.app.schoolapp.model.DBHelper;
import com.miguel.app.schoolapp.model.Student;
import com.miguel.app.schoolapp.model.StudentDB;
import com.miguel.app.schoolapp.service.HTTPManager;
import com.miguel.app.schoolapp.service.SincronizzaDati;
import com.miguel.app.schoolapp.view.DetailsActivity;
import com.miguel.app.schoolapp.view.adapter.StudentAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Context context;
    ArrayList<Student> studentSAMPLE;
    DBHelper dbHelper;
    FloatingActionButton addBtn;
    ListView lista;

    StudentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        studentSAMPLE = new ArrayList<>();

        try {
            dbHelper = new DBHelper(context);
            showToolbar("", false, false);
//            sampleList(); // Popola arrayList di prova

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            loadList();
            getData();

            addBtn = findViewById(R.id.fab);
            addBtn.setOnClickListener(addBtnEvent);

        } catch (Exception error) {
            Log.e("MITO_TAG", "err: " + error.getMessage());
        }

        lista.setOnItemClickListener((parent, view, position, id) -> {

            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("sqlID", id);
            startActivity(intent);
        });

    }


    private void getData() {
        Cursor cursor = dbHelper.select();

        while (cursor.moveToNext()) {
            String tmpNome = cursor.getString(cursor.getColumnIndex(StudentDB.Data.COL_NAME));
            String tmpCognome = cursor.getString(cursor.getColumnIndex(StudentDB.Data.COL_LASTNAME));
            String tmpData = cursor.getString(cursor.getColumnIndex(StudentDB.Data.COL_DATE));
            int tmpID = cursor.getInt(cursor.getColumnIndex(StudentDB.Data._ID));
            String tmpAPI_ID = cursor.getString(cursor.getColumnIndex(StudentDB.Data.COL_API_ID));

            studentSAMPLE.add(new Student(tmpNome, tmpCognome, tmpData, tmpID, tmpAPI_ID));
        }
        cursor.close();

        adapter = new StudentAdapter(context, studentSAMPLE);
        lista.setAdapter(adapter);
    }


    private void loadList() {

        lista = (ListView) findViewById(R.id.listStudent);
        SincronizzaDati sd = new SincronizzaDati(studentSAMPLE, adapter, lista, context, dbHelper);
//        sd.execute("https://torregatti.com/dati.php?opzione=mysql&key=" + HTTPManager.key);
        sd.execute("https://alunni.herokuapp.com/api/alunni");

    }

    // Creo un arraylist<String> per popolare la listview tramite l'adapter
    public void sampleList() {
//        studentSAMPLE.add(new Student("Lionel", "Messi", "13/05/2021", "998"));
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