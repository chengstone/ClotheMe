package com.daogen.clotheme;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table Category.
 */
public class Category {

    private long id;
    private String Name;
    private Integer IsSubCategory;
    private Integer BelongCategoryID;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Category() {
    }

    public Category(long id) {
        this.id = id;
    }

    public Category(long id, String Name, Integer IsSubCategory, Integer BelongCategoryID) {
        this.id = id;
        this.Name = Name;
        this.IsSubCategory = IsSubCategory;
        this.BelongCategoryID = BelongCategoryID;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Integer getIsSubCategory() {
        return IsSubCategory;
    }

    public void setIsSubCategory(Integer IsSubCategory) {
        this.IsSubCategory = IsSubCategory;
    }

    public Integer getBelongCategoryID() {
        return BelongCategoryID;
    }

    public void setBelongCategoryID(Integer BelongCategoryID) {
        this.BelongCategoryID = BelongCategoryID;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}