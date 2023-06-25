package com.example.plantsapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.plantsapp.fagment.ArticleFragment
import com.example.plantsapp.fagment.SpeciesFragment

class ProfileAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SpeciesFragment()
            1 -> ArticleFragment()
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }

    override fun getItemCount(): Int {
        return 2
    }
}
