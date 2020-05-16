package beans;

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

@Named("editUser")
@ApplicationScoped
public class EditUserBean {

	@Inject
	UserDAO userDAO;

	UserDTO user;

	String password;
	String passwordC;

	@PostConstruct
	public void init() {
		user = new UserDTO();
	}

	public UserDTO getUser() {
		return this.user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordC() {
		return passwordC;
	}

	public void setPasswordC(String passwordC) {
		this.passwordC = passwordC;
	}

	public void register() {

		if (this.user.getEmail().isEmpty()) {
			NotificationUtils.showMessage(false, 1, "register:email", "E-Mail leer",
					"Bitte tragen Sie Ihre E-Mail Adresse ein.");
			return;
		}

		if (this.user.getPasswort().isEmpty()) {
			NotificationUtils.showMessage(false, 1, "register:email", "Passwort leer",
					"Bitte vergeben Sie ein Passwort.");
			return;
		}

		if (this.user.getName().isEmpty()) {
			NotificationUtils.showMessage(false, 1, "register:email", "Name leer", "Bitte tragen Sie Ihren Namen ein.");
			return;
		}

		if (this.user.getVorname().isEmpty()) {
			NotificationUtils.showMessage(false, 1, "register:email", "Vorname leer",
					"Bitte tragen Sie Ihren Vornamen ein.");
			return;
		}

		if (this.user.getPrivilegien().isEmpty()) {
			NotificationUtils.showMessage(false, 1, "register:email", "Privilegien leer",
					"Bitte vergeben Sie die Privilegien.");
			return;
		}

		if (!userDAO.existsByEmail(this.user.getEmail())) {
			NotificationUtils.showMessage(false, 1, "register:email", "E-Mail in Verwendung",
					"Ein Nutzer mit der E-Mail ist bereits registriert.");
			return;
		}
		
		User userEntity = new User();
		userEntity.setEmail(this.user.getEmail());
		userEntity.setPasswort(this.user.getPasswort());
		userEntity.setVorname(this.user.getVorname());
		userEntity.setName(this.user.getName());
		userEntity.setPrivilegien(this.user.getPrivilegien());

		try {
			userDAO.save(userEntity);
			user = new UserDTO();
			NotificationUtils.showMessage(false, 0, "register:email", "Registrierung erfolreich",
					"Der Nutzer wurde erfolgreich registriert.");
		} catch (EJBException e) {
			NotificationUtils.showMessage(false, 2, "register:email", "Unerwarteter Fehler",
					"Es ist ein unerwarteter Fehler aufgetreten.");
		}	
	}

	public void changePassword() {
		
		if(this.password.isEmpty() || this.passwordC.isEmpty()) { 
			NotificationUtils.showMessage(false, 1, "newPassword:passwordC", "Feld leer", "Bitte füllen Sie beide Felder aus."); 
			return;
		}

		if(!this.password.equals(this.passwordC)) { 
			NotificationUtils.showMessage(false, 1, "newPassword:passwordC", "Ungleiche Passwörter", "Die Passwörter stimmen nicht überein.");
			return;
		}
				
		String[] parms = {this.password};
				
		User userEntity = userDAO.get(SessionUtils.getUid());
		
		userDAO.update(userEntity, parms);
		this.password = "";
		this.passwordC = "";
		NotificationUtils.showMessage(false, 0, "newPassword:passwordC", "Passwort geändert", "Das Passwort wurde erfolgreich geändert.");
	}
}