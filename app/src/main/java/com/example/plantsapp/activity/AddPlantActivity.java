package com.example.plantsapp.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.plantsapp.R;
import com.example.plantsapp.custom.Const;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddPlantActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private static final int REQUEST_STORAGE_PERMISSION = 1;
    private ImageView imageView;
    ImageView btn_save;
    EditText txt_name, txt_family, txt_kingdom, txt_des, txt_hagtag;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plants);
        imageView = findViewById(R.id.imageView);
        imageView.setOnClickListener(this);
        if (getIntent().hasExtra("captured_image")) {
            Bitmap capturedImage = getIntent().getParcelableExtra("captured_image");
            imageView.setImageBitmap(capturedImage);
        }
        btn_save = (ImageView) findViewById(R.id.button);
        btn_save.setOnClickListener(this);
        init();
    }
    private void init(){
        txt_name = (EditText) findViewById(R.id.add_name);
        txt_kingdom = (EditText) findViewById(R.id.add_kingdom);
        txt_des = (EditText) findViewById(R.id.add_description);
        txt_hagtag = (EditText) findViewById(R.id.add_hagtag);
        txt_family = (EditText) findViewById(R.id.add_family);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.imageView) {
            showImageSelectionDialog();
        } else if (v.getId()==R.id.button) {
            try {
                pushImage();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void pushdata(String imageUrl){
        String name =  txt_name.getText().toString();
        String family =  txt_family.getText().toString();
        String kingdom =  txt_kingdom.getText().toString();
        String des =  txt_des.getText().toString();
        String hagtag =  txt_hagtag.getText().toString();
        String[] hashtag = hagtag.split(",");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child(Const.refPlant);
        ref.child(name).child(Const.FieldNameAdd).setValue(name);
        ref.child(name).child(Const.FieldFamilyAdd).setValue(family);
        ref.child(name).child(Const.FieldKingdomAdd).setValue(kingdom);
        ref.child(name).child(Const.FieldDesAdd).setValue(des);
        ref.child(name).child(Const.FieldHagtagAdd).setValue(hashtag[0]);
        ref.child(name).child(Const.FieldHagtag2Add).setValue(hashtag[1]);

        ref.child(name).child(Const.FieldImageAdd).setValue(imageUrl);
    }

    private void pushImage(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        String imageFileName = UUID.randomUUID().toString() + ".jpg";
        StorageReference imageRef = storageRef.child("Plants Image/" + imageFileName);
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageData = baos.toByteArray();
        UploadTask uploadTask = imageRef.putBytes(imageData);
        uploadTask.addOnSuccessListener(taskSnapshot -> {
            // Lấy URL của hình ảnh sau khi tải lên thành công
            imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                 imageUrl = uri.toString();
                 pushdata(imageUrl);
                Toast.makeText(AddPlantActivity.this, "Upload Succesfull", Toast.LENGTH_SHORT).show();
            });
        }).addOnFailureListener(exception -> {
            Toast.makeText(AddPlantActivity.this, "Upload Failed", Toast.LENGTH_SHORT).show();
        });
    }

    private void showImageSelectionDialog() {
        String[] options = {"Chụp ảnh", "Chọn ảnh từ thư viện"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setTitle("Chọn ảnh");
        builder.setItems(options, (dialog, which) -> {
            if (which == 0) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_IMAGE_CAPTURE);
                }
            } else if (which == 1) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_IMAGE_PICK);
            }
        });
        builder.setNegativeButton("Hủy", null);
        builder.create().show();
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // Chụp ảnh từ camera
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    imageView.setImageBitmap(imageBitmap);
                }
            } else if (requestCode == REQUEST_IMAGE_PICK) {
                // Chọn ảnh từ thư viện
                Uri selectedImageUri = data.getData();
                String imagePath = getPathFromUri(selectedImageUri);
                if (imagePath != null) {
                    Bitmap imageBitmap = BitmapFactory.decodeFile(imagePath);
                    imageView.setImageBitmap(imageBitmap);
                }
            }
        }
    }
    private String getPathFromUri(Uri uri) {
        String filePath = null;
        if (uri != null) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                filePath = cursor.getString(columnIndex);
                cursor.close();
            }
        }
        return filePath;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            } else {
                Toast.makeText(this, "Camera access denied", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "Memory access denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onBackPressed() {
        // Quay trở lại trang home
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

}