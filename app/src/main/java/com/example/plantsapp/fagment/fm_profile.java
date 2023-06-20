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
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fm_profile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fm_profile extends Fragment {

    public fm_profile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fm_profile2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Ánh xạ các thành phần UI trong giao diện
        TextView textView4 = view.findViewById(R.id.textView4);
        TextView textView5 = view.findViewById(R.id.textView5);
        ImageView imgAvt = view.findViewById(R.id.img_avt);
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        ViewPager2 viewPager = view.findViewById(R.id.view_paper);

        // Thiết lập các thông tin và xử lý UI tại đây

        // Ví dụ: Thiết lập text cho TextView
        textView4.setText("Hello Tory,");
        textView5.setText("Let's Learn More About Plants");

        // Ví dụ: Gán hình ảnh cho ImageView
        imgAvt.setImageResource(R.drawable.plant);

        // Ví dụ: Thiết lập Adapter cho ViewPager2 và đặt TabLayout kết hợp
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