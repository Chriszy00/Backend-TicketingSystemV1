package com.ts.security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ts.entity.User;

public class UserPrincipal implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1455158300651397311L;

	private Long id;
	
	private String firstName;
	private String lastName;
	
	@JsonIgnore
	private String email;
	
	@JsonIgnore
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;

	public UserPrincipal(Long id, String firstName, String lastName, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}
	
	public static UserPrincipal create(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());
		
		return new UserPrincipal(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(),
				authorities);
	}
	
	
	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}


	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}
	
	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserPrincipal that = (UserPrincipal) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id);
	}
	
	
	
	

}
