package com.javaenthu.blog.app.blogapplication.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseIdEntity implements UserDetails{
	
	
	@NotNull
	@Size(min=3, max = 15, message = "First Name must be of atleast 3 and max 15 characters!!")
	@Column(name ="first_name", nullable = false)
	private String firstName;
	
	@Column(name ="middle_name")
	private String middleName;
	
	@NotNull
	@Size(min = 3, max = 15, message = "Last Name Must be of Atleast 3 and max 15 Characters!!")
	@Column(name ="last_name")
	private String lastName;
	
	@NotNull
	@Email(message = "Email Address is not valid !!")
	@NotEmpty(message = "Email is required")
	@Column(name = "email_id", unique = true, nullable = false)
	private String email;
	
	@NotNull
	@NotEmpty
	@Size(min =3, max =10, message = "min 3 and max 10 characters !!")
	@Column(name ="password")
	private String password;
	
	@NotNull
	@Column(name = "profession", nullable = false)
	private String profession;
	
	@Column(name ="description")
	private String description;
	
	@OneToMany(mappedBy = "user" , cascade = CascadeType.ALL)
	private List<Post> post= new ArrayList<Post>();
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", 
	   joinColumns = @JoinColumn(name ="user", referencedColumnName = "id"),
	   inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id"))
	private Set<Role> role = new HashSet<Role>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorties = this.getRole().stream()
		                      .map(role -> new SimpleGrantedAuthority(role.getName()))
		                      .collect(Collectors.toList());
		return authorties;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
