package beans;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import ejb.VerbindungenEJB;

@ApplicationScoped
@ManagedBean
public class VerbindungenBean {

	@Inject
	VerbindungenEJB verbindungenEJB;
	
}