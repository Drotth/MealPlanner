package com.da401a.mealplanner;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Mattias on 2015-01-12.
 */
public class RecipesAdapter extends CursorAdapter {
    public RecipesAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipesrow, parent, false);
        ViewHolder holder = new ViewHolder();
        holder.name = (TextView) view.findViewById(R.id.rowRecipeName);
        holder.desc = (TextView) view.findViewById(R.id.rowRecipeDesc);
        holder.meat = (TextView) view.findViewById(R.id.rowRecipeMeat);
        holder.acc = (TextView) view.findViewById(R.id.rowRecipeAcc);
        holder.veg = (TextView) view.findViewById(R.id.rowRecipeVeg);
        holder.drink = (TextView) view.findViewById(R.id.rowRecipeDrink);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        holder.name.setText("Name: " + cursor.getString(1));
        holder.desc.setText("Desc: " + cursor.getString(2));
        holder.meat.setText("Meat: " + cursor.getString(3));
        holder.acc.setText("Accessories: " + cursor.getString(4));
        holder.veg.setText("Vegetables: " + cursor.getString(5));
        holder.drink.setText("Drink: " + cursor.getString(6));
    }

    private class ViewHolder {
        TextView name;
        TextView desc;
        TextView meat;
        TextView acc;
        TextView veg;
        TextView drink;
    }
}
