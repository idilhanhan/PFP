package rawdata.pfp.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

import rawdata.pfp.controller.Controller;
import rawdata.pfp.model.ProjectIdea;

/**
 * Servlet for the Home page of PFP
 * Created by idilhanhan on 10.05.2019.
 */
@WebServlet("/browse")
public class BrowseServlet extends HttpServlet {

    Controller controller;

    public BrowseServlet(){
        try {
            controller = new Controller();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public BrowseServlet(Controller controller){
        this.controller = controller;
    }

    /**
     * DoGet method of browse page
     * Redirects the User to browse.jsp page with the list of all of the projects in the application
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{ //TODO: check if this works with get or post

        List<ProjectIdea> projects = controller.getAll();
        request.setAttribute("projects", projects);

        ServletContext servletContext = getServletContext(); //why??
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/browse.jsp");
        requestDispatcher.forward(request, response);
    }

    /**
     * DoPost method of the addProject page
     * Redirects the User to browse.jsp page with all of the projects in the application
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{ //TODO: check if this works with get or post

        List<ProjectIdea> projects = controller.getAll();
        request.setAttribute("projects", projects);

        ServletContext servletContext = getServletContext(); //why??
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/browse.jsp");
        requestDispatcher.forward(request, response);
    }

}
