package com.da401a.mealplanner;

import android.database.Cursor;
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbController = new DBController(getActivity());
        dbController.open();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);

        weekListView = (ExpandableListView) view.findViewById(R.id.weeksList);
        prepareListData();
        weekListAdapter = new WeekListAdapter(getActivity(), listDataWeeks, listDataDays);
        weekListView.setAdapter(weekListAdapter);

        return view;
    }

    private void prepareListData() {

        Cursor c = dbController.getWeekMeal();

//        if(c != null && c.moveToFirst());
//        do {
//            c.getString(0).toString();
//        }while (c.moveToNext());

        listDataWeeks = new ArrayList<String>();
        listDataDays = new HashMap<String, List<String>>();

        listDataWeeks.add("Vecka 3");
        listDataWeeks.add("Vecka 4");
        listDataWeeks.add("Vecka 5");

        List<String> Vecka_3 = new ArrayList<String>();
        Vecka_3.add("Korv Stroganoff");
        Vecka_3.add("Kötbullar med mos");
        Vecka_3.add("Lugnt, mamma fixar");
        Vecka_3.add("Marabou hela dagen");
        Vecka_3.add("Sibylla");
        Vecka_3.add("Kanske dags för riktig mat?");
        Vecka_3.add(".. Not really");

        List<String> Vecka_4 = new ArrayList<String>();
        Vecka_4.add("Ny vecka, nya vanor!");
        Vecka_4.add("Köttbullar med mos");
        Vecka_4.add("Korv stroganoff");
        Vecka_4.add("Nudlar");

        List<String> Vecka_5 = new ArrayList<String>();
        Vecka_5.add("Nudlar");
        Vecka_5.add("Mer nudlar..");

        listDataDays.put(listDataWeeks.get(0), Vecka_3);
        listDataDays.put(listDataWeeks.get(1), Vecka_4);
        listDataDays.put(listDataWeeks.get(2), Vecka_5);
    }
}
