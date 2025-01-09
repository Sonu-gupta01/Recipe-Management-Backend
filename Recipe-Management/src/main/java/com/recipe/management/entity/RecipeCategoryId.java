//// RecipeCategoryId.java
//package com.recipe.management.entity;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//public class RecipeCategoryId implements Serializable {
//
//    private int recipe;
//    private int category;
//
//    public RecipeCategoryId() {}
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(recipe, category);
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) return true;
//        if (obj == null || getClass() != obj.getClass()) return false;
//        RecipeCategoryId that = (RecipeCategoryId) obj;
//        return recipe == that.recipe && category == that.category;
//    }
//}
