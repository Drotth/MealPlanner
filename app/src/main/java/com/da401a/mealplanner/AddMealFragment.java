package com.da401a.mealplanner;

import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddMealFragment extends Fragment{
    private DBController dbController;
    private EditText date;
    private String resultSpinner = "";
    private List<String> recipesList;
    private ArrayAdapter<String> arrayAdapter;

    // Required empty public constructor
    public AddMealFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_meal, container, false);

        date = (EditText) view.findViewById(R.id.editTextAddMealSelectDate);
        Button buttonDone = (Button) view.findViewById(R.id.buttonAddMealDone);
        Button newRecipe = (Button) view.findViewById(R.id.buttonAddMealNew);
        Spinner spinner = (Spinner) view.findViewById(R.id.spinnerShowRecipes);

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                String newDate = date.getText().toString();

                if (newDate.isEmpty() || resultSpinner.isEmpty()) {
                    Toast.makeText(getActivity(), R.string.all_fields, Toast.LENGTH_SHORT).show();
                }
                else {
                    dbController.dataIntoWeekMeal(newDate, resultSpinner);
                    ((MainActivity) getActivity()).updateWeekList();
                    getFragmentManager().popBackStackImmediate();

                }
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        arrayAdapter = new ArrayAdapter<> (getActivity(),
                android.R.layout.simple_list_item_1, recipesList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        arrayAdapter.setNotifyOnChange(true);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                resultSpinner = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        newRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new NewRecipeFragment();
                FragmentTransaction ftAddMeal = getFragmentManager().beginTransaction();
                ftAddMeal.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim);
                ftAddMeal.replace(R.id.container,newFragment);
                ftAddMeal.addToBackStack(null);
                ftAddMeal.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ftAddMeal.commit();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        dbController.open();
        recipesList.clear();

        Cursor c = dbController.getRecipeName();
        if(c != null && c.moveToFirst());
        do{
            try {
                recipesList.add(c.getString(0));
            } catch (CursorIndexOutOfBoundsException exception){}
        }while (c.moveToNext());
    }

    @Override
    public void onPause() {
        super.onPause();
        dbController.close();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbController = new DBController(getActivity());
        dbController.open();
        recipesList = new ArrayList<String>();

        Cursor c = dbController.getRecipeName();
        if(c != null && c.moveToFirst());
        do{
            try {
                recipesList.add(c.getString(0));
            } catch (CursorIndexOutOfBoundsException exception){}
        }while (c.moveToNext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbController.close();
    }
}
