package com.bookcross;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import android.text.format.DateFormat;

public class ChatScreen extends AppCompatActivity {//вылетает

    FirebaseListAdapter<Message> adapter;
    private FloatingActionButton sendBtn;
    DatabaseReference databaseUsers;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat3);
        sendBtn = findViewById(R.id.btnSend);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText textField = findViewById(R.id.messageField);
                if (textField.getText().toString() == "")
                    return;

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                databaseUsers = database.getReference("Users");
                DatabaseReference username = databaseUsers.child("username");

                TextView messagUser = findViewById(R.id.message_user);

                //String ms = messagUser.setText(username);
                textField.setText("");
                displayAllMessages();

            }

            private void displayAllMessages() {

                ListView listOfMessages = findViewById(R.id.list_of_messages);
                FirebaseListOptions<Message> options = new FirebaseListOptions.Builder<Message>()
                        .setQuery(FirebaseDatabase.getInstance().getReference(), Message.class)
                        .setLayout(R.layout.list_item)
                        .build();
                adapter = new FirebaseListAdapter<Message>(options) {
                    @Override
                    protected void populateView(View v, Message model, int position) {
                        TextView mess_user, mess_time, mess_text;

                        mess_user = v.findViewById(R.id.message_user);
                        mess_time = v.findViewById(R.id.message_time);
                        mess_text = v.findViewById(R.id.message_text);

                        mess_user.setText(model.getUserName());
                        mess_text.setText(model.getTextMessage());
                        mess_time.setText(DateFormat.format("dd-mm-yyyy HH:mm:ss", model.getMessageTime()));

                    }
                };

                listOfMessages.setAdapter(adapter);

            }

        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }


    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}