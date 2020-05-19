package beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dao.UserDAO;
import dto.UserDTO;
import entity.User;
import util.NotificationUtils;
import util.SessionUtils;

@Named("login")
@ApplicationScoped
public class LoginBean {

	@Inject
	UserDAO userDAO;

	UserDTO userDTO;

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
	/**
	 * Eingaben werden validiert und Login-Prozess wird angestoßen
	 * Session wird ggf. aufgebaut
	 */
	public String validate() {
		User userEntity = new User();
		userEntity.setEmail(userDTO.getEmail());
		userEntity.setPasswort(userDTO.getPasswort());

		if (!userDAO.valid(userEntity)) {
			NotificationUtils.showMessage(false, 1, "login:password", 
					"E-Mail / Passwort falsch",
					"Die E-Mail oder das Passwort ist falsch.");
			return "login";
		}

		userEntity = userDAO.getByEmail(userDTO.getEmail());

		SessionUtils.setUid(userEntity.getUid());
		SessionUtils.setEmail(userEntity.getEmail());
		SessionUtils.setPrivilegien(userEntity.getPrivilegien());

		NotificationUtils.showMessage(true, 0, "", 
				SessionUtils.getEmail() + " hat sich angemeldet", "");
		userDTO = new UserDTO(userEntity);

		return "index";
	}

	/**
	 * Session wird zerstört
	 */
	public String invalidate() {
		NotificationUtils.showMessage(true, 0, "", SessionUtils.getEmail() + " hat sich abgemeldet.", "");
		SessionUtils.invalidate();
		this.userDTO = new UserDTO();

		return "login";
	}
}