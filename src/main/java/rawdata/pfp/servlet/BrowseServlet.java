package rawdata.pfp.servlet;

import rawdata.pfp.controller.Controller;
import rawdata.pfp.model.ProjectIdea;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
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

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{ //TODO: check if this works with get or post

        List<ProjectIdea> projects = controller.getAll();
        request.setAttribute("projects", projects);

        ServletContext servletContext = getServletContext(); //why??
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/browse.jsp");
        requestDispatcher.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{ //TODO: check if this works with get or post

        List<ProjectIdea> projects = controller.getAll();
        request.setAttribute("projects", projects);

        ServletContext servletContext = getServletContext(); //why??
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/browse.jsp");
        requestDispatcher.forward(request, response);
    }

}
