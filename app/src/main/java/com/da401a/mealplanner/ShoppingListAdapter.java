package com.da401a.mealplanner;

/**
 * Created by Mattias on 2015-01-13.
 */
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by sebastianolsson on 15-01-13.
 */
public class ShoppingListAdapter extends CursorAdapter {

    public ShoppingListAdapter(Context context, Cursor c, boolean autoRequery){
        super(context,c,autoRequery);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View root = LayoutInflater.from(context).inflate(R.layout.shoplistrow, parent, false);

        ViewHolder holder = new ViewHolder();

        holder.Date = (TextView) root.findViewById(R.id.date);
        holder.Title = (TextView) root.findViewById(R.id.title);
        root.setTag(holder);

        return root;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        holder.Title.setText("Date:\t"+cursor.getString(2));
        holder.Date.setText("Products:\t"+cursor.getString(1));

    }

    private class ViewHolder {
        TextView Date;
        TextView Title;
    }

}
