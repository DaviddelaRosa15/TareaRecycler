package com.example.tarearecycler;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarearecycler.database.SqliteDatabase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FoodAdapter extends RecyclerView.Adapter<ViewHolder>{


    private final List<Food> mFoodList;

    public FoodAdapter(List<Food> foodList) {
        mFoodList = foodList;
    }

    @Override
    public void onBindViewHolder(com.example.tarearecycler.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @NonNull
    @Override
    public com.example.tarearecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.food_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (mFoodList != null & mFoodList.size() > 0) {
            return mFoodList.size();
        } else {
            return 0;
        }
    }

    public void addItems(List<Food> foodList) {
        mFoodList.addAll(foodList);
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        if (mFoodList != null & mFoodList.size() > 0) {
            mFoodList.remove(position);
        }
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public class ViewHolder extends com.example.tarearecycler.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.card_view)
        CardView foodCardView;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.deleteImageView)
        ImageView mDeleteImageVIew;

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.editImageView)
        ImageView mEditImageView;

        SqliteDatabase dataBase;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        protected void clear() {

        }

        public void onBind(int position) {
            super.onBind(position);

            Food food = mFoodList.get(position);
            dataBase = new SqliteDatabase(itemView.getContext());

            foodCardView.setOnClickListener(v -> {
                Toast.makeText(itemView.getContext(), food.getName(), Toast.LENGTH_SHORT).show();
            });

            mEditImageView.setOnClickListener(v -> {
                Intent intent=new Intent(itemView.getContext(), EditActivity.class);
                intent.putExtra("id",  food.getId());
                itemView.getContext().startActivity(intent);
            });

            mDeleteImageVIew.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                builder.setTitle("Confirmación")
                        .setMessage("¿Está seguro de eliminar el alimento " + food.getName() + "?")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dataBase.deleteFood(food.getId());
                                deleteItem(position);
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                builder.setIcon(R.drawable.baseline_warning_24);
                builder.create();
                builder.show();
            });
        }
    }

}