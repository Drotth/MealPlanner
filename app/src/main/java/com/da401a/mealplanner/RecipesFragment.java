package com.da401a.mealplanner;


import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipesFragment extends Fragment {
    private DBController dbController;
    private ListView listRecipes;
    private RecipesAdapter recipesAdapter;

    public RecipesFragment() {
        // Required empty public constructor
    }

    public RecipesFragment newInstance(){
        RecipesFragment fragment = new RecipesFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes, container, false);
        listRecipes = (ListView) view.findViewById(R.id.listViewRecipes);
        listRecipes.setAdapter(recipesAdapter);
        Button newRecipe = (Button) view.findViewById(R.id.buttonNewRecipe);
        newRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewRecipeFragment newRecipeFragment = NewRecipeFragment.newInstance();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container_main, newRecipeFragment);
                transaction.addToBackStack("New recipe");
                transaction.commit();
            }
        });
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbController = new DBController(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        dbController.open();
        Cursor c = dbController.getRecipes();
        recipesAdapter = new RecipesAdapter(getActivity(), c, true);
        listRecipes.setAdapter(recipesAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();
        dbController.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbController.close();
    }
}
