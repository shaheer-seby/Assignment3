package com.example.assignment3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  //implements ContactAdapter.ContactClicked
{

    FloatingActionButton fabAdd, fabFilter;
    RecyclerView rvContacts;
    LinearLayoutManager manager;
    ContactAdapter adapter;
    ArrayList<Contact> contacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddContact.class));
            }
        });
        fabFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FilteredContacts.class));
                finish();;
            }
        });

    }
    private void init()
    {
        fabAdd = findViewById(R.id.fabAdd);
        fabFilter = findViewById(R.id.fabFilter);
        rvContacts = findViewById(R.id.rvContacts);
        rvContacts.setHasFixedSize(true);
        manager = new LinearLayoutManager(this);
        rvContacts.setLayoutManager(manager);

        MyDatabaseHelper database = new MyDatabaseHelper(this);
        database.open();
        contacts = database.readAllContacts();
        database.close();

        adapter = new ContactAdapter(this, contacts);
        rvContacts.setAdapter(adapter);
    }


    public void deleteContactFromList(int index) {
        contacts.remove(index);
        adapter.notifyDataSetChanged();
   }
}