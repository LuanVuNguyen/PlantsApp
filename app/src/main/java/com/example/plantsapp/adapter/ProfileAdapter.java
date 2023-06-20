package com.example.plantsapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.plantsapp.fagment.ArticleFragment;
import com.example.plantsapp.fagment.SpeciesFragment;

public class ProfileAdapter extends FragmentStateAdapter {

    public ProfileAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new SpeciesFragment();
        } else if (position == 1) {
            return new ArticleFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
