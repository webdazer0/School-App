package com.miguel.app.schoolapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.miguel.app.schoolapp.R;

import java.util.ArrayList;

public class StudentAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<String> students;
    private LayoutInflater inflater;


    public StudentAdapter(Context context, ArrayList<String> students) {
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
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.student_custom, null);

        TextView nameStudent = (TextView)convertView.findViewById(R.id.nameStudent);

        String VEAMOS = (String) nameStudent.getText();

        String DEBUG = students.get(position);

        nameStudent.setText(students.get(position));

        return convertView;
    }
}
