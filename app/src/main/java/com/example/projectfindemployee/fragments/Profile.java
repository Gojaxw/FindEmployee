package com.example.projectfindemployee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projectfindemployee.MainActivity;
import com.example.projectfindemployee.R;


public class Profile extends Fragment {
    EditText name_field,surname_field,mail_field;
    Button save_changes;
    MainActivity mainActivity;

    public Profile(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        name_field=getView().findViewById(R.id.edit_field_name);
        mail_field=getView().findViewById(R.id.edit_field_mail);
        surname_field=getView().findViewById(R.id.edit_field_surname);
    }
    public void setText(){
        name_field.setText(mainActivity.employee.getName());
        mail_field.setText(mainActivity.employee.getMail());
        surname_field.setText(mainActivity.employee.getSurname());
    }
}