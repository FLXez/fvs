package beans;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import ejb.AblaufEJB;

@ApplicationScoped
@ManagedBean
public class AblaufBean {

	@Inject
	AblaufEJB ablaufEJB;
	
}