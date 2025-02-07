package services;

import dao.CategoryDAO;
import models.Category;
import java.util.List;

public class CategoryService implements ICategoryService {
    private final CategoryDAO categoryDAO;

    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @Override
    public boolean addCategory(String categoryName, int seats) {
        return categoryDAO.addCategory(categoryName, seats);
    }

    @Override
    public boolean deleteCategory(String categoryName) {
        return categoryDAO.deleteCategory(categoryName);
    }

    @Override
    public List<Category> listCategories() {
        return categoryDAO.getAllCategories();
    }

    public int getSeatsForCategory(String categoryName) {
        return categoryDAO.getSeatsForCategory(categoryName);
    }
}