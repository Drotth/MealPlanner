package com.da401a.mealplanner;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;

public class WeekFragment extends Fragment {

    private ExpandableListView weekListView;
    private WeekListAdapter weekListAdapter;
    private DBController dbController;
    private ArrayList<RecipeDay> mainList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbController = new DBController(getActivity());
        dbController.open();
        prepareListData();
        weekListAdapter = new WeekListAdapter(getActivity(), mainList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);

        weekListView = (ExpandableListView) view.findViewById(R.id.weeksList);
        weekListView.setAdapter(weekListAdapter);
        if(!mainList.isEmpty()) weekListView.expandGroup(0);

        return view;
    }

    @Override
    public void onPause(){
        super.onPause();
        dbController.close();
    }

    @Override
    public void onStart(){
        super.onStart();
        dbController.open();
    }

    public void updateList(){
        dbController.open();
        mainList.clear();
        prepareListData();
        weekListAdapter.notifyDataSetChanged();
        dbController.close();
    }

    private void prepareListData() {
        Cursor c = dbController.getWeekMeal();

        if(c != null && c.moveToFirst());
        do {
            try {
                mainList.add(new RecipeDay(c.getInt(2), c.getInt(3), c.getString(4)));
            } catch (CursorIndexOutOfBoundsException exception){}
        }while (c.moveToNext());
    }
}
