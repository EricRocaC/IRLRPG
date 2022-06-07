package com.example.irlrpg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TrainingAutism extends AppCompatActivity {

    private FloatingActionButton btnAddTraining;
    private Button btnReturn;
    private ListView listViewTrain;
    private ArrayList<String> trainingDataArrayList;
    private ArrayAdapter<String> adapterT;
    TrainingData dataT;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser currentFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_autism);
        listViewTrain = findViewById(R.id.training);
        btnAddTraining = findViewById(R.id.goAddTrain);
        btnReturn = findViewById(R.id.comebackTrain);
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        listViewTrain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(TrainingAutism.this,EditTraining.class);
                String descQuest = parent.getItemAtPosition(position).toString();
                String str[] = descQuest.split(" - ");
                List<String> splited;
                splited = Arrays.asList(str);
                dataT.setDescTraining(splited.get(0));
                i.putExtra("descTrainEdit", dataT);
                Toast.makeText(TrainingAutism.this, "Going to quest Configuration", Toast.LENGTH_SHORT).show();
                startActivity(i);
                finish();
            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TrainingAutism.this, "Going to profile", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(TrainingAutism.this, MainActivity.class));
                finish();
            }
        });

        btnAddTraining.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                startActivity(new Intent(TrainingAutism.this, ConfigTraining.class));
                finish();
            }
        });

        trainingDataArrayList = new ArrayList<>();
        adapterT = new ArrayAdapter<String>(this, R.layout.training_rv_item, trainingDataArrayList);
        listViewTrain.setAdapter(adapterT);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Training");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                trainingDataArrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    dataT = dataSnapshot.getValue(TrainingData.class);
                    if(dataT.getUserUID().equals(currentFirebaseUser.getUid())){
                        trainingDataArrayList.add(dataT.getDescTraining() + " - " + dataT.getExpTrain() + " EXP");
                    }
                }
                adapterT.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}