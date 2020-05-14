package util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {

	public static void invalidate() {
		HttpSession session = getSession();
		session.invalidate();
	}
	
	public static HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
	}

	public static HttpServletRequest getRequest() {
		return (HttpServletRequest) FacesContext.getCurrentInstance()
				.getExternalContext().getRequest();
	}

	
	public static void setEmail(String email) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.setAttribute("email", email);
	}
	
	public static String getEmail() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		if (session != null)
			return session.getAttribute("email").toString();
		else
			return null;
	}

	public static void setUid(int uid) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.setAttribute("uid", uid);
	}
	
	
	public static int getUid() {
		HttpSession session = getSession();
		if (session != null)
			return (int) session.getAttribute("uid");
		else
			return 0;
	}
	
	public static void setPrivilegien(String privilegien) {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.setAttribute("privilegien", privilegien);
	}
	
	
	public static String getPrivilegien() {
		HttpSession session = getSession();
		if (session != null)
			return (String) session.getAttribute("privilegien");
		else
			return null;
	}

}
