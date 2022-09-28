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

@Controller
public class CommentController {

	@Autowired
	UserRepository userRepository;
	@Autowired
	PostsRepository postsRepository;
	@Autowired
	TagsRepository tagsRepository;
	@Autowired
	CommentsRepository commentsRepository;

	@PostMapping("/comment/{id}")
	public String addComments(@PathVariable("id") int postId, HttpServletRequest request, Model model) {
		Comment comments = new Comment();
		comments.setName(request.getParameter("name"));
		comments.setComment(request.getParameter("comment"));
		comments.setEmail(request.getParameter("email"));
		comments.setPostId(postId);
		comments.setCreateAt(LocalDateTime.now().toString());
		comments.setUpdateAt(LocalDateTime.now().toString());
		commentsRepository.save(comments);
		return "redirect:/fullarticle/" + postId;
	}

	@PostMapping("/comment/edit/{id}")
	public String editComments(@PathVariable("id") int commentId, Model model) {
		Comment comments = commentsRepository.findById(commentId).get();
		model.addAttribute("comments", comments);
		return "updateComments";
	}

	@PostMapping("/delete/post/{id}")
	public String deleteComment(@PathVariable("id") int commentId, Model model) {
		Comment comments = commentsRepository.findById(commentId).get();
		commentsRepository.deleteById(commentId);
		int postId = comments.getPostId();

		List<Comment> AllComments = commentsRepository.findCommentsById(postId);
		Post postList = postsRepository.findById(postId);

		model.addAttribute("postList", postList);
		model.addAttribute("AllComments", AllComments);
		model.addAttribute("id", postId);
		return "redirect:/fullarticle/" + postId;
	}

	@PostMapping("/update/comment/{id}")
	public String updateComments(@PathVariable("id") int commentId, HttpServletRequest request, Model model) {
		Comment comments = commentsRepository.findById(commentId).get();
		comments.setComment(request.getParameter("comments"));
		comments.setName(request.getParameter("name"));
		comments.setEmail(request.getParameter("email"));
		comments.setUpdateAt(LocalDateTime.now().toString());
		comments.setCreateAt(LocalDateTime.now().toString());
		commentsRepository.save(comments);
		Comment comments2 = commentsRepository.findById(commentId).get();

		int postId = comments2.getPostId();
		return "redirect:/fullarticle/" + postId;
	}

}
