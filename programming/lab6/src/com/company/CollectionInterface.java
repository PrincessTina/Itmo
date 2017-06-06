package com.company;

import com.google.gson.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import java.io.FileReader;
import java.util.*;
import java.util.regex.*;
import java.util.regex.Pattern;

class CollectionInterface {
  private static String peopleFileName = "";
  private static ArrayList<Shorty> people = new ArrayList<>();

  static void createInterface() {
    // windows's settings
    Display display = new Display();
    Shell shell = new Shell(display);
    shell.setText("Platoon Manager");
    shell.setSize(1010, 690);
    shell.setBackground(new Color(display, 255, 255, 255));
    GridLayout shellLayout = new GridLayout();
    shellLayout.numColumns = 2;
    shell.setLayout(shellLayout);

    // toolBar's settings
    ToolBar toolBar = new ToolBar(shell, SWT.HORIZONTAL);
    GridData data = new GridData();
    data.horizontalAlignment = SWT.FILL;
    data.grabExcessHorizontalSpace = true;
    toolBar.setLayoutData(data);

    ToolItem file = new ToolItem(toolBar, SWT.PUSH);
    file.setText("File");

    Menu fileMenu = new Menu(shell, SWT.POP_UP);
    Menu submenu = new Menu(fileMenu);
    MenuItem set = new MenuItem(fileMenu, SWT.PUSH);
    set.setText("Set File");
    new MenuItem(fileMenu, SWT.SEPARATOR);

    MenuItem save = new MenuItem(fileMenu, SWT.PUSH);
    save.setText("Save All");
    new MenuItem(fileMenu, SWT.SEPARATOR);

    MenuItem mode = new MenuItem(fileMenu, SWT.CASCADE);
    mode.setText("Mode");
    new MenuItem(fileMenu, SWT.SEPARATOR);

    mode.setMenu(submenu);
    MenuItem dayLight = new MenuItem(submenu, SWT.PUSH);
    dayLight.setText("DayLight");
    new MenuItem(submenu, SWT.SEPARATOR);

    MenuItem nightTime = new MenuItem(submenu, SWT.PUSH);
    nightTime.setText("NightTime");
    new MenuItem(submenu, SWT.SEPARATOR);

    MenuItem citySpirit = new MenuItem(submenu, SWT.PUSH);
    citySpirit.setText("CitySpirit");

    MenuItem exit = new MenuItem(fileMenu, SWT.PUSH);
    exit.setText("Exit");

    // File Event
    file.addListener(SWT.Selection, event -> {
      if(event.detail == SWT.NONE) {
        Rectangle bounds = file.getBounds();
        Point point = toolBar.toDisplay(bounds.x, bounds.y + bounds.height);
        fileMenu.setLocation(point);
        fileMenu.setVisible(true);
      }
    });

    ToolItem command = new ToolItem(toolBar, SWT.PUSH);
    command.setText("Commands");

    Menu commandsMenu = new Menu(shell, SWT.POP_UP);
    MenuItem info = new MenuItem(commandsMenu, SWT.PUSH);
    info.setText("Info");
    new MenuItem(commandsMenu, SWT.SEPARATOR);

    MenuItem add = new MenuItem(commandsMenu, SWT.PUSH);
    add.setText("Add");
    new MenuItem(commandsMenu, SWT.SEPARATOR);

    MenuItem remove_first = new MenuItem(commandsMenu, SWT.CASCADE);
    remove_first.setText("Remove First");
    new MenuItem(commandsMenu, SWT.SEPARATOR);

    MenuItem remove_all = new MenuItem(commandsMenu, SWT.PUSH);
    remove_all.setText("Remove All");
    new MenuItem(commandsMenu, SWT.SEPARATOR);

    MenuItem modify = new MenuItem(commandsMenu, SWT.CASCADE);
    modify.setText("Modify");

    // Command Event
    command.addListener(SWT.Selection, event -> {
      if(event.detail == SWT.NONE) {
        Rectangle bounds = command.getBounds();
        Point point = toolBar.toDisplay(bounds.x, bounds.y + bounds.height);
        commandsMenu.setLocation(point);
        commandsMenu.setVisible(true);
      }
    });

    ToolItem help = new ToolItem(toolBar, SWT.PUSH);
    help.setText("Help");

    info.setEnabled(false);
    add.setEnabled(false);
    remove_first.setEnabled(false);
    save.setEnabled(false);
    remove_all.setEnabled(false);
    modify.setEnabled(false);

    // Help Event
    help.addListener(SWT.Selection, e -> {
      int style = SWT.APPLICATION_MODAL | SWT.OK | SWT.COLOR_WIDGET_BACKGROUND |SWT.LEFT_TO_RIGHT | SWT.LEFT;
      MessageBox messageBox = new MessageBox(shell, style);

      messageBox.setText("Help");
      messageBox.setMessage("Use one of the commands: \n" +
          "\n Info: Displays information about the collection" +
          "\n Add: Adds new element in json or other format" +
          "\n Remove first: Removes the first element of the collection" +
          "\n Remove All: Removes all elements that match the specified in json format " +
          "\n Modify: Changes the element with given index " +
          "\n Set File: Chooses file that you want working with" +
          "\n Save All: Saves all changes in file" +
          "\n Mode: Sets one of the mods" +
          "\n\n*Also you can click the right mouse button or on one of the " +
          "table columns to choose variant of sorting elements in the collection");
      messageBox.open();
    });

    // Create Search Line
    final Text search = new Text(shell, SWT.BORDER);
    search.setForeground(new Color(display, 255, 255, 255));
    data = new GridData();
    data.horizontalAlignment = GridData.FILL;
    data.horizontalSpan = 1;
    data.grabExcessHorizontalSpace = true;
    search.setLayoutData(data);
    search.setBackgroundImage(new Image (display, "..\\..\\61.jpg"));

    // Create column for tree
    data = new GridData(SWT.FILL, SWT.FILL, true, true);
    Composite treeWindow = new Composite (shell, SWT.BORDER);
    treeWindow.setSize(500, SWT.NONE);
    treeWindow.setLayoutData(data);
    treeWindow.setLayout(new GridLayout());
    treeWindow.setBackgroundImage(new Image (display, "..\\..\\24.jpg"));

    // Tree of the shorty's
    Tree tree = new Tree(treeWindow, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
    createTree(shell, tree, display);

    Menu menu = new Menu(shell, SWT.POP_UP);
    MenuItem sort = new MenuItem(menu, SWT.CASCADE);
    sort.setText("Sort by");
    Menu subMenu = new Menu(menu);
    sort.setMenu(subMenu);

    MenuItem names = new MenuItem(subMenu, SWT.PUSH);
    names.setText("Names");
    new MenuItem(subMenu, SWT.SEPARATOR);
    MenuItem ages = new MenuItem(subMenu, SWT.POP_UP);
    ages.setText("Ages");
    new MenuItem(subMenu, SWT.SEPARATOR);
    MenuItem heights = new MenuItem(subMenu, SWT.PUSH);
    heights.setText("Heights");
    new MenuItem(subMenu, SWT.SEPARATOR);
    MenuItem hobbies = new MenuItem(subMenu, SWT.PUSH);
    hobbies.setText("Hobbies");
    new MenuItem(subMenu, SWT.SEPARATOR);
    MenuItem statuses = new MenuItem(subMenu, SWT.PUSH);
    statuses.setText("Statuses");
    new MenuItem(subMenu, SWT.SEPARATOR);
    tree.setMenu(menu);

    // Names Sort Event
    names.addListener(SWT.Selection, e -> sort(shell, tree, 0));

    // Ages Sort Event
    ages.addListener(SWT.Selection, e -> sort(shell, tree, 1));

    // Heights Sort Event
    heights.addListener(SWT.Selection, e -> sort(shell, tree, 2));

    // Hobbies Sort Event
    hobbies.addListener(SWT.Selection, e -> sort(shell, tree, 3));

    // Status Sort Event
    statuses.addListener(SWT.Selection, e -> sort(shell, tree, 4));

    // Create column for browser
    data = new GridData(SWT.FILL, SWT.FILL, true, true);
    Composite browserWindow = new Composite (shell, SWT.BORDER);
    browserWindow.setLayoutData(data);
    browserWindow.setLayout(new GridLayout());
    new Label(browserWindow, SWT.FILL).setText("Yandex it");

    // Browser's search in column
    final Browser browser = new Browser(browserWindow, 1);
    browser.setLayoutData(data);

    // Search event
    search.addListener(SWT.DefaultSelection, (Event e) -> {
      String url = "";
      for(String tokens: search.getText().split(" ")) {
        url = url + tokens + "%20";
      }
      browser.setUrl("https://yandex.ru/search/?text=" + url);
    });

    // Set Event
    set.addListener (SWT.Selection, e -> Display.getDefault().syncExec(() -> {
      String extraPeopleFileName;
      extraPeopleFileName = peopleFileName;
      try {
        FileDialog setFile = new FileDialog(shell, SWT.OPEN);
        setFilters(setFile);
        peopleFileName = setFile.open();
        if (peopleFileName == null) {throw new Exception("Extra return to the previous file");}

        String fileContent = "";

        FileReader fileReader = new FileReader(peopleFileName);
        int c;
        while ((c = fileReader.read()) != -1) {
          fileContent = fileContent + (char) c;
        }

        if (fileContent.matches("\\s*")) {
          int style = SWT.APPLICATION_MODAL | SWT.YES | SWT.NO;
          MessageBox question = new MessageBox(shell, style);
          question.setText("Warning");
          question.setMessage("This file's empty. Do you want to \nfill it with default content?");

          if (question.open() == 64) {
            CollectionController.writeDefaultCollectionToFile(peopleFileName);
            if (!Objects.equals(setFile.getFileName(), "")) {
              people = CollectionController.readFromFile(peopleFileName);
              modifyTree(tree);
              info.setEnabled(true);
              add.setEnabled(true);
              remove_all.setEnabled(true);
              remove_first.setEnabled(true);
              modify.setEnabled(true);
              save.setEnabled(true);
            }
          }
        } else {
          if (!Objects.equals(setFile.getFileName(), "")) {
            people = CollectionController.readFromFile(peopleFileName);
            modifyTree(tree);
            info.setEnabled(true);
            add.setEnabled(true);
            remove_all.setEnabled(true);
            remove_first.setEnabled(true);
            modify.setEnabled(true);
            save.setEnabled(true);
          }
        }

      } catch (JsonSyntaxException | IllegalStateException ex) {
        int style = SWT.APPLICATION_MODAL | SWT.OK;
        MessageBox question = new MessageBox(shell, style);
        question.setText("Error");
        question.setMessage("The content of this file \ncan't be collected in the collection");
        question.open();
      } catch (Exception ex) {
        if ((extraPeopleFileName != null) && (ex.getMessage() == "Extra return to the previous file")) {
          peopleFileName = extraPeopleFileName;
        }
        else {
          int style = SWT.APPLICATION_MODAL | SWT.OK;
          MessageBox error = new MessageBox(shell, style);
          error.setText("Warning");
          error.setMessage(ex.getMessage());
          error.open();
        }
      }
    }));

    // Save Event
    save.addListener (SWT.Selection, (Event e) -> {
      Thread thread = new Thread(() -> CollectionController.writeToFile(peopleFileName, people));
      thread.start();
    });

    // Exit event
    exit.addListener(SWT.Selection, (Event e) -> {
      Thread thread = new Thread(() -> {
        CollectionController.writeToFile(peopleFileName, people);
        System.exit(0);
      });
      thread.start();
    });

    // DayLight Event
    dayLight.addListener (SWT.Selection, e -> {
      search.setBackgroundImage(new Image (display, "..\\..\\clear.jpg"));
      search.setForeground(new Color(display, 0, 0, 0));

      tree.setBackgroundImage(new Image (display, "..\\..\\clear.jpg"));
      tree.setForeground(new Color(display, 0, 0, 0));
    });

    // NightTime Event
    nightTime.addListener (SWT.Selection, e -> {
      search.setBackgroundImage(new Image (display, "..\\..\\24.jpg"));
      search.setForeground(new Color(display, 255, 255, 255));

      tree.setBackgroundImage(new Image (display, "..\\..\\24.jpg"));
      tree.setForeground(new Color(display, 255, 255, 255));
    });

    // CitySpirit Event
    citySpirit.addListener (SWT.Selection, e -> {
      search.setBackgroundImage(new Image (display, "..\\..\\61.jpg"));
      search.setForeground(new Color(display, 0, 0, 0));

      tree.setBackgroundImage(new Image (display, "..\\..\\61.jpg"));
      tree.setForeground(new Color(display, 0, 0, 0));
    });

    // Info event
    info.addListener(SWT.Selection, e -> {
      int style = SWT.APPLICATION_MODAL | SWT.OK;
      MessageBox messageBox = new MessageBox(shell, style);
      messageBox.setText("Information");
      messageBox.setMessage(CollectionController.info(people, peopleFileName));
      messageBox.open();
    });

    // Add Event
    add.addListener(SWT.Selection, (Event e) -> addWindow(display, tree, shell));

    // Remove first event
    remove_first.addListener(SWT.Selection, (Event e) -> {
      try {
        CollectionController.remove_first(people);

        if (people.size() != 0) {
          modifyTree(tree);

          int style = SWT.APPLICATION_MODAL | SWT.OK;
          MessageBox window = new MessageBox(shell, style);
          window.setMessage("Successfully deleted");
          window.open();
        } else {
          throw new Exception("Empty collection");
        }
      } catch (Exception ex) {
        tree.removeAll();

        int style = SWT.APPLICATION_MODAL | SWT.OK;
        MessageBox window = new MessageBox(shell, style);
        window.setText("Warning");
        window.setMessage("Collection is already empty");
        window.open();
      }
    });

    // Remove All Event
    remove_all.addListener(SWT.Selection, (Event e) -> removeWindow(display, tree));

    // Modify Event
    modify.addListener(SWT.Selection, (Event e) -> modifyWindow(display, tree));

    // Names Sort Event
    names.addListener(SWT.Selection, (Event e) -> {
      for(TreeColumn column: tree.getColumns()) {
        if(Objects.equals(column.getText(), "Name")) {
          tree.setSortColumn(column);
        }
      }
    });

    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
      {
        display.sleep();
      }
    }
    display.dispose();
  }

