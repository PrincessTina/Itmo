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
import java.util.Arrays;

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
      ArrayList<Image> imageList = getContent(new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3, 4)));
      String answer = new Gson().toJson(imageList);

      response.setContentType("application/json");
      response.setCharacterEncoding("UTF-8");
      response.getWriter().write(answer);
    } else {
      throw new ServletException("Unknown action");
    }
  }

  private ArrayList<Image> getContent(ArrayList<Integer> listOfId) {
    return images.readInitImages(listOfId);
  }
}
