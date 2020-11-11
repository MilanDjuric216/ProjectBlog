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
import javax.persistence.Table;

@Entity
@Table
public class Tag {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
	
	@Column
	public String name;
	
	@JoinTable(name = "blog_tag", joinColumns = @JoinColumn(name = "id_tag"), inverseJoinColumns = @JoinColumn(name = "id_blog"))
	@ManyToMany
	public List<Blog> blogs;
	
	public Tag() {
		
	}
	
	public Tag(String name) {
		super();
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Blog> getBlogs() {
		return blogs;
	}

	public void setBlogs(List<Blog> blogs) {
		this.blogs = blogs;
	}

	@Override
	public String toString() {
		
		return "("+id+")"+name;
	}
}
