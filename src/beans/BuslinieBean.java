package beans;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import ejb.BuslinienEJB;

@ApplicationScoped
@ManagedBean
public class BuslinienBean {

	@Inject
	BuslinienEJB buslinienEJB;
	
}