package ejb;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entities.User;

/**
 * Session Bean implementation class UserEJB
 */
@Stateless
@LocalBean
public class UserEJB implements UserEJBLocal {

    /**
     * Default constructor. 
     */
	
	@PersistenceContext
	EntityManager em;
	
	
    public UserEJB() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void userSpeichern(User user) {
		// TODO Auto-generated method stub
		em.merge(user);
		
	}

	@Override
	public void userLoeschen(User user) {
		// TODO Auto-generated method stub
		em.remove(user);
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		Query q = em.createQuery("SELECT user FROM User user");
		return (List<User>) q.getResultList();
		
	}

}
