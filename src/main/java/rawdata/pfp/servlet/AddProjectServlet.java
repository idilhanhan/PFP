package rawdata.pfp.servlet;

import rawdata.pfp.controller.Controller;
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

/**
 * Created by idilhanhan on 19.05.2019.
 */
@WebServlet("/addProject")
public class AddProjectServlet extends HttpServlet {

    Controller controller;

    public AddProjectServlet(){
        try {
            controller = new Controller();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public AddProjectServlet(Controller controller){
        this.controller = controller;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String name = request.getParameter("projectName");
        String projectAbstract = request.getParameter("projectAbstract");
        int limit = Integer.parseInt(request.getParameter("memberLimit"));
        String keywords = request.getParameter("keywords");
        HttpSession session = request.getSession(true);
        User currUser = (User)session.getAttribute("currentUser");
        boolean check = controller.addProject(name, projectAbstract, currUser, limit, keywords);
        if (check){
            response.sendRedirect("myProjects");
        }
        else{
            request.setAttribute("failure", 1);
            ServletContext servletContext = getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("addProject.jsp");
            requestDispatcher.forward(request, response);
            //response.sendRedirect("addProject.jsp");
        }

    }
}
