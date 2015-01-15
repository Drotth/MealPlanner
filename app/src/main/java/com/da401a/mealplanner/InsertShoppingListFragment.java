package com.da401a.mealplanner;

import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertShoppingListFragment extends Fragment {
    private EditText date, product;
    private DBController dbController;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_add_buy_item, container, false);

        product = (EditText) root.findViewById(R.id.editText_Product);
        date = (EditText) root.findViewById(R.id.editTextShoppinglistDate);


        Button button = (Button) root.findViewById(R.id.create_new_product);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newProduct = product.getText().toString();
                String newDate = date.getText().toString();

                if (newProduct.isEmpty() || newDate.isEmpty()){
                    Toast.makeText(getActivity(), R.string.all_fields,
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    dbController.dataIntoShoppingList(newProduct, newDate);
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

        return root;
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
