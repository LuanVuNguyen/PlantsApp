package com.example.plantsapp.custom;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseUpdateThread extends Thread {
    private DatabaseReference databaseReference;
    private Context context;
    public FirebaseUpdateThread(Context context) {
        databaseReference = FirebaseDatabase.getInstance().getReference(Const.User);
    }

    @Override
    public void run() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(Const.Userid).exists()) {
                    for (DataSnapshot likedPlantSnapshot : dataSnapshot.child(Const.Userid).child(Const.likedplants).getChildren()) {
                        String likedPlant = likedPlantSnapshot.getValue(String.class);
                        Const.addStringToList(likedPlant);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "Load Data Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}