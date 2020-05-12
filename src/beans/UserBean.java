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
	
	@PostConstruct
	public void init() {
		registerUser = new UserDTO();
	}
	
	public UserDTO getRegisterUser() {
		return this.registerUser;
	}
	
	public void setRegisterUser(UserDTO registerUser) {
		this.registerUser = registerUser;
	}
	
	
	public List<UserDTO> getAllUsers() {
		List<User> users = new ArrayList<User>();
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		users = userDAO.getAll();
		users.forEach((user) -> userDTOs.add(new UserDTO(user)));
		
		return userDTOs;
	}
	
	//Testweise, später werden die Werte anders überführt etc.
	public void add() {
		registerUser.setPrivilegien("Mitarbeiter");
		//Provisorischer PAsswordhash
		Integer passwordhash = registerUser.getPasswort().hashCode();
		registerUser.setPasswort(passwordhash.toString());
		System.out.println(registerUser.getPasswort());
		User user = new User();
		user.setEmail(registerUser.getEmail());
		user.setPasswort(registerUser.getPasswort());
		user.setVorname(registerUser.getVorname());
		user.setName(registerUser.getName());
		user.setPrivilegien(registerUser.getPrivilegien());
		
		try {
			userDAO.save(user);
			System.out.println("WORKS");
		} catch (EJBException e) {
			System.out.println("FEHLER");
			e.printStackTrace();
		}
	}
}