package context;

import com.google.gson.Gson;
import entity.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

@WebServlet(name = "context", urlPatterns = {"/context"})
public class ContextController extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");
    HttpSession session = request.getSession();

    if (action.isEmpty()) {
      throw new ServletException("Action is null");
    } else if (action.equals("get_user")) {
      if (session.getAttribute("user") == null) {
        removeUserFromContext(request);
      }

      ArrayList<Users> users = new ArrayList<Users>(Collections.singletonList(getUserFromContext(request)));

      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(new Gson().toJson(users));
    } else if (action.equals("get_name")) {
      if (session.getAttribute("name") == null) {
        removeNameFromContext(request);
      }

      ArrayList<String> name = new ArrayList<String>(Collections.singletonList(getNameFromContext(request)));

      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(new Gson().toJson(name));
    } else {
      throw new ServletException("Unknown action");
    }
  }

  private Users getUserFromContext(HttpServletRequest request) {
    return (Users) request.getSession().getAttribute("user");
  }

  public void addUserInContext(String login, String password, HttpServletRequest request) {
    Users user = new Users();

    user.setLogin(login);
    user.setPassword(password);

    request.getSession().setAttribute("user", user);
  }

  public void addNameInContext(String name, HttpServletRequest request) {
    request.getSession().setAttribute("name", name);
  }

  private String getNameFromContext(HttpServletRequest request) {
    return (String) request.getSession().getAttribute("name");
  }

  public void removeNameFromContext(HttpServletRequest request) {
    request.getSession().setAttribute("name", "");
  }

  public void removeUserFromContext(HttpServletRequest request) {
    Users user = new Users();

    user.setLogin("");
    user.setPassword("");

    request.getSession().setAttribute("user", user);
  }
}
