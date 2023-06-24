package com.example.plantsapp.fagment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.plantsapp.activity.AddPlantActivity;
import com.example.plantsapp.activity.ArticleActivity;
import com.example.plantsapp.R;
import com.example.plantsapp.activity.SpiecesActivity;
import com.example.plantsapp.custom.Const;
import com.example.plantsapp.custom.FirebaseUpdateThread;
import com.example.plantsapp.custom.FirebaseUpdateThreadArticle;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fm_home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fm_home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static Activity mActivity;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView btn_add, btn_species, btn_article;


    public fm_home(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fm_home.
     */
    // TODO: Rename and change types and number of parameters
    public static fm_home newInstance(String param1, String param2) {
        fm_home fragment = new fm_home(new Activity());
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fm_home, container, false);

        btn_add = view.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddPlantActivity.class);
                startActivity(intent);
                mActivity.finish();
            }
        });

        btn_species = view.findViewById(R.id.btn_species);
        btn_species.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SpiecesActivity.class);
                startActivity(intent);
                mActivity.finish();
            }
        });

        btn_article = view.findViewById(R.id.btn_article);
        btn_article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                startActivity(intent);
                mActivity.finish();
            }
        });
        ImageView imgAvt = view.findViewById(R.id.img_avt);
        imgAvt.setImageResource(R.drawable.babygroot);
        FirebaseUpdateThreadArticle firebaseUpdateThreadArticle = new FirebaseUpdateThreadArticle(mActivity);
        FirebaseUpdateThread firebaseUpdateThread = new FirebaseUpdateThread(mActivity);
        firebaseUpdateThread.start();
        firebaseUpdateThreadArticle.start();
        return view;
    }

}