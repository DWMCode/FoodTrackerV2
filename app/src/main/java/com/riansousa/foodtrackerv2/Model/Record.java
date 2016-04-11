package com.riansousa.foodtrackerv2.Model;

/**
 * Record class construct with setters and getters
 * used for gridview binding.
 * Research cited: http://stackoverflow.com/questions/27818786/fetch-data-from-sqlite-and-display-in-gridview
 */
public class Record {

    /* declare privavate variables */
    private int id;
    private String time;
    private String group;
    private String item;
    private String portion;
    private String calories;

    /* setters */
    public void setId(int id) {
        this.id = id;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public void setGroup(String group) {
        this.group = group;
    }
    public void setItem(String item) {
        this.item = item;
    }
    public void setPortion(String portion) {
        this.portion = portion;
    }
    public void setCalories(String calories) {
        this.calories = calories;
    }

    /* getters */
    public int getId() {
        return this.id;
    }
    public String getTime() {
        return this.time;
    }
    public String getGroup() {
        return this.group;
    }
    public String getItem() {
        return this.item;
    }
    public String getPortion() {
        return this.portion;
    }
    public String getCalories() {
        return this.calories;
    }

}
