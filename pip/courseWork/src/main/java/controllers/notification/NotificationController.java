package controllers.notification;

import classes.Notification;
import classes.QueueNames;
import com.google.gson.Gson;
import ejb.context.ContextAccess;
import ejb.notification.NotificationLogic;
import ejb.notification.Producer;
import ejb.notification.Receiver;
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
import java.util.concurrent.TimeoutException;

@WebServlet(name = "notifications", urlPatterns = {"/notifications"})
public class NotificationController extends HttpServlet {
  @EJB
  private NotificationLogic notificationLogic;

  @EJB
  private Producer producer;

  @EJB
  private Receiver receiver;

  @EJB
  private ContextAccess context;

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
        producer.produce(QueueNames.NEWS, description);
      } else {
        throw new ServletException("Unknown type");
      }
    } catch (JSONException e) {
      throw new ServletException("Error parsing JSON request string");
    } catch (TimeoutException ex) {
      throw new ServletException(ex.getMessage());
    }
  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      if (receiver.receive(QueueNames.NEWS, context.getUserFromContext(request).getLogin()) != null) {
        Notification news = new Notification("new", "We got the updates");
        String answer = new Gson().toJson(news);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(answer);
      }
    } catch (Exception ex) {
      throw new ServletException(ex.getMessage());
    }
  }
}
