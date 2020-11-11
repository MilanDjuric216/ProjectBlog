package cubes.main.dao;

import java.util.List;

import cubes.main.entity.Blog;
import cubes.main.entity.Comment;

public interface BlogDAO {

	public List<Blog> getBlogs();
	
	public List<Blog> getLastBlogs(int number);
	
	public List<Blog> getLastBlogsPopular(int number);
	
	public List<Blog> getTwelveBlog(int side);
	
	public List<Blog> getTwelveBlogCategory(String title, int side);
	
	public List<Blog> getTwelveBlogTag(int id, int side);
	
	public List<Blog> getTwelveBlogSearch(String search, int side);
	
	public List<Blog> getTwelveBlogAuthor(String username, int side);
	
	public List<Blog> getBlogSearch(String search);
	
	public int getCountBlogs();
	
	public int getCountBlogsCategory(int id);
	
	public int getCountBlogsTag(int id);
	
	public int getCountBlogsSearch(String search);
	
	public int getCountBlogsAuthor(String username);
	
	public void saveBlog(Blog blog);
	
	public Blog getBlog(int id);
	
	public Blog getBlogByName(String name);
	
	public void deleteBlog(int id);
	
	public List<Blog> getBlogByCategory(int id);
	
	public Blog getBlogWithTag(int id);
	
	public void incrementView(Blog blog);
	
	public int getBlogCommentsCount(int id);
	
	public int getBlogCommentsCountEnable(int id);
	
	public Blog getNextBlog(int id);
	
	public Blog getPreviousBlog(int id);
}

