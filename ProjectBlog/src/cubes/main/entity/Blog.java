package cubes.main.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

@Entity
@Table
public class Blog {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	@Size(min = 20, max = 255, message = "Minimum characters is 20 and maximum is 255")
	private String title;
	@Column
	@Size(min = 50, max = 500, message = "Minimum characters is 50 and maximum is 500")
	private String shortDescription;
	@Column
	private String description;
	@Column
	private String picture;
	@Column
	private String dateCreated;
	@Column
	private int view;
	
	@JoinColumn(name = "categoryId")
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH,  CascadeType.REMOVE})
	private Category category;
	
	@JoinTable(name = "blog_tag", joinColumns = @JoinColumn(name = "id_blog"), inverseJoinColumns = @JoinColumn(name = "id_tag"))
	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Tag> tags;
	
	@Column
	private String author;
	@Column
	private boolean enable;
	@Column
	private boolean important;
	
	@Transient
	public long monthAgo;
	
	public Blog() {
		
	}
	
	public Blog(String title, String picture, String dateCreated, int view, String author, boolean enable,
			boolean important) {
		this.title = title;
		this.picture = picture;
		this.dateCreated = dateCreated;
		this.view = view;
		this.author = author;
		this.enable = enable;
		this.important = important;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPicture() {
		return picture;
	}
	
	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public int getView() {
		return view;
	}

	public void setView(int view) {
		this.view = view;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean getEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public boolean getImportant() {
		return important;
	}

	public void setImportant(boolean important) {
		this.important = important;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	
}
