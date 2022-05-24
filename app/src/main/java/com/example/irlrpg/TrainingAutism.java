package com.example.irlrpg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TrainingAutism extends AppCompatActivity {

    private FloatingActionButton btnAddTraining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_autism);
        btnAddTraining = findViewById(R.id.addTraining);

        btnAddTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TrainingAutism.this, "Training Configuration", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(TrainingAutism.this, ConfigTraining.class));
            }
        });
    }
}