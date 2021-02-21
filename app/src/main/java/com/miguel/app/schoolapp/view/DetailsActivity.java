package com.miguel.app.schoolapp.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.miguel.app.schoolapp.R;
import com.miguel.app.schoolapp.model.DBHelper;
import com.miguel.app.schoolapp.model.StudentDB;

public class DetailsActivity extends AppCompatActivity {

    DBHelper dbHelper;
    Context context;

    Button save;

    String name;
    String lastname;
    String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        context = this;

        try {
            dbHelper = new DBHelper(context);
            showToolbar("Aggiungi studente", true, true);

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

        long lastId = db.insert(StudentDB.Data.TABLE_NAME, null, values);
        Log.i("MITO_TAG", "id Studente appena creato: " + lastId);
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