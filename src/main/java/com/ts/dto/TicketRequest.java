package com.ts.dto;

import com.ts.entity.CategoryName;

public class TicketRequest {

    private String title;
    private String description;
    private CategoryName categoryNames;

    public TicketRequest() {
    }


    public TicketRequest(String title, String description, CategoryName categoryNames) {
		this.title = title;
		this.description = description;
		this.categoryNames = categoryNames;
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

}
