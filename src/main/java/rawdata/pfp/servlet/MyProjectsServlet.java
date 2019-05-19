package rawdata.pfp.servlet;

import rawdata.pfp.controller.Controller;
import rawdata.pfp.model.ProjectIdea;
import rawdata.pfp.model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
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

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException { //TODO: check if this works with get or post

        HttpSession session = request.getSession(true);
        User currUser = (User)session.getAttribute("currentUser");

        List<ProjectIdea> projects = controller.getMyProjects(currUser);
        request.setAttribute("projects", projects);

        ServletContext servletContext = getServletContext(); //why??
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/myProjects.jsp");
        requestDispatcher.forward(request, response);
    }
}
