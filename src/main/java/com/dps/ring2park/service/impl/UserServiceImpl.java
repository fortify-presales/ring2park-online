package com.dps.ring2park.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.dps.ring2park.dao.RoleDao;
import com.dps.ring2park.dao.UserDao;
import com.dps.ring2park.domain.Role;
import com.dps.ring2park.domain.User;
import com.dps.ring2park.service.UserService;

/**
 * User Service
 * 
 * @author Kevin A. Lee
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	protected AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	private MailSender mailSender;

	private SimpleMailMessage messageTemplate;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setAuthenticationManager(AuthenticationManager authManager) {
		this.authenticationManager = authManager;
	}

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setMessageTemplate(SimpleMailMessage messageTemplate) {
		this.messageTemplate = messageTemplate;
	}

	public List<User> findUsers() {
		return userDao.findAll();
	}

	public User findUserByUsername(String username) {
		return userDao.findByUsername(username);
	}

	public User findUserByUsernameAndPassword(String username, String password) {
		return userDao.findByUsernameAndPassword(username, password);
	}

	public User findUserByMobile(String mobile) {
		return userDao.findByMobile(mobile);
	}

	public boolean userExists(String username) {
		return userDao.exists(username);
	}

	public boolean emailTaken(String email) {
		return userDao.emailExists(email);
	}

	public User addUser(User user) {
		String encodedPassword = passwordEncoder.encodePassword(
				user.getPassword(), null);
		user.setPassword(encodedPassword);
		// TODO: hack to make sure user has a role
		if (user.getRoles().size() == 0) {
			Set<Role> roles = new HashSet<Role>();
			roles.add(roleDao.find("ROLE_USER"));
			user.setRoles(roles);
		}
		userDao.persist(user);
		return user;
	}

	public User updateUser(User user) {
		User oldUser = userDao.find(user.getUsername());
		String oldPassword;
		oldPassword = (oldUser == null) ? "" : oldUser.getPassword();
		if (!user.getPassword().equals(oldPassword)) {
			String encodedPassword = passwordEncoder.encodePassword(
					user.getPassword(), null);
			user.setPassword(encodedPassword);
		}
		// TODO: hack to make sure user has a role
		if (user.getRoles().size() == 0) {
			Set<Role> roles = new HashSet<Role>();
			roles.add(roleDao.find("ROLE_USER"));
			user.setRoles(roles);
		}
		userDao.merge(user);
		return user;
	}

	public void deleteUser(User user) {
		userDao.remove(user);
	}

	public void deleteUserByUsername(String username) {
		User user = userDao.find(username);
		userDao.remove(user);
	}

	public User createNewUser() {
		User user = new User();
		String code = RandomStringUtils.randomAlphabetic(32);
		user.setVerifyCode(code);
		user.setEnabled(false);
		return user;
	}

	public boolean notifyNewUser(User user, String contextPath) {
		this.addUser(user);
		SimpleMailMessage message = new SimpleMailMessage(this.messageTemplate);
		message.setFrom("registration@ring2park.com");
		message.setTo(user.getEmail());
		message.setText(user.getName()
				+ ",\n\n"
				+ "Thank you for your registration at Ring2Park, please click on the link below to complete the registration process:\n\n"
				+ "http://ring2park.cloudfoundry.com/register?username="
				+ user.getUsername()
				+ "&verificationCode="
				+ user.getVerifyCode()
				+ "\n\n"
				+ "If the link does not work, then please and copy and paste the complete line into your browsers URL field.\n\n"
				+ "Ring2Park Online");
		try {
			mailSender.send(message);
			return true;
		} catch (MailException e) {
			// log the message and go on
			System.err.println(e.getMessage());
			return false;
		}
	}

	public boolean verifyNewUser(String username, String verifyCode) {
		User user = userDao.findByUsername(username);
		if (user.getVerifyCode().equals(verifyCode)) {
			authenticateUser(user);
			user.setEnabled(true);
			userDao.merge(user);
			return true;
		} else {
			System.err.println("Verification code is invalid");
			return false;
		}
	}

	public void authenticateUser(User user) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				user.getUsername(), user.getPassword());
		try {
			Authentication auth = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(auth);
		} catch (BadCredentialsException e) {
			// log the message and go on
			System.err.println(e.getMessage());
		}
	}

	public boolean hasRole(String role) {
		boolean hasRole = false;
		UserDetails userDetails = getUserDetails();
		if (userDetails != null) {
			Collection<? extends GrantedAuthority> authorities = userDetails
					.getAuthorities();
			if (isRolePresent(authorities, role)) {
				hasRole = true;
			}
		}
		return hasRole;
	}

	public UserDetails getUserDetails() {
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
		}
		return userDetails;
	}

	/**
	 * Check if a role is present in the authorities of current user
	 * 
	 * @param authorities
	 *            all authorities assigned to current user
	 * @param role
	 *            required authority
	 * @return true if role is present in list of authorities assigned to
	 *         current user, false otherwise
	 */
	private boolean isRolePresent(
			Collection<? extends GrantedAuthority> authorities, String role) {
		boolean isRolePresent = false;
		for (GrantedAuthority grantedAuthority : authorities) {
			isRolePresent = grantedAuthority.getAuthority().equals(role);
			if (isRolePresent)
				break;
		}
		return isRolePresent;
	}

}