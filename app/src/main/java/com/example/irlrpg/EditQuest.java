package com.example.irlrpg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
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

public class EditQuest extends AppCompatActivity {
    private TextInputEditText descQuestEdt;
    private CalendarView calQuestEdit;
    private TextView importanceText;
    private Button updateQuest, deleteQuest;
    private Spinner spinEdit;
    private String expQuest;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String descQuest;
    private QuestData questData;
    private FirebaseUser currentFirebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_edit_autism);
        calQuestEdit = findViewById(R.id.calendarQuestEdt);
        descQuestEdt = findViewById(R.id.descriptionEdit);
        importanceText = findViewById(R.id.importanciaEdit);
        spinEdit = findViewById(R.id.levelsArrayEdit);
        updateQuest = findViewById(R.id.editQuest);
        deleteQuest = findViewById(R.id.deleteQuest);
        firebaseDatabase = FirebaseDatabase.getInstance();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        questData = getIntent().getParcelableExtra("descriptionEdit");
        if(questData != null){
            descQuestEdt.setText(questData.getDescQuest());
            Integer[] items = new Integer[]{1, 2, 3, 4, 5};
            ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item, items);
            spinEdit.setAdapter(adapter);
            descQuest = questData.getDescQuest();
            descQuestEdt.setEnabled(false);

        }

        databaseReference = firebaseDatabase.getReference("Quests").child(descQuest);
        updateQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String importance = spinEdit.getSelectedItem().toString();
                String currentUser = currentFirebaseUser.getUid();
                String dateEdt = String.valueOf(calQuestEdit.getDate());

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
                map.put("importance",importance);
                map.put("expQuest",expQuest);
                map.put("userUID",currentUser);
                map.put("calendarQuest",dateEdt);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
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
