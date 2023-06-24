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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.plantsapp.R;
import com.example.plantsapp.custom.Const;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ArticleFragment extends Fragment {

    public ArticleFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_article, container, false);
        TableLayout tableLayout = view.findViewById(R.id.tablelayout2);
        addTableRowToTableLayout(tableLayout);
        return view;
    }

    private void addTableRowToTableLayout(TableLayout tableLayout) {
        tableLayout.removeAllViewsInLayout();

        for (String name : Const.stringList2) {
            TableRow tableRow = new TableRow(getContext());
            tableRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            tableRow.setGravity(Gravity.CENTER);

            ImageView imageView = new ImageView(getContext());
            TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            imageView.setLayoutParams(params);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setPadding(10, 10, 10, 10);

            try {
                if (name.equals(Const.Writer1)) {
                    imageView.setImageResource(R.drawable.article_like);
                } else if (name.equals(Const.Writer2)) {
                    imageView.setImageResource(R.drawable.article_like_2);
                }
                tableRow.addView(imageView);
                tableLayout.addView(tableRow);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }


}