package com.dps.ring2park.web;

import java.util.Collection;
import java.util.List;

import javax.persistence.NoResultException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dps.ring2park.domain.User;
import com.dps.ring2park.service.UserService;
import com.dps.ring2park.web.extensions.FlashMap;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/users/*")
public class UserController {

	@Autowired
	private UserService userService;

	// list all of the users - form
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.GET)
	public String listForm(Model model) {
		List<User> users = userService.findUsers();
		model.addAttribute("userList", users);
		return "users/list";
	}

	// view a specific user - form
	@RequestMapping(value = "{username}", method = RequestMethod.GET)
	public String viewForm(@PathVariable String username, Model model) {
		User user;
		boolean editable = false;
		try {
			user = userService.findUserByUsername(username);
		} catch (NoResultException e) {
			return "users/invalidUser";
		}
		model.addAttribute(user);
		if (SecurityContextHolder.getContext().getAuthentication().getName().equals(username))
			editable = true;
		Collection<GrantedAuthority> authorities =  SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) { 
				editable = true;
			}
		}     
		model.addAttribute("editable", editable);
		return "users/view";
	}

	// edit a specific user - form
	@PreAuthorize("#user.username == authentication.name or hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "{username}/edit", method = RequestMethod.GET)
	public String editForm(@PathVariable String username, Model model) {
		User user;
		try {
			user = userService.findUserByUsername(username);
		} catch (NoResultException e) {
			return "users/invalidUser";
		}
		model.addAttribute(user);
		return "users/edit";
	}

	// delete a specific user - form
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "{username}/delete", method = RequestMethod.GET)
	public String deleteForm(@PathVariable String username, Model model) {
		User user;
		try {
			user = userService.findUserByUsername(username);
		} catch (NoResultException e) {
			return "users/invalidUser";
		}
		model.addAttribute(user);
		return "users/delete";
	}

	// add a new user - form
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "add", method = RequestMethod.GET)
	public String addForm(Model model) {
		model.addAttribute(new User());
		return "users/add";
	}
	
	// REST style action URIs

	// update a user
	@PreAuthorize("#user.username == authentication.name or hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	public String update(@Valid User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "users/edit";
		}
		userService.updateUser(user);
		String message = "Succesfully updated user " + user.getUsername() + ".";
		FlashMap.setSuccessMessage(message);
		return "redirect:/users/" + user.getUsername();
	}

	// add a user
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT)
	public String add(@Valid User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "users/add";
		}
		userService.addUser(user);
		String message = "Succesfully added user " + user.getUsername() + ".";
		FlashMap.setSuccessMessage(message);
		return "redirect:/users/" + user.getUsername();
	}

	// delete a user
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "{username}", method = RequestMethod.DELETE)
	public String delete(@PathVariable String username, Model model) {
		userService.deleteUserByUsername(username);
		String message = "Succesfully deleted user " + username + ".";
		FlashMap.setSuccessMessage(message);
		return "redirect:../users/";
	}

}