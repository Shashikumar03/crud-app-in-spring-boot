package com.shashi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shashi.entities.Comment;

public interface CommentsRepository  extends CrudRepository<Comment, Integer>{

	@Query(value = "Select * from comments where post_id = ?1", nativeQuery = true)
	public List<Comment> findCommentsById(int id);
}
