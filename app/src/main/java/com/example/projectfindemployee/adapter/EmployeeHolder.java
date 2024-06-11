package com.example.projectfindemployee.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectfindemployee.R;

public class EmployeeHolder extends RecyclerView.ViewHolder {

    TextView name,surname,mail;

    public EmployeeHolder(@NonNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.employeeListItem_name);
        surname=itemView.findViewById(R.id.employeeListItem_surname);
        mail=itemView.findViewById(R.id.employeeListItem_mail);

    }
}
