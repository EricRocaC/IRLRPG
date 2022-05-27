package com.example.irlrpg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditQuest extends AppCompatActivity {
    private TextInputEditText descQuestEdt;
    private Button updateQuest, deleteQuest;
    private Spinner spin;
    private String expQuest;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String descQuest;
    private QuestData questData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_edit_autism);
        firebaseDatabase = FirebaseDatabase.getInstance();
        descQuestEdt = findViewById(R.id.descriptionEdit);
        spin = findViewById(R.id.levelsArrayTrainEdit);
        updateQuest = findViewById(R.id.editQuest);
        deleteQuest = findViewById(R.id.deleteQuest);
        //questData = getIntent().getParcelableExtra("i");
        if(questData != null){
            descQuestEdt.setText(questData.getDescQuest());
            String[] items = new String[]{"1","2","3","4","5"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>
                    (this, android.R.layout.simple_spinner_item,
                            items);
            spin.setAdapter(adapter);
            descQuest = questData.getDescQuest();

        }

        databaseReference = firebaseDatabase.getReference().child("Quests");
        updateQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descQuest = descQuestEdt.getText().toString();
                String importance = spin.getSelectedItem().toString();

                if(importance.equals("1")){
                    expQuest = "100";
                }else if(importance.equals("2")){
                    expQuest = "200";
                } else if(importance.equals("3")){
                    expQuest = "300";
                } else if(importance.equals("4")){
                    expQuest = "400";
                } else if(importance.equals("5")){
                    expQuest = "500";
                }

                Map<String,Object> map = new HashMap<>();
                map.put("descQuest",descQuest);
                map.put("importance",importance);
                map.put("exp",expQuest);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.updateChildren(map);
                        Toast.makeText(EditQuest.this, "Quest edited", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditQuest.this,QuestAutism.class));
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EditQuest.this, "Failed to update course...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        deleteQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteQuest();
            }
        });

    }

    private void deleteQuest(){
        databaseReference.removeValue();
        Toast.makeText(EditQuest.this, "Quest Deleted", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditQuest.this,QuestAutism.class));
        finish();
    }
}
