package com.dps.ring2park.web;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.easymock.EasyMock;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.config.FlowDefinitionResourceFactory;
import org.springframework.webflow.core.collection.LocalAttributeMap;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.test.MockExternalContext;
import org.springframework.webflow.test.MockFlowBuilderContext;
import org.springframework.webflow.test.execution.AbstractXmlFlowExecutionTests;

import com.dps.ring2park.domain.Booking;
import com.dps.ring2park.domain.Location;
import com.dps.ring2park.domain.PaymentCard;
import com.dps.ring2park.domain.Role;
import com.dps.ring2park.domain.User;
import com.dps.ring2park.domain.Vehicle;
import com.dps.ring2park.service.BookingService;

@Transactional
@TransactionConfiguration(transactionManager = "transactionManager")
@ContextConfiguration(locations = { "classpath:ring2park-test.xml" })
public class BookingFlowExecutionTests extends AbstractXmlFlowExecutionTests {

	private Vehicle vehicleBean;
	private PaymentCard cardBean;
    private BookingService bookingService;
	private BookingActions bookingActions;

    protected void setUp() {
    	vehicleBean = EasyMock.createMock(Vehicle.class);
    	vehicleBean = new Vehicle();
    	cardBean = EasyMock.createMock(PaymentCard.class);
    	cardBean = new PaymentCard();
    	bookingService = EasyMock.createMock(BookingService.class);
    	bookingActions = EasyMock.createMock(BookingActions.class);
    }

    @Override
    protected FlowDefinitionResource getResource(FlowDefinitionResourceFactory resourceFactory) {
    	return resourceFactory.createFileResource("src/main/webapp/WEB-INF/views/booking/booking-flow.xml");
    }

    @Override
    protected void configureFlowBuilderContext(MockFlowBuilderContext builderContext) {
    	builderContext.registerBean("vehicleBean", vehicleBean);
    	builderContext.registerBean("cardBean", cardBean);
    	builderContext.registerBean("bookingService", bookingService);
    	builderContext.registerBean("bookingActions", bookingActions);
    }

    public void testStartBookingFlow() {
		Booking booking = createTestBooking();
		Event event = createVehicleList();
	
		EasyMock.expect(bookingService.createBooking(null, "wilma")).andReturn(booking);
		EasyMock.expect(bookingActions.setUsersCards(null)).andReturn(event);
	
		EasyMock.replay(bookingService);
	
		MutableAttributeMap input = new LocalAttributeMap();
		input.put("locationId", "1");
		MockExternalContext context = new MockExternalContext();
		context.setCurrentUser("wilma");
		startFlow(input, context);
	
		assertCurrentStateEquals("selectVehicle");
		assertResponseWrittenEquals("selectVehicle", context);
		assertTrue(getRequiredFlowAttribute("booking") instanceof Booking);
	
		EasyMock.verify(bookingService);
    }
    
    private Event createVehicleList() {
		return new Event(this, "success");
	}

	public void testSelectVehicle_Next() {
		setCurrentState("selectVehicle");
		getFlowScope().put("booking", createTestBooking());
	
		MockExternalContext context = new MockExternalContext();
		context.setEventId("next");
		resumeFlow(context);
	
		assertCurrentStateEquals("enterBookingDetails");
		assertResponseWrittenEquals("enterBookingDetails", context);
    }

    public void testEnterBookingDetails_Next() {
		setCurrentState("enterBookingDetails");
		getFlowScope().put("booking", createTestBooking());
	
		MockExternalContext context = new MockExternalContext();
		context.setEventId("next");
		resumeFlow(context);
	
		assertCurrentStateEquals("selectPaymentCard");
		assertResponseWrittenEquals("selectPaymentCard", context);
    }
    
    public void tesSelectPaymentCard_Next() {
		setCurrentState("selectPaymentCard");
		getFlowScope().put("booking", createTestBooking());
	
		MockExternalContext context = new MockExternalContext();
		context.setEventId("next");
		resumeFlow(context);
	
		assertCurrentStateEquals("reviewBooking");
		assertResponseWrittenEquals("reviewBooking", context);
    }

    public void testReviewBooking_Confirm() {
		setCurrentState("reviewBooking");
		getFlowScope().put("booking", createTestBooking());
		MockExternalContext context = new MockExternalContext();
		context.setEventId("confirm");
		resumeFlow(context);
		assertFlowExecutionEnded();
		assertFlowExecutionOutcomeEquals("bookingConfirmation");
    }
    
    public void testCancelBooking() {
		setCurrentState("reviewBooking");
		getFlowScope().put("booking", createTestBooking());
		MockExternalContext context = new MockExternalContext();
		context.setEventId("cancel");
		resumeFlow(context);
		assertFlowExecutionEnded();
		assertFlowExecutionOutcomeEquals("cancel");
    }

    private Booking createTestBooking() {
    	// Test Location
		Location location = new Location();
		location.setName("Bedrock Shopping Mall");
		location.setAddress("1 Cobblestone Way");
		location.setCity("Bedrock");
		location.setState("Nevada");
		location.setCountry("Cobblestone");
		location.setZip("BD1");
		location.setPrice(new BigDecimal("12.50"));
		
		// Test User
		User user = new User();
		user.setUsername("wilma");
		user.setName("Wilma Flintstone");
		user.setPassword("password");
		user.setEmail("wilma@flintstones.com");
		user.setMobile("0777712345678");
		
		// Test User Roles
		Role role = new Role();
		role.setAuthority("ROLE_CUSTOMER");
		Set<Role> roles = new HashSet<Role>();
		roles.add(role);
		user.setRoles(roles);
		
		// Test Vehicle
		Vehicle vehicle = new Vehicle();
		vehicle.setLicense("XYZ 999");
		vehicle.setBrand("Ford");
		vehicle.setColor("Blue");
		vehicle.setPreferred(false);
		vehicle.setUser(user);
		
		// Test Payment Card
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
		card.setUser(user);
		
		// Test Booking
		Booking booking = new Booking(location, user);
		return booking;
    }

}
