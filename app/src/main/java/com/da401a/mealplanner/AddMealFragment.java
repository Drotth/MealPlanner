package com.da401a.mealplanner;


import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddMealFragment extends Fragment{
    private DBController dbController;
    private EditText date, recipe;
    private List<String> recipesList;
    private String[] recipeArray;
    private int userSelectedIndex;

    public AddMealFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_meal, container, false);
        date = (EditText) view.findViewById(R.id.editTextAddMealSelectDate);
        recipe = (EditText) view.findViewById(R.id.editTextAddMealSelectMeal);

        Button buttonDone = (Button) view.findViewById(R.id.buttonAddMealDone);
        Button newRecipe = (Button) view.findViewById(R.id.buttonAddMealNew);
        EditText editDateSelect = (EditText) view.findViewById(R.id.editTextAddMealSelectDate);
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinnerShowRecipes);

        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String newDate = date.getText().toString();
                String newRecipe = recipe.getText().toString();
                if(newDate.isEmpty() || newRecipe.isEmpty()){
                    Toast.makeText(getActivity(), "You have to fill in all fields",
                            Toast.LENGTH_SHORT).show();
                } else {
                    dbController.dataIntoWeekMeal(newDate);
                    date.setText("");
                    recipe.setText("");
                }*/

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

        editDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        ArrayAdapter<String> adp1=new ArrayAdapter<String> (getActivity(),
                android.R.layout.simple_list_item_1, recipeArray);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String result = parent.getItemAtPosition(position).toString();
                Toast.makeText(getActivity(), result,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        dbController.open();

        recipesList = new ArrayList<String>();

        Cursor c = dbController.getRecipeName();
        if(c != null && c.moveToFirst());
        do{
            try {
                recipesList.add(c.getString(0));
            } catch (CursorIndexOutOfBoundsException exception){}
        }while (c.moveToNext());

        recipeArray = new String[recipesList.size()];
        for (int i = 0; i < recipesList.size(); i++){
            recipeArray[i] = recipesList.get(i);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, recipeArray);
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbController.close();
    }
}
