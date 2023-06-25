package com.example.plantsapp.fagment;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.plantsapp.R;
import com.example.plantsapp.activity.TusArticleActivity;
import com.example.plantsapp.custom.Const;
import com.example.plantsapp.custom.FirebaseUpdateThread;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SpeciesFragment extends Fragment  {

    public SpeciesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_species, container, false);
        TableLayout tableLayout = view.findViewById(R.id.tablelayout2);

        addTableRowToTableLayout(tableLayout);
        return view;
    }
    private DatabaseReference databaseRef;
    private void addTableRowToTableLayout(TableLayout tableLayout) {
        tableLayout.removeAllViewsInLayout();
        for (String name : Const.stringList) {
            TableRow tableRow = new TableRow(getContext());
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            tableRow.setGravity(Gravity.CENTER);

            ImageView imageView = new ImageView(getContext());
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
            params.gravity = Gravity.CENTER;
            imageView.setLayoutParams(params);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setPadding(10, 10, 10, 10);

            try {
                databaseRef = FirebaseDatabase.getInstance().getReference().child(Const.refPlant).child(name);
                databaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String image = dataSnapshot.child(Const.FieldImageAdd).getValue(String.class);
                        String name = dataSnapshot.child(Const.FieldNameAdd).getValue(String.class);
                        Picasso.get().load(image).into(imageView);
                        addTextToImageView(imageView, name);
                        tableRow.addView(imageView);
                        tableLayout.addView(tableRow);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }
    private void addTextToImageView(ImageView imageView, String text) {
        Drawable drawable = imageView.getDrawable();
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            Bitmap bitmap = bitmapDrawable.getBitmap().copy(Bitmap.Config.ARGB_8888, true);

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setTextSize(30);
            paint.setAntiAlias(true);

            // Vị trí và độ căn chỉnh của chữ trên ImageView
            int x = 20;
            int y = 50;

            canvas.drawText(text, x, y, paint);

            imageView.setImageBitmap(bitmap);
        }
    }

}