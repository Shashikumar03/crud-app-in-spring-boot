package com.shashi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shashi.entities.Tag;

public interface TagsRepository extends CrudRepository<Tag, Integer> {

	boolean existsByName(String foo);

	@Query(value = "select id from tags where name = ?1", nativeQuery = true)
	public int findIdByName(String name);
	
	@Query(value = "select name from tags", nativeQuery = true)
	public List<String> findAllTags();

	  public Tag findByName(String tag);
}
