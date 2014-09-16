package com.dps.ring2park.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * The Role (authorities) of a User.
 * @author Kevin A. Lee
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@NotEmpty(message= "Authority must not be blank")
	@Size(min = 7, max = 40, message = "Authority must be between 7 and 40 characters")
	@Pattern(regexp="ROLE_[a-zA-Z0-9]+$", message = "Authority must begin with ROLE_ and contain alphanumeric characters only") 
    private String authority;	
	
	public Role() {
	}
	
	public Role(String authority) {
		this();
		this.authority = authority;
	}	  

    public String getAuthority() {
        return this.authority;
    }
    
    public void setAuthority(String authority) {
        this.authority = authority;
    }
	
	@Override
	public String toString() {
		return "Role(" + authority + ")";
	}

}
