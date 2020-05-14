package dao;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import dto.UserDTO;
import entity.User;

@Stateless
@LocalBean
public class UserDAO implements DAO<User, UserDTO> {
	
	@PersistenceContext
	EntityManager em;
    
	@Override
	public Optional<User> get(int id) {		
		return Optional.ofNullable(em.find(User.class, id));		
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
		user.setEmail(Objects.requireNonNull(parms[0], "E-Mail muss angegeben sein!"));
		user.setName(Objects.requireNonNull(parms[1], "Name muss angegeben sein!"));
		user.setPasswort(Objects.requireNonNull(parms[2], "Passwort muss angegeben sein!"));
		
		em.merge(user);	
	} 
	
	@Override
	public void delete(User user) {		
		em.remove(user);		
	}
	
	public boolean login(User user) {
		
		Query q = em.createQuery("SELECT u.email, u.passwort FROM User u WHERE u.email = '" + user.getEmail() + "' and u.passwort = '" + user.getPasswort() +"'");
		try {
			q.getSingleResult();
			return true;
		} catch (NoResultException e) {
			e.printStackTrace();
			return false;
		}
		
	}
}
