package com.example.myapplication_tips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class PdfTableActivity extends AppCompatActivity {

    //private ArrayList<Shift> shifts;
    //private RecyclerView recycler_list;
    //private MyAdapter shiftAdapter;
    //private ActivityPdfTableBinding binding;

//    DatabaseReference databaseReference;
//    FirebaseDatabase database;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("Shift");
    private StorageReference storageReference;
    private List<Shift> shiftList;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_table);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shiftList = new ArrayList<>();
//        binding = DataBindingUtil.setContentView(
//                this,
//                R.layout.activity_pdf_table
//        );
//        database = FirebaseDatabase.getInstance();
//        databaseReference = database.getReference("shifts");
        //recycler_list = binding.recyclerList;
        //recycler_list.setAdapter(shiftAdapter);
        //recycler_list.setLayoutManager(new LinearLayoutManager(this));
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
//                    Shift shift = dataSnapshot.getValue(Shift.class);
//                    shifts.add(shift);
//                }
//                shiftAdapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        shifts = new ArrayList<>();
//        shiftAdapter = new MyAdapter(this, shifts);
//        recycler_list.setAdapter(shiftAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        collectionReference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (QueryDocumentSnapshot shifts: queryDocumentSnapshots) {
                    Shift shift = shifts.toObject(Shift.class);
                    shiftList.add(shift);
                }
                myAdapter = new MyAdapter(PdfTableActivity.this, shiftList);
                recyclerView.setAdapter(myAdapter);
                myAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PdfTableActivity.this, "opps! some some thing went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}