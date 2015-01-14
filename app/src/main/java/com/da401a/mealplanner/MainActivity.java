package com.da401a.mealplanner;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;

import java.lang.reflect.Field;

public class MainActivity extends Activity {
    private FragmentManager fragmentManager;
    private RecipesFragment recipesFragment;

    FragmentManager fm;
    WeekFragment weeks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getFragmentManager();
        weeks = new WeekFragment();

        // Force the Overflow Menu to show even on phones with dedicated menu button
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception exc) {}

        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.container, weeks);
        ft.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Fragment newFragment = null;

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
            case R.id.menu_about:
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
