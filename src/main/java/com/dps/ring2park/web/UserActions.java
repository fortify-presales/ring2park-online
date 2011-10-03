package com.dps.ring2park.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.webflow.action.MultiAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import com.dps.ring2park.domain.User;
import com.dps.ring2park.service.UserService;

@Component
public class UserActions extends MultiAction {

	private UserService userService;
	
	@Autowired(required=true)
	public void setUserService(UserService userService) {
		this.userService = userService;
	} 
	
	public Event createUser(RequestContext context) {
		Event event = null;      
      
		User user = userService.createNewUser();
		context.getFlowScope().put("user", user);
       	event = new Event(this, "success");
        
        return event;
            
	}
	
	public Event validateUser(RequestContext context) {
		Event event = null;
        User user = (User)context.getFlowScope().get("user");         
      
        if (userService.userExists(user.getUsername())) {
        	context.getMessageContext().addMessage(new MessageBuilder().code("error_usernametaken").error()
                .resolvableArg(user.getUsername()).defaultText("The username is already taken").build());
        	event = new Event(this, "invalid");
        } else if (userService.emailTaken(user.getEmail())) {
        	context.getMessageContext().addMessage(new MessageBuilder().code("error_emailtaken").error()
        			.resolvableArg(user.getEmail()).defaultText("The email address is already registered").build());
        	event = new Event(this, "invalid");
        } else if (!user.getPassword().equals(user.getConfirmPassword())) {
        	context.getMessageContext().addMessage(new MessageBuilder().code("error_passwordmismatch").error()
        			.defaultText("The password and confirm password fields must match").build());
        	event = new Event(this, "invalid");
        } else {
        	event = new Event(this, "success");
        }
        
        return event;
            
	}
	
	public Event validateTerms(RequestContext context) {
		Event event = null;
        User user = (User)context.getFlowScope().get("user");   
        String contextPath = (String)context.getExternalContext().getContextPath(); 
      
        if (!user.getAcceptTerms()) {
        	context.getMessageContext().addMessage(new MessageBuilder().code("error_acceptterms").error()
                .defaultText("You need to accept the terms and conditions before registration is complete").build());
        	event = new Event(this, "reject");
        } else {
        	userService.updateUser(user);
        	userService.notifyNewUser(user, contextPath);
        	event = new Event(this, "accept");
        }
        
        return event;
            
	}
	
	public Event verifyUser(RequestContext context) {
		Event event = null;  
		User user = null;
      
		String username = (String)context.getFlowScope().get("username");
		String verificationCode = (String)context.getFlowScope().get("verificationCode");
		
		try {
			user = userService.findUserByUsername(username);
			if (user.getVerifyCode().equals(verificationCode)) {
				user.setEnabled(true);
				userService.updateUser(user);
				//authenticateUser(user);
				event = new Event(this, "success");
			} else {
	        	context.getMessageContext().addMessage(new MessageBuilder().code("error_codevalidation").error()
		                .defaultText("The validation code is incorrect for the username specified").build());
		        	event = new Event(this, "error");
			}
		}
		catch (Exception e) {
			context.getMessageContext().addMessage(new MessageBuilder().code("error_uservalidation").error()
	                .defaultText("The username specified cannot be found").build());
	        	event = new Event(this, "error");
		}
				 
		return event;
            
	}
	
}