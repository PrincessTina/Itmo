package controllers.data;

import com.google.gson.Gson;
import ejb.data.Controller;
import ejb.data.NoteAccess;
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
  private NoteAccess notes;

  @EJB
  private Controller controller;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      JSONObject jsonObject = controller.getAnswerFromPost(request);

      String link = jsonObject.getString("link");
      String description = jsonObject.getString("description");

      notes.addNewNote(link, description, request);
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
