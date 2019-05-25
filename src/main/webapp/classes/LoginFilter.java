package rawdata.pfp.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by idilhanhan on 23.05.2019.
 */

public class LoginFilter implements Filter{


    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);//just returns the current session

        String requestPath = HttpServletRequest.getRequestURI();

        if (needsAuthentication(requestPath) || session == null || session.getAttribute("currentUser") == null) {
            response.sendRedirect(request.getContextPath() +"/login"); // redirect to login
        } else {
            System.out.println("hey" + session.getAttribute("currentUser"));
            chain.doFilter(req, res); //user is logged in, process the request
        }
    }

    private boolean needsAuthentication(String url) {
        String[] validNonAuthenticationUrls =
                { "PFP/login", "PFP/login.jsp", "PFP/signup", "PFP/signup.jsp", "PFP/index.jsp"};
        for(String validUrl : validNonAuthenticationUrls) {
            if (url.endsWith(validUrl)) {
                return false;
            }
        }
        return true;
    }
}
