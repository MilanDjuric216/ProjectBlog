package cubes.main.dao;

import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import cubes.main.entity.Blog;
import cubes.main.entity.Category;
import cubes.main.entity.Comment;
import cubes.main.entity.Tag;

@Repository
public class BlogDAOImpl implements BlogDAO {
	
	@Autowired
	private SessionFactory sessionFactory;	
	
	@Transactional
	@Override
	public List<Blog> getBlogs() {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Blog> query = session.createQuery("from Blog order by id desc", Blog.class);
		List<Blog> list = query.getResultList();
		
		return list;
	}
	
	@Transactional
	@Override
	public List<Blog> getTwelveBlog(int side) {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Blog> query = session.createQuery("from Blog where enable = 1 order by id desc", Blog.class);
		List<Blog> list = query.getResultList();
		
		List<Blog> list2 = list.subList(side *12, Math.min((side *12)+12, list.size()));
		
		return list2;
	}
	
	@Transactional
	@Override
	public List<Blog> getTwelveBlogCategory(String title, int side) {
		Session session = sessionFactory.getCurrentSession();
		Query que = session.createQuery("from Category where name=:title", Category.class);
		que.setParameter("title", title);
		Category cat =(Category) que.getSingleResult();
		
		Query<Blog> query = session.createQuery("from Blog where categoryId =:id and enable=1 order by id desc", Blog.class);
		query.setParameter("id", cat.getId());
		List<Blog> list = query.getResultList();
		
		List<Blog> list2 = list.subList(side *12, Math.min((side *12)+12, list.size()));
		
		return list2;
	}
	
	@Transactional
	@Override
	public List<Blog> getTwelveBlogTag(int id, int side) {
		Session session = sessionFactory.getCurrentSession();
		
		Tag tag = session.get(Tag.class, id);
		Hibernate.initialize(tag.getBlogs());
		
		List<Blog> bls = tag.getBlogs();
		
		List<Blog> li = new ArrayList<Blog>();
		
		for(Blog bl : bls) {
			if (bl.getEnable()) {
				li.add(bl);
			}
		}
		
		List<Blog> blogs = li.subList(side *12, Math.min((side *12)+12, li.size()));
		
		return blogs;
	}
	
	@Transactional
	@Override
	public List<Blog> getTwelveBlogSearch(String search, int side) {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Blog> query = session.createQuery("from Blog where title like :search or shortDescription like :search or description like :search and enable=1 order by id desc", Blog.class);
		query.setParameter("search", "%" + search +"%");
		List<Blog> list = query.getResultList();
		
		List<Blog> blogs = list.subList(side *12, Math.min((side *12)+12, list.size()));
		
		return blogs;
	}
	
	@Transactional
	@Override
	public List<Blog> getTwelveBlogAuthor(String username, int side) {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Blog> query = session.createQuery("from Blog where author=:username and enable=1 order by id desc", Blog.class);
		query.setParameter("username", username);
		List<Blog> list = query.getResultList();
		
		List<Blog> blogs = list.subList(side *12, Math.min((side *12)+12, list.size()));
		
		return blogs;
	}
	
	@Transactional
	@Override
	public List<Blog> getBlogSearch(String search) {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Blog> query = session.createQuery("from Blog where title like :search or shortDescription like :search or description like :search order by id desc", Blog.class);
		query.setParameter("search", "%" + search +"%");
		List<Blog> list = query.getResultList();
		
		return list;
	}
	
	@Transactional
	@Override
	public int getCountBlogs() {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Long> query = session.createQuery("select count(id) from Blog where enable=1");
		int count = Math.round((query.uniqueResult()-1) /12);
		
		return count;
	}
	
	@Transactional
	@Override
	public int getCountBlogsCategory(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Long> query = session.createQuery("select count(id) from Blog where categoryId=:id and enable = 1");
		query.setParameter("id", id);
		int count = Math.round((query.uniqueResult()-1) /12);
		
		return count;
	}
	
	@Transactional
	@Override
	public int getCountBlogsTag(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Tag tag = session.get(Tag.class, id);
		Hibernate.initialize(tag.getBlogs());
		
		int li = 0;
		
		for(Blog bl : tag.getBlogs()) {
			if (bl.getEnable()) {
				li++;
			}
		}
		
		int count = Math.round((li-1) /4);
		
		return count;
	}
	
	@Transactional
	@Override
	public int getCountBlogsSearch(String search) {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Long> query = session.createQuery("select count(id) from Blog where title like :search or shortDescription like :search or description like :search and enable=1 order by id desc");
		query.setParameter("search", "%" + search +"%");
		int count = Math.round((query.uniqueResult()-1) /12);
		
		return count;
	}
	
