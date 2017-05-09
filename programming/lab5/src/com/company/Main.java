package com.company;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
  public static void main(String[] args) {
    String peopleFileName = null;
    ArrayList<Shorty> people = null;

    try {
      peopleFileName = args[0];
      if (!peopleFileName.matches(".+\\.csv")) {
        throw new Exception("\n Incorrect filename \n Please, input filename in format *.csv");
      }

      people = CollectionController.readFromFile(peopleFileName);
      Scanner sc = new Scanner(System.in);

      help();
      while (true) {
        System.out.println("Input command:");
        String command = sc.nextLine();
        switch (command) {
          case "help":
            help();
            break;

          case "info":
            CollectionController.info(people, peopleFileName);
            break;

          case "load":
            CollectionController.load(people);
            break;

          case "remove_first":
            CollectionController.remove_first(people);
            CollectionController.writeToFile(peopleFileName, people);
            break;

          case "break":
            System.out.println("\n Bye!");
            System.exit(0);

          default:
            Pattern removeAllRegex = Pattern.compile("remove_all\\s*(\\{.+)(\\{.+}\\s*})");
            Matcher matcher = removeAllRegex.matcher(command);
            if (matcher.matches()) {
              // Removes extra brackets
              String shortyJson, group2;
              group2 = matcher.group(2).trim().substring(1, matcher.group(2).length() - 1);
              shortyJson = matcher.group(1) + group2;

              CollectionController.remove_all(people, shortyJson);
              CollectionController.writeToFile(peopleFileName, people);
            } else {
              System.out.println("\n This command isn't supported \n P.S. If you want, you can use 'help' \n");
            }
        }
      }
    } catch (IndexOutOfBoundsException ex) {
      System.out.println("Input filename, please");
    } catch (Exception ex) {
      if (ex.getMessage().equals("Empty collection")) {
        System.out.println("Collection is already empty");
        CollectionController.writeToFile(peopleFileName, people);
        System.exit(0);
      } else {
        System.out.println("Oops: " + ex.getMessage());
      }
    }
  }

  private static void help() {
    System.out.println(
        "\n Use one of the commands: " +
            "\n help " +
            "\n info            Display information about the collection" +
            "\n load            Display the contents of the collection" +
            "\n remove_first    Remove the first element of the collection" +
            "\n remove_all      {name: 'mean', age: digit, height: digit, hobby: 'mean', status: {mean} }" +
            "\n                 Remove all elements that match the specified" +
            "\n break           Exit the program \n"
    );
  }
}