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
    private FragmentManager fm;
    private WeekFragment weeks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getFragmentManager();
        weeks = new WeekFragment();

        // Force the Overflow Menu to show even on phones with dedicated menu button
        // Used when debugging GUI
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
        Fragment newFragment;

        switch(item.getItemId()){
            case R.id.action_recipes:
                newFragment = new RecipesFragment();
                FragmentTransaction ftRecipe = getFragmentManager().beginTransaction();
                ftRecipe.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim);
                ftRecipe.replace(R.id.container,newFragment);
                ftRecipe.addToBackStack(null);
                ftRecipe.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ftRecipe.commit();
                return true;

            case R.id.shoplist:
                newFragment = new FoodFragment();
                FragmentTransaction ftFood = getFragmentManager().beginTransaction();
                ftFood.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim);
                ftFood.replace(R.id.container,newFragment);
                ftFood.addToBackStack(null);
                ftFood.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ftFood.commit();
                return true;

            case R.id.add_meal:
                newFragment = new AddMealFragment();
                FragmentTransaction ftAdd = getFragmentManager().beginTransaction();
                ftAdd.setCustomAnimations(R.anim.enter_anim, R.anim.exit_anim);
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

    public void updateWeekList(){
        weeks.updateList();
    }
}
