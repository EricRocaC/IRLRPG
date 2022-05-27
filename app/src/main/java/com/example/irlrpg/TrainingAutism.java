package com.example.irlrpg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TrainingAutism extends AppCompatActivity {

    private FloatingActionButton btnAddTraining;
    private ListView listViewTrain;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_autism);
        btnAddTraining = findViewById(R.id.addTraining);

        btnAddTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TrainingAutism.this, "Going to training Configuration", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(TrainingAutism.this, ConfigTraining.class));
            }
        });

        ArrayList<String> trainList = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.training_rv_item, trainList);
        listViewTrain.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Training");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trainList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    trainList.add(snapshot.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}