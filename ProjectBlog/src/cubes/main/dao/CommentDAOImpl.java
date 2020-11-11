package cubes.main.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cubes.main.entity.Blog;
import cubes.main.entity.Category;
import cubes.main.entity.Comment;
import cubes.main.entity.Tag;

@Repository
public class CommentDAOImpl implements CommentDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public List<Comment> getComments() {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Comment> query = session.createQuery("from Comment order by id desc", Comment.class);
		List<Comment> list = query.getResultList();
		
		return list;
	}

	@Transactional
	@Override
	public void saveComment(Comment comment) {
		Session session = sessionFactory.getCurrentSession();
		
		session.saveOrUpdate(comment);
	}
	
	
	@Transactional
	@Override
	public List<Comment> getCommentsByBlogId(Blog blog) {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Comment> query = session.createQuery("from Comment where blogId = :id and enable = 0  order by id desc");
		query.setParameter("id", blog.getId());
		
		List<Comment> comments = query.getResultList();
		
		return comments;
	}
	
	@Transactional
	@Override
	public Comment getComment(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Comment comment = session.get(Comment.class, id);
		
		return comment;
		
	}

	@Transactional
	@Override
	public void deleteComment(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("delete from Comment where id=:id");
		query.setParameter("id", id);
		query.executeUpdate();	
	}
	
	@Transactional
	@Override
	public void deleteCommentsByBlog(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("delete from Comment where blogId=:id");
		query.setParameter("id", id);
		query.executeUpdate();	
	}
}
