package controllers.data;

import com.google.gson.Gson;
import ejb.data.ArtifactAccess;
import entity.Artifact;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "artifacts", urlPatterns = {"/artifacts"})
public class ArtifactController extends HttpServlet {
  @EJB
  private ArtifactAccess artifacts;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    try {
      List<Artifact> list = artifacts.getAllList();
      String answer = new Gson().toJson(list);

      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(answer);

    } catch (Exception ex) {
      throw new ServletException(ex.getMessage());
    }
  }
}
