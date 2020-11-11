package cubes.main.dao;

import java.util.List;

import cubes.main.entity.Tag;

public interface TagDAO {

	public List<Tag> getTags();
	
	public void saveTag(Tag tag);
	
	public void deleteTag(int id);
	
	public Tag getTag(int id);
	
	public List<Tag> getTagsById(List<Integer> ids);
	
}
