package services;

import models.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> getAllCategories();
    boolean addCategory(String category);
    boolean removeCategory(String category);
}
