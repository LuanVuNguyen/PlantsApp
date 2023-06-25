package com.example.plantsapp.fagment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import androidx.fragment.app.Fragment
import com.example.plantsapp.R
import com.example.plantsapp.custom.Const

class ArticleFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_article, container, false)
        val tableLayout = view.findViewById<TableLayout>(R.id.tablelayout2)
        addTableRowToTableLayout(tableLayout)
        return view
    }

    private fun addTableRowToTableLayout(tableLayout: TableLayout) {
        tableLayout.removeAllViewsInLayout()
        for (name in Const.stringList2) {
            val tableRow = TableRow(context)
            tableRow.layoutParams = TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT
            )
            tableRow.gravity = Gravity.CENTER
            val imageView = ImageView(context)
            val params = TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT
            )
            params.gravity = Gravity.CENTER
            imageView.layoutParams = params
            imageView.adjustViewBounds = true
            imageView.scaleType = ImageView.ScaleType.CENTER_INSIDE
            imageView.setPadding(10, 10, 10, 10)
            try {
                if (name == Const.Writer1) {
                    imageView.setImageResource(R.drawable.article_like)
                } else if (name == Const.Writer2) {
                    imageView.setImageResource(R.drawable.article_like_2)
                }
                tableRow.addView(imageView)
                tableLayout.addView(tableRow)
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }
}