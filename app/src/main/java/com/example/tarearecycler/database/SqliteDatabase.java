package com.example.tarearecycler.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.tarearecycler.Food;

import java.util.ArrayList;
import java.util.List;


public class SqliteDatabase extends SQLiteOpenHelper {

    private static final int    DATABASE_VERSION =	1;
    private static final String DATABASE_NAME = "AlimentosDB";
    private final static String TABLE_NAME ="Alimentos";

    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_IMAGE = "IMAGE";
    public SqliteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTS_TABLE = "CREATE	TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME+ " TEXT," + COLUMN_IMAGE+ " INTEGER" + ");";
        db.execSQL(CREATE_PRODUCTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public List<Food> listFood(){
        String sql = "select * from " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        List<Food> listFood = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int image = cursor.getInt(2);
                listFood.add(new Food(id, name, image));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return listFood;
    }

    public  Food getFood(int id){
        String sql = "select * from " + TABLE_NAME+" where "+ COLUMN_ID + "	= ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Food food = new Food();

        Cursor cursor = db.rawQuery(sql, new String[] { String.valueOf(id)});
        if(cursor.moveToFirst()){
            do{
                food.setName(cursor.getString(1));
                food.setImage(cursor.getInt(2));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return food;
    }

    public void newFood(Food food){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, food.getName());
        values.put(COLUMN_IMAGE, food.getImage());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_NAME, null, values);
    }

    public  void updateFood(Food food, int id){
        ContentValues values= new ContentValues();
        values.put(COLUMN_NAME, food.getName());
        values.put(COLUMN_IMAGE, food.getImage());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_NAME, values, COLUMN_ID + "=" + id,null);
    }

    public void deleteFood(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(id)});
    }


}
