package beans;

import java.util.List;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import ejb.UserEJB;
import entities.User;

@ApplicationScoped
@ManagedBean
public class UserBean {

	@Inject
	UserEJB userEJB;
	
	public List<User> getAllUsers() {
		return userEJB.getAll();
	}
	
	public void add() {
		User user = new User();
		user.setEmail("YoutubeTest");
		userEJB.userSpeichern(user);
		
	}
}