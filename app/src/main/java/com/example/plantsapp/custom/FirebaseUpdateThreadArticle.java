package com.example.plantsapp.custom;

import android.content.Context;
import android.widget.Toast;

import com.example.plantsapp.activity.TusArticleActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseUpdateThreadArticle extends Thread {
    private DatabaseReference databaseReference;
    private Context context;

    public FirebaseUpdateThreadArticle(Context context) {
        this.context = context;
        databaseReference = FirebaseDatabase.getInstance().getReference(Const.User);
    }

    @Override
    public void run() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(Const.Userid).exists()) {
                    for (DataSnapshot likedArticleSnapshot : dataSnapshot.child(Const.Userid).child(Const.likedarticle).getChildren()) {
                        String likedarticle = likedArticleSnapshot.getValue(String.class);
                        Const.addStringToList2(likedarticle);
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
