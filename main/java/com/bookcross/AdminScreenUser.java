package com.bookcross;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class AdminScreenUser extends AppCompatActivity {//показывает список пользователей
    ListView listView;
    FirebaseListAdapter adapter;
    Button showBook, showUser, showPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);
        showBook = findViewById(R.id.showBook);
        showUser = findViewById(R.id.showUser);
        showPlace = findViewById(R.id.showPlace);

        showBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminScreenUser.this, AdminScreenBook.class));
            }
        });

        listView = (ListView) findViewById(R.id.listviewtxt);
        Query query = FirebaseDatabase.getInstance().getReference().child("User");
        FirebaseListOptions<HelperClass> options = new FirebaseListOptions.Builder<HelperClass>()
                .setLayout(R.layout.book)
                .setQuery(query, HelperClass.class)
                .build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView nUs = v.findViewById(R.id.nameUser);
                TextView idUs = v.findViewById(R.id.userId);
                TextView umUs = v.findViewById(R.id.nameUsername);
                TextView emUs = v.findViewById(R.id.userEmail);

                HelperClass us = (HelperClass) model;
                nUs.setText(us.getName().toString());
                idUs.setText(us.getUserName().toString());
                umUs.setText(us.getIdUser().toString());
                emUs.setText(us.getEmail().toString());

            }
        };
        listView.setAdapter(adapter);
    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}