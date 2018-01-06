package context;

import com.google.gson.Gson;
import entity.Users;

import javax.ejb.EJB;
import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "context", urlPatterns = {"/context"})
public class ContextController extends HttpServlet{
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");
    HttpSession session = request.getSession();

    if (action.isEmpty()) {
      throw new ServletException("Action is null");
    } else if (action.equals("get_user")) {
      if (session.getAttribute("user") == null) {
        Users user = new Users();
        user.setPassword("");
        user.setLogin("");
        session.setAttribute("user", user);
      }

      ArrayList<Users> users = new ArrayList<Users>();
      users.add((Users)session.getAttribute("user"));

      String answer = new Gson().toJson(users);

      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(answer);
    } else {
      throw new ServletException("Unknown action");
    }
  }
}
