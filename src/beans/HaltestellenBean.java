package beans;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import ejb.HaltestellenEJB;

@ApplicationScoped
@ManagedBean
public class HaltestellenBean {

	@Inject
	HaltestellenEJB haltestellenEJB;
	
}