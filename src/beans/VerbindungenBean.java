package beans;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Session Bean implementation class VerbindungenBean
 */
@Stateless
@LocalBean
public class VerbindungenBean implements VerbindungenBeanLocal {
	
	@PersistenceContext(unitName = "fvs")
	EntityManager em;

    /**
     * Default constructor. 
     */
    public VerbindungenBean() {
        // TODO Auto-generated constructor stub
    }

}
