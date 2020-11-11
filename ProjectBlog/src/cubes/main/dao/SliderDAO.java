package cubes.main.dao;

import java.util.List;

import cubes.main.entity.Slider;

public interface SliderDAO {

	public List<Slider> getSliders();
	
	public List<Slider> getSlidersFilter(int id);
	
	public void saveSlider(Slider slider);
	
	public Slider getSlider(int id);
	
	public void deleteSlider(int id);
}
