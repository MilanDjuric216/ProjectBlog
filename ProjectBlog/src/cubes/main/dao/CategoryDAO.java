package cubes.main.dao;

import java.util.List;

import cubes.main.entity.Category;

public interface CategoryDAO {

	public List<Category> getCategories();
	
	public List<Category> getCategoriesForFilter();
	
	public List<Category> getCategoriesFour();
	
	public List<Category> getCategoriesSort(int id);
	
	public void saveCategory(Category category);
	//category koja treba biti sacuvana
	public void deleteCategory(int id);
	
	public Category getCategory(int id);
	//update category
}
