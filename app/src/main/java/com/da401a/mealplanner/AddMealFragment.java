package com.da401a.mealplanner;


import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddMealFragment extends Fragment {
    private DBController dbController;
    private EditText date, recipe;

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
        Button newRecipe = (Button) view.findViewById(R.id.buttonAddMealNew);
        newRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment newFragment = new NewRecipeFragment();
                FragmentTransaction ftNewRecipe = getFragmentManager().beginTransaction();
                ftNewRecipe.replace(R.id.container,newFragment);
                ftNewRecipe.addToBackStack(null);
                ftNewRecipe.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ftNewRecipe.commit();
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
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        dbController.open();
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbController.close();
    }
}
