package com.propertybuysell.mhome.ModelClasses;

public class MyItem {
    private String itemName;
    private String category;
    private boolean isSelected;

    public MyItem(String itemName,String category) {
        this.itemName = itemName;
        this.category = category;
        this.isSelected = false; // Initially, no items are selected
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemName() {
        return itemName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}