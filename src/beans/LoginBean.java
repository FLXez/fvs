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
	
	UserDTO user;
	
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

	public String validate() {
		User userEntity = new User();
		userEntity.setEmail(this.user.getEmail());
		userEntity.setPasswort(this.user.getPasswort());
		
		if(!userDAO.valid(userEntity)) {	
			NotificationUtils.showMessage(false, 1, "login:password", "E-Mail / Passwort falsch", "Die E-Mail oder das Passwort ist falsch."); 
			return "login";
		}
			
		userEntity = userDAO.getByEmail(this.user.getEmail());
		
		SessionUtils.setUid(userEntity.getUid());
		SessionUtils.setEmail(userEntity.getEmail());
		SessionUtils.setPrivilegien(userEntity.getPrivilegien());
		
		NotificationUtils.showMessage(true, 0, "", SessionUtils.getEmail() + " hat sich angemeldet", "");
		user = new UserDTO(userEntity);
		

		return "index";		
	}
	
	public String invalidate() {
		NotificationUtils.showMessage(true, 0, "", SessionUtils.getEmail() + " hat sich abgemeldet.", "");
		SessionUtils.invalidate();
		this.user = new UserDTO();
		
		return "login";
	}
}