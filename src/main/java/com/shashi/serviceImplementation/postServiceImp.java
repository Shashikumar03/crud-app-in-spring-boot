package com.shashi.serviceImplementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shashi.entities.Post;
import com.shashi.repository.PostsRepository;
import com.shashi.repository.TagsRepository;
import com.shashi.service.PostService;

@Service
public class postServiceImp implements PostService {

	@Autowired
	private PostsRepository postRepository;
	
	private TagsRepository tagRepository;

	@Override
	public void savePost(Post post) {
		postRepository.save(post);
	}

	@Override
	public Post getPostById(int id) {
		// TODO Auto-generated method stub
		return postRepository.findById(id);

	}

	@Override
	public void deletePost(Post post) {
		postRepository.delete(post);

	}

	@Override
	public List<String> getAllAuthor() {
		return postRepository.findAllAuthor();

	}

	@Override
	public Page<Post> getAllPost(Pageable pageable) {
		Page<Post> findAll = postRepository.findAll(pageable);
		return  findAll;

	}

	@Override
	public void deleteById(int id) {
		postRepository.deleteById(id);
		
	}

	@Override
	public List<Post> getAllByAuthors(String authors) {
		     
		return postRepository.findAllByAuthor(authors);
	}

	@Override
	public List<String> getAllDateTime() {
		// TODO Auto-generated method stub
		return postRepository.findAllDataAndTime();
	}

	@Override
	public List<Post> getPostByDateTime(String dateTime) {
		// TODO Auto-generated method stub
		return postRepository.findByPublishAt(dateTime);
	}

	@Override
	public List<String> getAllTags() {
		// TODO Auto-generated method stub
		return tagRepository.findAllTags();
	}

	@Override
	public List<Post> getPostByTagsName(String Tags) {
		// TODO Auto-generated method stub
		return postRepository.findAllByTagsName(Tags);
	}

	@Override
	public List<Post> getPostSortedByDateTime() {
		// TODO Auto-generated method stub
		return  postRepository.findByOrderByPublishAtAsc();
	}

	@Override
	public Page<Post> getPostSearchByArg(String title, Pageable pageable) {
		
		return postRepository.searchBy(title, pageable);
	}

}
