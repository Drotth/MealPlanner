package com.da401a.mealplanner;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewRecipeFragment extends Fragment {
    private EditText name, desc, meat, acc, veg, drink;
    private DBController dbController;

    // Required empty public constructor
    public NewRecipeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_recipe, container, false);
        name = (EditText) view.findViewById(R.id.editTextNewRecName);
        desc = (EditText) view.findViewById(R.id.editTextNewRecDesc);
        meat = (EditText) view.findViewById(R.id.editTextNewRecMeat);
        acc = (EditText) view.findViewById(R.id.editTextNewRecAcc);
        veg = (EditText) view.findViewById(R.id.editTextNewRecVeg);
        drink = (EditText) view.findViewById(R.id.editTextNewRecDrink);
        Button buttonInput = (Button) view.findViewById(R.id.buttonNewRecipeInput);

        buttonInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = name.getText().toString();
                String newDesc = desc.getText().toString();
                String newMeat = meat.getText().toString();
                String newAcc = acc.getText().toString();
                String newVeg = veg.getText().toString();
                String newDrink = drink.getText().toString();
                if(newName.isEmpty() || newDesc.isEmpty() || newMeat.isEmpty()
                        || newAcc.isEmpty() || newVeg.isEmpty() || newDrink.isEmpty()){
                    Toast.makeText(getActivity(), "All fields need to be filled!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    dbController.dataIntoRecipe(newName, newDesc, newMeat, newAcc, newVeg, newDrink);
                    getFragmentManager().popBackStackImmediate();
                }
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
