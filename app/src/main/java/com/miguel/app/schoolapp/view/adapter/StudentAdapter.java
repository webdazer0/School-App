package com.miguel.app.schoolapp.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.miguel.app.schoolapp.R;
import com.miguel.app.schoolapp.model.DBHelper;
import com.miguel.app.schoolapp.model.Student;
import com.miguel.app.schoolapp.model.StudentDB;

import java.util.ArrayList;

public class StudentAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Student> students;
    private LayoutInflater inflater;

    public StudentAdapter(Context context, ArrayList<Student> students) {
        this.context = context;
        this.students = students;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position); // Ritorna la classe Student come Object
    }

    @Override
    public long getItemId(int position) {
        return students.get(position).getID(); // Ritorna l'id (primary key)
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.student_custom, null);

        TextView name = convertView.findViewById(R.id.name);
        TextView lastName = convertView.findViewById(R.id.lastName);
        TextView date = convertView.findViewById(R.id.date);
        TextView btnDelete = convertView.findViewById(R.id.btn_delete);

        String name_for_debug = students.get(position).getName(); // Per controllare che riceva il valore, e visualizzarlo in modalità DEBUG

        name.setText(students.get(position).getName());
        lastName.setText(students.get(position).getLastName());
        date.setText(students.get(position).getDate());

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Hai clicatto il idbtn: " + position + " e anche: id: " + getItemId(position) , Toast.LENGTH_SHORT).show();
                Log.i("MITO_TAG", "Hai clicatto il idbtn: " + position + " e anche: id: " + getItemId(position));

                deleteSQL(getItemId(position));
            }
        });

        return convertView;
    }

    private void deleteSQL(long _ID) {
//        DELETE FROM student  WHERE _id = 4

        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String customQuery = "DELETE FROM " + StudentDB.Data.TABLE_NAME + " WHERE " + StudentDB.Data._ID + "=" + _ID;
        boolean result = db.rawQuery(customQuery, null).moveToFirst();
        Log.i("MITO_TAG", "deleteSQL: " + result);


    }

}