	@Transactional
	@Override
	public int getCountBlogsAuthor(String username) {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Long> query = session.createQuery("select count(id) from Blog where author=:username and enable=1 order by id desc");
		query.setParameter("username", username);
		int count = Math.round((query.uniqueResult()-1) /12);
		
		return count;
	}
	
	@Transactional
	@Override
	public void saveBlog(Blog blog) {
		Session session = sessionFactory.getCurrentSession();
		
		session.saveOrUpdate(blog);
		
	}
	
	@Transactional
	@Override
	public Blog getBlog(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Blog blog = session.get(Blog.class, id);
		
		return blog;
	}
	
	@Transactional
	@Override
	public Blog getBlogByName(String name) {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("from Blog where title=:name");
		query.setParameter("name", name);
		
		Blog blog = (Blog) query.getSingleResult();
		
		return blog;
	}
	
	@Transactional
	@Override
	public Blog getBlogWithTag(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Blog blog = session.get(Blog.class, id);
		
		Hibernate.initialize(blog.getTags());
		
		return blog;
	}
	
	@Transactional
	@Override
	public void deleteBlog(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("delete from Blog where id=:id");
		query.setParameter("id", id);
		query.executeUpdate();
		
	}

	@Transactional
	@Override
	public List<Blog> getLastBlogs(int number) {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Blog> query;
		
		if (number <= 3) {
			query = session.createQuery("from Blog where important = 1 and enable = 1 order by id desc", Blog.class).setMaxResults(number);
		}else {
			query = session.createQuery("from Blog where enable = 1 order by id desc", Blog.class).setMaxResults(number);
		}
		
		List<Blog> list = query.getResultList();
		
		return list;
	}
	
	@Transactional
	@Override
	public List<Blog> getLastBlogsPopular(int number){
		Session session = sessionFactory.getCurrentSession();
		
		Query<Blog> query;
		
		query = session.createQuery("from Blog where enable = 1 order by view desc", Blog.class);
		
		List<Blog> list = query.getResultList();
		List<Blog> list2 = new ArrayList<Blog>();
		
		for(Blog bl : list) {
			try {
				if (getDateDiff(bl.getDateCreated()) < 1 && list2.size() < 3) {
					list2.add(bl);
				}else if (list2.size() >= 3){
					break;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list2;
	}
	
	@Transactional
	@Override
	public List<Blog> getBlogByCategory(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Blog> query = session.createQuery("from Blog blog where blog.category.id = :id");
		query.setParameter("id", id);
		
		return query.getResultList();
	}
	
	@Transactional
	@Override
	public void incrementView(Blog blog) {
		Session session = sessionFactory.getCurrentSession();
		
		blog.setView(blog.getView() +1);
		
		session.saveOrUpdate(blog);
	}
	
	@Transactional
	@Override
	public int getBlogCommentsCount(int id) {
		Session session = sessionFactory.getCurrentSession();

		Query<Long> query = session.createQuery("select count(id) from Comment where blogId = :id and enable = 0");
		query.setParameter("id", id);
		
		int result = Math.round(query.uniqueResult());
		
		return result;
	}
	
	@Transactional
	@Override
	public int getBlogCommentsCountEnable(int id) {
		Session session = sessionFactory.getCurrentSession();

		Query<Long> query = session.createQuery("select count(id) from Comment where blogId = :id");
		query.setParameter("id", id);
		
		int result = Math.round(query.uniqueResult());
		
		return result;
	}

	@Transactional
	@Override
	public Blog getNextBlog(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Integer> que = session.createQuery("select max(id) from Blog");
		int res = que.uniqueResult();
		Blog bl = null;
		
		if (id != res) {
			Query<Blog> query = session.createQuery("from Blog where id>:id").setMaxResults(1);
			query.setParameter("id", id);
			
			bl = query.getSingleResult();
		}
			
		
		return bl;
	}

	@Transactional
	@Override
	public Blog getPreviousBlog(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Integer> que = session.createQuery("select min(id) from Blog");
		int res = que.uniqueResult();
		Blog bl = null;
		
		if (id != res) {
			Query<Blog> query = session.createQuery("from Blog where id<:id order by id desc").setMaxResults(1);
			query.setParameter("id", id);
			
			bl = query.getSingleResult();
		}
		
		return bl;
	}
	
	public long getDateDiff(String date) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
	    Date firstDate = sdf.parse(date);
	    String date2 = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
	    Date secondDate = sdf.parse(date2);
	 
	    long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
	    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) /30;
	   
		return diff;
	}
}
