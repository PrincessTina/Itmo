package controllers;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

import context.ContextAccess;
import ejb.UsersAccess;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet(name = "users", urlPatterns = {"/users"})
public class UsersController extends HttpServlet {

  @EJB
  private UsersAccess users;

  @EJB
  private ContextAccess context;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    BufferedReader reader = request.getReader();
    StringBuilder builder = new StringBuilder();
    String line;
    String action;
    String password;
    String login;
    String email;

    try {
      while ((line = reader.readLine()) != null) {
        builder.append(line);
      }

      JSONObject jsonObject = new JSONObject(builder.toString());

      action = jsonObject.getString("action");
      login = jsonObject.getString("login");
      password = jsonObject.getString("password");
      email = jsonObject.getString("email");
    } catch (JSONException e) {
      throw new IOException("Error parsing JSON request string");
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