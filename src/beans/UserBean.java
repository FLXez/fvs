package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.UserDAO;
import dto.UserDTO;
import entity.User;

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
	
	public void login() {
		User user = new User();
		user.setEmail(this.loginUser.getEmail());
		user.setPasswort(this.loginUser.getPasswort());
		
		if(userDAO.login(user)) {
			System.out.println("Anmeldung erfolgreich.");
		} else {
			System.out.println("Anmeldung fehlgeschlagen.");
		}
		
	}
}