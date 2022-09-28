package com.shashi.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shashi.entities.Post;

public interface PostsRepository extends CrudRepository<Post, Integer> {

	@Query(value = "select author from posts group by author", nativeQuery = true)
	public List<String> findAllAuthor();

	@Query(value = "select publish_at from posts group by publish_at", nativeQuery = true)
	public List<String> findAllDataAndTime();

	public List<Post> findByAuthor(String author);

	@Query(value = "Select * from posts where id = ?1", nativeQuery = true)
	public Post findById(int id);

	public List<Post> findAllByAuthor(String author);

	public List<Post> findByPublishAt(LocalDateTime dataAndTime);

	public List<Post> findAllByTagsName(String name);

	public List<Post> findByOrderByPublishAtAsc();

	@Query(value = "SELECT * FROM posts p WHERE LOWER(p.author) LIKE LOWER(CONCAT(?1))"
			+ " or LOWER(p.title) LIKE LOWER(CONCAT(?1)) "
			+ "or LOWER(p.content) LIKE LOWER(CONCAT(?1)) ", nativeQuery = true)
	public List<Post> searchBy(String title);

	Page<Post> findAll(Pageable pageable);
}
