package com.example.andrey.myledger.Model;

public class IncomCategory {

    private Long incom_categoryID;
    private String incom_categoryName;

    public IncomCategory(){}

    public IncomCategory(String incom_categoryName) {

        this.incom_categoryName=incom_categoryName;
    }

    public Long getIncom_categoryID() {
        return incom_categoryID;
    }

    public void setIncom_categoryID(Long incom_categoryID) {
        this.incom_categoryID = incom_categoryID;
    }

    public String getIncom_categoryName() {
        return incom_categoryName;
    }

    public void setIncom_categoryName(String incom_categoryName) {
        this.incom_categoryName = incom_categoryName;
    }





}
