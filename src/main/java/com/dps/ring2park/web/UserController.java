package com.dps.ring2park.web;

import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.dps.ring2park.web.helpers.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dps.ring2park.domain.User;
import com.dps.ring2park.security.LoginStatus;
import com.dps.ring2park.service.UserService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Secured("ROLE_USER")
@RequestMapping("/users/*")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	protected AuthenticationManager authenticationManager;
	  
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
		
		// only allow user to be edited if it their account or logged as admin
		UserDetails userDetails = userService.getUserDetails();
		if (userDetails.getUsername().equals(username) || (userService.hasRole("ROLE_ADMIN")))
			editable = true;
		/*Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
				editable = true;
			}
		}*/
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
	public String update(@Valid User user, BindingResult bindingResult,
			Model model,
            final RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "users/edit";
		}
		userService.updateUser(user);
		String message = "Successfully updated user " + user.getUsername() + ".";
        redirectAttributes.addFlashAttribute(String.valueOf(Message.SUCCESS), message);

        return "redirect:/users/" + user.getUsername();
	}

	// add a user
	@Secured("ROLE_ADMIN")
	@RequestMapping(method = RequestMethod.PUT)
	public String add(@Valid User user, BindingResult bindingResult, Model model,
                      final RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "users/add";
		}
		userService.addUser(user);
		String message = "Successfully added user " + user.getUsername() + ".";
        redirectAttributes.addFlashAttribute(String.valueOf(Message.SUCCESS), message);

		return "redirect:/users/" + user.getUsername();
	}

	// delete a user
	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "{username}", method = RequestMethod.DELETE)
	public String delete(@PathVariable String username, Model model,
                         final RedirectAttributes redirectAttributes) {
		userService.deleteUserByUsername(username);
		String message = "Successfully deleted user " + username + ".";
        redirectAttributes.addFlashAttribute(String.valueOf(Message.SUCCESS), message);

		return "redirect:../users/";
	}

	// AJAX URIs
	
	// get users login status via AJAX
	@RequestMapping(value = "loginstatus.json", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	LoginStatus getStatus() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null && !auth.getName().equals("anonymousUser")
				&& auth.isAuthenticated()) {
			return new LoginStatus(true, auth.getName(), null);
		} else {
			return new LoginStatus(false, null, null);
		}
	}

	// login a user via AJAX
	@RequestMapping(value = "/login.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	LoginStatus login(@RequestParam("j_username") String username,
			@RequestParam("j_password") String password) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				username, password);
		try {
			Authentication auth = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(auth);
			return new LoginStatus(auth.isAuthenticated(), auth.getName(),
					"success");
		} catch (BadCredentialsException e) {
			return new LoginStatus(false, null, "invalid credentials");
		}
	}

	// logout a user via AJAX
	@RequestMapping(value = "/logout.json", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	LoginStatus logout(HttpServletRequest request, HttpServletResponse response) {
		try {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			if (auth != null) {
				new SecurityContextLogoutHandler().logout(request, response,
						auth);
				new PersistentTokenBasedRememberMeServices().logout(request,
						response, auth);
			}
			return new LoginStatus(false, null, null);
		} catch (Exception e) {
			return new LoginStatus(false, null, "unable to logout");
		}
	}
	
	// view a specific user - via AJAX
	@RequestMapping(value = "/{username}/view.json", method = RequestMethod.GET, headers="Accept=application/json")
	public @ResponseBody User view(@PathVariable String username) {
		User user = new User();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
				if (username != null) {
					try {
						user = userService.findUserByUsername(username);
					} catch (NoResultException e) {
						//
					}
				}
			}
		} catch (Exception e) {
			//
		}
		return user;
	}
	
	// edit a specific user - via AJAX
	@RequestMapping(value = "/{username}/edit.json", method = RequestMethod.POST, headers="Accept=application/json")
	public @ResponseBody User edit(@PathVariable String username, @RequestParam("name") String name,
			@RequestParam("email") String email, @RequestParam("password") String password) {
		User user = new User();
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
				if (username != null) {
					try {
						user = userService.findUserByUsername(username);
						// update user
						user.setName(name);
						user.setEmail(email);
						user.setPassword(password);
						user = userService.updateUser(user);
					} catch (NoResultException e) {
						//
					}
				}
			}
		} catch (Exception e) {
			//
		}
		return user;
	}


}