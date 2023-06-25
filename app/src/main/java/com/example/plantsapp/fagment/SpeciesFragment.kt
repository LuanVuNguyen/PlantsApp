package com.example.plantsapp.fagment

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
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
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class SpeciesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_species, container, false)
        val tableLayout = view.findViewById<TableLayout>(R.id.tablelayout2)
        addTableRowToTableLayout(tableLayout)
        return view
    }

    private var databaseRef: DatabaseReference? = null
    private fun addTableRowToTableLayout(tableLayout: TableLayout) {
        tableLayout.removeAllViewsInLayout()
        for (name in Const.stringList) {
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
                databaseRef =
                    FirebaseDatabase.getInstance().reference.child(Const.refPlant).child(name)
                databaseRef!!.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val image = dataSnapshot.child(Const.FieldImageAdd).getValue(
                            String::class.java
                        )
                        val name = dataSnapshot.child(Const.FieldNameAdd).getValue(
                            String::class.java
                        )
                        Picasso.get().load(image).into(imageView)
                        addTextToImageView(imageView, name)
                        tableRow.addView(imageView)
                        tableLayout.addView(tableRow)
                    }

                    override fun onCancelled(databaseError: DatabaseError) {}
                })
            } catch (e: Exception) {
                println(e.message)
            }
        }
    }

    private fun addTextToImageView(imageView: ImageView, text: String?) {
        val drawable = imageView.drawable
        if (drawable is BitmapDrawable) {
            val bitmap = drawable.bitmap.copy(Bitmap.Config.ARGB_8888, true)
            val canvas = Canvas(bitmap)
            val paint = Paint()
            paint.color = Color.RED
            paint.textSize = 30f
            paint.isAntiAlias = true

            // Vị trí và độ căn chỉnh của chữ trên ImageView
            val x = 20
            val y = 50
            canvas.drawText(text!!, x.toFloat(), y.toFloat(), paint)
            imageView.setImageBitmap(bitmap)
        }
    }
}