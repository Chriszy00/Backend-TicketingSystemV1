package com.ts.dto;

import com.ts.entity.CategoryName;
import com.ts.entity.PriorityName;

public class TicketRequest {

    private String title;
    private String description;
    private CategoryName categoryNames;
    private PriorityName priorityNames;

    public TicketRequest() {
    }


    public TicketRequest(String title, String description, CategoryName categoryNames, PriorityName priorityNames) {
		this.title = title;
		this.description = description;
		this.categoryNames = categoryNames;
		this.priorityNames = priorityNames;
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

	public CategoryName getCategoryNames() {
		return categoryNames;
	}


	public void setCategoryNames(CategoryName categoryNames) {
		this.categoryNames = categoryNames;
	}


	public PriorityName getPriorityNames() {
		return priorityNames;
	}


	public void setPriorityNames(PriorityName priorityNames) {
		this.priorityNames = priorityNames;
	}
	
}
