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

      if (new File(peopleFileName).length() == 0) {
        writeDefaultCollectionToFile(peopleFileName);
      }

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

    } catch (FileNotFoundException e) {
      System.out.println("This file isn't exists");
      System.exit(1);
    } catch (JsonSyntaxException e) {
      System.out.println("The contents of this file can't be collected in the collection");
      System.exit(1);
    } catch (Exception e) {
      System.out.println("Oops: " + e.getMessage());
    }

    return searchedCollection;
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
  public static void info(ArrayList<Shorty> people, String peopleFileName) {
    try {
      System.out.println("Collection type: " + people.getClass().getTypeName() + ", Update time: " +
            getLastModificationDate(peopleFileName) + ", Size: " + people.size());
    } catch (Exception ex) {
      System.out.println("Ups: " + ex.getMessage());
    }
  }

  /**
   * Prints collection content
   *
   * @param people - the collection, which will be printed
   */
  public static void load(ArrayList<Shorty> people) {
    for (Shorty shorty : people) {
      System.out.println(shorty);
    }
  }

  /**
   * Removes first collection's element
   *
   * @param people - collection to be interacted with
   */
  public static void remove_first(ArrayList<Shorty> people) throws Exception {
    people.remove(0);
    System.out.println("Successfully deleted");

    if (people.size() == 0) {
      throw new Exception("Empty collection");
    }
  }

  /**
   * Removes all elements, which are equals to  the element
   *
   * @param people  - collection to be interacted with
   * @param shortyJson - shorty pattern by which will delete from collection
   */
  public static void remove_all(ArrayList<Shorty> people, String shortyJson) throws Exception {
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

    System.out.println("Successfully deleted " + indexArray.size() + " objects");
    if (people.size() == 0) {
      throw new Exception("Empty collection");
    }
  }

  private static String getLastModificationDate(String peopleFileName) {
    Date lastModified = new Date(new File(peopleFileName).lastModified());
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    return formatter.format(lastModified);
  }

  private static void writeDefaultCollectionToFile(String peopleFileName) {
    System.out.println("\n This file's empty. Do you want to fill the file with default content? \n Write yes/no.");

    String answer = new Scanner(System.in).nextLine();
    switch (answer) {
      case "yes":
        try (PrintWriter pw = new PrintWriter(peopleFileName)) {
          File file = new File(peopleFileName);
          if (!file.exists()) {
            boolean isCreated = file.createNewFile();
            if (!isCreated) {
              throw new Exception("Can't create new file");
            }
          }

          for (int a = 0; a < Shorty.defaultCollection().size(); a++) {
            pw.print(Shorty.defaultCollection().get(a).toString());
            pw.print("; ");
          }

        } catch (Exception ex) {
          System.out.println("Oops: " + ex.getMessage());
        }

        break;
      case "no":
        System.exit(1);
    }
  }
}
