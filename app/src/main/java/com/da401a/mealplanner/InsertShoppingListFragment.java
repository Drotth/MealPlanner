package com.da401a.mealplanner;

import android.app.Activity;
import android.app.DialogFragment;
import android.net.Uri;
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
        View root =  inflater.inflate(R.layout.fragment_insert_shopping_list, container, false);

        product = (EditText) root.findViewById(R.id.editText_Product);
        date = (EditText) root.findViewById(R.id.editTextShoppinglistDate);


        Button button = (Button) root.findViewById(R.id.create_new_product);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newProduct = product.getText().toString();
                String newDate = date.getText().toString();
                if(newProduct.isEmpty() || newDate.isEmpty()){
                    Toast.makeText(getActivity(), "You have to fill in all fields",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    dbController.dataIntoShoppingList(newProduct, newDate);
                    getFragmentManager().popBackStackImmediate();
                }

            }
        });


        EditText editDateSelect = (EditText) root.findViewById(R.id.editTextShoppinglistDate);
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
