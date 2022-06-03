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
    private ArrayList<String> trainingDataArrayList;
    private ArrayAdapter<String> adapter;
    TrainingData dataT;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_autism);
        listViewTrain = findViewById(R.id.trainings);
        btnAddTraining = findViewById(R.id.addTraining);

        btnAddTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TrainingAutism.this, "Going to training Configuration", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(TrainingAutism.this, ConfigTraining.class));
            }
        });

        trainingDataArrayList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.quest_rv_item, trainingDataArrayList);
        listViewTrain.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Training");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trainingDataArrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    dataT = dataSnapshot.getValue(TrainingData.class);
                    trainingDataArrayList.add(dataT.getDescTraining() + " - " + dataT.getExpTrain() + " EXP");
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}