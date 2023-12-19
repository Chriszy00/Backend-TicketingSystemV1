package com.ts.dto;

public class JwtAuthenticationResponse {

    private String accessToken;
    private String tokenType = "Bearer";
    private String roleName;
    private Long userId;
    private String email;
    
	public JwtAuthenticationResponse(String accessToken, String roleName, Long userId, String email) {
		this.accessToken = accessToken;
		this.roleName = roleName;
		this.userId = userId;
		this.email = email;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
