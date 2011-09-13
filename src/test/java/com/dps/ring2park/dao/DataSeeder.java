package com.dps.ring2park.dao;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.dps.ring2park.domain.Booking;
import com.dps.ring2park.domain.PaymentCard;
import com.dps.ring2park.domain.Location;
import com.dps.ring2park.domain.Role;
import com.dps.ring2park.domain.User;
import com.dps.ring2park.domain.Vehicle;

/**
 * A simple data seeder for domain objects.
 * @author Kevin A. Lee
 * 
 */
public class DataSeeder {
	
	public static Booking generateBooking() {
		Booking booking = new Booking(generateLocation(), generateUser());
		booking.setVehicle(generateVehicle());
		return booking;
	}
	
	public static Location generateLocation() {
		Location location = new Location();
		location.setName("Bedrock Shopping Mall");
		location.setAddress("1 Cobblestone Way");
		location.setCity("Bedrock");
		location.setState("Nevada");
		location.setCountry("Cobblestone");
		location.setZip("BD1");
		location.setPrice(new BigDecimal("12.50"));
		return location;
	}
	
	public static Vehicle generateVehicle() {
		Vehicle vehicle = new Vehicle();
		vehicle.setLicense("XYZ 999");
		vehicle.setBrand("Ford");
		vehicle.setColor("Blue");
		vehicle.setPreferred(false);
		vehicle.setUser(generateUser());
		return vehicle;
	}
	
	public static PaymentCard generatePaymentCard() {
		PaymentCard card = new PaymentCard();
		card.setNumber("1234567812345678");
		card.setType(PaymentCard.CardType.VISA);
		card.setExpiryMonth(1);
		card.setExpiryYear(2012);
		card.setSecurityCode("1234");
		card.setAddress("301 Cobblestone Way");
		card.setCounty("Cobblestone");
		card.setPostalCode("BD1");
		card.setCountry("USA");
		card.setUser(generateUser());
		return card;
	}
	
	public static User generateUser() {
		User user = new User();
		user.setUsername("wilma");
		user.setName("Wilma Flintstone");
		user.setPassword("password");
		user.setEmail("wilma@flintstones.com");
		user.setMobile("0777712345678");
		Set<Role> roles = new HashSet<Role>();
		roles.add(generateCustomerRole());
		roles.add(generateSupervisorRole());
		user.setRoles(roles);
		return user;
	}
	
	public static Role generateCustomerRole() {
		Role role = new Role();
		role.setAuthority("ROLE_CUSTOMER");
		return role;
	}
	
	public static Role generateSupervisorRole() {
		Role role = new Role();
		role.setAuthority("ROLE_SUPERVISOR");
		return role;
	}
	
	public static Role generateGuestRole() {
		Role role = new Role();
		role.setAuthority("ROLE_GUEST");
		return role;
	}
	
}
