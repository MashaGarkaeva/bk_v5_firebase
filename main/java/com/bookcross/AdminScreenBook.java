package com.bookcross;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AdminScreenBook extends AppCompatActivity{//показывает список книг
    ListView listView;
    FirebaseListAdapter adapter;
    Button showBook, showUser, showPlace;
    SearchView searchView;
    ImageView btnAdd, btnUpdate, btnDelete;
    String select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_book);
        showBook = findViewById(R.id.showBook);
        showUser = findViewById(R.id.showUser);
        showPlace = findViewById(R.id.showPlace);
        btnAdd = findViewById(R.id.btnAddAdmin);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();

        showUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminScreenBook.this, AdminScreenUser.class));
            }
        });

        listView = (ListView) findViewById(R.id.listviewtxt);
        Query query = FirebaseDatabase.getInstance().getReference().child("Book");
        FirebaseListOptions<DataClass> options = new FirebaseListOptions.Builder<DataClass>()
                .setLayout(R.layout.book)
                .setQuery(query, DataClass.class)
                .build();
        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                TextView nB = v.findViewById(R.id.nameBook);
                TextView aB = v.findViewById(R.id.auhtorBook);
                TextView pB = v.findViewById(R.id.placeBook);
                TextView idB = v.findViewById(R.id.keyBook);

                DataClass bk = (DataClass) model;
                nB.setText("Название книги: " + bk.getdataName());
                aB.setText("Автор книги: " + bk.getdataAuhtor());
                pB.setText("Где книга находится: " + bk.getPlace());
                idB.setText("id: " + bk.getKey());

            }
        };

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                select = (String) adapterView.getItemAtPosition(i);

                //btnDelete.setOnClickListener(this);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //processSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AdminScreenBook.this, AddBookScreen.class));
            }
        });

    }

    private void processSearch(String s) {//поиск не работает
        FirebaseListOptions<DataClass> options = new FirebaseListOptions.Builder<DataClass>()
                .setLayout(R.layout.book)
                .setQuery(FirebaseDatabase.getInstance().getReference()
                .child("Book").startAt(s).endAt(s + "\uf8ff"), DataClass.class)
                .build();

        adapter = new FirebaseListAdapter(options) {
            @Override
            protected void populateView(View v, Object model, int position) {
                adapter.startListening();
                listView.setAdapter(adapter);
            }
        };

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