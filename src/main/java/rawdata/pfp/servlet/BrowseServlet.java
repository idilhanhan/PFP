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

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{ //TODO: check if this works with get or post

        System.out.println("DO GET METHOD OF BROWSE");
        List<ProjectIdea> projects = controller.getAll();

        req.setAttribute("projects", projects);

        ServletContext servletContext = getServletContext(); //why??
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/browse.jsp");
        requestDispatcher.forward(req, res);
    }
}
