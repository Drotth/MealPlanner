package com.da401a.mealplanner;

import android.app.FragmentTransaction;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


public class FoodFragment extends Fragment implements ListView.OnItemLongClickListener{
    private ListView shoppingList;
    private ShoppingListAdapter sAdapter;
    private DBController dbController;





    public FoodFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbController =new DBController(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();

        dbController.open();

        Cursor c = dbController.getShopList();
        sAdapter = new ShoppingListAdapter(getActivity(), c, true);
        shoppingList.setAdapter(sAdapter);
    }


    @Override
    public void onPause() {
        super.onPause();
        dbController.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_food, container, false);

        shoppingList=(ListView)root.findViewById(R.id.listViewShop);
        shoppingList.setAdapter(sAdapter);
        shoppingList.setOnItemLongClickListener(this);


        Button button = (Button) root.findViewById(R.id.toShop);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertShoppingListFragment insertShoppingListFragment = new InsertShoppingListFragment();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim);
                ft.replace(R.id.container,insertShoppingListFragment);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        });

        return root;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        dbController.deleteRowShoppinglist(id);
        Cursor c = dbController.getShopList();
        sAdapter = new ShoppingListAdapter(getActivity(), c, true);
        shoppingList.setAdapter(sAdapter);
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbController.close();
    }
}
