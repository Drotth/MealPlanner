package com.da401a.mealplanner;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {
    private FragmentManager fragmentManager;
    private RecipesFragment recipesFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        Fragment newFragment=null;

        //noinspection SimplifiableIfStatement
        switch(item.getItemId()){
            case R.id.action_recipes:
                newFragment= new RecipesFragment();
                FragmentTransaction ftRecipe = getFragmentManager().beginTransaction();
                ftRecipe.replace(R.id.container,newFragment);
                ftRecipe.addToBackStack(null);
                ftRecipe.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ftRecipe.commit();
                return true;
            case R.id.shoplist:
                newFragment= new FoodFragment();
                FragmentTransaction ftFood = getFragmentManager().beginTransaction();
                ftFood.replace(R.id.container,newFragment);
                ftFood.addToBackStack(null);
                ftFood.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ftFood.commit();
                return true;
            case R.id.add_meal:
                newFragment= new AddMealFragment();
                FragmentTransaction ftAdd = getFragmentManager().beginTransaction();
                ftAdd.replace(R.id.container,newFragment);
                ftAdd.addToBackStack(null);
                ftAdd.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ftAdd.commit();
                return true;
            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }


}
