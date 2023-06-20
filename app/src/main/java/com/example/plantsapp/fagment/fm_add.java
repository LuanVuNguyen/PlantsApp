package com.example.plantsapp.fagment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.plantsapp.activity.AddPlantActivity;
import com.example.plantsapp.activity.HomeActivity;
import com.example.plantsapp.R;

public class fm_add extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    Activity mActivity = null;

    ImageView btn_open,btn_back;
    public fm_add(Activity activity) {
        this.mActivity = activity;

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fm_add2, container, false);
        dispatchTakePictureIntent();
        btn_open = rootView.findViewById(R.id.btn_add_image);
        btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent ();
            }
        });

        btn_back = rootView.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, HomeActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // Lấy ảnh đã chụp từ Intent
            Bundle extras = data.getExtras();
            Bitmap capturedImage = (Bitmap) extras.get("data");

            // Chuyển đến trang khác và truyền ảnh đã chụp
            Intent intent = new Intent(getActivity(), AddPlantActivity.class);
            intent.putExtra("captured_image", capturedImage);
            startActivity(intent);
        }
    }

}
