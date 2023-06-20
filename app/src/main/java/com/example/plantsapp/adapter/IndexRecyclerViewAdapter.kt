package com.example.plantsapp

import android.widget.SectionIndexer
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.plantsapp.custom.Helpers.Companion.sectionsHelper

import java.util.*
import kotlin.collections.ArrayList

class RecyclerViewAdapter(private val mDataArray: ArrayList<String>?) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(), SectionIndexer {

    private val mSections = "ABCDEFGHIJKLMNOPQRSTUVWXYZ#"
    private var sectionsTranslator = HashMap<Int, Int>()
    private var mSectionPositions: ArrayList<Int>? = null
    private var itemClickListener: OnItemClickListener? = null

    override fun getItemCount(): Int {
        return mDataArray?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rv, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mTextView.text = mDataArray!![position]
        holder.mImageButton.setOnClickListener {
            mDataArray.removeAt(holder.adapterPosition)
            notifyItemRemoved(position)
        }
        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(mDataArray[position])
        }
    }

    override fun getSectionForPosition(position: Int): Int {
        return 0
    }

    override fun getSections(): Array<String> {
        val sections: MutableList<String> = ArrayList(27)
        val alphabetFull = ArrayList<String>()
        mSectionPositions = ArrayList()
        run {
            var i = 0
            val size = mDataArray!!.size
            while (i < size) {
                val section = mDataArray[i][0].toString().uppercase(Locale.getDefault())
                if (!sections.contains(section)) {
                    sections.add(section)
                    mSectionPositions?.add(i)
                }
                i++
            }
        }
        for (element in mSections) {
            alphabetFull.add(element.toString())
        }
        sectionsTranslator = sectionsHelper(sections, alphabetFull)
        return alphabetFull.toTypedArray()
    }

    override fun getPositionForSection(sectionIndex: Int): Int {
        return mSectionPositions!![sectionsTranslator[sectionIndex]!!]
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(item: String)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mTextView: TextView
        var mImageButton: ImageButton

        init {
            mTextView = itemView.findViewById(R.id.tv_alphabet)
            mImageButton = itemView.findViewById(R.id.ib_alphabet)
        }
    }
}
