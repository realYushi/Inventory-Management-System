/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.Dto;

/**
 *
 * @author yushi
 */
public class CategoryDto implements ICategoryDto {

    private final String categoryID;
    private final String categoryName;

    private CategoryDto(Builder builder) {
        this.categoryID = builder.categoryID;
        this.categoryName = builder.categoryName;
    }

    public static class Builder {

        private String categoryID;
        private String categoryName;

        public Builder categoryID(String categoryID) {
            this.categoryID = categoryID;
            return this;
        }

        public Builder categoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

     

        public CategoryDto build() {
            return new CategoryDto(this);
        }
    }

    @Override
    public String getCategoryID() {
        return this.categoryID;
    }

    @Override
    public String getCategoryName() {
        return this.categoryName;
    }

   

}
