package com.dps.ring2park.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.webflow.action.MultiAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.dps.ring2park.domain.PaymentCard;
import com.dps.ring2park.domain.Vehicle;
import com.dps.ring2park.service.PaymentCardService;
import com.dps.ring2park.service.VehicleService;

@Component
public class BookingActions extends MultiAction {

	private VehicleService vehicleService;
	private PaymentCardService cardService;
	
	@Autowired(required=true)
	public void setVehicleService(VehicleService vehicleService) {
		this.vehicleService = vehicleService;
	} 
	
	@Autowired(required=true)
	public void setPaymentCardService(PaymentCardService cardService) {
		this.cardService = cardService;
	} 
	
	public Event setUsersVehicles(RequestContext context) {
		Event event = null;
		String username = (String) context.getFlowScope().get("username"); 
		if (username != null) {
			List<Vehicle> vehicleList = vehicleService.findVehicles(username);
			if (vehicleList.size() > 0) {
				context.getFlowScope().put("vehicleList", vehicleList);
				event = new Event(this, "success");
			} else {
				event = new Event(this, "empty");
			}
		}
		else {
			event = new Event(this, "error"); 
		}
		return event;
	}
	
	public Event setUsersCards(RequestContext context) {
		Event event = null;
		String username = (String) context.getFlowScope().get("username"); 
		if (username != null) {
			List<PaymentCard> cardList = cardService.findPaymentCards(username);
			if (cardList.size() > 0) {
				context.getFlowScope().put("cardList", cardList);
				event = new Event(this, "success");
			} else {
				event = new Event(this, "empty");
			}
		}
		else {
			event = new Event(this, "error"); 
		}
		return event;
	}
	
	public Event setBrands(RequestContext context) {
		context.getFlowScope().put("brandList", vehicleService.getBrands());
		return success();
	}
	
	public Event setColors(RequestContext context) {
		context.getFlowScope().put("colorList", vehicleService.getColors());
		return success();
	}
}