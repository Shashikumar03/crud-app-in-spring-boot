package com.shashi.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.shashi.entities.Comment;
import com.shashi.entities.Post;
import com.shashi.entities.Tag;
import com.shashi.repository.CommentsRepository;
import com.shashi.repository.PostsRepository;
import com.shashi.repository.TagsRepository;
import com.shashi.repository.UserRepository;
import com.shashi.service.PostService;

@Controller
public class PostController {

	@Autowired
	PostService postService;

	@Autowired
	UserRepository userRepository;
	
	
//	  @Autowired PostsRepository postsRepository;

	
	@Autowired
	TagsRepository tagsRepository;
	@Autowired
	CommentsRepository commentsRepository;

	@PostMapping("/createblog")
	public String blogForm(Model model) {
		return "blogForm";
	}

	@PostMapping("/newblog")
	public String createBlog(HttpServletRequest request, Model model) {
		Post post = new Post();
		post.setTitle(request.getParameter("title").trim());
		post.setExcerpt(request.getParameter("excerpt").trim());
		post.setContent(request.getParameter("content").trim());
		post.setAuthor(request.getParameter("author").trim());
		post.setCreateAt(LocalDateTime.now().toString());
		post.setUpdateAt(LocalDateTime.now().toString());
		post.setPublishAt(LocalDateTime.now().toString());
		if (request.getParameter("isPublished") != null) {
			post.setPublished(true);
		} else {
			post.setPublished(false);
		}
		List<Tag> tagsList = new ArrayList<>();
		String[] tagsArray = request.getParameter("name").split(" ");
		for (String allTags : tagsArray) {
			if (allTags.length() > 0) {
				if (!tagsRepository.existsByName(allTags)) {
					Tag tags = new Tag();
					tags.setName(allTags.trim());
					tags.setCreateAt(LocalDateTime.now());
					tags.setUpdateAt(LocalDateTime.now());
					tagsList.add(tags);
					tagsRepository.save(tags);
				} else {
					int findIdByName = tagsRepository.findIdByName(allTags);
					Optional<Tag> tag = tagsRepository.findById(findIdByName);
					tagsList.add(tag.get());
					post.setTags(tagsList);
				}
			}
		}
		post.setTags(tagsList);
		postService.savePost(post);
		return "redirect:/";
	}

	@PostMapping("/deletePost/{id}")
	public String deletePost(@PathVariable int id, Model model) {
		List<Comment> commentsById = commentsRepository.findCommentsById(id);
		for (Comment comment : commentsById) {
			commentsRepository.deleteById(comment.getId());
		}
		 postService.deleteById(id);
		return "redirect:/";
	}

	@PostMapping("/fullarticle/{id}/update")
	public String updatePost(@PathVariable int id, Model model) {
		
		System.out.println("dddd");
		Post posts = postService.getPostById(id);
		model.addAttribute("posts", posts);
		return "updatePost";
	}

	@PostMapping("/updating/{id}")
	public String updating(@PathVariable int id, HttpServletRequest request, Model model) {
		Post posts = postService.getPostById(id);
		posts.setTitle(request.getParameter("title").trim());
		posts.setExcerpt(request.getParameter("excerpt").trim());
		posts.setContent(request.getParameter("content").trim());
		posts.setAuthor(request.getParameter("author").trim());
		posts.setPublished(request.getParameter("isPublished") == "true");

		posts.setCreateAt(LocalDateTime.now().toString());
		posts.setUpdateAt(LocalDateTime.now().toString());
		posts.setPublishAt(LocalDateTime.now().toString());
		List<Tag> tagsList = new ArrayList<>();
		String[] tagsArray = request.getParameter("name").split(" ");
		for (String allTags : tagsArray) {
			if (allTags.length() > 0) {
				if (!tagsRepository.existsByName(allTags)) {
					Tag tags = new Tag();
					tags.setName(allTags.trim());
					tags.setCreateAt(LocalDateTime.now());
					tags.setUpdateAt(LocalDateTime.now());
					tagsList.add(tags);
					tagsRepository.save(tags);
				} else {
					int findIdByName = tagsRepository.findIdByName(allTags);
					Optional<Tag> tag = tagsRepository.findById(findIdByName);
					tagsList.add(tag.get());
					posts.setTags(tagsList);
				}
			}
		}
		posts.setTags(tagsList);
		postService.savePost(posts);
		return "redirect:/fullarticle/{id}";
	}

}
