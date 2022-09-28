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

@Controller
public class PostController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	PostsRepository postsRepository;
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
		post.setTitle(request.getParameter("title"));
		post.setExcerpt(request.getParameter("excerpt"));
		post.setContent(request.getParameter("content"));
		post.setAuthor(request.getParameter("author"));
	      (boolean)request.getParameter("isPublished");
		post.setCreateAt(LocalDateTime.now());
		post.setPublishAt(LocalDateTime.now());
		post.setUpdateAt(LocalDateTime.now());
		List<Tag> tagsList = new ArrayList<>();
		String[] tagsArray = request.getParameter("name").split(" ");
		for (String allTags : tagsArray) {
			if (allTags.length() > 0) {
				if (!tagsRepository.existsByName(allTags)) {
					Tag tags = new Tag();
					tags.setName(allTags.trim());
					tags.setCreateAt(LocalDateTime.now().toString());
					tags.setUpdateAt(LocalDateTime.now().toString());
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
		postsRepository.save(post);
		return "redirect:/";
	}

	@PostMapping("/deletePost/{id}")
	public String deletePost(@PathVariable int id, Model model) {
		List<Comment> commentsById = commentsRepository.findCommentsById(id);
		for (Comment comment : commentsById) {
			commentsRepository.deleteById(comment.getId());
		}
		postsRepository.deleteById(id);
		return "redirect:/";
	}

	@PostMapping("/fullarticle/{id}/update")
	public String updatePost(@PathVariable String id, Model model) {
		int postId = Integer.parseInt(id);
		Post posts = postsRepository.findById(postId);
		model.addAttribute("posts", posts);
		return "updatePost";
	}

	@PostMapping("/updating/{id}")
	public String updating(@PathVariable int id, HttpServletRequest request, Model model) {
		Post posts = postsRepository.findById(id);
		posts.setTitle(request.getParameter("title").trim());
		posts.setExcerpt(request.getParameter("excerpt").trim());
		posts.setContent(request.getParameter("content").trim());
		posts.setAuthor(request.getParameter("author").trim());
		posts.setPublished(request.getParameter("isPublished").);
		posts.setCreateAt(LocalDateTime.now());
		posts.setPublishAt(LocalDateTime.now());
		posts.setUpdateAt(LocalDateTime.now());
		List<Tag> tagsList = new ArrayList<>();
		String[] tagsArray = request.getParameter("name").split(" ");
		for (String allTags : tagsArray) {
			if (allTags.length() > 0) {
				if (!tagsRepository.existsByName(allTags)) {
					Tag tags = new Tag();
					tags.setName(allTags.trim());
					tags.setCreateAt(LocalDateTime.now().toString());
					tags.setUpdateAt(LocalDateTime.now().toString());
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
		postsRepository.save(posts);
		return "redirect:/fullarticle/{id}";
	}

}
