package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
	public UserDTO get(int id) {		
		return new UserDTO(em.find(User.class, id));		
	}

	public UserDTO getByEmail(String email) {		
		Query q = em.createNativeQuery("SELECT u.email, u.uid, u.privilegien, u.vorname, u.name FROM User u WHERE u.email = '" + email + "'", User.class);
		
		return new UserDTO((User) q.getSingleResult());
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
	public List<UserDTO> getAll() {
		Query q = em.createQuery("SELECT u FROM User u");
		List<User> userEntites = new ArrayList<User>();
		List<UserDTO> userDTOs = new ArrayList<UserDTO>();
		
		userEntites = q.getResultList();
		userEntites.forEach((userEntity) -> userDTOs.add(new UserDTO(userEntity)));
		
		return userDTOs;				
	}

	@Override
	public void save(UserDTO userDTO) {
			em.persist(userDTO.toEntity());
	}
	
	@Override
	public void update(UserDTO userDTO, String[] parms) {
		userDTO.setPasswort(Objects.requireNonNull(parms[0], "Passwort muss angegeben sein!"));
		
		em.merge(userDTO.toEntity());
	} 
	
	@Override
	public void delete(UserDTO userDTO) {		
		em.remove(userDTO.toEntity());	
	}
	
	public boolean valid(UserDTO userDTO) {		
		Query q = em.createQuery("SELECT u.email, u.passwort FROM User u WHERE u.email = '" + userDTO.getEmail() + "' and u.passwort = '" + userDTO.getPasswort() +"'");
		try {
			q.getSingleResult();
			return true;
		} catch (NoResultException e) {
			System.out.println(e.getMessage());
			return false;
		}
		
	}	
}
