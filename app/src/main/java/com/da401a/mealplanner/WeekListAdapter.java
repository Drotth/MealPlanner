package com.da401a.mealplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class WeekListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Integer> listDataWeeks = new ArrayList<>();
    private HashMap<Integer, ArrayList<RecipeDay>> listDataDays = new HashMap<>();
    private ArrayList<RecipeDay> mainList;

    public WeekListAdapter(Context context, ArrayList<RecipeDay> list) {
        this.context = context;
        this.mainList = list;

        for (int i = 0; i < mainList.size(); i++){
            int week = mainList.get(i).getWeek();
            if (!checkWeek(week)){
                listDataWeeks.add(week);
                listDataDays.put(week, new ArrayList<RecipeDay>());
            }
        }

        for (int i = 0; i <mainList.size(); i++){
            listDataDays.get(mainList.get(i).getWeek()).add(mainList.get(i));
        }
    }

    private boolean checkWeek(int week){
        if (listDataWeeks.size() == 0) return false;
        for (int i = 0; i < listDataWeeks.size(); i++){
            if (listDataWeeks.get(i).equals(week)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void notifyDataSetChanged() {
        listDataDays.clear();
        listDataWeeks.clear();

        for (int i = 0; i < mainList.size(); i++){
            int week = mainList.get(i).getWeek();
            if (!checkWeek(week)){
                listDataWeeks.add(week);
                listDataDays.put(week, new ArrayList<RecipeDay>());
            }
        }

        for (int i = 0; i <mainList.size(); i++){
            listDataDays.get(mainList.get(i).getWeek()).add(mainList.get(i));
        }

        super.notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {
        return listDataWeeks.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listDataDays.get(listDataWeeks.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataWeeks.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listDataDays.get(listDataWeeks.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = "Week " + getGroup(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_week, null);
        }

        TextView weekHeader = (TextView) convertView.findViewById(R.id.weekItem);
        weekHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        RecipeDay child = (RecipeDay) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_day, null);
        }

        TextView dayIcon = (TextView) convertView.findViewById(R.id.dayItemIcon);
        TextView dayChild = (TextView) convertView.findViewById(R.id.dayItem);
        switch (child.getDay()){
            case 1: dayIcon.setText("Mon"); break;
            case 2: dayIcon.setText("Tue"); break;
            case 3: dayIcon.setText("Wed"); break;
            case 4: dayIcon.setText("Thu"); break;
            case 5: dayIcon.setText("Fri"); break;
            case 6: dayIcon.setText("Sat"); break;
            case 7: dayIcon.setText("Sun"); break;
        }
        dayChild.setText(child.getRecipe());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
