package bkp.com.androidmultitaskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CrudOperationActivity extends AppCompatActivity {

    private TextView createTxt, readTxt, updateTxt, deleteTxt;
    private Button homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_operation);

        createTxt = findViewById(R.id.create_crud);
        readTxt = findViewById(R.id.read_crud);
        updateTxt = findViewById(R.id.update_crud);
        deleteTxt = findViewById(R.id.delete_crud);

        homeBtn = findViewById(R.id.home_crud);

        createTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrudOperationActivity.this, CreateDataActivity.class);
                startActivity(intent);
            }
        });

        readTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrudOperationActivity.this, ReadDataActivity.class);
                startActivity(intent);
            }
        });

        updateTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrudOperationActivity.this, UpdateDataActivity.class);
                startActivity(intent);
            }
        });

        deleteTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrudOperationActivity.this, DeleteDataActivity.class);
                startActivity(intent);
            }
        });


        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CrudOperationActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });



    }
}
