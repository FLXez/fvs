package beans;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class AblaufBean
 */
@Stateless
@LocalBean
public class AblaufBean implements AblaufBeanLocal {
	
	@PersistenceContext(unitName = "fvs")
	EntityManager em;

    /**
     * Default constructor. 
     */
    public AblaufBean() {
        // TODO Auto-generated constructor stub
    }

}
