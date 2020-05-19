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

	UserDTO userDTO;

	String password;
	String passwordC;

	@PostConstruct
	public void init() {
		userDTO = new UserDTO();
	}

	public UserDTO getUserDTO() {
		return this.userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
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
		
		if(!inputOkay("register")) {
			return;
		}
		
		User userEntity = new User();
		userEntity.setEmail(userDTO.getEmail());
		userEntity.setPasswort(userDTO.getPasswort());
		userEntity.setVorname(userDTO.getVorname());
		userEntity.setName(userDTO.getName());
		userEntity.setPrivilegien(userDTO.getPrivilegien());

		try {
			userDAO.save(userEntity);
			NotificationUtils.showMessage(false, 0, "register:email", "Registrierung erfolreich",
					"Der Nutzer wurde erfolgreich registriert.");
		} catch (EJBException e) {
			NotificationUtils.showMessage(false, 2, "register:email", "Unerwarteter Fehler",
					"Es ist ein unerwarteter Fehler aufgetreten.");
		}	
		
		userDTO = new UserDTO();
	}

	public void changePassword() {
		
		if(!inputOkay("changePassword")) {
			return;
		}
		
		String[] parms = {password};
				
		User userEntity = userDAO.get(SessionUtils.getUid());
		try {
			userDAO.update(userEntity, parms);
			NotificationUtils.showMessage(false, 0, "newPassword:passwordC", "Passwort ge�ndert", "Das Passwort wurde erfolgreich ge�ndert.");			
		} catch (EJBException e) {
			NotificationUtils.showMessage(false, 2, "newPassword:passwordC", "Unerwarteter Fehler",
					"Es ist ein unerwarteter Fehler aufgetreten.");
		}
		
		password = new String();
		passwordC = new String();
	}
	
	/**
	 * �berpr�ft alle Eingaben auf ihre Richtigkeit
	 * @param method Methode, in der inputOkay() aufgerufen wird
	 */
	private boolean inputOkay(String method) {
	
		switch (method) {
		case "register":
			if (userDTO.getEmail().isEmpty()) {
				NotificationUtils.showMessage(false, 1, "register:email", "E-Mail leer",
						"Bitte tragen Sie Ihre E-Mail Adresse ein.");
				return false;
			}

			if (userDTO.getPasswort().isEmpty()) {
				NotificationUtils.showMessage(false, 1, "register:email", "Passwort leer",
						"Bitte vergeben Sie ein Passwort.");
				return false;
			}

			if (userDTO.getName().isEmpty()) {
				NotificationUtils.showMessage(false, 1, "register:email", "Name leer", "Bitte tragen Sie Ihren Namen ein.");
				return false;
			}

			if (userDTO.getVorname().isEmpty()) {
				NotificationUtils.showMessage(false, 1, "register:email", "Vorname leer",
						"Bitte tragen Sie Ihren Vornamen ein.");
				return false;
			}

			if (userDTO.getPrivilegien().isEmpty()) {
				NotificationUtils.showMessage(false, 1, "register:email", "Privilegien leer",
						"Bitte vergeben Sie die Privilegien.");
				return false;
			}

			if (!userDAO.existsByEmail(userDTO.getEmail())) {
				NotificationUtils.showMessage(false, 1, "register:email", "E-Mail in Verwendung",
						"Ein Nutzer mit der E-Mail ist bereits registriert.");
				return false;
			}
			return true;
			
		case "changePassword":
			if(this.password.isEmpty() || this.passwordC.isEmpty()) { 
				NotificationUtils.showMessage(false, 1, "newPassword:passwordC", "Feld leer", "Bitte f�llen Sie beide Felder aus."); 
				return false;
			}

			if(!this.password.equals(this.passwordC)) { 
				NotificationUtils.showMessage(false, 1, "newPassword:passwordC", "Ungleiche Passw�rter", "Die Passw�rter stimmen nicht �berein.");
				return false;
			}				
			return true;	
			
		default:
			NotificationUtils.showMessage(true, 3, "", "EditUserBean - CheckInput - Ung�ltige Methode", "");			
			return false;
		}				
	}
}