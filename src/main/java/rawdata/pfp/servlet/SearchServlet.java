package rawdata.pfp.servlet;

import rawdata.pfp.controller.Controller;
import rawdata.pfp.model.ProjectIdea;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by idilhanhan on 20.05.2019.
 */
@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    Controller controller;

    public SearchServlet(){
        try {
            controller = new Controller();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public SearchServlet(Controller controller){
        this.controller = controller;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException { //TODO: check if this works with get or post

        String search = (String)request.getParameter("search");

        List<ProjectIdea> projects = controller.search(search);
        request.setAttribute("projects", projects);

        ServletContext servletContext = getServletContext(); //why??
        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/searchResults.jsp");
        requestDispatcher.forward(request, response);
    }


}
