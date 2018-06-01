package controllers.context;

import com.google.gson.Gson;
import ejb.context.ContextAccess;
import ejb.data.UsersAccess;
import entity.Users;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "context", urlPatterns = {"/context"})
public class ContextController extends HttpServlet {
  @EJB
  private ContextAccess context;

  @EJB
  private UsersAccess users;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");
    HttpSession session = request.getSession();

    if (action.isEmpty()) {
      throw new ServletException("Action is null");
    } else if (action.equals("get_user")) {
      if (session.getAttribute("user") == null) {
        context.removeUserFromContext(request);
      }

      Users user = context.getUserFromContext(request);
      int id;

      if (!user.getLogin().equals("")) {
        id = users.findUserId(user.getLogin());
      } else {
        id = -1;
      }

      user.setId(id);

      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(new Gson().toJson(user));
    } else {
      throw new ServletException("Unknown action");
    }
  }
}
