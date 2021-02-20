package com.miguel.app.schoolapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miguel.app.schoolapp.R;
import com.miguel.app.schoolapp.model.Student;

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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.student_custom, null);

        TextView name = convertView.findViewById(R.id.name);
        TextView lastName = convertView.findViewById(R.id.lastName);
        TextView date = convertView.findViewById(R.id.date);

        String name_for_debug = students.get(position).getName(); // Per controllare che riceva il valore, e visualizzarlo in modalità DEBUG

        name.setText(students.get(position).getName());
        lastName.setText(students.get(position).getLastName());
        date.setText(students.get(position).getDate());

        return convertView;
    }
}
