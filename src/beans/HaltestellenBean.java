package beans;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class HaltestellenBean
 */
@Stateless
@LocalBean
public class HaltestellenBean implements HaltestellenBeanLocal {
	
	@PersistenceContext(unitName = "fvs")
	EntityManager em;

    /**
     * Default constructor. 
     */
    public HaltestellenBean() {
        // TODO Auto-generated constructor stub
    }

}
