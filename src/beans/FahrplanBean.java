package beans;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class FarhplanBean
 */
@Stateless
@LocalBean
public class FahrplanBean implements FahrplanBeanLocal {
	
	@PersistenceContext(unitName = "fvs")
	EntityManager em;

    /**
     * Default constructor. 
     */
    public FahrplanBean() {
        // TODO Auto-generated constructor stub
    }

}
