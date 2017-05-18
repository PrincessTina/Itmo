package com.company;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.*;

public class CollectionController {
  /**
   * Reads saved collection from file
   *
   * @param peopleFileName - file name to be read from
   * @return collection to be worked with
   */
  public static ArrayList<Shorty> readFromFile(String peopleFileName) {
    ArrayList<Shorty> searchedCollection = new ArrayList<>();

    try {
      String fileContent = "";

      FileReader fileReader = new FileReader(peopleFileName);
      int c;
      while ((c = fileReader.read()) != -1) {
        fileContent = fileContent + (char) c;
      }

      fileContent = fileContent.replaceAll("\\s+", " ");

      // Данный блок преобразует объекты, которые хранятся в файле в кастомном формате, в формат json
      // hobby: main tech -> hobby: "main tech"
      {
        Pattern forWords = Pattern.compile("[a-z]+\\s[\\s\\w]*[a-z]*");
        Matcher matcher;
        Gson gson = new Gson();
        for (String tokens : fileContent.split("; ")) {
          matcher = forWords.matcher(tokens);
          while (matcher.find()) {
            tokens = matcher.replaceAll("\"" + matcher.group() + "\"");
          }
          tokens = "{" + tokens + "}";
          searchedCollection.add(gson.fromJson(tokens, Shorty.class));
        }
      }

      Comparator<Shorty> shortyComparator = new Shorty();
      searchedCollection.sort(shortyComparator);

    } catch (IOException e) {
      System.out.println("This file isn't exists");
      System.exit(1);
    }

    return searchedCollection;
  }

  public static void writeDefaultCollectionToFile(String peopleFileName) {
    try (PrintWriter pw = new PrintWriter(peopleFileName)) {
      for (int a = 0; a < Shorty.defaultCollection().size(); a++) {
        pw.print(Shorty.defaultCollection().get(a).toString());
        pw.print("; ");
      }

    } catch (Exception ex) {
      System.out.println("Oops: " + ex.getMessage());
    }

  }


  /**
   * Saves current conditions to file
   *
   * @param peopleFileName - backup file name
   * @param people - collection to be saved
   */
  public static void writeToFile(String peopleFileName, ArrayList<Shorty> people) {
    try (PrintWriter pw = new PrintWriter(peopleFileName)) {
      for (Shorty shorty : people) {
        pw.print(shorty.toString());
        pw.print("; ");
      }
    } catch (Exception ex) {
      System.out.println("Oops: " + ex.getMessage());
    }
  }

  /**
   * Returns last modification date, collection size and collection type.
   *
   * @param people         - collection, where info is gathered
   * @param peopleFileName - file name, from which the collection is initialized
   */
  public static String info(ArrayList<Shorty> people, String peopleFileName) {
      return "Collection type: " + people.getClass().getTypeName() + ", Update time: " +
            getLastModificationDate(peopleFileName) + ", Size: " + people.size();
  }

  /**
   * Removes first collection's element
   *
   * @param people - collection to be interacted with
   */
  public static void remove_first(ArrayList<Shorty> people) throws Exception {
    people.remove(0);
  }

  /**
   * Removes all elements, which are equals to  the element
   *
   * @param people  - collection to be interacted with
   * @param shortyJson - shorty pattern by which will delete from collection
   */
  public static int remove_all(ArrayList<Shorty> people, String shortyJson) throws Exception {
    Gson gson = new Gson();
    Shorty example_object = gson.fromJson(shortyJson, Shorty.class);
    ArrayList<Integer> indexArray = new ArrayList<>();

    // Finds people indexes
    for (int a = 0; a < people.size(); a++) {
      if (people.get(a).compare(people.get(a), example_object) == 0) {
        indexArray.add(a);
      }
    }

    // Removes persons from collection
    for (int a = 0, m = 0; a < indexArray.size(); a++, m++) {
      int c;
      c = indexArray.get(a) - m;
      people.remove(c);
    }

    return indexArray.size();
  }

  private static String getLastModificationDate(String peopleFileName) {
    Date lastModified = new Date(new File(peopleFileName).lastModified());
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    return formatter.format(lastModified);
  }
}
