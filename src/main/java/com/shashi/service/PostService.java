package com.shashi.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shashi.entities.Post;

public interface PostService {

	public void savePost(Post post);

	public Post getPostById(int id);

	public void deletePost(Post post);

	public void deleteById(int id);

	public List<String> getAllAuthor();

	public List<String> getAllDateTime();

	public List<String> getAllTags();

	public Page<Post> getAllPost(Pageable pageable);

	public List<Post> getAllByAuthors(String authors);
	

	public List<Post> getPostByDateTime(String datetTime);
	
	public List<Post> getPostByTagsName(String Tags);
	
	public List<Post> getPostSortedByDateTime();
	
	public Page<Post> getPostSearchByArg(String title, Pageable pageable);

}
