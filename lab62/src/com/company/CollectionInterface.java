package com.company;

import com.google.gson.Gson;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.*;

import static com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table;

public class CollectionInterface {
  private static String peopleFileName = "";
  private static ArrayList<Shorty> people = new ArrayList<>();

  public static void createInterface() {
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

    ToolItem file = new ToolItem(toolBar, SWT.PUSH | SWT.COLOR_GREEN);
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
    mode.setMenu(submenu);
    MenuItem dayLight = new MenuItem(submenu, SWT.PUSH);
    dayLight.setText("DayLight");
    new MenuItem(submenu, SWT.SEPARATOR);

    MenuItem nightTime = new MenuItem(submenu, SWT.PUSH);
    nightTime.setText("NightTime");
    new MenuItem(submenu, SWT.SEPARATOR);

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
      int style = SWT.APPLICATION_MODAL | SWT.OK;
      MessageBox messageBox = new MessageBox(shell, style);
      messageBox.setText("Help");
      messageBox.setMessage("Use one of the commands: \n" +
            "\n Info: Display information about the collection" +
            "\n Add: Add new element in json or other format" +
            "\n Remove first: Remove the first element of the collection" +
            "\n Remove All: Remove all elements that match the specified" +
            "\n Load: Display the contents of the collection" +
            "\n Set File: Choose file that you want working with" +
            "\n Save All: Save all changes in file" +
            "\n Mode: Set one of the mods");
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
    search.setBackgroundImage(new Image (display, "..\\24.jpg"));

    // Create column for tree
    data = new GridData(SWT.FILL, SWT.FILL, true, true);
    Composite treeWindow = new Composite (shell, SWT.BORDER);
    treeWindow.setSize(500, SWT.NONE);
    treeWindow.setLayoutData(data);
    treeWindow.setLayout(new GridLayout());
    treeWindow.setBackgroundImage(new Image (display, "..\\24.jpg"));

    // Tree of the shorty's
    Tree tree = new Tree(treeWindow, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
    createTree(tree);

    Menu menu = new Menu(shell, SWT.POP_UP);
    MenuItem names = new MenuItem(menu, SWT.PUSH);
    names.setText("Names Sort");
    new MenuItem(menu, SWT.SEPARATOR);
    MenuItem ages = new MenuItem(menu, SWT.POP_UP);
    ages.setText("Ages Sort");
    new MenuItem(menu, SWT.SEPARATOR);
    MenuItem heights = new MenuItem(menu, SWT.PUSH);
    heights.setText("Heights Sort");
    new MenuItem(menu, SWT.SEPARATOR);
    MenuItem hobbies = new MenuItem(menu, SWT.PUSH);
    hobbies.setText("Hobbies Sort");
    new MenuItem(menu, SWT.SEPARATOR);
    MenuItem statuses = new MenuItem(menu, SWT.PUSH);
    statuses.setText("Statuses Sort");
    new MenuItem(menu, SWT.SEPARATOR);
    tree.setMenu(menu);

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
    search.addListener(SWT.DefaultSelection, e -> {
      String url = "";
      for(String tokens: search.getText().split(" ")) {
        url = url + tokens + "%20";
      }
      browser.setUrl("https://yandex.ru/search/?text=" + url);
    });

    // Set Event
    set.addListener (SWT.Selection, e -> {
      FileDialog setFile = new FileDialog(shell, SWT.OPEN);
      setFilters(setFile);
      peopleFileName = setFile.open();

      if(!Objects.equals(setFile.getFileName(), "")) {
        info.setEnabled(true);
        add.setEnabled(true);
        remove_all.setEnabled(true);
        remove_first.setEnabled(true);
        modify.setEnabled(true);
        save.setEnabled(true);
        people = CollectionController.readFromFile(peopleFileName);
        modifyTree(tree);
      }
    });

    // Save Event
    save.addListener (SWT.Selection, e -> CollectionController.writeToFile(peopleFileName, people));

    // DayLight Event
    dayLight.addListener (SWT.Selection, e -> {
      treeWindow.setBackgroundImage(new Image (display, "..\\clear.jpg"));
      search.setBackgroundImage(new Image (display, "..\\clear.jpg"));
      search.setForeground(new Color(display, 0, 0, 0));
    });

    // NightTime Event
    nightTime.addListener (SWT.Selection, e -> {
      treeWindow.setBackgroundImage(new Image (display, "..\\24.jpg"));
      search.setBackgroundImage(new Image (display, "..\\24.jpg"));
      search.setForeground(new Color(display, 255, 255, 255));
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
    add.addListener(SWT.Selection, e -> addWindow(display, tree));

    // Remove first event
    remove_first.addListener(SWT.Selection, (Event e) -> {
      try {
        CollectionController.remove_first(people);
        modifyTree(tree);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    });

    // Remove All Event
    remove_all.addListener(SWT.Selection, (Event e) -> removeWindow(display, tree));

    // Modify Event
    modify.addListener(SWT.Selection, (Event e) -> modifyWindow(display, tree));

    // Names Sort Event
    names.addListener(SWT.Selection, (Event e) -> {
      for(TreeColumn column: tree.getColumns()) {
        if(column.getText() == "Name") {
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

  private static void addWindow (Display display, Tree tree) {
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
    scale.setPageIncrement(5);
    scale.setMaximum(100);
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
        if (scale.getSelection() <= 50) {
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
        java.util.regex.Pattern removeAllRegex = java.util.regex.Pattern.compile("\\s*(\\{.+)(\\{.+}\\s*})");
        Matcher matcher = removeAllRegex.matcher(spaceJson.getText());

        if (matcher.matches()) {
          // Removes extra brackets
          String shortyJson, group2;
          group2 = matcher.group(2).trim().substring(1, matcher.group(2).length() - 1);
          shortyJson = matcher.group(1) + group2;
          Gson gson = new Gson();
          try {
            people.add(gson.fromJson(shortyJson, Shorty.class));
          } catch (Exception ex) {
            int style = SWT.APPLICATION_MODAL | SWT.OK;
            MessageBox error = new MessageBox(shell, style);
            error.setText("Error");
            error.setMessage(ex.getMessage());
            error.open();
          }
          modifyTree(tree);
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
            people.add(new Shorty(spaceName.getText(), Integer.parseInt(spaceAge.getText()),
                Double.parseDouble(spaceHeight.getText()), spaceHobby.getText(), meaning));
            modifyTree(tree);
          } catch (Exception ex) {
            
          }
        }
        shell.close();
    });
  }

  private static void createTree(Tree tree) {
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
  }

  private static void modifyTree (Tree tree) {
    tree.removeAll();
    tree.setRedraw(false);
    TreeItem item = new TreeItem(tree, SWT.NONE);
    TreeItem subItem;
    for(Shorty shorty: people) {
      if(people.indexOf(shorty) == 0) {
        item = new TreeItem(tree, SWT.NONE);
        item.setText(new String[] {shorty.name, Integer.toString(shorty.age), Double.toString(shorty.height),
              shorty.hobby, shorty.status.toString()});
      }
      else {
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
      java.util.regex.Pattern removeAllRegex = java.util.regex.Pattern.compile("\\s*(\\{.+)(\\{.+}\\s*})");
      Matcher matcher = removeAllRegex.matcher(jsonString.getText());
      if (matcher.matches()) {
        // Removes extra brackets
        String shortyJson, group2;
        group2 = matcher.group(2).trim().substring(1, matcher.group(2).length() - 1);
        shortyJson = matcher.group(1) + group2;

        try {
          CollectionController.remove_all(people, shortyJson);
        } catch (Exception ex) {
          ex.printStackTrace();
        }

        modifyTree(tree);
      } else {
        System.out.println("\n This command isn't supported \n P.S. If you want, you can use 'help' \n");
      }
    });
  }

  private static void modifyWindow (Display display, Tree tree) {
    Shell shell = new Shell(display);
    shell.setText("Modify");
    shell.setSize(300, 570);
    shell.setLayout(new GridLayout());

    Label title = new Label(shell, SWT.NULL);
    title.setText("Input index of element that you want to \nchange and then new parametres");

    new Label(shell, SWT.NULL).setText("Index");
    Text index = new Text(shell, SWT.BORDER | SWT.FILL);
    index.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true));

    new Label(shell, SWT.NULL).setText("Name");
    Text spaceName = new Text(shell, SWT.BORDER | SWT.FILL);
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

      try {
        Shorty shorty = new Shorty(spaceName.getText(), Integer.parseInt(spaceAge.getText()),
              Double.parseDouble(spaceHeight.getText()), spaceHobby.getText(), meaning);
        people.set(Integer.parseInt(index.getText()), shorty);
      } catch (Exception ex) {
        int style = SWT.APPLICATION_MODAL | SWT.OK;
        MessageBox error = new MessageBox(shell, style);
        error.setText("Error");
        error.setMessage(ex.getMessage());
        error.open();
      }
      modifyTree(tree);
    });
  }

}
