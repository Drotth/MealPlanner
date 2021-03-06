package com.da401a.mealplanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DBController extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private static final String TAG ="MyActivity" ;
    private static final String NAME = "MEALPLANNER_DATABASE";
    private static final String TABLE_SHOPPINGLIST ="Shoppinglist";
    private static final String TABLE_RECIPES = "Recipes";
    private static final String TABLE_WEEKMEAL = "Weekmeal";
    private static final int VERSION =1;

    private static final String CREATETABLE_RECIPES = "CREATE TABLE " + TABLE_RECIPES +
            "(_id integer primary key autoincrement, " +
            "Name text not null, " +
            "Desc text not null, " +
            "Meat text not null, " +
            "Acc text not null, " +
            "Veg text not null, " +
            "Drink text not null);";

    private static final String CREATETABLE_SHOPPINGLIST = "CREATE TABLE " + TABLE_SHOPPINGLIST+
            "(_id integer primary key autoincrement, " +
            "ProductToBuy text not null, " +
            "DateToBuy text not null);";

    private static final String CREATETABLE_WEEKMEAL = "CREATE TABLE " + TABLE_WEEKMEAL +
            "(_id integer primary key autoincrement, " +
            "DateToEat text not null," +
            "Week integer not null," +
            "WeekDay integer not null," +
            "RecipeName text not null);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATETABLE_RECIPES);
        db.execSQL(CREATETABLE_SHOPPINGLIST);
        db.execSQL(CREATETABLE_WEEKMEAL);
    }

    public DBController(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXIST" + TABLE_SHOPPINGLIST);
        db.execSQL("DROP TABLE IF EXIST" + TABLE_WEEKMEAL);
        onCreate(db);
    }

    public void open(){
        db = getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    public long dataIntoRecipe(String name, String desc, String meat,
                               String acc, String veg, String drink) {
        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Desc", desc);
        values.put("Meat", meat);
        values.put("Acc", acc);
        values.put("Veg", veg);
        values.put("Drink", drink);
        return db.insert("Recipes", null, values);
    }

    public long dataIntoShoppingList(String product,String date) {
        ContentValues values = new ContentValues();

        values.put("ProductToBuy", product);
        values.put("DateToBuy",date);

        return db.insert("Shoppinglist",null,values);
    }

    public long dataIntoWeekMeal(String date, String meal) {
        ContentValues values = new ContentValues();

        String format = "yyyyMMdd";
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date dateObj = null;
        try{
            dateObj = df.parse(date);
        }catch(ParseException exception){}

        Calendar cal = Calendar.getInstance();
        cal.setTime(dateObj);
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        int weekDay = cal.get(Calendar.DAY_OF_WEEK);

        // This is due to the american week standard being different.
        weekDay -= 1;
        if (weekDay == 0) weekDay = 7;

        values.put("DateToEat", date);
        values.put("Week", week);
        values.put("WeekDay", weekDay);
        values.put("RecipeName",meal);

        return db.insert("Weekmeal",null,values);
    }

    public Cursor getRecipes() {
        return db.query(
                "Recipes",
                new String[]{"_id", "Name", "Desc", "Meat", "Acc", "Veg", "Drink"},
                null, null, null, null, null);
    }

    public Cursor getRecipeName(){
        return db.query(
                "Recipes",
                new String[]{"Name"},
                null, null, null, null, null);
    }

    public Cursor getShopList(){
        return db.query(
                "Shoppinglist",
                new String[]{"_id", "ProductToBuy", "DateToBuy"},
                null, null, null, null, null);
    }

    public Cursor getWeekMeal(){
        return db.query(
                "Weekmeal",
                new String[]{"_id", "DateToEat", "Week", "WeekDay", "RecipeName"},
                null, null, null, null, "Week ASC, " + "WeekDay ASC");


    }

    public void removeShoppinglist(){
        getWritableDatabase();
        db.delete(TABLE_SHOPPINGLIST, null, null);
    }

    public boolean deleteRowShoppinglist(long rowId){
        String where = "_id =" + rowId;
        return db.delete(TABLE_SHOPPINGLIST, where, null) != 0;
    }

    public void removeRecipes(){
        getWritableDatabase();
        db.delete(TABLE_RECIPES, null, null);
    }

    public boolean deleteRowRecipe(long rowId){
        String where = "_id =" + rowId;
        return db.delete(TABLE_RECIPES, where, null) != 0;
    }
}
