package com.da401a.mealplanner;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class WeekFragment extends Fragment {

    ExpandableListView weekListView;
    ExpandableListAdapter weekListAdapter;
    List<String> listDataWeeks;
    HashMap<String, List<String>> listDataDays;

    private DBController dbController;
    ArrayList<RecipeDay> mainList = new ArrayList<RecipeDay>();


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

        return view;
    }

    @Override
    public void onPause(){
        super.onPause();
        dbController.close();
    }

    @Override
    public void onResume(){
        super.onResume();
        dbController.open();
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
