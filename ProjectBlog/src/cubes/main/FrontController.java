package cubes.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cubes.main.dao.BlogDAO;
import cubes.main.dao.CategoryDAO;
import cubes.main.dao.CommentDAO;
import cubes.main.dao.ContactDAO;
import cubes.main.dao.SliderDAO;
import cubes.main.dao.TagDAO;
import cubes.main.entity.Blog;
import cubes.main.entity.Comment;
import cubes.main.entity.Contact;
import cubes.main.entity.Tag;

@Controller
@RequestMapping("/")
public class FrontController {
	
	@Autowired
	private SliderDAO sliderDAO;
	@Autowired
	private BlogDAO blogDAO;
	@Autowired
	private ContactDAO contactDAO;
	@Autowired
	private CategoryDAO categoryDAO;
	@Autowired
	private TagDAO tagDAO;
	@Autowired
	private CommentDAO commentDAO;
	
	@RequestMapping({"/", "index-page"})
	public String getIndexPage(Model model) throws ParseException {
		
		List<Blog> blogs = blogDAO.getLastBlogs(3);
		List<Integer> li = new ArrayList<Integer>();
		
		List<Long> liMonthAgo = new ArrayList<Long>();
		for(Blog blog2 : blogs) {
			
			liMonthAgo.add(getDateDiff(blog2.getDateCreated()));
			li.add(blogDAO.getBlogCommentsCount(blog2.getId()));
		}
		
		model.addAttribute("monthAgo1", liMonthAgo);
		
		model.addAttribute("sliderList", sliderDAO.getSliders());
		model.addAttribute("categoryFourList", categoryDAO.getCategoriesFour());
		model.addAttribute("threeBlogs", blogs);
		model.addAttribute("twelveBlogs", blogDAO.getLastBlogs(12));
		model.addAttribute("commentList", li);
		
		return "front/index-page";
	}
	
	
	//Blog
	
	@RequestMapping("/blog-page-side")
	public String getBlogPagination(@RequestParam int id, Model model) throws ParseException {
		
		List<Blog> blogs = blogDAO.getTwelveBlog(id);
		List<Integer> li = new ArrayList<Integer>();
		List<Long> liMonthAgo = new ArrayList<Long>();
		
		for(Blog blog2 : blogs) {
			
			liMonthAgo.add(getDateDiff(blog2.getDateCreated()));
			li.add(blogDAO.getBlogCommentsCount(blog2.getId()));
		}
		
		model.addAttribute("monthAgo1", liMonthAgo);
		
		model.addAttribute("blogSixList", blogs); //Nisam uspeo da iz baze cupam po 4 bloga, pa sam morao da cupam sve pa da prikazujem po 4 bloga na stranici
		model.addAttribute("commentListTwo", li); //Znam da nije bas najbolje resenje, ali nisam znao drugacije - nastavak dole
		model.addAttribute("blogCount", blogDAO.getCountBlogs()); //probao sam u mysql-u da koristim LIMIT funkciju, ali izgleda da java to ne podrzava
		model.addAttribute("activePage", id);
		
		
		
		List<Blog> blogs2 = blogDAO.getLastBlogsPopular(3);
		List<Integer> li2 = new ArrayList<Integer>();
		
		for(Blog blog3 : blogs2) {
			li2.add(blogDAO.getBlogCommentsCount(blog3.getId()));
		}
		
		model.addAttribute("categoryList", categoryDAO.getCategories());
		model.addAttribute("tagList", tagDAO.getTags());
		model.addAttribute("categoryFourList", categoryDAO.getCategoriesFour());
		model.addAttribute("threeBlogs", blogs2);
		model.addAttribute("commentList", li2);
		
		return "front/blog-page";
	}
	
	//Contact
	
	@RequestMapping("/contact-page")
	public String getContactPage(Model model) {
		
		Contact contact = new Contact();
		
		
		List<Blog> blogs = blogDAO.getLastBlogsPopular(3);
		
		List<Integer> li = new ArrayList<Integer>();
		
		for(Blog blog2 : blogs) {
			li.add(blogDAO.getBlogCommentsCount(blog2.getId()));
		}
		
		model.addAttribute("contact", contact);
		model.addAttribute("categoryFourList", categoryDAO.getCategoriesFour());
		model.addAttribute("threeBlogs", blogs);
		model.addAttribute("commentList", li);
		
		return "front/contact-page";
	}
	
	@RequestMapping("/contact-save")
	public String getContactSave(@ModelAttribute Contact contact, BindingResult result) {
		
		if (result.hasErrors()) {
			return "redirect:/contact-page";
		}
		
		contactDAO.saveContact(contact);
		
		return "redirect:/index-page";
	}
	
	//Blog post
	
