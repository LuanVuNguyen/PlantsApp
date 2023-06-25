package com.example.plantsapp.fagment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.plantsapp.adapter.ProfileAdapter;
import com.example.plantsapp.R;
import com.example.plantsapp.custom.Const;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class fm_profile extends Fragment {

    public fm_profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_fm_profile2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TextView uname = view.findViewById(R.id.txt_edit_name);
        TextView title = view.findViewById(R.id.textView5);
        ImageView imgAvt = view.findViewById(R.id.img_avt);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        ViewPager2 viewPager = view.findViewById(R.id.view_paper);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(Const.User);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String usrname = dataSnapshot.child(Const.Userid).child("displayName").getValue(String.class);
                uname.setText(usrname + ", ");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });




        title.setText("Let's Learn More About Plants");


        imgAvt.setImageResource(R.drawable.babygroot);


        ProfileAdapter adapter = new ProfileAdapter(getChildFragmentManager(), getLifecycle());
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("Species");
            } else if (position == 1) {
                tab.setText("Article");
            }
        }).attach();
    }
}