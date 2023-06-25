package com.example.plantsapp.fagment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.plantsapp.R
import com.example.plantsapp.activity.AddPlantActivity
import com.example.plantsapp.activity.ArticleActivity
import com.example.plantsapp.activity.SpiecesActivity
import com.example.plantsapp.custom.FirebaseUpdateThread
import com.example.plantsapp.custom.FirebaseUpdateThreadArticle

class fm_home(private val mActivity: Activity) : Fragment() {

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): fm_home {
            val fragment = fm_home(Activity())
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var btn_add: ImageView
    private lateinit var btn_species: ImageView
    private lateinit var btn_article: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_fm_home, container, false)

        btn_add = view.findViewById(R.id.icon_add)
        btn_add.setOnClickListener {
            val intent = Intent(activity, AddPlantActivity::class.java)
            startActivity(intent)
            mActivity.finish()
        }

        btn_species = view.findViewById(R.id.icon_species)
        btn_species.setOnClickListener {
            val intent = Intent(activity, SpiecesActivity::class.java)
            startActivity(intent)
            mActivity.finish()
        }

        btn_article = view.findViewById(R.id.icon_article)
        btn_article.setOnClickListener {
            val intent = Intent(activity, ArticleActivity::class.java)
            startActivity(intent)
            mActivity.finish()
        }

        val firebaseUpdateThreadArticle = FirebaseUpdateThreadArticle(mActivity)
        val firebaseUpdateThread = FirebaseUpdateThread(mActivity)
        firebaseUpdateThread.start()
        firebaseUpdateThreadArticle.start()
        return view
    }
}
