package beans;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import ejb.FahrplanEJB;

@ApplicationScoped
@ManagedBean
public class FahrplanBean {

	@Inject
	FahrplanEJB fahrplanEJB;
	
}