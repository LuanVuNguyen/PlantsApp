package com.example.plantsapp.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plantsapp.R
import com.example.plantsapp.RecyclerViewAdapter
import com.example.plantsapp.custom.Const
import com.example.plantsapp.custom.DataHelper
import com.example.plantsapp.custom.ProgressBarManager
import com.google.firebase.database.FirebaseDatabase
import `in`.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView

class SpiecesActivity : AppCompatActivity(), RecyclerViewAdapter.OnItemClickListener {

    private var mRecyclerView: IndexFastScrollRecyclerView? = null
    private var mSearchEditText: EditText? = null
    private val mData = ArrayList<String>()
    private val filteredData = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spieces)
        mRecyclerView = findViewById(R.id.fast_scroller_recycler)
        mSearchEditText = findViewById(R.id.txt_search_spieces)
        initialiseUI()
        fetchDataFromFirebase()

        mSearchEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterData(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun initialiseUI() {
        mRecyclerView?.apply {
            layoutManager = LinearLayoutManager(this@SpiecesActivity)
            val adapter = RecyclerViewAdapter(mData)
            adapter.setOnItemClickListener(this@SpiecesActivity)
            setIndexTextSize(12)
            setIndexBarCornerRadius(0)
            setIndexBarTransparentValue(0.4.toFloat())
            setPreviewPadding(0)
            setPreviewTextSize(60)
            setPreviewTransparentValue(0.6f)
            setIndexBarVisibility(true)
            setIndexBarStrokeVisibility(true)
            setIndexBarStrokeWidth(1)
            setIndexBarHighLightTextVisibility(true)
            this.adapter = adapter
        }
        mRecyclerView?.layoutManager?.scrollToPosition(0)
    }

    private fun fetchDataFromFirebase() {
        DataHelper.getAlphabetFullData { dataList ->
            mData.clear()
            mData.addAll(dataList)
            val adapter = RecyclerViewAdapter(mData)
            adapter.setOnItemClickListener(this)
            mRecyclerView?.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    private fun filterData(query: String) {
        filteredData.clear()

        for (item in mData) {
            if (item.contains(query, ignoreCase = true)) {
                filteredData.add(item)
            }
        }

        val adapter = RecyclerViewAdapter(filteredData)
        adapter.setOnItemClickListener(this)
        mRecyclerView?.adapter = adapter
        adapter.notifyDataSetChanged()

        mRecyclerView?.layoutManager?.scrollToPosition(0)
    }

    override fun onItemClick(item: String) {
        val intent = Intent(this, SpieceArticleActivity::class.java)
        intent.putExtra("itemData", item)
        startActivity(intent)
    }

    override fun onBackPressed() {
        // Quay trở lại trang home
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
