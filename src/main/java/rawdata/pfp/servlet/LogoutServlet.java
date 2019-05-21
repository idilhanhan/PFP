package rawdata.pfp.servlet;

import rawdata.pfp.controller.Controller;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by idilhanhan on 21.05.2019.
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private Controller controller; //not sure about this?

    public LogoutServlet(){
        try {
            controller = new Controller();
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public LogoutServlet(Controller controller){
        this.controller = controller;
    } //what can ı do with this?? check inject*/

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session=request.getSession();
        session.invalidate();

        response.sendRedirect("index.jsp"); //create a popup to say that the user has failed

    }
}
