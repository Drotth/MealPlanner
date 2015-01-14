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
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddMealFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    private DBController dbController;
    private EditText date, recipe;
    private ArrayList<String> recipesList;
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
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newDate = date.getText().toString();
                String newRecipe = recipe.getText().toString();
                if(newDate.isEmpty() || newRecipe.isEmpty()){
                    Toast.makeText(getActivity(), "You have to fill in all fields",
                            Toast.LENGTH_SHORT).show();
                } else {
                    dbController.dataIntoWeekMeal(newDate);
                    date.setText("");
                    recipe.setText("");
                }

            }
        });

        Button newRecipe = (Button) view.findViewById(R.id.buttonAddMealNew);
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

        EditText editDateSelect = (EditText) view.findViewById(R.id.editTextAddMealSelectDate);
        editDateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }

            /*
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    showDialog(DIALOG_DATE_PICKER);
                }
                return false;
            }
            */
        });

        Spinner spinner = (Spinner) view.findViewById(R.id.spinnerShowRecipes);
        ArrayAdapter<String> adp1=new ArrayAdapter<String> (getActivity(),
                android.R.layout.simple_list_item_1,recipesList);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp1);
        spinner.setOnItemSelectedListener(this);

        /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Toast.makeText(getActivity(), "knas knas",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });*/
        /*
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                userSelectedIndex = position;
                Toast.makeText(getActivity(), "" + userSelectedIndex,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        dbController.open();
        Cursor c = dbController.getRecipeName();
        if(c != null && c.moveToFirst());
        do{
            try {
                recipesList.add(c.getString(0));
            } catch (CursorIndexOutOfBoundsException exception){}
        }while (c.moveToNext());
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, recipesList);
        //recipesList = dbController.getRecipeName();
        /*Cursor c = dbController.getRecipes();
        recipesAdapter = new RecipesAdapter(getActivity(), c, true);
        listRecipes.setAdapter(recipesAdapter);*/
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
        recipesList = new ArrayList<String>();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbController.close();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "knas knas",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
