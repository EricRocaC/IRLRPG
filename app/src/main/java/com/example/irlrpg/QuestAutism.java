package com.example.irlrpg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuestAutism extends AppCompatActivity {

    private FloatingActionButton btnAdd;
    private RecyclerView questRV;
    private Button btnEdit;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<QuestData> questDataArrayList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quests_autism);
        btnAdd = findViewById(R.id.addQuest);
        btnEdit = findViewById(R.id.btnEditQuest);
        listView = findViewById(R.id.quests);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuestAutism.this, "Going to quest Configuration", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(QuestAutism.this, ConfigQuest.class));

            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuestAutism.this, "Going to edit quest", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(QuestAutism.this, EditQuest.class));
            }
        });

        ArrayList<String> list = new ArrayList<>();
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.quest_rv_item, list);
        listView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Quests");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    list.add(snapshot.getValue().toString());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}