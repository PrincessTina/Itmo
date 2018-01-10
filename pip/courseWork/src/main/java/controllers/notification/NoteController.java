package controllers.notification;

import com.google.gson.Gson;
import ejb.data.Controller;
import ejb.data.NoteAccess;
import ejb.notification.NotificationLogic;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "notes", urlPatterns = {"/notes"})
public class NoteController extends HttpServlet {
  @EJB
  private NotificationLogic notificationLogic;

  @EJB
  private NoteAccess notes;

  @EJB
  private Controller controller;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String type;

    try {
      JSONObject jsonObject = controller.getAnswerFromPost(request);
      type = jsonObject.getString("type");

      if (type.isEmpty()) {
        throw new ServletException("Type is null");
      } else if (type.equals("new")) {
        String link = jsonObject.getString("link");
        String description = jsonObject.getString("description");

        notificationLogic.addNewNote(link, description, request);
      } else {
        throw new ServletException("Unknown type");
      }
    } catch (JSONException e) {
      throw new ServletException("Error parsing JSON request string");
    } catch (Exception ex) {
      throw new ServletException(ex.getMessage());
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String answer = new Gson().toJson(notes.getAllNotes());

      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(answer);
    } catch (Exception ex) {
      throw new ServletException(ex.getMessage());
    }
  }
}
