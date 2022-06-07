package com.example.irlrpg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditTraining extends AppCompatActivity {

    private TextInputEditText descTrainEdt;
    private TextView difficultText;
    private Button updateTrain, deleteTrain;
    private Spinner spinTrainEdit;
    private String expTrain;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String descTrain;
    private TrainingData trainData;
    private FirebaseUser currentFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_edit_autism);
        descTrainEdt = findViewById(R.id.descTrainEdit);
        difficultText = findViewById(R.id.dificultatEdit);
        spinTrainEdit = findViewById(R.id.levelsArrayTrainEdit);
        updateTrain = findViewById(R.id.editTrain);
        deleteTrain = findViewById(R.id.deleteTraining);
        firebaseDatabase = FirebaseDatabase.getInstance();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        trainData = getIntent().getParcelableExtra("descTrainEdit");
        if(trainData != null){
            descTrainEdt.setText(trainData.getDescTraining());
            Integer[] items = new Integer[]{1, 2, 3, 4, 5};
            ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, items);
            spinTrainEdit.setAdapter(adapter);
            descTrain = trainData.getDescTraining();
            descTrainEdt.setEnabled(false);

        }

        databaseReference = firebaseDatabase.getReference("Training").child(descTrain);
        updateTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String difficult = spinTrainEdit.getSelectedItem().toString();
                String currentUser = currentFirebaseUser.getUid();

                if(difficult.equals("1")){
                    expTrain = "100";
                }else if(difficult.equals("2")){
                    expTrain = "200";
                } else if(difficult.equals("3")){
                    expTrain = "300";
                } else if(difficult.equals("4")){
                    expTrain = "400";
                } else if(difficult.equals("5")){
                    expTrain = "500";
                }

                Map<String,Object> map = new HashMap<>();
                map.put("difficult",difficult);
                map.put("expTrain",expTrain);
                map.put("userUID",currentUser);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditTraining.this, "Quest edited", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditTraining.this,TrainingAutism.class));
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditTraining.this, "Failed to update training...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        deleteTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteQuest();
            }
        });

    }

    private void deleteQuest(){
        databaseReference.removeValue();
        Toast.makeText(EditTraining.this, "Training Deleted", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditTraining.this,TrainingAutism.class));
        finish();
    }
}
