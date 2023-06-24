package com.example.plantsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.plantsapp.R;
import com.example.plantsapp.custom.Const;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ArticleActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView btn_1, btn_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        init();
    }
    private void init(){

        btn_1 = findViewById(R.id.article1);
        btn_1.setOnClickListener(this);
        btn_2 = findViewById(R.id.article2);
        btn_2.setOnClickListener(this);
        check();
    }
    private void check(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Const.User);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(Const.Userid).exists()) {
                    for (DataSnapshot likedArticleSnapshot : dataSnapshot.child(Const.Userid).child(Const.likedarticle).getChildren()) {
                        String likedArticle = likedArticleSnapshot.getValue(String.class);
                        if (likedArticle.equals(Const.Writer1)) {
                            btn_1.setImageResource(R.drawable.article_like);
                        } else if (likedArticle.equals(Const.Writer2)) {
                            btn_2.setImageResource(R.drawable.article_like_2);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ArticleActivity.this, "Load Data Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.article1:
            {
                Intent intent = new Intent(this, TusArticleActivity.class);
                intent.putExtra("key", Const.article1);
                startActivity(intent);
                break;
            }

            case R.id.article2:
            {
                Intent intent = new Intent(this, TusArticleActivity.class);
                intent.putExtra("key", Const.article2);
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Quay trở lại trang home
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }
}