package com.shashi.controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.shashi.entities.Comment;
import com.shashi.entities.Post;
import com.shashi.repository.CommentsRepository;
import com.shashi.repository.PostsRepository;
import com.shashi.repository.TagsRepository;
import com.shashi.repository.UserRepository;
import com.shashi.service.PostService;
import com.shashi.serviceImplementation.postServiceImp;

@Controller
public class CommentController {

	@Autowired
	UserRepository userRepository;
//	@Autowired
//	PostsRepository postsRepository;
	
	@Autowired
	PostService postService;
	@Autowired
	TagsRepository tagRepository;
	@Autowired
	CommentsRepository commentRepository;

	@PostMapping("/comment/{id}")
	public String addComments(@PathVariable("id") int postId, HttpServletRequest request, Model model) {
		Comment comments = new Comment();
		comments.setName(request.getParameter("name"));
		comments.setComment(request.getParameter("comment"));
		comments.setEmail(request.getParameter("email"));
		comments.setPostId(postId);
		comments.setCreateAt(LocalDateTime.now().toString());
		comments.setUpdateAt(LocalDateTime.now().toString());
		commentRepository.save(comments);
		return "redirect:/fullarticle/" + postId;
	}

	@PostMapping("/comment/edit/{id}")
	public String editComments(@PathVariable("id") int commentId, Model model) {
		Comment comments = commentRepository.findById(commentId).get();
		model.addAttribute("comments", comments);
		return "updateComments";
	}

	@PostMapping("/delete/post/{id}")
	public String deleteComment(@PathVariable("id") int commentId, Model model) {
		Comment comments = commentRepository.findById(commentId).get();
		commentRepository.deleteById(commentId);
		int postId = comments.getPostId();

		List<Comment> AllComments = commentRepository.findCommentsById(postId);
		Post postList = postService.getPostById(postId);

		model.addAttribute("postList", postList);
		model.addAttribute("AllComments", AllComments);
		model.addAttribute("id", postId);
		return "redirect:/fullarticle/" + postId;
	}

	@PostMapping("/update/comment/{id}")
	public String updateComments(@PathVariable("id") int commentId, HttpServletRequest request, Model model) {
		Comment comments = commentRepository.findById(commentId).get();
		comments.setComment(request.getParameter("comments"));
		comments.setName(request.getParameter("name"));
		comments.setEmail(request.getParameter("email"));
		comments.setUpdateAt(LocalDateTime.now().toString());
		comments.setCreateAt(LocalDateTime.now().toString());
		commentRepository.save(comments);
		Comment comments2 = commentRepository.findById(commentId).get();

		int postId = comments2.getPostId();
		return "redirect:/fullarticle/" + postId;
	}

}
