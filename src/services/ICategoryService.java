package services;

import models.Category;
import java.util.List;

public interface ICategoryService {
    boolean addCategory(String categoryName, int seats);
    boolean deleteCategory(String categoryName);
    List<Category> listCategories();
}