package com.example.plantsapp.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.plantsapp.R
import com.example.plantsapp.custom.Const
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.util.*

class AddPlantActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_IMAGE_PICK = 2
        private const val REQUEST_STORAGE_PERMISSION = 1
    }
    private lateinit var imageView: ImageView
    private lateinit var btn_save: ImageView
    private lateinit var txt_name: EditText
    private lateinit var txt_family: EditText
    private lateinit var txt_kingdom: EditText
    private lateinit var txt_des: EditText
    private lateinit var txt_hagtag: EditText
    private var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_plants)
        imageView = findViewById(R.id.imageView)
        imageView.setOnClickListener(this)
        if (intent.hasExtra("captured_image")) {
            val capturedImage: Bitmap? = intent.getParcelableExtra("captured_image")
            imageView.setImageBitmap(capturedImage)
        }
        btn_save = findViewById(R.id.button)
        btn_save.setOnClickListener(this)
        init()
    }

    private fun init() {
        txt_name = findViewById(R.id.add_name)
        txt_kingdom = findViewById(R.id.add_kingdom)
        txt_des = findViewById(R.id.add_description)
        txt_hagtag = findViewById(R.id.add_hagtag)
        txt_family = findViewById(R.id.add_family)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.imageView) {
            showImageSelectionDialog()
        } else if (v.id == R.id.button) {
            try {
                pushImage()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }

    private fun pushdata(imageUrl: String?) {
        val name: String = txt_name.text.toString()
        val family: String = txt_family.text.toString()
        val kingdom: String = txt_kingdom.text.toString()
        val des: String = txt_des.text.toString()
        var hagtag: String = txt_hagtag.text.toString()
        if (!hagtag.contains(",")){
            hagtag+=","
        }
        val hashtag: Array<String> = hagtag.split(",").toTypedArray()
        val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference().child(Const.refPlant)
        ref.child(name).child(Const.FieldNameAdd).setValue(name)
        ref.child(name).child(Const.FieldFamilyAdd).setValue(family)
        ref.child(name).child(Const.FieldKingdomAdd).setValue(kingdom)
        ref.child(name).child(Const.FieldDesAdd).setValue(des)
        ref.child(name).child(Const.FieldHagtagAdd).setValue(hashtag[0])
        ref.child(name).child(Const.FieldHagtag2Add).setValue(hashtag[1])
        ref.child(name).child(Const.FieldImageAdd).setValue(imageUrl)
    }

    private fun pushImage() {

        val storage: FirebaseStorage = FirebaseStorage.getInstance()
        val storageRef: StorageReference = storage.reference
        val imageFileName: String = UUID.randomUUID().toString() + ".jpg"
        val imageRef: StorageReference = storageRef.child("Plants Image/$imageFileName")
        imageView.isDrawingCacheEnabled = true
        imageView.buildDrawingCache()
        val bitmap: Bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageData: ByteArray = baos.toByteArray()
        val uploadTask: UploadTask = imageRef.putBytes(imageData)
        uploadTask.addOnSuccessListener { taskSnapshot ->
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                imageUrl = uri.toString()
                pushdata(imageUrl)
                Toast.makeText(this@AddPlantActivity, "Upload Successful", Toast.LENGTH_SHORT).show()

            }
        }.addOnFailureListener { exception ->
            Toast.makeText(this@AddPlantActivity, "Upload Failed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showImageSelectionDialog() {
        val options = arrayOf("Capture Photo", "Choose from Gallery")
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Select an Image")
        builder.setItems(options) { dialog, which ->
            if (which == 0) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    dispatchTakePictureIntent()
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.CAMERA),
                        REQUEST_IMAGE_CAPTURE
                    )
                }
            } else if (which == 1) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    val intent = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(intent, REQUEST_IMAGE_PICK)
                }
                else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        REQUEST_IMAGE_PICK
                    )
                }

            }
        }
        builder.setNegativeButton("Cancel", null)
        builder.create().show()
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // Capture photo from camera
                val extras: Bundle? = data?.extras
                if (extras != null) {
                    val imageBitmap: Bitmap? = extras.get("data") as Bitmap?
                    imageView.setImageBitmap(imageBitmap)
                }
            } else if (requestCode == REQUEST_IMAGE_PICK) {
                // Choose photo from gallery
                val selectedImageUri: Uri? = data?.data
                val imagePath: String? = getPathFromUri(selectedImageUri)
                if (imagePath != null) {
                    val imageBitmap: Bitmap = BitmapFactory.decodeFile(imagePath)
                    imageView.setImageBitmap(imageBitmap)
                }
            }
        }
    }

    private fun getPathFromUri(uri: Uri?): String? {
        var filePath: String? = null
        if (uri != null) {
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val cursor: Cursor? = contentResolver.query(uri, projection, null, null, null)
            if (cursor != null) {
                val columnIndex: Int = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                filePath = cursor.getString(columnIndex)
                cursor.close()
            }
        }
        return filePath
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent()
            } else {
                Toast.makeText(this, "Camera access denied", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Handle storage permission
            } else {
                Toast.makeText(this, "Storage access denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        // Go back to home screen
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}
