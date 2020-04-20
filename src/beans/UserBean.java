package beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.UserDAO;
import dto.UserDTO;
import entity.User;

@Named("userBean")
@ApplicationScoped
@ManagedBean
public class UserBean {

	@Inject
	UserDAO userDAO;
	
	public List<UserDTO> getAllUsers() {
		List<User> users = new ArrayList<User>();
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		users = userDAO.getAll();
		users.forEach((user) -> userDTOs.add(new UserDTO(user)));
		
		return userDTOs;
	}
	
	//Testweise, später werden die Werte anders überführt etc.
	public void add() {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("YoutubeTest");
		userDTO.setPasswort("LEL");
		userDTO.setVorname("vorname");
		userDTO.setName("name");
		userDTO.setPrivilegien("Mitarbeiter");
		User user = new User();
		user.setEmail(userDTO.getEmail());
		user.setPasswort(userDTO.getPasswort());
		user.setVorname(userDTO.getVorname());
		user.setName(userDTO.getName());
		user.setPrivilegien(userDTO.getPrivilegien());
		userDAO.save(user);
		
	}
}