package com.dps.ring2park.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

/**
 * A Parking Booking made by a User.
 * @author Kevin A. Lee
 */
@Entity
public class Booking implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private User user;

	@ManyToOne
	private Location location;

	@ManyToOne
	private Vehicle vehicle;
	
	@ManyToOne
	private PaymentCard card;

    @JsonSerialize(using=DateSerializer.class)
	@DateTimeFormat(pattern = "MM-dd-yyyy")
	private Date startDate;

    @JsonSerialize(using=DateSerializer.class)
	@DateTimeFormat(pattern = "MM-dd-yyyy")
	private Date endDate;

	@Transient
	private String securityNumber;

	private boolean confirmation;

	private boolean reminder;
	
	public String formattedId;

	public Booking() {
		Calendar calendar = Calendar.getInstance();
		setStartDate(calendar.getTime());
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		setEndDate(calendar.getTime());
	}

	public Booking(Location location, User user) {
		this();
		this.location = location;
		this.user = user;
	}

	public BigDecimal getSMSCost() {
		// TODO: allow configuration
		return new BigDecimal(0.60);
	}

	@NumberFormat(style = Style.CURRENCY)
	@Transient
	public BigDecimal getTotal() {
		if (confirmation && reminder)
			return (BigDecimal) location.getPrice()
					.multiply(new BigDecimal(getDays())).add(getSMSCost())
					.add(getSMSCost());
		else if (confirmation || reminder)
			return (BigDecimal) location.getPrice()
					.multiply(new BigDecimal(getDays())).add(getSMSCost());
		else
			return (BigDecimal) location.getPrice().multiply(
					new BigDecimal(getDays()));
	}

	@Transient
	public int getDays() {
		if (startDate == null || endDate == null) {
			return 0;
		} else {
			return (int) (endDate.getTime() - startDate.getTime()) / 1000 / 60
					/ 60 / 24;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
		this.formattedId = String.format("PC%08d", this.id);
	}
	
	public String getFormattedId() {
		return String.format("PC%08d", this.id);
	}

	@Basic
	@Temporal(TemporalType.DATE)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date datetime) {
		this.startDate = datetime;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	public PaymentCard getCard() {
		return card;
	}

	public void setCard(PaymentCard card) {
		this.card = card;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Basic
	@Temporal(TemporalType.DATE)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Transient
	public String getDescription() {
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		return location == null ? null : location.getName() + ", "
				+ df.format(getStartDate()) + " to " + df.format(getEndDate());
	}

	public boolean isConfirmation() {
		return confirmation;
	}

	public void setConfirmation(boolean confirmation) {
		this.confirmation = confirmation;
	}

	public boolean isReminder() {
		return reminder;
	}

	public void setReminder(boolean reminder) {
		this.reminder = reminder;
	}
	
	public String getSecurityNumber() {
		return securityNumber;
	}

	public void setSecurityNumber(String securityNumber) {
		this.securityNumber = securityNumber;
	}

	// TODO replace with JSR 303
	public void validateEnterBookingDetails(ValidationContext context) {
		MessageContext messages = context.getMessageContext();
		if (startDate.before(today())) {
			messages.addMessage(new MessageBuilder().error()
					.source("startDate").code("booking.startDate.beforeToday")
					.build());
		} else if (endDate.before(startDate)) {
			messages.addMessage(new MessageBuilder().error().source("endDate")
					.code("booking.endDate.beforeStartDate").build());
		}
	}

	private Date today() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}

	@Override
	public String toString() {
		return "Booking(" + user + "," + location + "," + vehicle + " "
				+ confirmation + ")";
	}

}
