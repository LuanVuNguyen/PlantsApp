package com.example.plantsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.plantsapp.R;
import com.example.plantsapp.custom.Const;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        System.out.println(Const.stringList);
        for (String name : Const.stringList) {
            try {
                if (name.equals(Const.Writer1)) {
                    btn_1.setImageResource(R.drawable.article_like);
                } else if (name.equals(Const.Writer2)) {
                    btn_2.setImageResource(R.drawable.article_like_2);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
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