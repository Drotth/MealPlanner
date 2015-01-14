package com.da401a.mealplanner;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

public class WeekListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataWeeks;
    private HashMap<String, List<String>> listDataDays;

    public WeekListAdapter(Context context, List<String> listDataWeeks,
                                 HashMap<String, List<String>> listDataDays) {
        this.context = context;
        this.listDataWeeks = listDataWeeks;
        this.listDataDays = listDataDays;
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
        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_day, null);
        }
        TextView dayIcon = (TextView) convertView.findViewById(R.id.dayItemIcon);
        TextView dayChild = (TextView) convertView.findViewById(R.id.dayItem);
        dayIcon.setText("M");
        dayChild.setText(childText);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
