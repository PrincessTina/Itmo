package controllers;

import com.google.gson.Gson;
import ejb.ImageAccess;
import entity.Image;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "images", urlPatterns = {"/images"})
public class ImageController extends HttpServlet {

  @EJB
  private ImageAccess images;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String action = request.getParameter("action");

    if (action.isEmpty()) {
      throw new ServletException("Action is null");
    } else if (action.equals("get_sl")) {
      ArrayList<Image> images = getSliderContent();
      String answer = new Gson().toJson(images);

      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(answer);
    } else {
      throw new ServletException("Unknown action");
    }
  }

  private ArrayList<Image> getSliderContent() {
    ArrayList<Integer> listOfId = new ArrayList<Integer>();
    listOfId.add(0);
    listOfId.add(1);
    listOfId.add(2);
    listOfId.add(3);
    listOfId.add(4);

    return images.readInitImages(listOfId);
  }
}
