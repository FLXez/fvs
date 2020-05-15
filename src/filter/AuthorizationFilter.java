package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class AuthorizationFilter implements Filter {

	public AuthorizationFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
        // If you have any <init-param> in web.xml, then you could get them
        // here by config.getInitParameter("name") and assign it as field.
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		try {

			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			HttpSession session = request.getSession(false);

			String reqURI = request.getRequestURI();
			if (reqURI.indexOf("/login.xhtml") >= 0
					|| (session != null && session.getAttribute("privilegien").equals("Manager") && reqURI.indexOf("/manager/") >= 0)
					|| (session != null && session.getAttribute("privilegien").equals("Mitarbeiter") && reqURI.indexOf("/mitarbeiter/") >= 0)
					|| (session != null && session.getAttribute("privilegien") != null && reqURI.indexOf("/index.xhtml") >= 0)
					|| (session != null && session.getAttribute("privilegien") != null && reqURI.indexOf("/haltestellen.xhtml") >= 0)	
					|| (session != null && session.getAttribute("privilegien") != null && reqURI.indexOf("/linien.xhtml") >= 0)	
					|| (session != null && session.getAttribute("privilegien") != null && reqURI.indexOf("/register.xhtml") >= 0)	
					|| (session != null && session.getAttribute("privilegien") != null && reqURI.indexOf("/test.xhtml") >= 0)	
					|| (session != null && session.getAttribute("privilegien") != null && reqURI.indexOf("/copyme.xhtml") >= 0)	
					
					)
			{ chain.doFilter(request, response); }
			else {
				if((session != null && session.getAttribute("privilegien").equals("Manager") && reqURI.indexOf("/mitarbeiter/") >= 0) || (session != null && session.getAttribute("privilegien").equals("Mitarbeiter") && reqURI.indexOf("/manager/") >= 0))
				{
					response.sendRedirect(request.getContextPath() + "/index.xhtml"); 	
				}
				response.sendRedirect(request.getContextPath() + "/login.xhtml"); 
				}
		} catch (Exception e) { System.out.println(e.getMessage()); }
	}

	@Override
	public void destroy() {

	}
}
