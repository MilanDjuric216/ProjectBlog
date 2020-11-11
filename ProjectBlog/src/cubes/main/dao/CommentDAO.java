package cubes.main.dao;

import java.util.List;

import cubes.main.entity.Blog;
import cubes.main.entity.Comment;

public interface CommentDAO {

	public List<Comment> getComments();
	
	public void saveComment(Comment comment);
	
	public List<Comment> getCommentsByBlogId(Blog blog);
	
	public Comment getComment(int id);
	
	public void deleteComment(int id);
	
	public void deleteCommentsByBlog(int id);
	
}
