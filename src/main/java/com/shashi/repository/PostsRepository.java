package com.shashi.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.shashi.entities.Post;

public interface PostsRepository extends CrudRepository<Post, Integer> {

	@Query(value = "select author from posts group by author", nativeQuery = true)
	public List<String> findAllAuthor();

	@Query(value = "select publish_at from posts", nativeQuery = true)
     public   List<String> findAllDataAndTime();

	public List<Post> findByAuthor(String author);

	
	public Post findById(int id);

	public List<Post> findAllByAuthor(String author);
	
	public Page<Post> findAllByAuthor(String author, Pageable pageable);
	
	


	public List<Post> findByPublishAt(String dataAndTime);

	public List<Post> findAllByTagsName(String name);

	public List<Post> findByOrderByPublishAtAsc();
	
	public List<Post> findAllByAuthorOrTagsName(String name, String tags);
	
	@Query("SELECT post "
			+ "from "
			+ "Post post "
			+ "join "
			+ "post.tags tag "
			+ "where (tag.name) in (:tags)")
	List<Post> findAllByTagsArray(@Param("tags") String[] tags);
	
	@Query("select post "
			+ "from "
			+ "Post post "
			+ "where (post.author) in (:authors)")
	List<Post> findAllByAuthorArray(String[] authors);
	
	@Query("select post "
			+ "from "
			+ "Post post "
			+ "where (post.publishAt) in (:DateTime)")
	List<Post> findAllByDateTimeArray(String[] DateTime);

	/*
	 * @Query(value =
	 * "SELECT * FROM posts p WHERE LOWER(p.author) LIKE LOWER(CONCAT(?1))" +
	 * " or LOWER(p.title) LIKE LOWER(CONCAT(?1)) " +
	 * "or LOWER(p.content) LIKE LOWER(CONCAT(?1)) ", nativeQuery = true)
	 */
	@Query("select post from Post post join post.tags tag " +
            "where (post.isPublished = true) and " +
            "upper(tag.name) like concat('%', upper(?1), '%') " +
            "or upper(post.title) like concat('%', upper(?1), '%') " +
            "or upper(post.content) like concat('%', upper(?1), '%') " +
            "or upper(post.author) like concat('%', upper(?1), '%') group by  post.id")
	public Page<Post> searchBy(String title, Pageable pageable);

	Page<Post> findAll(Pageable pageable);
}
