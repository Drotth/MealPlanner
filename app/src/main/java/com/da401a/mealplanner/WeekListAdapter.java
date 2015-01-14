package com.da401a.mealplanner;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeekListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataWeeks = new ArrayList<String>();
    private HashMap<String, ArrayList<RecipeDay>> listDataDays =
            new HashMap<String, ArrayList<RecipeDay>>();
    private ArrayList<RecipeDay> mainList;

    public WeekListAdapter(Context context, ArrayList<RecipeDay> list) {
        this.context = context;
        this.mainList = list;

        for (int i = 0; i < mainList.size(); i++){
            if (!checkWeek(mainList.get(i).getWeek())){
                String week = "Week " + mainList.get(i).getWeek();
                listDataWeeks.add(week);
                listDataDays.put(week, new ArrayList<RecipeDay>());
            }
        }

        for (int i = 0; i <mainList.size(); i++){
            listDataDays.get("Week " + mainList.get(i).getWeek()).add(mainList.get(i));
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
    public int getGroupCount() {
        return listDataWeeks.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listDataDays.get(listDataWeeks.get(groupPosition))
                .size();
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
        String headerTitle = (String) getGroup(groupPosition);
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
            case 1: dayIcon.setText("S"); break;
            case 2: dayIcon.setText("M"); break;
            case 3: dayIcon.setText("T"); break;
            case 4: dayIcon.setText("W"); break;
            case 5: dayIcon.setText("T"); break;
            case 6: dayIcon.setText("F"); break;
            case 7: dayIcon.setText("S"); break;
        }
        dayChild.setText(child.getRecipe());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
