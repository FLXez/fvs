package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import dao.UserDAO;
import dto.UserDTO;
import entity.User;
import util.SessionUtils;

@Named("userBean")
@ApplicationScoped
public class UserBean {

	@Inject
	UserDAO userDAO;
	
	UserDTO registerUser;
	
	UserDTO loginUser;
	
	@PostConstruct
	public void init() {
		registerUser = new UserDTO();
		loginUser = new UserDTO();
	}
	
	public UserDTO getRegisterUser() {
		return this.registerUser;
	}
	
	public void setRegisterUser(UserDTO registerUser) {
		this.registerUser = registerUser;
	}

	public UserDTO getLoginUser() {
		return this.loginUser;
	}
	
	public void setLoginUser(UserDTO loginUser) {
		this.loginUser = loginUser;
	}
	
	
	public List<UserDTO> getAllUsers() {
		List<User> users = new ArrayList<User>();
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		users = userDAO.getAll();
		users.forEach((user) -> userDTOs.add(new UserDTO(user)));
		
		return userDTOs;
	}
	
	public void add() {
		this.registerUser.setPrivilegien("Mitarbeiter");
		User user = new User();
		user.setEmail(this.registerUser.getEmail());
		user.setPasswort(this.registerUser.getPasswort());
		user.setVorname(this.registerUser.getVorname());
		user.setName(this.registerUser.getName());
		user.setPrivilegien(this.registerUser.getPrivilegien());
		
		try {
			userDAO.save(user);
			System.out.println("User mit ID:" + user.getUId());
		} catch (EJBException e) {
			System.out.println("User konnte nicht hinzugefuegt werden.");
			e.printStackTrace();
		}
	}
	
	public String login() {
		User user = new User();
		user.setEmail(this.loginUser.getEmail());
		user.setPasswort(this.loginUser.getPasswort());
		
		if(userDAO.login(user)) {
			System.out.println("Anmeldung erfolgreich.");
			HttpSession session = SessionUtils.getSession();
			session.setAttribute("email", user.getEmail());
			session.setAttribute("privilegien", user.getPrivilegien());
			return "logout";
		} else {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Incorrect Username and Passowrd",
							"Please enter correct username and Password"));
			System.out.println("Anmeldung fehlgeschlagen.");
			return "login";
		}
		
	}
	
	// Logout event, Weiterleitung auf "login.xhtml"
	public String logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		loginUser = new UserDTO();
		System.out.println("Abmeldung erfolgreich.");
		return "login";
	}
}