  private static void setFilters (FileDialog dialog) {
    String[] FILTERS = {"Файлы Excel (*.csv)", "*.csv"};
    String[] fileName = new String[FILTERS.length];
    String[] extension  = new String[FILTERS.length];
    fileName[0] = FILTERS[0];
    extension[0] = FILTERS[1];
    dialog.setFilterNames(fileName);
    dialog.setFilterExtensions(extension);
  }

  private static void addWindow (Display display, Tree tree, Shell mainShell) {
    Shell shell = new Shell(display);
    shell.setText("Add element");
    shell.setSize(500, 570);
    GridLayout shellLayout = new GridLayout();
    shellLayout.numColumns = 2;
    shell.setLayout(shellLayout);

    Composite scalePlace = new Composite(shell, SWT.BORDER);
    scalePlace.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
    scalePlace.setLayout(new GridLayout(3, false));

    // Values
    Label values = new Label(scalePlace, SWT.NULL);
    values.setText("Values");
    GridData gridDataDialog = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
    values.setLayoutData(gridDataDialog);

    // Scale
    Scale scale = new Scale(scalePlace, SWT.HORIZONTAL | SWT.BORDER);
    scale.setMinimum(0);
    scale.setIncrement(1);
    scale.setPageIncrement(1);
    scale.setMaximum(1);
    scale.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));

    // Json
    Label json = new Label(scalePlace, SWT.NULL);
    json.setText("Json");
    json.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, false, false));

    // Column for values
    Composite valuesGroup = new Composite(shell, SWT.BORDER);
    valuesGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    valuesGroup.setLayout(new GridLayout());

    new Label(valuesGroup, SWT.NULL).setText("Name");
    Text spaceName = new Text(valuesGroup, SWT.BORDER);
    new Label(valuesGroup, SWT.NULL).setText("Age");
    Text spaceAge = new Text(valuesGroup, SWT.BORDER);
    new Label(valuesGroup, SWT.NULL).setText("Height");
    Text spaceHeight = new Text(valuesGroup, SWT.BORDER);
    new Label(valuesGroup, SWT.NULL).setText("Hobby");
    Text spaceHobby = new Text(valuesGroup, SWT.BORDER);
    new Label(valuesGroup, SWT.NULL).setText("Status");
    Combo status = new Combo(valuesGroup, SWT.READ_ONLY);
    String statusOptions[] = {"married", "single", "all_is_complicated", "have_a_girlfriend", "idle"};
    status.setItems(statusOptions);

    //Text.MULTI Json
    Composite jsonGroup = new Composite(shell, SWT.BORDER);
    jsonGroup.setLayout(new GridLayout());
    jsonGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

    Text spaceJson = new Text(jsonGroup, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER | SWT.H_SCROLL);
    spaceJson.setEnabled(false);
    gridDataDialog = new GridData(SWT.FILL, SWT.FILL, true, true);
    spaceJson.setLayoutData(gridDataDialog);

    // Info
    Label labelInfo = new Label(shell, SWT.LEFT);
    labelInfo.setLayoutData(new GridData(40, SWT.DEFAULT));

    //Button to add_element an object
    Button add_element = new Button(shell, SWT.PUSH);
    add_element.setText("Add element");
    add_element.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));

    // Event
    scale.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        int value = scale.getMinimum() + scale.getSelection();
        labelInfo.setText("" + value);
      }
    });

    //Listener for Scale
    scale.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent selectionEvent) {
        if (scale.getSelection() < 1) {
          spaceAge.setEnabled(true);
          spaceHeight.setEnabled(true);
          spaceName.setEnabled(true);
          spaceHobby.setEnabled(true);
          status.setEnabled(true);
          spaceJson.setEnabled(false);
        } else {
          spaceAge.setEnabled(false);
          spaceHeight.setEnabled(false);
          spaceName.setEnabled(false);
          spaceHobby.setEnabled(false);
          status.setEnabled(false);
          spaceJson.setEnabled(true);
        }
      }
    });

    shell.open();

    // Add element event
    add_element.addListener(SWT.Selection, (Event e) -> {
      if (spaceJson.getEnabled()) {
        java.util.regex.Pattern removeAllRegex = java.util.regex.Pattern.compile("\\s*(\\{(.+\\s*)*)(\\{.+}\\s*})");
        Matcher matcher = removeAllRegex.matcher(spaceJson.getText());

        if (matcher.matches()) {
          // Removes extra brackets
          String shortyJson, group3;
          group3 = matcher.group(3).trim().substring(1, matcher.group(3).length() - 1);
          shortyJson = matcher.group(1) + group3;
          Gson gson = new Gson();
          try {
            Shorty shorty = gson.fromJson(shortyJson, Shorty.class);
            if (shorty.age < 0) {
              throw new Exception("Age can't be negative");
            }

            if (shorty.height <=0 ) {
              throw new Exception("Height can only be positive");
            }
            people.add(shorty);
            Comparator<Shorty> shortyComparator = new Shorty();
            people.sort(shortyComparator);

            modifyTree(tree);
            int style = SWT.APPLICATION_MODAL | SWT.OK;
            MessageBox window = new MessageBox(mainShell, style);
            window.setText("");
            window.setMessage("Successfully added");
            window.open();
          } catch (Exception ex) {
            int style = SWT.APPLICATION_MODAL | SWT.OK;
            MessageBox error = new MessageBox(mainShell, style);
            error.setText("Error");
            error.setMessage(ex.getMessage());
            error.open();
          }
        }
        } else {
          Status meaning;
          switch (status.getText()) {
            case "married":
              meaning = Status.married;
              break;
            case "have_a_girlfriend":
              meaning = Status.have_a_girlfriend;
              break;
            case "idle":
              meaning = Status.idle;
              break;
            case "single":
              meaning = Status.single;
              break;
            default:
              meaning = Status.all_is_complicated;
          }
          try {
            if ((spaceName.getText().matches("\\s*")) || (spaceAge.getText().matches("\\s*")) ||
                  (spaceHeight.getText().matches("\\s*")) || (spaceHobby.getText().matches("\\s*"))) {
              throw new Exception("Complete the empty fields");
            } else if ((Integer.parseInt(spaceAge.getText())) < 0) {
              throw new Exception("Age can't be negative");
            } else if ((Double.parseDouble(spaceHeight.getText())) <= 0) {
              throw new Exception("Height can only be positive");
            }
            Shorty shorty = new Shorty(spaceName.getText(), Integer.parseInt(spaceAge.getText()),
                  Double.parseDouble(spaceHeight.getText()), spaceHobby.getText(), meaning);
            people.add(shorty);
            Comparator<Shorty> shortyComparator = new Shorty();
            people.sort(shortyComparator);

            modifyTree(tree);
            int style = SWT.APPLICATION_MODAL | SWT.OK;
            MessageBox window = new MessageBox(mainShell, style);
            window.setText("");
            window.setMessage("Successfully added");
            window.open();
          } catch (Exception ex) {
            int style = SWT.APPLICATION_MODAL | SWT.OK;
            MessageBox error = new MessageBox(mainShell, style);
            error.setText("Warning");
            error.setMessage(ex.getMessage());
            error.open();
          }
        }
    });
  }

  private static void createTree(Shell shell, Tree tree, Display display) {
    tree.setBackgroundImage(new Image (display, "..\\..\\61.jpg"));
    tree.setForeground(new Color(display, 0, 0, 0));

    GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
    tree.setHeaderVisible(true);
    TreeColumn column1 = new TreeColumn(tree, SWT.CENTER | SWT.PUSH);
    column1.setText("Name");
    column1.setWidth(100);
    TreeColumn column2 = new TreeColumn(tree, SWT.CENTER | SWT.PUSH);
    column2.setText("Age");
    column2.setWidth(50);
    tree.setSortColumn(column2);
    TreeColumn column3 = new TreeColumn(tree, SWT.CENTER);
    column3.setText("Height");
    column3.setWidth(70);
    TreeColumn column4 = new TreeColumn(tree, SWT.CENTER);
    column4.setText("Hobby");
    column4.setWidth(160);
    TreeColumn column5 = new TreeColumn(tree, SWT.CENTER);
    column5.setText("Status");
    column5.setWidth(130);
    tree.setLayoutData(data);

    // Names Sorting
    column1.addListener(SWT.Selection, e -> sort(shell, tree, 0));

    // Ages Sorting
    column2.addListener(SWT.Selection, e -> sort(shell, tree, 1));

    // Heights Sorting
    column3.addListener(SWT.Selection, e -> sort(shell, tree, 2));

    // Hobbies Sorting
    column4.addListener(SWT.Selection, e -> sort(shell, tree, 3));

    // Statuses Sorting
    column5.addListener(SWT.Selection, e -> sort(shell, tree, 4));
}

  private static void modifyTree (Tree tree) {
    tree.removeAll();
    tree.setRedraw(false);
    TreeItem item;
    TreeItem subItem;
    Shorty shorty;

    item = new TreeItem(tree, SWT.NONE);
    item.setText(new String[] {people.get(0).name, Integer.toString(people.get(0).age),
        Double.toString(people.get(0).height), people.get(0).hobby, people.get(0).status.toString()});

    for (int i = 1; i <people.size(); i++) {
      shorty = people.get(i);

      if(shorty.compare(shorty, people.get(people.indexOf(shorty) - 1)) == 0) {
        subItem = new TreeItem(item, SWT.NONE);
        subItem.setText(new String[] {shorty.name, Integer.toString(shorty.age), Double.toString(shorty.height),
            shorty.hobby, shorty.status.toString()});
      }
      else {
        item = new TreeItem(tree, SWT.NONE);
        item.setText(new String[] {shorty.name, Integer.toString(shorty.age), Double.toString(shorty.height),
            shorty.hobby, shorty.status.toString()});
      }
    }
    tree.setRedraw(true);
  }

  private static void removeWindow (Display display, Tree tree) {
    Shell shell = new Shell(display);
    shell.setSize(800,150);
    shell.setText("Remove Elements");
    shell.setLayout(new GridLayout());

    Label title = new Label(shell, SWT.NONE);
    title.setText("Input object in json format");
    GridData gridDataDialog = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
    title.setLayoutData(gridDataDialog);

    Text jsonString = new Text(shell, SWT.BORDER);
    gridDataDialog = new GridData(SWT.FILL, SWT.FILL, true, true);
    jsonString.setLayoutData(gridDataDialog);

    Button remove = new Button(shell, SWT.PUSH);
    remove.setText("Remove elements");

    shell.open();

    // Remove Event
    remove.addListener(SWT.Selection, (Event e) -> {
      try {
        if (people.size() == 0) {
          throw new Exception("Collection is already empty");
        }
        java.util.regex.Pattern removeAllRegex = java.util.regex.Pattern.compile("\\s*(\\{.+)(\\{.+}\\s*})");
        Matcher matcher = removeAllRegex.matcher(jsonString.getText());
        if (matcher.matches()) {
          // Removes extra brackets
          String shortyJson, group2;
          group2 = matcher.group(2).trim().substring(1, matcher.group(2).length() - 1);
          shortyJson = matcher.group(1) + group2;

          int number = CollectionController.remove_all(people, shortyJson);
          modifyTree(tree);

          int style = SWT.APPLICATION_MODAL | SWT.OK;
          MessageBox window = new MessageBox(shell, style);
          window.setText("");
          window.setMessage("Successfully deleted " + number + " objects");
          window.open();
        }
      } catch (Exception ex) {
        int style = SWT.APPLICATION_MODAL | SWT.OK;
        MessageBox error = new MessageBox(shell, style);
        error.setText("Warning");
        error.setMessage(ex.getMessage());
        error.open();
        }
    });
  }

  private static void modifyWindow (Display display, Tree tree) {
    Shell shell = new Shell(display);
    shell.setText("Modify");
    shell.setSize(300, 570);
    shell.setLayout(new GridLayout());

    Label title = new Label(shell, SWT.NULL);
    title.setText("Input index of element that you want to \nchange and then new parameters");

    new Label(shell, SWT.NULL).setText("Index");
    Text index = new Text(shell, SWT.BORDER);
    GridData data = new GridData(SWT.FILL, SWT.NONE, true, true);
    index.setLayoutData(data);

    new Label(shell, SWT.NULL).setText("Name");
    Text spaceName = new Text(shell, SWT.BORDER);
    spaceName.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true));

    new Label(shell, SWT.NULL).setText("Age");
    Text spaceAge = new Text(shell, SWT.BORDER | SWT.FILL);
    spaceAge.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true));

    new Label(shell, SWT.NULL).setText("Height");
    Text spaceHeight = new Text(shell, SWT.BORDER | SWT.FILL);
    spaceHeight.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true));

    new Label(shell, SWT.NULL).setText("Hobby");
    Text spaceHobby = new Text(shell, SWT.BORDER | SWT.FILL);
    spaceHobby.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true));

    new Label(shell, SWT.NULL).setText("Status");
    Combo status = new Combo(shell, SWT.READ_ONLY);
    String statusOptions[] = {"married", "single", "all_is_complicated", "have_a_girlfriend", "idle"};
    status.setItems(statusOptions);

    Button modify = new Button(shell, SWT.PUSH | SWT.CENTER);
    modify.setText("Modify it!");
    modify.setLayoutData(new GridData(SWT.CENTER, SWT.NULL, false, false));

    shell.open();

    // Modify Event
    modify.addListener(SWT.Selection, (Event e) -> {
      Status meaning;
      switch (status.getText()) {
        case "married":
          meaning = Status.married;
          break;
        case "have_a_girlfriend":
          meaning = Status.have_a_girlfriend;
          break;
        case "idle":
          meaning = Status.idle;
          break;
        case "single":
          meaning = Status.single;
          break;
        default:
          meaning = Status.all_is_complicated;
      }
      String expected_name, expected_hobby;
      int expected_age;
      double expected_height;
      try {
        if (index.getText().matches("\\s*")) {
          throw new Exception("Input index");
        } else if ((Integer.parseInt(index.getText()) < 0) || (Integer.parseInt(index.getText())
              >= people.size())) {
          throw new Exception("Index out of collection size");
        }

        if (spaceName.getText().matches("\\s*")) {
          expected_name = people.get(Integer.parseInt(index.getText())).name;
        } else {
          expected_name = spaceName.getText();
        }

        if (spaceAge.getText().matches("\\s*")) {
            expected_age = people.get(Integer.parseInt(index.getText())).age;
        } else if ((Integer.parseInt(spaceAge.getText())) < 0) {
            throw new Exception("Age can't be negative");
        } else {
            expected_age = Integer.parseInt(spaceAge.getText());
        }

        if (spaceHeight.getText().matches("\\s*")) {
          expected_height = people.get(Integer.parseInt(index.getText())).height;
        } else if ((Double.parseDouble(spaceHeight.getText())) <= 0) {
          throw new Exception("Height can only be positive");
        } else {
          expected_height = Double.parseDouble(spaceHeight.getText());
        }

        if (spaceHobby.getText().matches("\\s*")) {
          expected_hobby = people.get(Integer.parseInt(index.getText())).hobby;
        } else {
          expected_hobby = spaceHobby.getText();
        }

        Shorty shorty = new Shorty(expected_name, expected_age, expected_height, expected_hobby, meaning);
        people.set(Integer.parseInt(index.getText()), shorty);
        Comparator<Shorty> shortyComparator = new Shorty();
        people.sort(shortyComparator);

        modifyTree(tree);

        int style = SWT.APPLICATION_MODAL | SWT.OK;
        MessageBox window = new MessageBox(shell, style);
        window.setText("");
        window.setMessage("Successfully modified");
        window.open();
      } catch (Exception ex) {
        int style = SWT.APPLICATION_MODAL | SWT.NO | SWT.YES | SWT.ICON_QUESTION;
        MessageBox error = new MessageBox(shell, style);
        error.setText("ПРОБЛЕМА");
        error.setMessage("");
        if (error.open() == 64) {
          MessageBox error2 = new MessageBox(shell, SWT.OK);
          error2.setText("Решение");
          error2.setMessage(ex.getMessage());
          error2.open();
        }
      }
    });
  }

  private static void sort(Shell shell, Tree tree, int sortedColumn) {
    try {
      if (people.size() == 0) {
        throw new Exception("Collection is already empty");
      }
      ArrayList<String> sortedFields = new ArrayList<>();
      LinkedList<Integer> indexes = new LinkedList<>();
      int i = 0, numberOfItem, numberOfSubItem, j = 0, index = 0;
      double newDigit, oldDigit;
      String hashCode, newString, oldString;
      Pattern itemPattern = Pattern.compile("(.*)&(\\d+)");
      Pattern subItemPattern = Pattern.compile("(.*)&(\\d+)&(\\d+)");
      Matcher matcher;

      // Creating array of searched mean + number of item [+ number of subitem]
      for (TreeItem item : tree.getItems()) {
        newString = item.getText(sortedColumn);
        if (sortedFields.size() == 0) {
          hashCode = newString + "&" + i;
          sortedFields.add(hashCode);
        } else {
          for (String oldHashCode: sortedFields) {
            matcher = subItemPattern.matcher(oldHashCode);
            if (matcher.matches()) {
              if ((sortedColumn != 1) && (sortedColumn != 2)) {
                oldString = matcher.group(1);
                if (newString.compareToIgnoreCase(oldString) < 0) {
                  hashCode = newString + "&" + i;
                  sortedFields.add(sortedFields.indexOf(oldHashCode), hashCode);
                  indexes.clear();
                  break;
                } else {
                  index = sortedFields.indexOf(oldHashCode);
                  indexes.add(index);
                }
              } else {
                oldDigit = Double.parseDouble(matcher.group(1));
                newDigit = Double.parseDouble(newString);
                if (newDigit < oldDigit) {
                  hashCode = newDigit + "&" + i;
                  sortedFields.add(sortedFields.indexOf(oldHashCode), hashCode);
                  indexes.clear();
                  break;
                } else {
                  index = sortedFields.indexOf(oldHashCode);
                  indexes.add(index);
                }
              }
            } else {
              matcher = itemPattern.matcher(oldHashCode);
              if (matcher.matches()) {
                if ((sortedColumn != 1) && (sortedColumn != 2)) {
                  oldString = matcher.group(1);
                  if (newString.compareToIgnoreCase(oldString) < 0) {
                    hashCode = newString + "&" + i;
                    sortedFields.add(sortedFields.indexOf(oldHashCode), hashCode);
                    indexes.clear();
                    break;
                  } else {
                    index = sortedFields.indexOf(oldHashCode);
                    indexes.add(index);
                  }
                } else {
                  oldDigit = Double.parseDouble(matcher.group(1));
                  newDigit = Double.parseDouble(newString);
                  if (newDigit < oldDigit) {
                    hashCode = newDigit + "&" + i;
                    sortedFields.add(sortedFields.indexOf(oldHashCode), hashCode);
                    indexes.clear();
                    break;
                  } else {
                    index = sortedFields.indexOf(oldHashCode);
                    indexes.add(index);
                  }
                }
              }
            }
          }
          if (indexes.size() != 0) {
            hashCode = newString + "&" + i;
            sortedFields.add(indexes.getLast() + 1, hashCode);
          }
          indexes.clear();
        }
        for (TreeItem subItem : item.getItems()) {
          newString = subItem.getText(sortedColumn);
          if (sortedFields.size() == 0) {
            hashCode = newString + "&" + i + "&" + j;
            sortedFields.add(hashCode);
          } else {
            for (String oldHashCode: sortedFields) {
              matcher = subItemPattern.matcher(oldHashCode);
              if (matcher.matches()) {
                if ((sortedColumn != 1) && (sortedColumn != 2)) {
                  oldString = matcher.group(1);
                  if (newString.compareToIgnoreCase(oldString) < 0) {
                    hashCode = newString + "&" + i + "&" + j;
                    sortedFields.add(sortedFields.indexOf(oldHashCode), hashCode);
                    indexes.clear();
                    break;
                  } else {
                    index = sortedFields.indexOf(oldHashCode);
                    indexes.add(index);
                  }
                } else {
                  oldDigit = Double.parseDouble(matcher.group(1));
                  newDigit = Double.parseDouble(newString);
                  if (newDigit < oldDigit) {
                    hashCode = newDigit + "&" + i + "&" + j;
                    sortedFields.add(sortedFields.indexOf(oldHashCode), hashCode);
                    indexes.clear();
                    break;
                  } else {
                    index = sortedFields.indexOf(oldHashCode);
                    indexes.add(index);
                  }
                }
              } else {
                matcher = itemPattern.matcher(oldHashCode);
                if (matcher.matches()) {
                  if ((sortedColumn != 1) && (sortedColumn != 2)) {
                    oldString = matcher.group(1);
                    if (newString.compareToIgnoreCase(oldString) < 0) {
                      hashCode = newString + "&" + i + "&" + j;
                      sortedFields.add(sortedFields.indexOf(oldHashCode), hashCode);
                      indexes.clear();
                      break;
                    } else {
                      index = sortedFields.indexOf(oldHashCode);
                      indexes.add(index);
                    }
                  } else {
                    oldDigit = Double.parseDouble(matcher.group(1));
                    newDigit = Double.parseDouble(newString);
                    if (newDigit < oldDigit) {
                      hashCode = newDigit + "&" + i + "&" + j;
                      sortedFields.add(sortedFields.indexOf(oldHashCode), hashCode);
                      indexes.clear();
                      break;
                    } else {
                      index = sortedFields.indexOf(oldHashCode);
                      indexes.add(index);
                    }
                  }
                }
              }
            }
            if (indexes.size() != 0) {
              hashCode = newString + "&" + i + "&" + j;
              sortedFields.add(indexes.getLast(), hashCode);
            }
          }
          j++;
          indexes.clear();
        }
        j = 0;
        i++;
      }

      people.clear();

      // Creating new people collection
      for (String object : sortedFields) {
        matcher = subItemPattern.matcher(object);
        if (matcher.matches()) {
          numberOfItem = Integer.parseInt(matcher.group(2));
          numberOfSubItem = Integer.parseInt(matcher.group(3));
          TreeItem item1 = tree.getItem(numberOfItem);
          TreeItem item = item1.getItem(numberOfSubItem);
          Status meaning;
          switch (item.getText(4)) {
            case "married":
              meaning = Status.married;
              break;
            case "have_a_girlfriend":
              meaning = Status.have_a_girlfriend;
              break;
            case "idle":
              meaning = Status.idle;
              break;
            case "single":
              meaning = Status.single;
              break;
            default:
              meaning = Status.all_is_complicated;
          }

          people.add(new Shorty(item.getText(0), Integer.parseInt(item.getText(1)), Double.parseDouble(item.getText(2)),
                item.getText(3), meaning));
        } else {
          matcher = itemPattern.matcher(object);
          if (matcher.matches()) {
            numberOfItem = Integer.parseInt(matcher.group(2));

            TreeItem item = tree.getItem(numberOfItem);
            Status meaning;
            switch (item.getText(4)) {
              case "married":
                meaning = Status.married;
                break;
              case "have_a_girlfriend":
                meaning = Status.have_a_girlfriend;
                break;
              case "idle":
                meaning = Status.idle;
                break;
              case "single":
                meaning = Status.single;
                break;
              default:
                meaning = Status.all_is_complicated;
            }

            people.add(new Shorty(item.getText(0), Integer.parseInt(item.getText(1)), Double.parseDouble(item.getText(2)),
                  item.getText(3), meaning));
          }
        }
      }
      modifyTree(tree);
    } catch (Exception ex) {
      int style = SWT.APPLICATION_MODAL | SWT.OK;
      MessageBox error = new MessageBox(shell, style);
      error.setText("Warning");
      error.setMessage(ex.getMessage());
      error.open();
    }
  }
}

