package cubes.main;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.query.Query;
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
import cubes.main.entity.Category;
import cubes.main.entity.Comment;
import cubes.main.entity.Contact;
import cubes.main.entity.Slider;
import cubes.main.entity.Tag;

@Controller
@RequestMapping("/administration")
public class AdministrationController {

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
	
	//Slider DAO
	
	@RequestMapping({"/", "/slider-list"})
	public String getSliderList(Model model) {
		
		model.addAttribute("sliderList", sliderDAO.getSliders());
		model.addAttribute("contactCount", contactDAO.getUnreadMessages());
		
		return "slider-list";
	}
	
	@RequestMapping("/slider-form")
	public String getSliderForm(Model model) {
		
		Slider slider = new Slider();
		
		model.addAttribute("slider", slider);
		model.addAttribute("contactCount", contactDAO.getUnreadMessages());
		
		return "slider-form";
	}
	
	@RequestMapping("/slider-save")
	public String getSliderSave(@Valid @ModelAttribute Slider slider, BindingResult result) {
		
		if (result.hasErrors()) {
			return "slider-form";
		}
		
		sliderDAO.saveSlider(slider);
		
		return "redirect:/administration/slider-list";
	}
	
	@RequestMapping("/slider-enable")
	public String getSliderEnable(@RequestParam int id) {
		
		Slider slider = sliderDAO.getSlider(id);
		
		if (slider.getEnable()) {
			slider.setEnable(false);
		}else{
			slider.setEnable(true);
		}
		
		sliderDAO.saveSlider(slider);
		
		return "redirect:/administration/slider-list";
	}
	
	@RequestMapping("/slider-update-form")
	public String getSliderUpdate(@RequestParam int id, Model model) {
		
		model.addAttribute("slider", sliderDAO.getSlider(id));
		model.addAttribute("contactCount", contactDAO.getUnreadMessages());
		
		return "/slider-form";
	}
	
	@RequestMapping("/slider-delete")
	public String getSliderDelete(@RequestParam int id) {
		
		sliderDAO.deleteSlider(id);
		
		return "redirect:/administration/slider-list";
	}
	
	@RequestMapping("/slider-list-filter")
	public String getSliderListFilter(@RequestParam int id, Model model) {
		
		model.addAttribute("sliderList", sliderDAO.getSlidersFilter(id));
		model.addAttribute("contactCount", contactDAO.getUnreadMessages());
		
		return "slider-list";
	}
	
	//Blog DAO
	
	
	@RequestMapping("/blog-list")
	public String getBlogList(Model model) {
		
		List<Blog> blogs = blogDAO.getBlogs();
		
		List<Integer> li = new ArrayList<Integer>();
		
		for(Blog blog2 : blogs) {
			li.add(blogDAO.getBlogCommentsCountEnable(blog2.getId()));
		}
		
		model.addAttribute("blogList", blogs);
		model.addAttribute("commentList", li);
		model.addAttribute("contactCount", contactDAO.getUnreadMessages());
		
		return "blog-list";
	}
	
	@RequestMapping("/blog-form")
	public String getBlogForm(Model model) {
		
		Blog blog = new Blog();
		
		model.addAttribute("blog", blog);
		model.addAttribute("tagList", tagDAO.getTags());
		model.addAttribute("categoryList", categoryDAO.getCategories());
		model.addAttribute("contactCount", contactDAO.getUnreadMessages());
		
		return "blog-form";
	}
	
	@RequestMapping("/blog-save")
	public String getBlogSave(@Valid @ModelAttribute Blog blog, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			model.addAttribute("tagList", tagDAO.getTags());
			model.addAttribute("categoryList", categoryDAO.getCategories());
			return "blog-form";
		}
		
		Category category = categoryDAO.getCategory(blog.getCategory().getId());
		
		List<Integer> ids = new ArrayList<Integer>();
		
		
		for(Tag tag : blog.getTags()) {
			ids.add(Integer.parseInt(tag.getName()));
		}
		
		List<Tag> tags = tagDAO.getTagsById(ids);
		
		blog.setCategory(category);
		blog.setTags(tags);
		
		if (blog.getDateCreated() == "") {
			String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			blog.setDateCreated(date);
		}
		
		blogDAO.saveBlog(blog);
		
