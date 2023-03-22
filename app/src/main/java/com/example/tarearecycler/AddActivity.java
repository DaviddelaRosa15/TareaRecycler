package com.example.tarearecycler;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tarearecycler.database.SqliteDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddActivity extends AppCompatActivity {

    @BindView(R.id.nameEditText)
    EditText mNameEditText;

    @BindView(R.id.foodButton)
    Button foodButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        ButterKnife.bind(this);

        SqliteDatabase dataBase = new SqliteDatabase(this);

        foodButton.setOnClickListener(v -> {
            String name = mNameEditText.getText().toString();
            int image = R.drawable.food;

            if(name.length() == 0){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Advertencia")
                        .setMessage("Debe completar el campo")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                builder.setIcon(R.drawable.baseline_warning_24);
                builder.create();
                builder.show();
            }else {
                Food newFood = new Food(name, image);
                dataBase.newFood(newFood);
                Intent intent=new Intent(this, MainActivity.class);
                startActivity(intent);
            }

        });

    }
}
