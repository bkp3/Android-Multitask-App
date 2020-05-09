package bkp.com.androidmultitaskapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UpdateDataActivity extends AppCompatActivity {

    private EditText nameCRUD, dayCRUD, dateCRUD, timeCRUD;
    private Button updateDataBtn,home;

    FirebaseAuth mAuth;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();


        nameCRUD = findViewById(R.id.name_update_data);
        dayCRUD = findViewById(R.id.day_update_data);
        dateCRUD = findViewById(R.id.date_update_data);
        timeCRUD = findViewById(R.id.time_update_data);
        updateDataBtn = findViewById(R.id.save_update_data);
        home = findViewById(R.id.home_udo);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateDataActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        ref.child("Users").child(uid).child("CrudInfo").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    String name = dataSnapshot.child("name").getValue(String.class);
                    String day = dataSnapshot.child("day").getValue(String.class);
                    String date = dataSnapshot.child("date").getValue(String.class);
                    String time = dataSnapshot.child("time").getValue(String.class);

                    nameCRUD.setText(name);
                    dayCRUD.setText(day);
                    dateCRUD.setText(date);
                    timeCRUD.setText(time);

                    Toast.makeText(UpdateDataActivity.this, "Showing data for update.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(UpdateDataActivity.this, "No data for update.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        updateDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameCRUD.getText().toString();
                String day = dayCRUD.getText().toString();
                String date = dateCRUD.getText().toString();
                String time = timeCRUD.getText().toString();

                if(name.isEmpty()){
                    Toast.makeText(UpdateDataActivity.this, "fill name", Toast.LENGTH_SHORT).show();
                }else if(day.isEmpty()){
                    Toast.makeText(UpdateDataActivity.this, "fill day", Toast.LENGTH_SHORT).show();
                }else if(date.isEmpty()){
                    Toast.makeText(UpdateDataActivity.this, "fill date", Toast.LENGTH_SHORT).show();
                }else if(time.isEmpty()){
                    Toast.makeText(UpdateDataActivity.this, "fill time", Toast.LENGTH_SHORT).show();
                }else{
                    fun(name, day,date,time);
                }
            }
        });


    }
    private void fun(String name, String day, String date, String time) {

        DatabaseReference refr = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> mp = new HashMap<>();
        mp.put("name",name);
        mp.put("day",day);
        mp.put("date",date);
        mp.put("time",time);

        refr.child("Users").child(uid).child("CrudInfo").updateChildren(mp).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(UpdateDataActivity.this, "Data updated successfully.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateDataActivity.this, CrudOperationActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(UpdateDataActivity.this, "ERROR: Not updated.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
