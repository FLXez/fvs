package ejb;

import java.util.List;

import javax.ejb.Local;

import entities.User;

@Local
public interface UserEJBLocal {
	
	void userSpeichern(User user);
	
	void userLoeschen(User user);
	
	List<User> getAll();

}
