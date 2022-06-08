package com.example.irlrpg;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
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

public class ConfigQuest extends AppCompatActivity {
    public Spinner spin;
    private CalendarView calendarQuest;
    public TextInputEditText descQuestEdt;
    private Button btnAdd, btnBack;
    private String expQuest;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseUser currentFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quests_config_autism);
        spin = findViewById(R.id.levelsArray);
        calendarQuest = findViewById(R.id.calendarQuest);
        descQuestEdt = findViewById(R.id.description);
        btnAdd = findViewById(R.id.addQuest);
        btnBack = findViewById(R.id.backQuest);
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Quests");

        Integer[] items = new Integer[]{1, 2, 3, 4, 5};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, items);
        spin.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ConfigQuest.this, "Turning to quest...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ConfigQuest.this,QuestAutism.class));
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String descQuest = descQuestEdt.getText().toString();
                String importance = spin.getSelectedItem().toString();
                String currentUser = currentFirebaseUser.getUid();
                String date = String.valueOf(calendarQuest.getDate());

                if (TextUtils.isEmpty(descQuest)){
                    Toast.makeText(ConfigQuest.this, "Please enter quest data", Toast.LENGTH_SHORT).show();
                    return;
                } else {
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

                    QuestData questData = new QuestData(descQuest, importance, expQuest, date, currentUser);

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            databaseReference.child(descQuest).setValue(questData);

                            Toast.makeText(ConfigQuest.this, "Quest added", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ConfigQuest.this,QuestAutism.class));
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(ConfigQuest.this, "Error is" + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}
