package beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.UserDAO;
import dto.UserDTO;
import entity.User;
import util.NotificationUtils;
import util.SessionUtils;

@Named("userBean")
@ApplicationScoped
public class UserBean {

	@Inject
	UserDAO userDAO;
	
	UserDTO registerUser;
	
	UserDTO loginUser;
	
	String newPassword;
	String newPasswordC;
	
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
	
	
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPasswordC() {
		return newPasswordC;
	}

	public void setNewPasswordC(String newPasswordC) {
		this.newPasswordC = newPasswordC;
	}

	public List<UserDTO> getAllUsers() {
		List<User> users = new ArrayList<User>();
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		users = userDAO.getAll();
		users.forEach((user) -> userDTOs.add(new UserDTO(user)));
		
		return userDTOs;
	}
	
	public void add() {
		if(!userDAO.findByEmail(this.registerUser.getEmail())) {
			this.registerUser.setPrivilegien("Mitarbeiter");			
			User user = new User();
			user.setEmail(this.registerUser.getEmail());
			user.setPasswort(this.registerUser.getPasswort());
			user.setVorname(this.registerUser.getVorname());
			user.setName(this.registerUser.getName());
			user.setPrivilegien(this.registerUser.getPrivilegien());
			
			try {
				userDAO.save(user);
				registerUser = new UserDTO();
				NotificationUtils.showMessage(false, 0, "register:email", "Registrierung erfolreich", "Der Nutzer wurde erfolgreich registriert.");				
				
			} catch (EJBException e) { NotificationUtils.showMessage(false, 2, "register:email", "Unerwarteter Fehler", "Es ist ein unerwarteter Fehler aufgetreten."); }
		} else { NotificationUtils.showMessage(false, 1, "register:email", "E-Mail in Verwendung", "Ein Nutzer mit der E-Mail ist bereits registriert."); }			
	}
	
	public void changePassword() {
		
		if(!this.newPassword.isEmpty() || !this.newPasswordC.isEmpty()) {

			if(this.newPassword.equals(this.newPasswordC)) {
				String[] parms = {this.newPassword};
				
				Optional<User> oUser = userDAO.get(SessionUtils.getUid());
				
				if(oUser.isPresent()) {
					userDAO.update(oUser.get(), parms);
					this.newPassword = "";
					this.newPasswordC = "";
					NotificationUtils.showMessage(false, 0, "newPassword:passwordC", "Passwort geändert", "Das Passwort wurde erfolgreich geändert.");
					}
				
			} else { NotificationUtils.showMessage(false, 1, "newPassword:passwordC", "Ungleiche Passwörter", "Die Passwörter stimmen nicht überein."); }						
		} else { NotificationUtils.showMessage(false, 1, "newPassword:passwordC", "Feld leer", "Bitte füllen Sie beide Felder aus."); }
	}
	
	public String login() {
		User user = new User();
		user.setEmail(this.loginUser.getEmail());
		user.setPasswort(this.loginUser.getPasswort());
		
		if(userDAO.login(user)) {			
			user = userDAO.getByEmail(this.loginUser.getEmail());
			
			SessionUtils.setUid(user.getUId());
			SessionUtils.setEmail(user.getEmail());
			SessionUtils.setPrivilegien(user.getPrivilegien());
			
			NotificationUtils.showMessage(true, 0, "", SessionUtils.getEmail() + " hat sich angemeldet", "");

			return "index";
		} else {
			NotificationUtils.showMessage(false, 1, "login:password", "E-Mail / Passwort falsch", "Die E-Mail oder das Passwort ist falsch."); 
			return "login";
		}
		
	}
	
	public String logout() {
		NotificationUtils.showMessage(true, 0, "", SessionUtils.getEmail() + " hat sich abgemeldet.", "");
		SessionUtils.invalidate();
		this.loginUser = new UserDTO();
		return "login";
	}
}