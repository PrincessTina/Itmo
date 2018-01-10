package controllers.data;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import ejb.context.ContextAccess;
import ejb.data.Controller;
import ejb.data.UsersAccess;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet(name = "users", urlPatterns = {"/users"})
public class UsersController extends HttpServlet {

  @EJB
  private UsersAccess users;

  @EJB
  private ContextAccess context;

  @EJB
  private Controller controller;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action;
    String password;
    String login;
    String email;

    try {
      JSONObject jsonObject = controller.getAnswerFromPost(request);

      action = jsonObject.getString("action");
      login = jsonObject.getString("login");
      password = jsonObject.getString("password");
      email = jsonObject.getString("email");
    } catch (JSONException e) {
      throw new ServletException("Error parsing JSON request string");
    }

    if (action.isEmpty()) {
      throw new ServletException("Action is null");
    } else if (action.equals("reg")) {
      users.addNewUser(login, password, email);
      context.addUserInContext(login, password, request);
    } else if (action.equals("check")) {
      users.checkUser(login, password);
      context.addUserInContext(login, password, request);
    } else if (action.equals("exit")) {
      context.removeUserFromContext(request);
    } else {
      throw new ServletException("Unknown action");
    }
  }
}