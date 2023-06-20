package com.example.plantsapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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


public class SpieceArticleActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference databaseRef;
    TextView txt_hagtag1, txt_hagtag2, txt_name, txt_kingdom, txt_family, txt_des;
    ImageView avt_plant, btn_like, btn_back;
    String name;
    boolean isLiked = false;
    ProgressBar progressBar;
    ProgressBarManager progressBarManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spieces_article);
        progressBar = findViewById(R.id.progressBar2);
        progressBarManager = new ProgressBarManager(progressBar);
        init();
        progressBarManager.showProgressBar();
        String itemData = getIntent().getStringExtra("itemData");
        databaseRef = FirebaseDatabase.getInstance().getReference().child(Const.refPlant).child(itemData);
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                name = dataSnapshot.child(Const.FieldNameAdd).getValue(String.class);
                String family = dataSnapshot.child(Const.FieldFamilyAdd).getValue(String.class);
                String kingdom = dataSnapshot.child(Const.FieldKingdomAdd).getValue(String.class);
                String des = dataSnapshot.child(Const.FieldDesAdd).getValue(String.class);
                String hagtag1 = dataSnapshot.child(Const.FieldHagtagAdd).getValue(String.class);
                String hagtag2 = dataSnapshot.child(Const.FieldHagtag2Add).getValue(String.class);
                String image = dataSnapshot.child(Const.FieldImageAdd).getValue(String.class);
                txt_name.setText(name);
                txt_family.setText(family);
                txt_hagtag1.setText(hagtag1);
                txt_hagtag2.setText(hagtag2);
                txt_kingdom.setText(kingdom);
                txt_des.setText(des);
                check(name);
                Picasso.get().load(image).into(avt_plant);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý khi có lỗi xảy ra
            }
        });
        progressBarManager.hideProgressBar();
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

    private void init(){
        txt_des = findViewById(R.id.edit_des);
        txt_family = findViewById(R.id.edit_family);
        txt_hagtag1 = findViewById(R.id.hagtag1);
        txt_hagtag2 = findViewById(R.id.hagtag2);
        txt_name =  findViewById(R.id.name_article);
        avt_plant = findViewById(R.id.image_article);
        txt_kingdom = findViewById(R.id.edit_kingdom1);
        btn_like = findViewById(R.id.btn_like);
        btn_like.setOnClickListener(this);
        btn_back = findViewById(R.id.btn_plant_back);
        btn_back.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_like:
            {

                if (!isLiked) {
                    btn_like.setImageResource(R.drawable.like);
                    Const.addStringToList(name);
                    isLiked = true;
                } else {
                    btn_like.setImageResource(R.drawable.unlike);
                    Const.removeStringFromList(name);
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

}
