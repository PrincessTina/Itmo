package controllers.data;

import com.google.gson.Gson;
import ejb.data.EventAccess;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "events", urlPatterns = {"/events"})
public class EventController extends HttpServlet {

  @EJB
  private EventAccess events;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String action = request.getParameter("action");
      String description = request.getParameter("description");
      String answer;

      if (action == null) {
        throw new ServletException("Action is null");
      } else if (action.equals("event")) {
        answer = new Gson().toJson(events.find(description));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(answer);
      } else if (action.equals("answer")) {
        answer = new Gson().toJson(events.getAnswer(description));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(answer);
      }
    } catch (Exception ex) {
      throw new ServletException(ex.getMessage());
    }
  }
}
