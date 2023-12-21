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

@Entity
@Table(name = "ticket")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ticketId;
	
	private String title;
	
	private String description;
	
    @ManyToOne
    @JoinColumn(name = "user_id")
	private User creator;
	
	private String status;
	
	private String photoURL;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ticket_category", joinColumns = @JoinColumn(name = "ticket_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> category = new HashSet<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ticket_priority", joinColumns = @JoinColumn(name = "ticket_id"), inverseJoinColumns = @JoinColumn(name = "priority_id"))
	private Set<Priority> priority = new HashSet<>();


	public Ticket() {}

	public Ticket(Long ticketId, String title, String description, User creator, String status, String photoURL,
			Set<Category> category, Set<Priority> priority) {
		this.ticketId = ticketId;
		this.title = title;
		this.description = description;
		this.creator = creator;
		this.status = status;
		this.photoURL = photoURL;
		this.category = category;
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

	public Set<Category> getCategory() {
		return category;
	}

	public void setCategory(Set<Category> category) {
		this.category = category;
	}

	public Set<Priority> getPriority() {
		return priority;
	}

	public void setPriority(Set<Priority> priority) {
		this.priority = priority;
	}
	
}
