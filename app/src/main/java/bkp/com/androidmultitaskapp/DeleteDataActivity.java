package bkp.com.androidmultitaskapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DeleteDataActivity extends AppCompatActivity {

    private TextView nameCRUD, dayCRUD, dateCRUD, timeCRUD;
    private Button home,deleteData;

    FirebaseAuth mAuth;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_data);

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();


        nameCRUD = findViewById(R.id.name_delete_data);
        dayCRUD = findViewById(R.id.day_delete_data);
        dateCRUD = findViewById(R.id.date_delete_data);
        timeCRUD = findViewById(R.id.time_delete_data);
        home = findViewById(R.id.home_ddo);
        deleteData = findViewById(R.id.delete_data);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteDataActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        fun();

        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mref = FirebaseDatabase.getInstance().getReference();
                mref.child("Users").child(uid).child("CrudInfo").removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(DeleteDataActivity.this, "Data deleted successfully.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DeleteDataActivity.this,CrudOperationActivity.class);
                            startActivity(intent);

                        }else{
                            Toast.makeText(DeleteDataActivity.this, "Not deleted.", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });


    }

    private void fun() {

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
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
                    Toast.makeText(DeleteDataActivity.this, "Showing data for delete.", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(DeleteDataActivity.this, "No data for delete.", Toast.LENGTH_SHORT).show();
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
