package com.rsn.food2feedo.ui.history;

public class FoodModel {

    String FoodName;
    String Description;
    String image;

    public FoodModel() {

    }

    public FoodModel(String FoodName, String Description, String image) {
        this.FoodName = FoodName;
        this.Description = Description;
        this.image = image;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        this.FoodName = foodName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
