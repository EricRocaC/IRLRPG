package com.example.irlrpg;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

public class ConfigTraining extends AppCompatActivity {

    private Spinner spin;
    private TextInputEditText descTrainingEdt;
    private Button btnAddTrain;
    private String expTrain;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser currentFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_config_autism);
        spin = findViewById(R.id.levelsArrayTrain);
        descTrainingEdt = findViewById(R.id.descTrain);
        btnAddTrain = findViewById(R.id.addTrain);
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Training");

        Integer[] items = new Integer[]{1, 2, 3, 4, 5};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, items);
        spin.setAdapter(adapter);

        btnAddTrain.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String descTraining = descTrainingEdt.getText().toString();
                String difficult = spin.getSelectedItem().toString();
                String currentUser = currentFirebaseUser.getUid();

                if (TextUtils.isEmpty(descTraining)){
                    Toast.makeText(ConfigTraining.this, "Please enter training data", Toast.LENGTH_SHORT).show();
                    return;
                } else {
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

                    TrainingData trainingData = new TrainingData(descTraining, difficult, expTrain, currentUser);

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(descTraining).setValue(trainingData);

                            Toast.makeText(ConfigTraining.this, "Training added", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ConfigTraining.this,TrainingAutism.class));
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(ConfigTraining.this, "Error is" + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}

