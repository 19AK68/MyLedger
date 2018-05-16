package com.example.andrey.myledger.Model;

public class Category {


    private Long categoryID;
    private String categoryName;

    public  Category(){}

    public Category (String categoryName){

        this.categoryName = categoryName;
    }

    public Long getCategoryID(int string) {
        return categoryID;
    }

    public void setCategoryID(Long categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
