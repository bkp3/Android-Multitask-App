package bkp.com.androidmultitaskapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {

    private EditText nameEdt, fnameEdt, ageEdt, phoneEdt, emailEdt, passwordEdt, addressEdt;
    private Button createBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        nameEdt = findViewById(R.id.name_create);
        fnameEdt = findViewById(R.id.fname_create);
        ageEdt = findViewById(R.id.age_create);
        phoneEdt = findViewById(R.id.phone_create);
        emailEdt = findViewById(R.id.email_create);
        passwordEdt = findViewById(R.id.password_create);
        addressEdt = findViewById(R.id.address_create);

        createBtn = findViewById(R.id.account_create);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEdt.getText().toString();
                String fname = fnameEdt.getText().toString();
                String age = ageEdt.getText().toString();
                String phone = phoneEdt.getText().toString();
                String email = emailEdt.getText().toString();
                String password = passwordEdt.getText().toString();
                String address = addressEdt.getText().toString();

                if(name.isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "Fill name", Toast.LENGTH_SHORT).show();
                }else if(fname.isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "fill father name.", Toast.LENGTH_SHORT).show();
                }else if(age.isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "fill age.", Toast.LENGTH_SHORT).show();
                }else if(phone.isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "fill phone.", Toast.LENGTH_SHORT).show();
                }else if(email.isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "fill email.", Toast.LENGTH_SHORT).show();
                }else if(password.isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "fill password.", Toast.LENGTH_SHORT).show();
                }else if(address.isEmpty()){
                    Toast.makeText(RegistrationActivity.this, "fill address.", Toast.LENGTH_SHORT).show();
                }else{

                    createUser(name, fname, age, phone, email, password, address);

                }

            }
        });
    }



    private void createUser(String name, String fname, String age, String phone, String email, String password, String address) {

        final HashMap<String, Object>mp = new HashMap<>();
        mp.put("name",name);
        mp.put("fname",fname);
        mp.put("age",age);
        mp.put("phone",phone);
        mp.put("email",email);
        mp.put("password",password);
        mp.put("address",address);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    Toast.makeText(RegistrationActivity.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegistrationActivity.this, "Now you can login", Toast.LENGTH_SHORT).show();

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    ref.child("Users").child(mAuth.getUid()).child("PersonalInfo").updateChildren(mp);

                    Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();


                }else{
                    Toast.makeText(RegistrationActivity.this, "ERROR: Please try again", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
