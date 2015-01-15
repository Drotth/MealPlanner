package com.da401a.mealplanner;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        String selectedDate = Integer.toString(year) + getMonth(month) + Integer.toString(day);

        Fragment fragment = getActivity().getFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof InsertShoppingListFragment) {
            EditText editTextDateShoppinglist = (EditText) getActivity().findViewById(R.id.editTextShoppinglistDate);
            editTextDateShoppinglist.setText(selectedDate);
        }
        else if (fragment instanceof AddMealFragment){
            EditText editTextAddMeal = (EditText) getActivity().findViewById(R.id.editTextAddMealSelectDate);
            editTextAddMeal.setText(selectedDate);
        }
    }

    private String getMonth(int month){
        month += 1;
        if(month<10) {
            return "0" + month;
        }
        else{
            return Integer.toString(month);
        }
    }
}