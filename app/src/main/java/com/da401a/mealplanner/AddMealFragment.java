package com.da401a.mealplanner;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddMealFragment extends Fragment {


    public AddMealFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_meal, container, false);
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
        return view;
    }


}
