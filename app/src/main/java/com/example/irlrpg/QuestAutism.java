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

public class QuestAutism extends AppCompatActivity {

    private FloatingActionButton btnAdd;
    private Button btnReturn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<String> questDataArrayList;
    private ArrayAdapter<String> adapter;
    private FirebaseUser currentFirebaseUser;
    QuestData data;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quests_autism);
        btnAdd = findViewById(R.id.addQuest);
        listView = findViewById(R.id.quests);
        btnReturn = findViewById(R.id.comebackQuest);
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(QuestAutism.this,EditQuest.class);
                String descQuest = parent.getItemAtPosition(position).toString();
                String str[] = descQuest.split(" - ");
                List<String> splited;
                splited = Arrays.asList(str);
                data.setDescQuest(splited.get(0));
                i.putExtra("descriptionEdit", data);
                Toast.makeText(QuestAutism.this, "Going to edit quest", Toast.LENGTH_SHORT).show();
                startActivity(i);
                finish();
            }
        });

        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuestAutism.this, "Going to profile", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(QuestAutism.this, MainActivity.class));
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Toast.makeText(QuestAutism.this, "Going to quest Configuration", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(QuestAutism.this, ConfigQuest.class));
                finish();
            }
        });

        questDataArrayList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.quest_rv_item, questDataArrayList);
        listView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Quests");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                questDataArrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    data = dataSnapshot.getValue(QuestData.class);
                    if(data.getUserUID().equals(currentFirebaseUser.getUid())){
                        questDataArrayList.add(data.getDescQuest() + " - " + data.getExpQuest() + " EXP");
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}