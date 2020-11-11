package cubes.main.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cubes.main.entity.Slider;

@Repository
public class SliderDAOImpl implements SliderDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public List<Slider> getSliders() {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("from Slider order by id desc", Slider.class);
		
		List<Slider> list = query.getResultList();
		
		return list;
	}
	
	@Transactional
	@Override
	public List<Slider> getSlidersFilter(int id) {
		Session session = sessionFactory.getCurrentSession();
		Query query = null;
		
		
		if(id == 0) {
			query = session.createQuery("from Slider order by id asc", Slider.class);
		}else if(id == 1) {
			query = session.createQuery("from Slider order by id desc", Slider.class);
		}
		
		List<Slider> list = query.getResultList();
		
		return list;
	}

	@Transactional
	@Override
	public void saveSlider(Slider slider) {
		Session session = sessionFactory.getCurrentSession();
		
		session.saveOrUpdate(slider);
	}

	@Transactional
	@Override
	public Slider getSlider(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Slider slider = session.get(Slider.class, id);
		
		return slider;
	}
	
	@Transactional
	@Override
	public void deleteSlider(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("delete from Slider where id=:id");
		query.setParameter("id", id);
		query.executeUpdate();
		
	}
	
}
