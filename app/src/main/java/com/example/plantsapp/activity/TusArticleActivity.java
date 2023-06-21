package com.example.plantsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.plantsapp.R;
import com.example.plantsapp.custom.Const;
import com.example.plantsapp.custom.ProgressBarManager;
import com.example.plantsapp.fagment.SpeciesFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class TusArticleActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference databaseRef;
    private String nameperson;
    ImageView avt_tus, avt_persion, btn_like, btn_back;
    TextView hagtag1, hagtag2, name_tus, name_person, date, des;
    boolean isLiked = false;
    ProgressBar progressBar;
    ProgressBarManager progressBarManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_article);

        init();
    }
    private void intiview(){
        des = findViewById(R.id.edit_des);
        hagtag1 = findViewById(R.id.hagtag1);
        hagtag2 = findViewById(R.id.hagtag2);
        name_tus = findViewById(R.id.name_article);
        name_person = findViewById(R.id.name);
        date = findViewById(R.id.date);
        avt_tus = findViewById(R.id.image_article);
        avt_persion = findViewById(R.id.avt_person);
        btn_like = findViewById(R.id.btn_like);
        btn_like.setOnClickListener(this);
        btn_back = findViewById(R.id.btn_plant_back);
        btn_back.setOnClickListener(this);

    }
    private void init(){
        progressBar = findViewById(R.id.progressBar);
        progressBarManager = new ProgressBarManager(progressBar);
        progressBarManager.showProgressBar();
        intiview();
        Intent intent = getIntent();
        String receivedData = intent.getStringExtra("key");
        databaseRef = FirebaseDatabase.getInstance().getReference().child(Const.refArticle).child(receivedData);
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nameperson = dataSnapshot.child(Const.FieldNamePersonArticle).getValue(String.class);
                String hagtag_1 = dataSnapshot.child(Const.FieldHagtagArticle).getValue(String.class);
                String hagtag_2 = dataSnapshot.child(Const.FieldHagtag2Article).getValue(String.class);
                String des_ = dataSnapshot.child(Const.FieldDesArticle).getValue(String.class);
                String date_ = dataSnapshot.child(Const.FieldDateArticle).getValue(String.class);
                String avt_tus_ = dataSnapshot.child(Const.FieldAvtTusArticle).getValue(String.class);
                String avt_person_ = dataSnapshot.child(Const.FieldAvtArticle).getValue(String.class);
                String name_tus_ = dataSnapshot.child(Const.FieldNameTusArticle).getValue(String.class);
                hagtag1.setText(hagtag_1);
                hagtag2.setText(hagtag_2);
                des.setText(des_);
                date.setText(date_);
                name_tus.setText(name_tus_);
                name_person.setText(nameperson);
                Picasso.get().load(avt_tus_).into(avt_tus);
                Picasso.get().load(avt_person_).into(avt_persion);
                check(nameperson);
                progressBarManager.hideProgressBar();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TusArticleActivity.this, "Load Data Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void check(String name){
        if (Const.stringList.contains(name))
        {
            btn_like.setImageResource(R.drawable.like);
        }
        else{
            btn_like.setImageResource(R.drawable.unlike);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_like:
            {

                if (!isLiked) {
                    btn_like.setImageResource(R.drawable.like);
                    Const.addStringToList(nameperson);
                    isLiked = true;
                } else {
                    btn_like.setImageResource(R.drawable.unlike);
                    Const.removeStringFromList(nameperson);
                    isLiked = false;
                }
                System.out.println(Const.stringList);
                break;
            }

            case R.id.btn_plant_back:
            {
                onBackPressed();
            }
        }
    }
    @Override
    public void onBackPressed() {

        Intent intent = new Intent(this, ArticleActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

}