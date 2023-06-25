package com.example.plantsapp.fagment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.plantsapp.activity.AddPlantActivity
import com.example.plantsapp.activity.HomeActivity
import com.example.plantsapp.R

class fm_add(private val mActivity: Activity) : Fragment() {

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
    }

    private lateinit var btn_open: ImageView
    private lateinit var btn_back: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_fm_add2, container, false)

        if (ContextCompat.checkSelfPermission(
                mActivity,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            dispatchTakePictureIntent()
        } else {
            ActivityCompat.requestPermissions(
                mActivity,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_IMAGE_CAPTURE
            )
        }
        btn_open = rootView.findViewById(R.id.btn_add_image)
        btn_open.setOnClickListener {
            dispatchTakePictureIntent()
        }
        btn_back = rootView.findViewById(R.id.btn_back)
        btn_back.setOnClickListener {
            val intent = Intent(mActivity, HomeActivity::class.java)
            startActivity(intent)
        }
        return rootView
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // Get the captured image from the Intent
            val extras = data?.extras
            val capturedImage = extras?.get("data") as Bitmap?

            // Go to another page and pass the captured image
            val intent = Intent(activity, AddPlantActivity::class.java)
            intent.putExtra("captured_image", capturedImage)
            startActivity(intent)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != REQUEST_IMAGE_CAPTURE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent()
            } else {
                Toast.makeText(mActivity, "Camera access denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
