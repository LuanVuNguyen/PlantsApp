package com.example.plantsapp.fagment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.plantsapp.R
import com.example.plantsapp.adapter.ProfileAdapter
import com.example.plantsapp.custom.Const
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class fm_profile : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_fm_profile2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uname = view.findViewById<TextView>(R.id.txt_edit_name)
        val title = view.findViewById<TextView>(R.id.txt_email)
        val imgAvt = view.findViewById<ImageView>(R.id.img_avt)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = view.findViewById<ViewPager2>(R.id.view_paper)

        val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference(Const.User)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usrname: String? = dataSnapshot.child(Const.Userid).child("displayName").getValue(String::class.java)
                val avt: String? = dataSnapshot.child(Const.Userid).child("avatar").getValue(String::class.java)
                val email: String? = dataSnapshot.child(Const.Userid).child("email").getValue(String::class.java)
                Picasso.get().load(avt).into(imgAvt)
                uname.text = "$usrname, "
                title.text = "$email"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors if any
            }
        })

        val adapter = ProfileAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            if (position == 0) {
                tab.text = "Species"
            } else if (position == 1) {
                tab.text = "Article"
            }
        }.attach()
    }
}