	@RequestMapping("/blog-post-page")
	public String getBlogPostPage(@RequestParam String title, Model model) throws ParseException {
		
		Blog bl = blogDAO.getBlogByName(title);
		bl = blogDAO.getBlogWithTag(bl.getId());
		
		Comment comment = new Comment();
		model.addAttribute("comment", comment);
		
		
		
		bl.monthAgo = getDateDiff(bl.getDateCreated());
		
		blogDAO.incrementView(bl);
		model.addAttribute("monthAgo", bl.monthAgo);
		model.addAttribute("nextPost", blogDAO.getNextBlog(bl.getId()));
		model.addAttribute("previousPost", blogDAO.getPreviousBlog(bl.getId()));
		model.addAttribute("blogPost", bl);
		model.addAttribute("comments", commentDAO.getCommentsByBlogId(bl));
		
		
		
		List<Blog> blogs = blogDAO.getLastBlogsPopular(3);
		
		List<Integer> li = new ArrayList<Integer>();
		
		for(Blog blog2 : blogs) {
			li.add(blogDAO.getBlogCommentsCount(blog2.getId()));
		}
		
		model.addAttribute("categoryList", categoryDAO.getCategories());
		model.addAttribute("tagList", tagDAO.getTags());
		model.addAttribute("categoryFourList", categoryDAO.getCategoriesFour());
		model.addAttribute("threeBlogs", blogs);
		model.addAttribute("commentList", li);
		
		return "front/blog-post-page";
	}
	
	@RequestMapping("/submit-comment")
	public String getBlogComment(@ModelAttribute Comment comment, BindingResult result) {
		
		if (result.hasErrors()) {
			return "redirect:/blog-post-page";
		}
		
		if (comment.getDateCreated() == "") {
			String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			comment.setDateCreated(date);
		}
		
		commentDAO.saveComment(comment);
		
		return "redirect:/blog-post-page?id="+comment.getBlogId();
	}
	
	//Blog category
	
	@RequestMapping("/blog-category")
	public String getBlogCategory(@RequestParam String title, @RequestParam int id,  @RequestParam int sideId, Model model) throws ParseException {
		
		
		List<Blog> blogs = blogDAO.getTwelveBlogCategory(title, sideId);
		List<Integer> li = new ArrayList<Integer>();
		List<Long> liMonthAgo = new ArrayList<Long>();
		
		for(Blog blog2 : blogs) {
			liMonthAgo.add(getDateDiff(blog2.getDateCreated()));
			li.add(blogDAO.getBlogCommentsCount(blog2.getId()));
		}
		
		model.addAttribute("monthAgo1", liMonthAgo);
		
		model.addAttribute("blogFourCategoryList", blogs);
		model.addAttribute("commentListTwo", li);
		model.addAttribute("blogCount", blogDAO.getCountBlogsCategory(id));
		model.addAttribute("activePage", sideId);
		
		
		
		List<Blog> blogs2 = blogDAO.getLastBlogs(3);
		List<Integer> li2 = new ArrayList<Integer>();
		
		for(Blog blog3 : blogs2) {
			li2.add(blogDAO.getBlogCommentsCount(blog3.getId()));
		}
		
		
		
		
		List<Blog> blogs3 = blogDAO.getLastBlogsPopular(3);
		
		List<Integer> li3 = new ArrayList<Integer>();
		
		for(Blog blog2 : blogs3) {
			li3.add(blogDAO.getBlogCommentsCount(blog2.getId()));
		}
		
		model.addAttribute("categoryList", categoryDAO.getCategories());
		model.addAttribute("tagList", tagDAO.getTags());
		model.addAttribute("categorySearch", categoryDAO.getCategory(id));
		model.addAttribute("categoryFourList", categoryDAO.getCategoriesFour());
		model.addAttribute("threeBlogs", blogs3);
		model.addAttribute("commentList", li3);
		
		return "front/blog-category";
	}
	
	//Blog tag
	
	
	@RequestMapping("/blog-tag")
	public String getBlogTag(@RequestParam int id, @RequestParam int sideId, Model model) throws ParseException {
		
		
		List<Blog> blogs = blogDAO.getTwelveBlogTag(id, sideId);
		List<Integer> li = new ArrayList<Integer>();
		List<Long> liMonthAgo = new ArrayList<Long>();
		
		for(Blog blog2 : blogs) {
		    
		    liMonthAgo.add(getDateDiff(blog2.getDateCreated()));
			li.add(blogDAO.getBlogCommentsCount(blog2.getId()));
		}
		
		model.addAttribute("monthAgo1", liMonthAgo);
		
		model.addAttribute("blogFourTagList", blogs);
		model.addAttribute("commentListTwo", li);
		model.addAttribute("blogCount", blogDAO.getCountBlogsTag(id));
		model.addAttribute("activePage", sideId);
		
		
		
		List<Blog> blogs2 = blogDAO.getLastBlogs(3);
		List<Integer> li2 = new ArrayList<Integer>();
		
		for(Blog blog3 : blogs2) {
			li2.add(blogDAO.getBlogCommentsCount(blog3.getId()));
		}
		
		
		
		
		List<Blog> blogs3 = blogDAO.getLastBlogsPopular(3);
		
		List<Integer> li3 = new ArrayList<Integer>();
		
		for(Blog blog2 : blogs3) {
			li3.add(blogDAO.getBlogCommentsCount(blog2.getId()));
		}
		
		model.addAttribute("categoryList", categoryDAO.getCategories());
		model.addAttribute("tagList", tagDAO.getTags());
		model.addAttribute("tagSearch", tagDAO.getTag(id));
		model.addAttribute("categoryFourList", categoryDAO.getCategoriesFour());
		model.addAttribute("threeBlogs", blogs3);
		model.addAttribute("commentList", li3);
		
		return "front/blog-tag";
	}
	
