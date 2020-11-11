package cubes.main.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cubes.main.entity.Category;

@Repository
public class CategoryDAOImpl implements CategoryDAO{

	@Autowired
	private SessionFactory sessionFactory;

	
	@Transactional
	@Override
	public List<Category> getCategories() {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Category> query = session.createQuery("from Category", Category.class);
		List<Category> list = query.getResultList();
		
		return list;
	}
	
	@Transactional
	@Override
	public List<Category> getCategoriesForFilter() {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Category> query = session.createQuery("from Category", Category.class);
		List<Category> list = query.getResultList();
		
		
		for(Category cat : list) {
			
			Query queryCount = session.createQuery("select count(blog.id) from Blog blog where blog.category.id = :id and enable = 1");
			queryCount.setParameter("id", cat.getId());
			
			cat.setCount((long)queryCount.uniqueResult());
		}
		
		return list;
	}
	
	@Transactional
	@Override
	public List<Category> getCategoriesSort(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = null;
		
		if(id == 0) {
			query = session.createQuery("from Category order by id asc", Category.class);
		}else if(id == 1) {
			query = session.createQuery("from Category order by id desc", Category.class);
		}
		
		List<Category> list = query.getResultList();
		
		for(Category cat : list) {
			Hibernate.initialize(cat.getBlogs());
		}
		
		return list;
	}

	@Transactional
	@Override
	public void saveCategory(Category category) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(category);
	}

	@Transactional
	@Override
	public void deleteCategory(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("delete from Category where id=:id");
		query.setParameter("id", id);
		query.executeUpdate();
	}

	@Transactional
	//transactional da se otvori i zatvori 
	@Override
	public Category getCategory(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Category category = session.get(Category.class, id);
		//update category
		return category;
	}

	@Transactional
	@Override
	public List<Category> getCategoriesFour() {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Category> query = session.createQuery("from Category", Category.class).setMaxResults(4);
		List<Category> list = query.getResultList();
		
		return list;
	}
	
	
}
