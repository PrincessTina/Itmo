package jms.notifications;

import com.google.gson.Gson;
import context.ContextAccess;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

@WebServlet(name = "notifications", urlPatterns = "/notifications")
public class NotificationsController extends HttpServlet {

  @EJB
  NotificationsReceiver notificationsReceiver;

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
      Notification notification = new Notification(notificationId, NotificationTypes.NEWS);
      answer = new Gson().toJson(notification);
    } catch (TimeoutException e) {
      answer = new Gson().toJson(new Object());
    }

    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(answer);
  }
}
