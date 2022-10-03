package com.shashi.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.shashi.entities.Comment;
import com.shashi.entities.Post;
import com.shashi.entities.Tag;
import com.shashi.repository.CommentsRepository;
import com.shashi.repository.PostsRepository;
import com.shashi.repository.TagsRepository;
import com.shashi.repository.UserRepository;

@Controller
public class MainController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	PostsRepository postsRepository;
	@Autowired
	TagsRepository tagsRepository;
	@Autowired
	CommentsRepository commentsRepository;

	@RequestMapping("/")
	public String blogs(@RequestParam(value = "start", defaultValue = "0") Integer start,
			@RequestParam(value = "limit", defaultValue = "5") Integer limit, Model model) {
		Page<Post> posts = postsRepository.findAll(PageRequest.of(start, limit));
		List<String> allAuthors = postsRepository.findAllAuthor();
		List<String> allTags = tagsRepository.findAllTags();
		List<String> allDateAndTime = postsRepository.findAllDataAndTime();
		for (Post p : posts) {
			p.getPublishAt();
		}
		model.addAttribute("author", allAuthors);
		model.addAttribute("allTags", allTags);
		model.addAttribute("allDateTime", allDateAndTime);
		model.addAttribute("currentPage", start);
		model.addAttribute("postData", posts);
		model.addAttribute("limit", limit);
		model.addAttribute("totalPages", posts.getTotalPages());
		return "blogpost";
	}

	@GetMapping("/fullarticle/{id}")
	public String fullArticle(@PathVariable("id") int postId, Model model) {
		Post postList = postsRepository.findById(postId);
		Iterable<Comment> allComments = commentsRepository.findCommentsById(postId);
		model.addAttribute("id", postId);
		model.addAttribute("postList", postList);
		model.addAttribute("AllComments", allComments);
		return "fullarticle";
	}

	@GetMapping("/filterby/authors")
	public String filterByAuthorl(@RequestParam(value = "author", required = false) String[] author, Model model) {
		System.out.println("sahi hai");
		ArrayList<Post> allPostByAuthor = new ArrayList<>();
		for (String nameOfAuthor : author) {
              List<Post> findAllByAuthor = postsRepository.findAllByAuthor(nameOfAuthor);
              allPostByAuthor.addAll(findAllByAuthor);
		}
	
		List<String> allAuthors = postsRepository.findAllAuthor();
		List<String> allTags = tagsRepository.findAllTags();
		List<String> allDateAndTime = postsRepository.findAllDataAndTime();
		model.addAttribute("allDateTime", allDateAndTime);
		model.addAttribute("author", allAuthors);
		model.addAttribute("allTags", allTags);
		model.addAttribute("postData", allPostByAuthor);
		return "filterview/display";
	}

	@GetMapping("/filterby/tags")
	public String filterByTags(@RequestParam(value = "tags", required = false) String[] tags, Model model) {
		ArrayList<Post> allPostByTags = new ArrayList<>();
		for (String tag : tags) {
			List<Post> postByTag = postsRepository.findAllByTagsName(tag);
			allPostByTags.addAll(postByTag);
		}
		List<String> allAuthors = postsRepository.findAllAuthor();
		List<String> allTags = tagsRepository.findAllTags();
		List<String> allDateAndTime = postsRepository.findAllDataAndTime();
		model.addAttribute("allDateTime", allDateAndTime);
		model.addAttribute("author", allAuthors);
		model.addAttribute("allTags", allTags);
		model.addAttribute("postData", allPostByTags);
		return "filterview/display";
	}

	@GetMapping("/filterby/dateTime")
	public String filterByDateTime(@RequestParam(value = "dateTime", required = false) String[] dateTime,
			Model model) {
		ArrayList<Post> allPostByDateTime = new ArrayList<>();
		for (String date : dateTime) {
			List<Post> postByDateTime = postsRepository.findByPublishAt(date);
			allPostByDateTime.addAll(postByDateTime);
		}
		List<String> allAuthors = postsRepository.findAllAuthor();
		List<String> allTags = tagsRepository.findAllTags();
		List<String> allDateAndTime = postsRepository.findAllDataAndTime();
		model.addAttribute("allDateTime", allDateAndTime);
		model.addAttribute("author", allAuthors);
		model.addAttribute("allTags", allTags);
		model.addAttribute("postData", allPostByDateTime);
		return "filterview/display";
	}

	@GetMapping("/sortby")
	public String sortByDateTime(Model model) {
		List<Post> sortedPosts = postsRepository.findByOrderByPublishAtAsc();
		List<String> allAuthors = postsRepository.findAllAuthor();
		List<String> allTags = tagsRepository.findAllTags();
		List<String> allDateAndTime = postsRepository.findAllDataAndTime();
		model.addAttribute("allDateTime", allDateAndTime);
		model.addAttribute("author", allAuthors);
		model.addAttribute("allTags", allTags);
		model.addAttribute("postData", sortedPosts);
		return "filterview/display";
	}

	@GetMapping("/search")
	public String search(HttpServletRequest request, Model model) {

		List<Post> searchBy = postsRepository.searchBy(request.getParameter("search").trim());
		model.addAttribute("postData", searchBy);
		List<String> allAuthors = postsRepository.findAllAuthor();
		List<String> allTags = tagsRepository.findAllTags();
		List<String> allDateAndTime = postsRepository.findAllDataAndTime();
		model.addAttribute("allDateTime", allDateAndTime);
		model.addAttribute("author", allAuthors);
		model.addAttribute("allTags", allTags);
		return "filterview/display";
	}

}
