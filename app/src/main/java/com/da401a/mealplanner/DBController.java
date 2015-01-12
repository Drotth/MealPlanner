package com.da401a.mealplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mattias on 2015-01-12.
 */
public class DBController extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private static final String NAME = "COMPANY_DATABASE";
    private static final String TABLE_SHOPPINGLIST = "Shoppinglist";
    private static final String TABLE_FOODPLAN = "Foodplan";
    private static final int VERSION =1;

    private static final String SHOPPINGLIST = "CREATE TABLE Shoppinglist"+
            "(_id integer primary key autoincrement, " +
            "LastDate text not null, " +
            "Product text not null);";
    private static final String FOODPLAN = "CREATE TABLE Foodplan"+
            "(_id integer primary key autoincrement, " +
            "Meat text not null, " +
            "Acces text not null, " +
            "Veg text not null, " +
            "Date text not null, " +
            "Descri text not null, " +
            "Drink text not null);";

    private static final String RECIPE = "CREATE TABLE Recipe"+
            "(_id integer primary key autoincrement, " +
            "Name text not null, " +
            "Meat text not null, " +
            "Acces text not null, " +
            "Veg text not null, " +
            "Descri text not null, " +
            "Drink text not null);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SHOPPINGLIST);
        db.execSQL(FOODPLAN);
        db.execSQL(RECIPE);
    }

    public DBController(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPPINGLIST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOODPLAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOODPLAN);
        onCreate(db);
    }

    public void open(){
        db = getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    public long dataFoodplan(String lastDate, String product) {
        ContentValues values = new ContentValues();
        values.put("LastDate", lastDate);
        values.put("Product", product);
        return db.insert("Foodplan",null,values);
    }

    public long dataShoppinglist(String name, String meat, String acces, String veg, String date,
                                 String desc, String drink) {
        ContentValues values = new ContentValues();
        values.put("Meat", meat);
        values.put("Acces",acces);
        values.put("Veg",veg);
        values.put("Date",date);
        values.put("Descri",desc);
        values.put("Drink",drink);
        return db.insert("Shoppinglist",null,values);
    }

    public long dataRecipe(String name, String meat, String acces,
                           String veg, String desc, String drink) {
        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Meat", meat);
        values.put("Acces",acces);
        values.put("Veg",veg);
        values.put("Descri",desc);
        values.put("Drink",drink);
        return db.insert("Shoppinglist",null,values);
    }

    public Cursor getFoodPlan(){
        return db.query(
                "Foodplan",
                new String[]{"_id", "LastDate", "Product"},
                null, null, null, null, null);
    }

    public Cursor getShoppinglist() {
        return db.query(
                "Expenses",
                new String[]{"_id", "Meat", "Acces", "Veg", "Date", "Descri", "Drink"},
                null, null, null, null, null);

    }
}