	@RequestMapping("/blog-search")
	public String getBlogSearch(@RequestParam String search, @RequestParam int sideId, Model model) throws ParseException {
		
		
		List<Blog> blogs = blogDAO.getTwelveBlogSearch(search, sideId);
		List<Integer> li = new ArrayList<Integer>();
		List<Long> liMonthAgo = new ArrayList<Long>();
		
		for(Blog blog2 : blogs) {
			liMonthAgo.add(getDateDiff(blog2.getDateCreated()));
			li.add(blogDAO.getBlogCommentsCount(blog2.getId()));
		}
		
		model.addAttribute("monthAgo1", liMonthAgo);
		
		
		model.addAttribute("blogFourSearchList", blogs);
		model.addAttribute("commentListTwo", li);
		model.addAttribute("blogCount", blogDAO.getCountBlogsSearch(search));
		model.addAttribute("activePage", sideId);
		
		
		
		List<Blog> blogs2 = blogDAO.getLastBlogs(3);
		List<Integer> li2 = new ArrayList<Integer>();
		
		for(Blog blog3 : blogs2) {
			li2.add(blogDAO.getBlogCommentsCount(blog3.getId()));
		}
		
		
		
		
		List<Blog> blogs3 = blogDAO.getLastBlogsPopular(3);
		
		List<Integer> li3 = new ArrayList<Integer>();
		
		for(Blog blog2 : blogs3) {
			li3.add(blogDAO.getBlogCommentsCount(blog2.getId()));
		}
		
		model.addAttribute("categoryList", categoryDAO.getCategories());
		model.addAttribute("tagList", tagDAO.getTags());
		model.addAttribute("searchTitle", search);
		model.addAttribute("categoryFourList", categoryDAO.getCategoriesFour());
		model.addAttribute("threeBlogs", blogs3);
		model.addAttribute("commentList", li3);
		
		return "front/blog-search";
	}
	
	@RequestMapping("/blog-author")
	public String getBlogAuthor(@RequestParam String username, @RequestParam int sideId, Model model) throws ParseException {
		
		
		List<Blog> blogs = blogDAO.getTwelveBlogAuthor(username, sideId);
		List<Integer> li = new ArrayList<Integer>();
		List<Long> liMonthAgo = new ArrayList<Long>();
		
		for(Blog blog2 : blogs) {
			liMonthAgo.add(getDateDiff(blog2.getDateCreated()));
			li.add(blogDAO.getBlogCommentsCount(blog2.getId()));
		}
		
		model.addAttribute("monthAgo1", liMonthAgo);
		
		
		model.addAttribute("blogFourAuthorList", blogs);
		model.addAttribute("commentListTwo", li);
		model.addAttribute("blogCount", blogDAO.getCountBlogsAuthor(username));
		model.addAttribute("activePage", sideId);
		
		
		
		List<Blog> blogs2 = blogDAO.getLastBlogs(3);
		List<Integer> li2 = new ArrayList<Integer>();
		
		for(Blog blog3 : blogs2) {
			li2.add(blogDAO.getBlogCommentsCount(blog3.getId()));
		}
		
		
		
		
		List<Blog> blogs3 = blogDAO.getLastBlogsPopular(3);
		
		List<Integer> li3 = new ArrayList<Integer>();
		
		for(Blog blog2 : blogs3) {
			li3.add(blogDAO.getBlogCommentsCount(blog2.getId()));
		}
		
		model.addAttribute("categoryList", categoryDAO.getCategories());
		model.addAttribute("tagList", tagDAO.getTags());
		model.addAttribute("categoryFourList", categoryDAO.getCategoriesFour());
		model.addAttribute("threeBlogs", blogs3);
		model.addAttribute("commentList", li3);
		
		return "front/blog-author";
	}
	
	
	public long getDateDiff(String date) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
	    Date oneDate = sdf.parse(date);
	    String date2 = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
	    Date secondDate = sdf.parse(date2);
	 
	    long diffInMillies = Math.abs(secondDate.getTime() - oneDate.getTime());
	    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS) /30;
	   
		return diff;
	}
}
