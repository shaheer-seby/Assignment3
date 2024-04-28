package com.example.assignment3;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContact extends AppCompatActivity {

    EditText etName, etPhone;
    Button btnAdd, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        etName= findViewById(R.id.etName);
        etPhone= findViewById(R.id.etPhone);
        btnAdd= findViewById(R.id.btnAdd);
        btnCancel= findViewById(R.id.btnCancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact();

                startActivity(new Intent(AddContact.this, MainActivity.class));
                finish();
            }
        });

    }

    private void addContact()
    {
        String name = etName.getText().toString().trim();
        String phone = etPhone.getText().toString();

        MyDatabaseHelper myDatabaseHelper = new MyDatabaseHelper(this);
        myDatabaseHelper.open();

        myDatabaseHelper.insert(name, phone);

        myDatabaseHelper.close();
    }
}