package com.miguel.app.schoolapp.view;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.miguel.app.schoolapp.R;
import com.miguel.app.schoolapp.model.DBHelper;
import com.miguel.app.schoolapp.model.Student;
import com.miguel.app.schoolapp.model.StudentDB;

public class DetailsActivity extends AppCompatActivity {

    DBHelper dbHelper;
    Context context;
    int id;

    Button save;

    String name;
    String lastname;
    String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        id = (int)intent.getLongExtra("rambo", 0);


        context = this;

        try {
            dbHelper = new DBHelper(context);


            if(id > 0) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                selectSQL(db, id);

                showToolbar("", true, 3); // setTitle = nome
            } else {
                showToolbar("Aggiungi studente", true, 2);
            }

            save = findViewById(R.id.add_btn_save);
            save.setOnClickListener(saveStudentEvent);

        } catch (Exception error) {
            Log.e("MITO_TAG", "err: " + error.getMessage());
        }
    }



    View.OnClickListener saveStudentEvent = v -> {
        name = ((EditText)findViewById(R.id.add_name)).getText().toString();
        lastname = ((EditText)findViewById(R.id.add_lastname)).getText().toString();
        date = ((EditText)findViewById(R.id.add_date)).getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        inserToSQL(db, name, lastname, date);
    };

    private void inserToSQL(SQLiteDatabase db, String name, String lastname, String date) {
        ContentValues values = new ContentValues();
        values.put(StudentDB.Data.COL_NAME, name);
        values.put(StudentDB.Data.COL_LASTNAME, lastname);
        values.put(StudentDB.Data.COL_DATE, date);

        if(id > 0) {
            String myQueryUpdate = "UPDATE people SET (receive_qty=20,pub_lang='Hindi',receive_dt='2008-07-10') WHERE name = Jane";
            long lastId = db.update(StudentDB.Data.TABLE_NAME, values, "" + StudentDB.Data._ID +"=?", new String[]{String.valueOf(id)});
            Log.i("MITO_TAG", "id Studente: " + lastId);
        } else {
            long lastId = db.insert(StudentDB.Data.TABLE_NAME, null, values);
            Log.i("MITO_TAG", "id Studente appena creato: " + lastId);
        }

    }


    private void selectSQL(SQLiteDatabase db, int id) {

        String customQuery = "SELECT * FROM " + StudentDB.Data.TABLE_NAME + " WHERE " + StudentDB.Data._ID + "=" + id;

        Cursor cursor = db.rawQuery(customQuery, null);

        while (cursor.moveToNext()) {
            String tmpNome = cursor.getString(cursor.getColumnIndex(StudentDB.Data.COL_NAME));
            String tmpCognome = cursor.getString(cursor.getColumnIndex(StudentDB.Data.COL_LASTNAME));
            String tmpData = cursor.getString(cursor.getColumnIndex(StudentDB.Data.COL_DATE));
            int tmpID = cursor.getInt(cursor.getColumnIndex(StudentDB.Data._ID));

            ((TextView)findViewById(R.id.add_name)).setText(tmpNome);
            ((TextView)findViewById(R.id.add_lastname)).setText(tmpCognome);
            ((TextView)findViewById(R.id.add_date)).setText(tmpData);
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
                getSupportActionBar().setTitle(title); break;
            case 3:
                getSupportActionBar().setTitle(((EditText)findViewById(R.id.add_name)).getText().toString());
                break;
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }
}