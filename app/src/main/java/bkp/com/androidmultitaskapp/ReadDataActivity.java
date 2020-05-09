package bkp.com.androidmultitaskapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReadDataActivity extends AppCompatActivity {

    private TextView nameCRUD, dayCRUD, dateCRUD, timeCRUD;
    private Button home;

    FirebaseAuth mAuth;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_data);

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();


        nameCRUD = findViewById(R.id.name_read_data);
        dayCRUD = findViewById(R.id.day_read_data);
        dateCRUD = findViewById(R.id.date_read_data);
        timeCRUD = findViewById(R.id.time_read_data);

        home = findViewById(R.id.home_rdo);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReadDataActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        fun();


    }

    private void fun() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Users").child(uid).child("CrudInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    String name = dataSnapshot.child("name").getValue(String.class);
                    String day = dataSnapshot.child("day").getValue(String.class);
                    String date = dataSnapshot.child("date").getValue(String.class);
                    String time = dataSnapshot.child("time").getValue(String.class);

                    nameCRUD.setText("Name :- " + name);
                    dayCRUD.setText("Current Day :- " + day);
                    dateCRUD.setText("Current Date :- " + date);
                    timeCRUD.setText("Current Time :- " + time);

                    Toast.makeText(ReadDataActivity.this, "Showing saved data", Toast.LENGTH_SHORT).show();


                }else{
                    Toast.makeText(ReadDataActivity.this, "No saved data", Toast.LENGTH_SHORT).show();
                    nameCRUD.setText("Name :- " + "null");
                    dayCRUD.setText("Current Day :- " + "null");
                    dateCRUD.setText("Current Date :- " + "null");
                    timeCRUD.setText("Current Time :- " + "null");


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
