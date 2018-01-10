package jms.notifications;

import com.google.gson.Gson;
import ejb.context.ContextAccess;
import ejb.logic.NotificationAccess;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "b", urlPatterns = "/b")
public class Controller extends HttpServlet {

  @EJB
  NotificationsReceiver notificationsReceiver;

  @EJB
  NotificationAccess notificationAccess;

  @EJB
  ContextAccess context;

  /**
   * Клиенты дергают каждые 10 секунд, проверяя уведомления. Если они есть - отдаем json.
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String answer;

    try {
      int notificationId = notificationsReceiver.receive(NotificationTypes.NEWS, context.getUserFromContext(request).getLogin());
      Notification notification = notificationAccess.createById(notificationId, NotificationTypes.NEWS);
      answer = new Gson().toJson(notification);
    } catch (Exception e) {
      answer = new Gson().toJson((Object) null);
    }

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(answer);
  }
}