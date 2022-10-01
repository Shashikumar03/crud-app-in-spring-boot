package com.shashi.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;



@Entity
@Table(name = "posts")
public class Post {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String title;
	private String excerpt;
	private String content;
	private String author;
	private boolean isPublished;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime publishAt;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime createAt;
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime updateAt;
	@ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST})
	private List<Tag> tags;
	
	public String getTagsToString() {
		String tag = "";
		for (Tag t : tags) {
			tag += t.getName() + " ";
		}
		return tag;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getExcerpt() {
		return excerpt;
	}
	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public LocalDateTime getPublishAt() {
		return publishAt;
	}
	public void setPublishAt(LocalDateTime publishAt) {
		this.publishAt = publishAt;
	}
	public boolean isPublished() {
		return isPublished;
	}
	public void setPublished(boolean isPublished) {
		this.isPublished = isPublished;
	}
	public LocalDateTime getCreateAt() {
		return createAt;
	}
	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}
	public LocalDateTime getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}
	public List<Tag> getTags() {
		return tags;
	}
	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}
	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", excerpt=" + excerpt + ", content=" + content + ", author="
				+ author + ", publishAt=" + publishAt + ", isPublished=" + isPublished + ", createAt=" + createAt
				+ ", updateAt=" + updateAt + ", tags=" + tags + "]";
	}
   
}
