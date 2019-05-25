package rawdata.pfp.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import rawdata.pfp.controller.Controller;
import rawdata.pfp.model.ProjectIdea;
import rawdata.pfp.model.User;

/**
 * Servlet for the MyProjects page of PFP
 * Created by idilhanhan on 19.05.2019.
 */
@WebServlet("/myProjects")
public class MyProjectsServlet extends HttpServlet{

    Controller controller;

    public MyProjectsServlet(){
        try {
            controller = new Controller();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public MyProjectsServlet(Controller controller){
        this.controller = controller;
    }

    /**
     * DoGet method of the myProject page
     * Redirects the User to myProjects.jsp page with the list of the projects they are participating in
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException { //TODO: check if this works with get or post

        HttpSession session = request.getSession(true);
        User currUser = (User)session.getAttribute("currentUser");

        List<ProjectIdea> projects = controller.getMyProjects(currUser);
        request.setAttribute("projects", projects);

        ServletContext servletContext = getServletContext();
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/myProjects.jsp");
        requestDispatcher.forward(request, response);
    }

    /**
     * DoPost method of the addProject page
     * Redirects the User to myProjects.jsp page with the list of the projects they are participating in
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException { //TODO: check if this works with get or post

        HttpSession session = request.getSession(true);
        User currUser = (User)session.getAttribute("currentUser");

        List<ProjectIdea> projects = controller.getMyProjects(currUser);
        request.setAttribute("projects", projects);

        ServletContext servletContext = getServletContext(); //why??
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/myProjects.jsp");
        requestDispatcher.forward(request, response);
    }
}
