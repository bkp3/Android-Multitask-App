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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CreateDataActivity extends AppCompatActivity {

    private EditText nameCRUD, dayCRUD, dateCRUD, timeCRUD;
    private Button saveDataBtn,home;

    FirebaseAuth mAuth;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_data);

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();


        nameCRUD = findViewById(R.id.name_create_data);
        dayCRUD = findViewById(R.id.day_create_data);
        dateCRUD = findViewById(R.id.date_create_data);
        timeCRUD = findViewById(R.id.time_create_data);
        saveDataBtn = findViewById(R.id.save_create_data);
        home = findViewById(R.id.home_cdo);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateDataActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        saveDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameCRUD.getText().toString();
                String day = dayCRUD.getText().toString();
                String date = dateCRUD.getText().toString();
                String time = timeCRUD.getText().toString();

                if(name.isEmpty()){
                    Toast.makeText(CreateDataActivity.this, "fill name", Toast.LENGTH_SHORT).show();
                }else if(day.isEmpty()){
                    Toast.makeText(CreateDataActivity.this, "fill day", Toast.LENGTH_SHORT).show();
                }else if(date.isEmpty()){
                    Toast.makeText(CreateDataActivity.this, "fill date", Toast.LENGTH_SHORT).show();
                }else if(time.isEmpty()){
                    Toast.makeText(CreateDataActivity.this, "fill time", Toast.LENGTH_SHORT).show();
                }else{
                    fun(name, day,date,time);
                }
            }
        });

    }

    private void fun(String name, String day, String date, String time) {

        HashMap<String, Object>mp = new HashMap<>();
        mp.put("name",name);
        mp.put("day",day);
        mp.put("date",date);
        mp.put("time",time);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Users").child(uid).child("CrudInfo").updateChildren(mp).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(CreateDataActivity.this, "Data saved successfully.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateDataActivity.this, CrudOperationActivity.class);
                    startActivity(intent);
                    nameCRUD.setText("");
                    dayCRUD.setText("");
                    dateCRUD.setText("");
                    timeCRUD.setText("");
                }else{
                    Toast.makeText(CreateDataActivity.this, "ERROR: Not saved.", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}
