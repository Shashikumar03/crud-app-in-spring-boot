package com.shashi.controller;


import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.shashi.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.shashi.entities.Comment;
import com.shashi.entities.Post;
import com.shashi.repository.CommentsRepository;
import com.shashi.repository.PostsRepository;
import com.shashi.repository.TagsRepository;
import com.shashi.repository.UserRepository;
import com.shashi.service.PostService;

@Controller
public class MainController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	PostService postService;

	@Autowired
	PostsRepository postsRepository;
	@Autowired
	TagsRepository tagsRepository;
	@Autowired
	CommentsRepository commentsRepository;
	String searchName = "";

	@RequestMapping("/")
	public String blogs(@RequestParam(value = "start", defaultValue = "0") Integer start,
						@RequestParam(value = "limit", defaultValue = "5") Integer limit, Model model) {
//		Page<Post> posts = postsRepository.findAll(PageRequest.of(start, limit));
		Page<Post> posts = postService.getAllPost(PageRequest.of(start, limit));

		List<String> allAuthors = postService.getAllAuthor();
		List<String> allTags = tagsRepository.findAllTags();
		List<String> allDateTime = postService.getAllDateTime();

		model.addAttribute("author", allAuthors);
		model.addAttribute("allTags", allTags);
		model.addAttribute("allDateTime", allDateTime);

		model.addAttribute("currentPage", start);
		model.addAttribute("limit", limit);
		model.addAttribute("totalPages", posts.getTotalPages());
		model.addAttribute("postData", posts);
		return "blogpost";
	}

	@GetMapping("/fullarticle/{id}")
	public String fullArticle(@PathVariable("id") int postId, Model model) {
		Post postList = postService.getPostById(postId);
		Iterable<Comment> allComments = commentsRepository.findCommentsById(postId);
		model.addAttribute("id", postId);
		model.addAttribute("postList", postList);
		model.addAttribute("AllComments", allComments);
		return "fullarticle";
	}

	@GetMapping("/filterby/authors")
	public String filterByAuthorl(@RequestParam(value = "author", required = false) String[] author,
								  @RequestParam(value = "tags", required = false) String[] tags,
								  @RequestParam(value = "dateTime", required = false) String[] dateTime, Model model) {

		List<Post> findAllByAuthorArray = postsRepository.findAllByAuthorArray(author);
		List<Post> findAllByTagsArray = postsRepository.findAllByTagsArray(tags);
		List<Post> findAllByDateTimeArray = postsRepository.findAllByDateTimeArray(dateTime);
		ArrayList<Post> allPost = new ArrayList<>();
		allPost.addAll(findAllByAuthorArray);
		allPost.addAll(findAllByTagsArray);
		allPost.addAll(findAllByDateTimeArray);


		List<String> allAuthors = postService.getAllAuthor();
		List<String> allTags = tagsRepository.findAllTags();
		List<String> allDateAndTime = postService.getAllDateTime();

		model.addAttribute("allDateTime", allDateAndTime);
		model.addAttribute("author", allAuthors);
		model.addAttribute("allTags", allTags);
		model.addAttribute("postData", allPost);
		return "filterview/display";
	}

	/*
	 * @GetMapping("/filterby/tags") public String filterByTags(@RequestParam(value
	 * = "tags", required = false) String[] tags, Model model) { ArrayList<Post>
	 * allPostByTags = new ArrayList<>();
	 *
	 * List<Post> findAllByTagsArray = postsRepository.findAllByTagsArray(tags); //
	 * for (String tag : tags) { // List<Post> postByTag =
	 * postService.getPostByTagsName(tag); // allPostByTags.addAll(postByTag); // }
	 * allPostByTags.addAll(findAllByTagsArray); List<String> allAuthors =
	 * postService.getAllAuthor(); List<String> allTags =
	 * tagsRepository.findAllTags(); List<String> allDateAndTime =
	 * postService.getAllDateTime(); model.addAttribute("allDateTime",
	 * allDateAndTime); model.addAttribute("author", allAuthors);
	 * model.addAttribute("allTags", allTags); model.addAttribute("postData",
	 * allPostByTags); return "filterview/display"; }
	 *
	 * @GetMapping("/filterby/dateTime") public String
	 * filterByDateTime(@RequestParam(value = "dateTime", required = false) String[]
	 * dateTime, Model model) { ArrayList<Post> allPostByDateTime = new
	 * ArrayList<>(); for (String date : dateTime) { List<Post> postByDateTime =
	 * postService.getPostByDateTime(date);
	 * allPostByDateTime.addAll(postByDateTime); } List<String> allAuthors =
	 * postService.getAllAuthor(); List<String> allTags =
	 * tagsRepository.findAllTags(); List<String> allDateAndTime =
	 * postService.getAllDateTime(); model.addAttribute("allDateTime",
	 * allDateAndTime); model.addAttribute("author", allAuthors);
	 * model.addAttribute("allTags", allTags); model.addAttribute("postData",
	 * allPostByDateTime); return "filterview/display"; }
	 */

	@GetMapping("/sortby")
	public String sortByDateTime(Model model) {
		List<Post> sortedPosts = postService.getPostSortedByDateTime();
		List<String> allAuthors = postService.getAllAuthor();
		List<String> allTags = tagsRepository.findAllTags();
		List<String> allDateAndTime = postService.getAllDateTime();
		model.addAttribute("allDateTime", allDateAndTime);
		model.addAttribute("author", allAuthors);
		model.addAttribute("allTags", allTags);
		model.addAttribute("postData", sortedPosts);
		return "filterview/display";
	}

	@GetMapping("/search")
	public String search(@RequestParam(value = "start", defaultValue = "0") Integer start,
						 @RequestParam(value = "limit", defaultValue = "3") Integer limit, HttpServletRequest request, Model model) {
		if (start == 0 && request.getParameter("search") != null || request.getParameter("search") != null) {
			searchName = request.getParameter("search").trim();
		}
		Page<Post> postSearchByArg = postService.getPostSearchByArg(searchName, PageRequest.of(start, limit));

		List<String> allAuthors = postService.getAllAuthor();
		List<String> allTags = tagsRepository.findAllTags();
		List<String> allDateAndTime = postService.getAllDateTime();

		model.addAttribute("currentPage", start);
		model.addAttribute("limit", limit);
		model.addAttribute("totalPages", postSearchByArg.getTotalPages());
		model.addAttribute("allDateTime", allDateAndTime);
		model.addAttribute("author", allAuthors);
		model.addAttribute("allTags", allTags);
		model.addAttribute("postData", postSearchByArg);
		return "filterview/searchdisplay";
	}

	@GetMapping("/sign")
	public String signup(Model model) {

		System.out.println("hello");
		model.addAttribute("user", new User());
		return"user";
	}

	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("user") User user, BindingResult result, Model model) {

		if(result.hasErrors()) {

			System.out.println("hai ki tha");
			model.addAttribute("user",user);
			return "user";
		}
//		System.out.println(user);
		System.out.println(result);
		model.addAttribute("user",user);
		userRepository.save(user);

		return "user";
	}



}
