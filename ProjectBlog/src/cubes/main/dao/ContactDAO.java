package cubes.main.dao;

import java.util.List;

import cubes.main.entity.Blog;
import cubes.main.entity.Contact;

public interface ContactDAO {

	public List<Contact> getContacts();
	
	public void saveContact(Contact contact);
	
	public void deleteContact(int id);
	
	public int getUnreadMessages();
	
	public Contact getContact(int id);
}