		return "redirect:/administration/blog-list";
	}
	
	@RequestMapping("/blog-enable")
	public String getBlogEnable(@RequestParam int id) {
		
		Blog blog = blogDAO.getBlog(id);
		
		if (blog.getEnable()) {
			blog.setEnable(false);
		}else{
			blog.setEnable(true);
		}
		
		blogDAO.saveBlog(blog);
		
		return "redirect:/administration/blog-list";
	}
	
	@RequestMapping("/blog-update-form")
	public String getBlogUpdate(@RequestParam int id, Model model) {
		
		Blog blog = blogDAO.getBlogWithTag(id);
		
		model.addAttribute("blog", blog);
		model.addAttribute("categoryList", categoryDAO.getCategories());
		model.addAttribute("tagList", tagDAO.getTags());
		model.addAttribute("contactCount", contactDAO.getUnreadMessages());
		
		return "/blog-form";
	}
	
	@RequestMapping("/blog-delete")
	public String getBlogDelete(@RequestParam int id) {
		
		commentDAO.deleteCommentsByBlog(id);
		blogDAO.deleteBlog(id);
		
		return "redirect:/administration/blog-list";
	}
	
	@RequestMapping("/blog-search-list")
	public String getBlogSearchList(@RequestParam String search, Model model) {
		
		List<Blog> blogs = blogDAO.getBlogSearch(search);
		
		List<Integer> li = new ArrayList<Integer>();
		
		for(Blog blog2 : blogs) {
			li.add(blogDAO.getBlogCommentsCount(blog2.getId()));
		}
		
		model.addAttribute("blogList", blogs);
		model.addAttribute("commentList", li);
		model.addAttribute("contactCount", contactDAO.getUnreadMessages());
		
		return "blog-list";
	}
	
	
	//Messages
	
	
	@RequestMapping("/contact-list")
	public String getContactList(Model model) {
		
		model.addAttribute("contactList", contactDAO.getContacts());
		model.addAttribute("contactCount", contactDAO.getUnreadMessages());
		
		return "contact-list";
	}
	
	@RequestMapping("/contact-delete")
	public String getContactDelete(@RequestParam int id) {
		
		contactDAO.deleteContact(id);
		
		return "redirect:/administration/contact-list";
	}
	
	@RequestMapping("/contact-read")
	public String getContactRead(@RequestParam int id) {
		
		Contact contact = contactDAO.getContact(id);
		
		contact.setSeen(true);
		
		contactDAO.saveContact(contact);
		
		return "redirect:/administration/contact-list";
	}
	
	//Category
	
	
	@RequestMapping("/category-list")
	public String getCategoryList(Model model) {
		
		model.addAttribute("categoryList", categoryDAO.getCategories());
		model.addAttribute("contactCount", contactDAO.getUnreadMessages());
		
		return "category-list";
	}
	
	@RequestMapping("/category-form")
	public String getCategoryForm(Model model) {
		
		Category category = new Category();
		
		model.addAttribute("category", category);
		model.addAttribute("contactCount", contactDAO.getUnreadMessages());
		
		return "category-form";
	}
	
	@RequestMapping("/category-save")
	public String getCategorySave(@Valid @ModelAttribute Category category, BindingResult result) {
		
		if (result.hasErrors()) {
			return "category-form";
		}
		
		categoryDAO.saveCategory(category);
		return "redirect:/administration/category-list";
	}
	
	@RequestMapping("/category-update-form")
	public String getCategoryUpdate(@RequestParam int id, Model model) {
		
		model.addAttribute("category", categoryDAO.getCategory(id));
		model.addAttribute("contactCount", contactDAO.getUnreadMessages());
		
		return "/category-form";
	}
	
	@RequestMapping("/category-delete")
	public String getCategoryDelete(@RequestParam int id) {
		
		categoryDAO.deleteCategory(id);
		//izbrise i redirektuje 
		return "redirect:/administration/category-list";
	}
	
	@RequestMapping("/category-list-filter")
	public String getCategoryListFilter(@RequestParam int id, Model model) {
		
		model.addAttribute("categoryList", categoryDAO.getCategories());
		model.addAttribute("contactCount", contactDAO.getUnreadMessages());
		
		return "category-list";
	}
	
	//Tag
	
	@RequestMapping("/tag-list")
	public String getTagList(Model model) {
		
		model.addAttribute("tagList", tagDAO.getTags());
		model.addAttribute("contactCount", contactDAO.getUnreadMessages());
		
		return "tag-list";
	}
	
	@RequestMapping("/tag-form")
	public String getTagForm(Model model) {
		
		Tag tag = new Tag();
		
		model.addAttribute("tag", tag);
		model.addAttribute("contactCount", contactDAO.getUnreadMessages());
		
		return "tag-form";
	}
	
	@RequestMapping("/tag-save")
	public String getTagSave(@Valid @ModelAttribute Tag tag, BindingResult result) {
		
		if (result.hasErrors()) {
			return "tag-form";
		}
		
		tagDAO.saveTag(tag);
		
		return "redirect:/administration/tag-list";
	}
	
	@RequestMapping("/tag-update-form")
	public String getTagUpdate(@RequestParam int id, Model model) {
		
		model.addAttribute("tag", tagDAO.getTag(id));
		model.addAttribute("contactCount", contactDAO.getUnreadMessages());
		
		return "/tag-form";
	}
	
	@RequestMapping("/tag-delete")
	public String getTagDelete(@RequestParam int id) {
		
		tagDAO.deleteTag(id);
		
		return "redirect:/administration/tag-list";
	}
	
	//Comments
	
	
	@RequestMapping("/comment-list")
	public String getCommentList(Model model) {
		
		model.addAttribute("commentList", commentDAO.getComments());
		model.addAttribute("contactCount", contactDAO.getUnreadMessages());
		
		return "comment-list";
	}
	
	@RequestMapping("/comment-enable")
	public String getCommentEnable(@RequestParam int id) {
		
		Comment comment = commentDAO.getComment(id);
		
		if (comment.getEnable()) {
			comment.setEnable(false);
		}else{
			comment.setEnable(true);
		}
		
		commentDAO.saveComment(comment);
		
		return "redirect:/administration/comment-list";
	}
	
	@RequestMapping("/comment-delete")
	public String getCommentDelete(@RequestParam int id) {
		
		commentDAO.deleteComment(id);
		
		return "redirect:/administration/comment-list";
	}
	
}