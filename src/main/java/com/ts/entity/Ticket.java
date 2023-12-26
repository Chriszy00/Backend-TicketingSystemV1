package com.ts.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "ticket")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ticketId;
	
	private String title;
	
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category_id;
	
	@ManyToOne
	@JoinColumn(name = "priority_id")
	private Priority priority_id;
	
    @ManyToOne
    @JoinColumn(name = "user_id")
	private User creator;
	
	private String status;
	
	private String photoURL;
	
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "ticket_category", joinColumns = @JoinColumn(name = "ticket_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ticket_priority", joinColumns = @JoinColumn(name = "ticket_id"), inverseJoinColumns = @JoinColumn(name = "priority_id"))
	private Set<Priority> priority = new HashSet<>();

	public Ticket() {}

	public Ticket(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Ticket(Long ticketId, String title, String description, User creator, String status, String photoURL,
			Set<Category> categories, Set<Priority> priority) {
		this.ticketId = ticketId;
		this.title = title;
		this.description = description;
		this.creator = creator;
		this.status = status;
		this.photoURL = photoURL;
		this.categories = categories;
		this.priority = priority;
	}

	public Ticket(Long ticketId, String title, String description, User creator, String status, String photoURL) {
		this.ticketId = ticketId;
		this.title = title;
		this.description = description;
		this.creator = creator;
		this.status = status;
		this.photoURL = photoURL;
	}
	
	public Ticket(Long ticketId, String title, String description, Category category_id, Priority priority_id, User creator, String status,
			String photoURL, Set<Category> categories, Set<Priority> priority) {
		this.ticketId = ticketId;
		this.title = title;
		this.description = description;
		this.category_id = category_id;
		this.priority_id = priority_id;
		this.creator = creator;
		this.status = status;
		this.photoURL = photoURL;
		this.categories = categories;
		this.priority = priority;
	}

	
	public Ticket(Long ticketId, String title, String description, Category category_id, Priority priority_id, User creator, String status,
			String photoURL, Set<Category> categories) {
		super();
		this.ticketId = ticketId;
		this.title = title;
		this.description = description;
		this.category_id = category_id;
		this.priority_id = priority_id;
		this.creator = creator;
		this.status = status;
		this.photoURL = photoURL;
		this.categories = categories;
	}

	public Ticket(Long ticketId, String title, String description, User creator, String status, String photoURL,
				  Set<Category> categories, Set<Priority> priority, List<Comment> comments) {
		this.ticketId = ticketId;
		this.title = title;
		this.description = description;
		this.creator = creator;
		this.status = status;
		this.photoURL = photoURL;
		this.categories = categories;
		this.priority = priority;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Category category_id) {
		this.category_id = category_id;
	}
	
	public Priority getPriority_id() {
		return priority_id;
	}

	public void setPriority_id(Priority priority_id) {
		this.priority_id = priority_id;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPhotoURL() {
		return photoURL;
	}

	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Set<Priority> getPriority() {
		return priority;
	}

	public void setPriority(Set<Priority> priority) {
		this.priority = priority;
	}
	
}
