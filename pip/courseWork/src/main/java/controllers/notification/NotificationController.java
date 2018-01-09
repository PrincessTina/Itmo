package controllers.notification;

import ejb.notification.NotificationLogic;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet(name = "notifications", urlPatterns = {"/notifications"})
public class NotificationController extends HttpServlet {
  @EJB
  private NotificationLogic notificationLogic;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    BufferedReader reader = request.getReader();
    StringBuilder builder = new StringBuilder();
    JSONObject jsonObject;
    String line;
    String type;

    try {
      while ((line = reader.readLine()) != null) {
        builder.append(line);
      }

      jsonObject = new JSONObject(builder.toString());
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
    }
  }
}
