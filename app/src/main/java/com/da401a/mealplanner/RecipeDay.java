package com.da401a.mealplanner;

public class RecipeDay {
    int week;
    int day;
    String recipe;

    public RecipeDay (int week, int day, String recipe){
        this.week = week;
        this.day = day;
        this.recipe = recipe;
    }

    public int getWeek(){
        return week;
    }

    public int getDay() {
        return day;
    }

    public String getRecipe() {
        return recipe;
    }
}
