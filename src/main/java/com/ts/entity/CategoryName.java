package com.ts.entity;

public enum CategoryName {
	TECHNICAL("Technical"),
	ACCOUNT_ISSUES("Account Issues"),
	BILLING_AND_PAYMENT("Billing and Payment"),
	CONTENT_ISSUES("Content Issues");
	
	private String displayName; 
	
	CategoryName( String displayName) {
		this.displayName = displayName;
	}

	public String displayName() { return displayName; }
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
}
