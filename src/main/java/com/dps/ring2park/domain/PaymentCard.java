package com.dps.ring2park.domain;

import java.io.Serializable;

import javax.persistence.*;

/**
 * A Payment Card belonging to a User.
 * @author Kevin A. Lee
 */
@Entity
@Table(name = "payment_card")
public class PaymentCard implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static enum CardType { 
		VISA ("Visa"), 
		MASTERCARD ("MasterCard"), 
		AMEX ("America Express"), 
		VISADEBIT ("Visa Debit"), 
		MAESTRO ("Maestro");
		
		private String type;

	    private CardType(String type) {
	        this.type = type;
	    }
	    
		@Override
	    public String toString() {
	        return type;
	    }
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private User user;

	@Enumerated(EnumType.STRING)
	private CardType type;

	private String number;

	private int expiryMonth;

	private int expiryYear;

	@Transient
	private String securityCode;

	private String address;
	
	private String state;
	
	private String county;
	
	private String postalCode;
	
	private String country;
	
	private boolean preferred;

	public PaymentCard() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CardType getType() {
		return type;
	}

	public void setType(CardType type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public int getExpiryMonth() {
		return expiryMonth;
	}

	public void setExpiryMonth(int expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	public int getExpiryYear() {
		return expiryYear;
	}

	public void setExpiryYear(int expiryYear) {
		this.expiryYear = expiryYear;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCounty() {
		return country;
	}

	public void setCounty(String country) {
		this.country = county;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	public boolean isPreferred() {
		return preferred;
	}

	public void setPreferred(boolean preferred) {
		this.preferred = preferred;
	}

	@Override
	public String toString() {
		return "Card(" + user + "," + type + "," + number + ")";
	}

}
