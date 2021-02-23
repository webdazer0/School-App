package com.miguel.app.schoolapp.view.adapter;

import android.content.Context;
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
import com.miguel.app.schoolapp.service.ApiRequest;

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
//        return 0;
    }

    public String getItemApiId(int position) {
        return students.get(position).getAPI_ID(); // Ritorna l'id (primary key) API
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
                Log.i("MITO_TAG", "Hai clicatto il idbtn: " + getItemApiId(position) + " e anche: id: " + getItemId(position));
                deleteStudentAPI(context, getItemApiId(position));
//                deleteSQL(getItemId(position));
            }
        });

        return convertView;
    }

    private void deleteSQL(long _ID) {
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.delete((int) _ID);
    }

    private void deleteStudentAPI(Context context, String api_id) {
//      Eliminazione dati online
        ApiRequest apiRequest = new ApiRequest(context, "delete", api_id);
        apiRequest.execute();
    }

}
