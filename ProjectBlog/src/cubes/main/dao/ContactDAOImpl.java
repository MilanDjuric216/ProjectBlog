package cubes.main.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cubes.main.entity.Contact;

@Repository
public class ContactDAOImpl implements ContactDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public List<Contact> getContacts() {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Contact> query = session.createQuery("from Contact order by id desc", Contact.class);
		
		List<Contact> list = query.getResultList();
		//ovde je onako produkt lekcija
		return list;
	}
	
	@Transactional
	@Override
	public void saveContact(Contact contact) {
		Session session = sessionFactory.getCurrentSession();
		
		session.saveOrUpdate(contact);
	}

	@Transactional
	@Override
	public void deleteContact(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("delete from Contact where id=:id");
		query.setParameter("id", id);
		query.executeUpdate();
		
	}
	
	@Transactional
	@Override
	public int getUnreadMessages() {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("from Contact where seen = 0");
		
		int result = query.getResultList().size();
		
		return result;
	}

	@Transactional
	@Override
	public Contact getContact(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Contact contact = session.get(Contact.class, id);
		
		return contact;
	}

}
