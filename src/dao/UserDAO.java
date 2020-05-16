package dao;

import java.util.List;
import java.util.Objects;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import entity.User;

@Stateless
@LocalBean
public class UserDAO implements DAO<User> {
	
	@PersistenceContext
	EntityManager em;
    
	@Override
	public User get(int id) {		
		return em.find(User.class, id);		
	}

	public User getByEmail(String email) {		
		Query q = em.createNativeQuery("SELECT u.email, u.uid, u.privilegien, u.vorname, u.name FROM User u WHERE u.email = '" + email + "'", User.class);
		
		return (User) q.getSingleResult();
	}
	
	public boolean existsByEmail(String email) {
		Query q = em.createQuery("SELECT u.email FROM User u WHERE u.email = '" + email + "'");
		try {
			q.getSingleResult();
			return true;
		} catch (NoResultException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAll() {
		Query q = em.createQuery("SELECT u FROM User u");
		
		return q.getResultList();
				
	}

	@Override
	public void save(User user) {
			em.persist(user);
	}
	
	@Override
	public void update(User user, String[] parms) {
		user.setPasswort(Objects.requireNonNull(parms[0], "Passwort muss angegeben sein!"));
		
		em.merge(user);
	} 
	
	@Override
	public void delete(User user) {		
		em.remove(user);	
	}
	
	public boolean valid(User user) {
		
		Query q = em.createQuery("SELECT u.email, u.passwort FROM User u WHERE u.email = '" + user.getEmail() + "' and u.passwort = '" + user.getPasswort() +"'");
		try {
			q.getSingleResult();
			return true;
		} catch (NoResultException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
	}
}
