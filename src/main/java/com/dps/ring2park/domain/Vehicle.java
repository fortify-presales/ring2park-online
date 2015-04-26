package com.dps.ring2park.domain;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * A Vehicle owned by a User.
 * @author Kevin A. Lee
 */
@Entity
@Table(name = "vehicle")
public class Vehicle implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public interface WithoutUserView {};
	public interface WithUserView extends WithoutUserView {};

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private User user;
	
	@Size(min = 6, message = "License  must be at least 6 characters long")
	@Pattern(regexp="[a-zA-Z0-9 ]+$", message = "License must be alphanumeric with spaces")
	private String license;

	@NotEmpty(message= "Brand must not be blank")
	private String brand;

	@NotEmpty(message= "Color must not be blank")
	private String color;

	private boolean preferred;
	
	public Vehicle() {
		this.preferred = false;
	}
	
	public Vehicle(User user) {
		this();
		this.user = user;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonView(WithoutUserView.class)
	public String getUsername() {
		return user.getUsername();
	}

	@JsonView(WithUserView.class)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}
	
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public boolean isPreferred() {
		return preferred;
	}

	public void setPreferred(boolean preferred) {
		this.preferred = preferred;
	}	
	
	@Override
	public String toString() {
		return "Vehicle(" + id + " - " + color + " " + brand + " - " + license + ")";
	}

}
