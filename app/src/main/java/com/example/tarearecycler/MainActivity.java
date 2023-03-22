package com.example.tarearecycler;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarearecycler.database.SqliteDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.item_list)
    RecyclerView mRecyclerView;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.fab)
    FloatingActionButton mFab;

    FoodAdapter foodAdapter;

    GridLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        Recycler();


        mFab.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this, AddActivity.class);
            startActivity(intent);
        });

    }

    public void Recycler() {

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLayoutManager = new GridLayoutManager(this, 2);
        } else {
            mLayoutManager = new GridLayoutManager(this, 1);
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        foodAdapter = new FoodAdapter(new ArrayList<>());

        Content();
    }

    private void Content() {

        SqliteDatabase dataBase = new SqliteDatabase(this);
        List<Food> foods = dataBase.listFood();


        if (foods.size() > 0) {
            foodAdapter = new FoodAdapter(foods);
        } else {
            ArrayList<Food> foodEmpty = new ArrayList<>();
            foodAdapter.addItems(foodEmpty);
        }

        mRecyclerView.setAdapter(foodAdapter);

    }

}