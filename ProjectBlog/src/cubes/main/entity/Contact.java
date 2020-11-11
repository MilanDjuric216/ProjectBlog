package cubes.main.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Contact {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//na koji nacin ce biti 
	private int id;
	@Column
	private String name;
	@Column
	private String email;
	@Column
	private String message;
	@Column
	private boolean seen;
	
	public Contact() {
		
	}
	
	
	public Contact(String name, String email, String message) {
		this.name = name;
		this.email = email;
		this.message = message;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public boolean getSeen() {
		return seen;
	}


	public void setSeen(boolean seen) {
		this.seen = seen;
	}
	
	
	
}
