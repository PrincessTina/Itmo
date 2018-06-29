package controllers.data;

import com.google.gson.Gson;
import ejb.data.PhraseAccess;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "phrases", urlPatterns = {"/phrases"})
public class PhraseController extends HttpServlet {

  @EJB
  private PhraseAccess phrases;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      String action = request.getParameter("action");
      String description = request.getParameter("description");
      String answer;

      if (action == null) {
        throw new ServletException("Action is null");
      } else if (action.equals("phrase")) {
        answer = new Gson().toJson(phrases.find(description));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(answer);
      } else if (action.equals("event")) {
        answer = new Gson().toJson(phrases.getEvent(description));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(answer);
      } else if(action.equals("question")) {
        answer = new Gson().toJson(phrases.getQuestion(description));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(answer);
      } else if(action.equals("answers")) {
        answer = new Gson().toJson(phrases.getAnswers(description));

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(answer);
      }
    } catch (Exception ex) {
      throw new ServletException(ex.getMessage());
    }
  }
}
