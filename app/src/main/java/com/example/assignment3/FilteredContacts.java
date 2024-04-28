package com.example.assignment3;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class FilteredContacts extends AppCompatActivity {

    ListView lvContacts;
    FilterContactAdapter adapter;

    SearchView svContact;
    ArrayList<Contact> contacts;

    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_contacts);
        lvContacts = findViewById(R.id.lvContacts);
        svContact = findViewById(R.id.svContact);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FilteredContacts.this, MainActivity.class));
                finish();
            }
        });


        svContact.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Contact> filterContacts = new ArrayList<>();

                for(Contact c: contacts)
                {
                    if(c.getName().toLowerCase().contains(newText.toLowerCase()))
                    {
                        filterContacts.add(c);
                    }
                }
                adapter = new FilterContactAdapter(FilteredContacts.this, 0, filterContacts);
                lvContacts.setAdapter(adapter);
                return false;
            }
        });


        MyDatabaseHelper database = new MyDatabaseHelper(this);
        database.open();
        contacts = database.readAllContacts();
        database.close();
        adapter = new FilterContactAdapter(FilteredContacts.this, 0, contacts);
        lvContacts.setAdapter(adapter);


    }
}
