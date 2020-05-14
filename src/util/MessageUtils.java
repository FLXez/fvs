package util;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

public class MessageUtils {

	
	void showMessage(int severity, String field, String summary, String detail){

		Severity ser;
		
		switch (severity) {
		case 0:
			ser = FacesMessage.SEVERITY_INFO; 
			break;
		case 1: 
			ser = FacesMessage.SEVERITY_WARN;
			break;
		case 2: 
			ser = FacesMessage.SEVERITY_ERROR;
		case 3: 
			ser = FacesMessage.SEVERITY_FATAL;
		default:
			ser = FacesMessage.SEVERITY_INFO;
			break;
		}
		
		FacesContext.getCurrentInstance().addMessage(
				field,
				new FacesMessage(ser, summary, detail));
	}
	
}
