package controllers.data;

import com.google.gson.Gson;
import ejb.data.CharacterAccess;
import ejb.data.Controller;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "characters", urlPatterns = {"/characters"})
public class CharacterController extends HttpServlet {

  @EJB
  private CharacterAccess characters;

  @EJB
  private Controller controller;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      JSONObject jsonObject = controller.getAnswerFromPost(request);
      String description = jsonObject.getString("description");
      String type = jsonObject.getString("type");
      String name = jsonObject.getString("name");

      characters.create(name, type, description);
    } catch (JSONException e) {
      throw new ServletException("Error parsing JSON request string ");
    } catch (Exception ex) {
      throw new ServletException(ex.getMessage());
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String answer = new Gson().toJson(characters.getAll());

      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(answer);
    } catch (Exception ex) {
      throw new ServletException(ex.getMessage());
    }
  }
}
