package beans;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class BuslinienBean
 */
@Stateless
@LocalBean
public class BuslinienBean implements BuslinienBeanLocal {
	
	@PersistenceContext(unitName = "fvs")
	EntityManager em;

    /**
     * Default constructor. 
     */
    public BuslinienBean() {
        // TODO Auto-generated constructor stub
    }

}
