package com.company;

import com.google.gson.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import java.text.NumberFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.regex.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class CollectionInterface {
  private static ArrayList<Shorty> people = new ArrayList<>();
  private static ArrayList<Shorty> filteredArray = new ArrayList<>();
  private static ResourceBundle resource;

  static void createInterface() {
    resource = Main.getResource();

    // windows's settings
    Display display = new Display();
    Shell shell = new Shell(display);
    shell.setText(resource.getString("platoon"));
    shell.setSize(1010, 690);
    shell.setBackground(new Color(display, 255, 255, 255));
    GridLayout shellLayout = new GridLayout();
    shellLayout.numColumns = 3;
    shell.setLayout(shellLayout);

    // toolBar's settings
    ToolBar toolBar = new ToolBar(shell, SWT.HORIZONTAL);
    GridData data = new GridData();
    data.horizontalAlignment = SWT.FILL;
    data.grabExcessHorizontalSpace = true;
    toolBar.setLayoutData(data);

    ToolItem file = new ToolItem(toolBar, SWT.PUSH);
    file.setText(resource.getString("file"));

    Menu fileMenu = new Menu(shell, SWT.POP_UP);
    Menu submenu = new Menu(fileMenu);
    Menu submenu2 = new Menu(fileMenu);
    MenuItem load = new MenuItem(fileMenu, SWT.PUSH);
    load.setText(resource.getString("load"));
    new MenuItem(fileMenu, SWT.SEPARATOR);

    MenuItem mode = new MenuItem(fileMenu, SWT.CASCADE);
    mode.setText(resource.getString("mode"));
    new MenuItem(fileMenu, SWT.SEPARATOR);

    mode.setMenu(submenu);
    MenuItem dayLight = new MenuItem(submenu, SWT.PUSH);
    dayLight.setText(resource.getString("daylight"));
    new MenuItem(submenu, SWT.SEPARATOR);

    MenuItem nightTime = new MenuItem(submenu, SWT.PUSH);
    nightTime.setText(resource.getString("nighttime"));
    new MenuItem(submenu, SWT.SEPARATOR);

    MenuItem citySpirit = new MenuItem(submenu, SWT.PUSH);
    citySpirit.setText(resource.getString("citySpirit"));

    MenuItem language = new MenuItem(fileMenu, SWT.CASCADE);
    language.setText(resource.getString("language"));
    new MenuItem(fileMenu, SWT.SEPARATOR);

    language.setMenu(submenu2);
    MenuItem russian = new MenuItem(submenu2, SWT.PUSH);
    russian.setText(resource.getString("russian"));
    new MenuItem(submenu2, SWT.SEPARATOR);

    MenuItem german = new MenuItem(submenu2, SWT.PUSH);
    german.setText(resource.getString("german"));
    new MenuItem(submenu2, SWT.SEPARATOR);

    MenuItem english = new MenuItem(submenu2, SWT.PUSH);
    english.setText(resource.getString("english"));

    new MenuItem(submenu2, SWT.SEPARATOR);
    MenuItem bulgarian = new MenuItem(submenu2, SWT.PUSH);
    bulgarian.setText(resource.getString("bulgarian"));

    MenuItem exit = new MenuItem(fileMenu, SWT.PUSH);
    exit.setText(resource.getString("exit"));

    // File Event
    file.addListener(SWT.Selection, event -> toolItemInitialization(event, file, toolBar, fileMenu));

    ToolItem command = new ToolItem(toolBar, SWT.PUSH);
    command.setText(resource.getString("commands"));

    Menu commandsMenu = new Menu(shell, SWT.POP_UP);
    MenuItem info = new MenuItem(commandsMenu, SWT.PUSH);
    info.setText(resource.getString("info"));
    new MenuItem(commandsMenu, SWT.SEPARATOR);

    MenuItem add = new MenuItem(commandsMenu, SWT.PUSH);
    add.setText(resource.getString("add"));
    new MenuItem(commandsMenu, SWT.SEPARATOR);

    MenuItem remove_first = new MenuItem(commandsMenu, SWT.CASCADE);
    remove_first.setText(resource.getString("remove_first"));
    new MenuItem(commandsMenu, SWT.SEPARATOR);

    MenuItem remove_all = new MenuItem(commandsMenu, SWT.PUSH);
    remove_all.setText(resource.getString("remove_all"));
    new MenuItem(commandsMenu, SWT.SEPARATOR);

    MenuItem modify = new MenuItem(commandsMenu, SWT.CASCADE);
    modify.setText(resource.getString("modify"));

    // Command Event
    command.addListener(SWT.Selection, event -> toolItemInitialization(event, command, toolBar, commandsMenu));

    ToolItem help = new ToolItem(toolBar, SWT.PUSH);
    help.setText(resource.getString("help"));

    // Help Event
    help.addListener(SWT.Selection, e -> {
      int style = SWT.APPLICATION_MODAL | SWT.OK | SWT.COLOR_WIDGET_BACKGROUND | SWT.LEFT_TO_RIGHT | SWT.LEFT;
      MessageBox messageBox = new MessageBox(shell, style);

      messageBox.setText(resource.getString("help"));
      messageBox.setMessage(resource.getString("help_text"));
      messageBox.open();
    });

    // Create filter Line
    final Text filterLine = new Text(shell, SWT.BORDER);
    filterLine.setForeground(new Color(display, 0, 0, 0));
    data = new GridData();
    data.horizontalAlignment = GridData.FILL;
    data.horizontalSpan = 1;
    data.grabExcessHorizontalSpace = true;
    filterLine.setLayoutData(data);
    filterLine.setBackgroundImage(new Image(display, "..\\..\\60.jpg"));

    // Create Search Line
    final Text search = new Text(shell, SWT.BORDER);
    search.setForeground(new Color(display, 0, 0, 0));
    data = new GridData();
    data.horizontalAlignment = GridData.FILL;
    data.horizontalSpan = 1;
    data.grabExcessHorizontalSpace = true;
    search.setLayoutData(data);
    search.setBackgroundImage(new Image(display, "..\\..\\60.jpg"));

    // Create column for tree
    data = new GridData(SWT.FILL, SWT.FILL, true, true);
    data.horizontalSpan = 2;
    Composite treeWindow = new Composite(shell, SWT.BORDER);
    treeWindow.setSize(500, SWT.NONE);
    treeWindow.setLayoutData(data);
    treeWindow.setLayout(new GridLayout());
    treeWindow.setBackgroundImage(new Image(display, "..\\..\\24.jpg"));

    // Tree of the shorty's
    Tree tree = new Tree(treeWindow, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
    createTree(tree, display);

    Menu menu = new Menu(shell, SWT.POP_UP);
    MenuItem sort = new MenuItem(menu, SWT.CASCADE);
    sort.setText(resource.getString("sort_by"));
    Menu subMenu = new Menu(menu);
    sort.setMenu(subMenu);

    MenuItem names = new MenuItem(subMenu, SWT.PUSH);
    names.setText(resource.getString("names"));
    new MenuItem(subMenu, SWT.SEPARATOR);
    MenuItem ages = new MenuItem(subMenu, SWT.POP_UP);
    ages.setText(resource.getString("ages"));
    new MenuItem(subMenu, SWT.SEPARATOR);
    MenuItem heights = new MenuItem(subMenu, SWT.PUSH);
    heights.setText(resource.getString("heights"));
    new MenuItem(subMenu, SWT.SEPARATOR);
    MenuItem hobbies = new MenuItem(subMenu, SWT.PUSH);
    hobbies.setText(resource.getString("hobbies"));
    new MenuItem(subMenu, SWT.SEPARATOR);
    MenuItem statuses = new MenuItem(subMenu, SWT.PUSH);
    statuses.setText(resource.getString("statuses"));
    new MenuItem(subMenu, SWT.SEPARATOR);
    MenuItem ides = new MenuItem(subMenu, SWT.PUSH);
    ides.setText(resource.getString("ides"));
    new MenuItem(subMenu, SWT.SEPARATOR);
    MenuItem dates = new MenuItem(subMenu, SWT.PUSH);
    dates.setText(resource.getString("dates"));
    tree.setMenu(menu);

    // Names Sort Event
    names.addListener(SWT.Selection, e -> {
      people.sort(new NameComparator());
      modifyTree(tree, people);
    });

    // Ages Sort Event
    ages.addListener(SWT.Selection, e -> {
      people.sort(new AgeComparator());
      modifyTree(tree, people);
    });

    // Heights Sort Event
    heights.addListener(SWT.Selection, e -> {
      people.sort(new HeightComparator());
      modifyTree(tree, people);
    });

    // Hobbies Sort Event
    hobbies.addListener(SWT.Selection, e -> {
      people.sort(new HobbyComparator());
      modifyTree(tree, people);
    });

    // Status Sort Event
    statuses.addListener(SWT.Selection, e -> {
      people.sort(new StatusComparator());
      modifyTree(tree, people);
    });

    // Id Sort Event
    ides.addListener(SWT.Selection, e -> {
      people.sort(new IdComparator());
      modifyTree(tree, people);
    });

    // Date Sort Event
    dates.addListener(SWT.Selection, e -> {
      people.sort(new DateComparator());
      modifyTree(tree, people);
    });

    // Create column for browser
    data = new GridData(SWT.FILL, SWT.FILL, true, true);
    Composite browserWindow = new Composite(shell, SWT.BORDER);
    browserWindow.setLayoutData(data);
    browserWindow.setLayout(new GridLayout());
    new Label(browserWindow, SWT.FILL).setText("Yandex it");

    // Browser's search in column
    final Browser browser = new Browser(browserWindow, 1);
    browser.setLayoutData(data);

    //Filter event
    filterLine.addListener(SWT.DefaultSelection, (Event e) -> {
      String filter;
      filter = filterLine.getText();
      filterTree(shell, tree, filter);
    });

    // Search event
    search.addListener(SWT.DefaultSelection, (Event e) -> {
      StringBuilder url = new StringBuilder();
      for (String tokens : search.getText().split(" ")) {
        url.append(tokens).append("%20");
      }
      browser.setUrl("https://yandex.ru/search/?text=" + url);
      browser.setJavascriptEnabled(true);
      browser.setRedraw(false);
      browser.setBackgroundImage(new Image(display, "..\\..\\60.jpg"));
      browser.setRedraw(true);
      browser.setBackground(new Color(display, 0, 0, 0));
    });

    // Load Event
    load.addListener(SWT.Selection, e -> {
      try {
        loading(tree);
      } catch (NullPointerException ex) {
        errorMessageWindow(shell, resource.getString("check_connection"));
      } catch (Exception ex) {
        errorMessageWindow(shell, resource.getString("empty_collection"));
      }
    });

    // Exit event
    exit.addListener(SWT.Selection, (Event e) -> {
      Thread thread = new Thread(() -> System.exit(0));
      thread.start();
    });

    // DayLight Event
    dayLight.addListener(SWT.Selection, e -> {
      search.setBackgroundImage(new Image(display, "..\\..\\clear.jpg"));
      search.setForeground(new Color(display, 0, 0, 0));
      filterLine.setBackgroundImage(new Image(display, "..\\..\\clear.jpg"));
      filterLine.setForeground(new Color(display, 0, 0, 0));
      tree.setBackgroundImage(new Image(display, "..\\..\\clear.jpg"));
      tree.setForeground(new Color(display, 0, 0, 0));
    });

    // NightTime Event
    nightTime.addListener(SWT.Selection, e -> {
      search.setBackgroundImage(new Image(display, "..\\..\\24.jpg"));
      search.setForeground(new Color(display, 255, 255, 255));
      filterLine.setBackgroundImage(new Image(display, "..\\..\\24.jpg"));
      filterLine.setForeground(new Color(display, 255, 255, 255));
      tree.setBackgroundImage(new Image(display, "..\\..\\24.jpg"));
      tree.setForeground(new Color(display, 255, 255, 255));
    });

    // CitySpirit Event
    citySpirit.addListener(SWT.Selection, e -> {
      search.setBackgroundImage(new Image(display, "..\\..\\60.jpg"));
      search.setForeground(new Color(display, 0, 0, 0));
      filterLine.setBackgroundImage(new Image(display, "..\\..\\60.jpg"));
      filterLine.setForeground(new Color(display, 0, 0, 0));
      tree.setBackgroundImage(new Image(display, "..\\..\\61.jpg"));
      tree.setForeground(new Color(display, 0, 0, 0));
    });

    // Info event
    info.addListener(SWT.Selection, e -> {
      int style = SWT.APPLICATION_MODAL | SWT.OK;
      MessageBox messageBox = new MessageBox(shell, style);
      messageBox.setText(resource.getString("info"));
      messageBox.setMessage(CollectionController.info(people));
      messageBox.open();
    });

    // Add Event
    add.addListener(SWT.Selection, (Event e) -> addWindow(display, tree, shell));

    // Remove first event
    remove_first.addListener(SWT.Selection, (Event e) -> {
      try {
        if (Objects.equals(ServerConnection.sendCommand("remove", "null",
            String.valueOf(people.get(0).id) + "%"), "1")) {
          CollectionController.remove_first(people);
          loading(tree);

          if (people.size() != 0) {
            int style = SWT.APPLICATION_MODAL | SWT.OK;
            MessageBox window = new MessageBox(shell, style);
            window.setMessage(resource.getString("successfully_deleted"));
            window.open();
          } else {
            throw new Exception(resource.getString("empty_collection"));
          }
        } else {
          extraLoading(shell, tree);
        }
      } catch (Exception ex) {
        tree.removeAll();

        int style = SWT.APPLICATION_MODAL | SWT.OK;
        MessageBox window = new MessageBox(shell, style);
        window.setText(resource.getString("warning"));
        window.setMessage(resource.getString("empty_collection"));
        window.open();
      }
    });

    // Remove All Event
    remove_all.addListener(SWT.Selection, (Event e) -> removeWindow(display, tree));

    // Modify Event
    modify.addListener(SWT.Selection, (Event e) -> modifyWindow(display, tree));

    // English Event
    english.addListener(SWT.Selection, e -> {
      display.dispose();
      Main.main(new String[]{"1"});
    });

    // Russian Event
    russian.addListener(SWT.Selection, e -> {
      display.dispose();
      Main.main(new String[]{"0"});
    });

    // German Event
    german.addListener(SWT.Selection, e -> {
      display.dispose();
      Main.main(new String[]{"2"});
    });

    // Bulgarian Event
    bulgarian.addListener(SWT.Selection, e -> {
      display.dispose();
      Main.main(new String[]{"3"});
    });

    shell.open();
    try {
      loading(tree);
    } catch (NullPointerException ex) {
      errorMessageWindow(shell, resource.getString("check_connection"));
    } catch (Exception ex) {
      errorMessageWindow(shell, ex.getMessage());
    }

    while (!shell.isDisposed()) {
      if (!display.readAndDispatch()) {
        display.sleep();
      }
    }
    display.dispose();
  }

  private static void createTree(Tree tree, Display display) {
    tree.setBackgroundImage(new Image(display, "..\\..\\61.jpg"));
    tree.setForeground(new Color(display, 0, 0, 0));

    GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
    tree.setHeaderVisible(true);
    TreeColumn column1 = new TreeColumn(tree, SWT.CENTER | SWT.PUSH);
    column1.setText(resource.getString("name"));
    column1.setWidth(80);
    TreeColumn column2 = new TreeColumn(tree, SWT.CENTER | SWT.PUSH);
    column2.setText(resource.getString("age"));
    column2.setWidth(50);
    tree.setSortColumn(column2);
    TreeColumn column3 = new TreeColumn(tree, SWT.CENTER);
    column3.setText(resource.getString("height"));
    column3.setWidth(70);
    TreeColumn column4 = new TreeColumn(tree, SWT.CENTER);
    column4.setText(resource.getString("hobby"));
    column4.setWidth(90);
    TreeColumn column5 = new TreeColumn(tree, SWT.CENTER);
    column5.setText(resource.getString("status"));
    column5.setWidth(80);
    TreeColumn column6 = new TreeColumn(tree, SWT.CENTER);
    column6.setText(resource.getString("id"));
    column6.setWidth(40);
    TreeColumn column7 = new TreeColumn(tree, SWT.CENTER);
    column7.setText(resource.getString("date"));
    column7.setWidth(40);
    tree.setLayoutData(data);

    // Names Sorting
    column1.addListener(SWT.Selection, e -> modifyTree(tree,
        people.stream().sorted(new NameComparator()).collect(Collectors.toList())));

    // Ages Sorting
    column2.addListener(SWT.Selection, e -> modifyTree(tree,
        people.stream().sorted(new AgeComparator()).collect(Collectors.toList())));

    // Heights Sorting
    column3.addListener(SWT.Selection, e -> modifyTree(tree,
        people.stream().sorted(new HeightComparator()).collect(Collectors.toList())));

    // Hobbies Sorting
    column4.addListener(SWT.Selection, e -> modifyTree(tree,
        people.stream().sorted(new HobbyComparator()).collect(Collectors.toList())));

    // Statuses Sorting
    column5.addListener(SWT.Selection, e -> modifyTree(tree,
        people.stream().sorted(new StatusComparator()).collect(Collectors.toList())));

    // ides Sorting
    column6.addListener(SWT.Selection, e -> modifyTree(tree,
        people.stream().sorted(new IdComparator()).collect(Collectors.toList())));

    // Dates Sorting
    column7.addListener(SWT.Selection, e -> modifyTree(tree,
        people.stream().sorted(new DateComparator()).collect(Collectors.toList())));
  }

  private static void modifyTree(Tree tree, List<Shorty> collection) {
    tree.removeAll();
    tree.setRedraw(false);
    TreeItem item;
    TreeItem subItem;
    Shorty shorty;

    Locale locale = Main.locale;
    String pattern = "dd.MM.yy hh:mm:ss a", deviation = " +01";

    switch (Main.variantOfLocale) {
      case 1:
        pattern = "dd/MM/yy hh:mm:ss a";
        break;
      case 2:
        pattern = "dd.MM.yy HH:mm:ss";
        break;
      case 3:
        deviation = " +02";
        break;
      default:
        pattern = "dd.MM.yy HH:mm:ss";
        deviation = " +03";
    }

    item = new TreeItem(tree, SWT.NONE);
    item.setText(new String[]{collection.get(0).name,
        NumberFormat.getNumberInstance(locale).format(collection.get(0).age),
        NumberFormat.getNumberInstance(locale).format(collection.get(0).height),
        collection.get(0).hobby, collection.get(0).status.toString(),
        NumberFormat.getNumberInstance(locale).format(collection.get(0).id),
        collection.get(0).date.format(DateTimeFormatter.ofPattern(pattern + deviation))});

    for (int i = 1; i < collection.size(); i++) {
      shorty = collection.get(i);

      if (shorty.compare(shorty, collection.get(collection.indexOf(shorty) - 1)) == 0) {
        subItem = new TreeItem(item, SWT.NONE);
        subItem.setText(new String[]{shorty.name, NumberFormat.getNumberInstance(locale).format(shorty.age),
            NumberFormat.getNumberInstance(locale).format(shorty.height),
            shorty.hobby, shorty.status.toString(), NumberFormat.getNumberInstance(locale).format(shorty.id),
            shorty.date.format(DateTimeFormatter.ofPattern(pattern + deviation))});
      } else {
        item = new TreeItem(tree, SWT.NONE);
        item.setText(new String[]{shorty.name, NumberFormat.getNumberInstance(locale).format(shorty.age),
            NumberFormat.getNumberInstance(locale).format(shorty.height),
            shorty.hobby, shorty.status.toString(), NumberFormat.getNumberInstance(locale).format(shorty.id),
            shorty.date.format(DateTimeFormatter.ofPattern(pattern + deviation))});
      }
    }
    tree.setRedraw(true);
  }

  private static void filterTree(Shell shell, Tree tree, String filter) {
    filteredArray.clear();
    try {
      if ((filter.length() != 0) && (!filter.matches("\\s*"))) {
        for (Shorty shorty : people) {
          if (Stream.of(shorty.name, Integer.toString(shorty.age), Double.toString(shorty.height),
                shorty.hobby, shorty.status.toString(), Integer.toString(shorty.id),
                shorty.date.format(DateTimeFormatter.ofPattern("dd.MM.yy HH:mm:ss"))).filter
                (w -> w.contains(filter)).count() != 0) {
            filteredArray.add(shorty);
          }
        }
        if (filteredArray.size() > 0) {
          modifyTree(tree, filteredArray);
        } else {
          tree.removeAll();
        }
      } else {
        modifyTree(tree, people);
      }
    } catch (Exception ex) {
      errorMessageWindow(shell, ex.getMessage());
    }
  }

  private static void removeWindow(Display display, Tree tree) {
    Shell shell = new Shell(display, SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
    shell.setSize(800, 190);
    shell.setText(resource.getString("remove_elements"));
    shell.setLayout(new GridLayout());

    Label title = new Label(shell, SWT.NONE);
    title.setText(resource.getString("input") + "\n{name: 'mean', age: mean, height: mean, hobby: 'mean', status: { }}");
    GridData gridDataDialog = new GridData(SWT.BEGINNING, SWT.CENTER, false, false);
    title.setLayoutData(gridDataDialog);

    Text jsonString = new Text(shell, SWT.BORDER | SWT.WRAP);
    gridDataDialog = new GridData(SWT.FILL, SWT.FILL, true, true);
    jsonString.setLayoutData(gridDataDialog);

    Button remove = new Button(shell, SWT.PUSH | SWT.CENTER);
    remove.setText(resource.getString("remove_elements"));

    shell.open();

    // Remove Event
    remove.addListener(SWT.Selection, (Event e) -> {
      try {
        if (people.size() == 0) {
          throw new Exception(resource.getString("empty_collection"));
        }
        Pattern removeAllRegex = java.util.regex.Pattern.compile("\\s*(\\{.+)(\\{.+}\\s*})");
        Matcher matcher = removeAllRegex.matcher(jsonString.getText());
        if (matcher.matches()) {
          // Removes extra brackets
          String shortyJson, group2, indexes;
          group2 = matcher.group(2).trim().substring(1, matcher.group(2).length() - 1);
          shortyJson = matcher.group(1) + group2;
          int number;
          indexes = CollectionController.remove_all(people, shortyJson);
          if (Objects.equals(indexes, "")) {
            number = 0;
          } else {
            number = indexes.split("%").length;
          }
          if (Objects.equals(ServerConnection.sendCommand("remove", "null", indexes), "1")) {
            loading(tree);
            CollectionController.getLastModificationDate();

            MessageBox window = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.OK);
            window.setText("");
            window.setMessage(resource.getString("successfully_deleted") + " " + number +
                resource.getString("objects"));
            window.open();
          } else {
            extraLoading(shell, tree);
          }
        } else {
          throw new Exception(resource.getString("check_correctness"));
        }
      } catch (Exception ex) {
        errorMessageWindow(shell, ex.getMessage());
      }
    });
  }

  private static void modifyWindow(Display display, Tree tree) {
    Shell shell = new Shell(display, SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
    shell.setText(resource.getString("modify"));
    shell.setSize(350, 600);
    shell.setLayout(new GridLayout());

    Label title = new Label(shell, SWT.NULL);
    title.setText(resource.getString("input_index"));
    GridData data = new GridData(SWT.FILL, SWT.NONE, true, true);

    new Label(shell, SWT.NULL).setText(resource.getString("index"));
    Text index = new Text(shell, SWT.BORDER);
    index.setLayoutData(data);

    new Label(shell, SWT.NULL).setText(resource.getString("name"));
    Text spaceName = new Text(shell, SWT.BORDER);
    spaceName.setLayoutData(data);

    new Label(shell, SWT.NULL).setText(resource.getString("age"));
    Text spaceAge = new Text(shell, SWT.BORDER);
    spaceAge.setLayoutData(data);

    new Label(shell, SWT.NULL).setText(resource.getString("height"));
    Text spaceHeight = new Text(shell, SWT.BORDER);
    spaceHeight.setLayoutData(data);

    new Label(shell, SWT.NULL).setText(resource.getString("hobby"));
    Text spaceHobby = new Text(shell, SWT.BORDER);
    spaceHobby.setLayoutData(data);

    new Label(shell, SWT.NULL).setText(resource.getString("status"));
    Combo status = new Combo(shell, SWT.READ_ONLY);
    status.setLayoutData(data);
    String statusOptions[] = {"all_is_complicated", "have_a_girlfriend", "married", "single", "idle"};
    status.setItems(statusOptions);

    Button modify = new Button(shell, SWT.PUSH | SWT.CENTER);
    modify.setText(resource.getString("modify") + "!");
    modify.setLayoutData(new GridData(SWT.CENTER, SWT.NULL, false, false));

    shell.open();

    // Modify Event
    modify.addListener(SWT.Selection, (Event e) -> {
      Status meaning = switchStatus(status.getText());
      String expected_name, expected_hobby;
      int expected_age;
      double expected_height;
      try {
        if (index.getText().matches("\\s*")) {
          throw new Exception(resource.getString("input_index_"));
        } else if ((Integer.parseInt(index.getText()) < 0) || (Integer.parseInt(index.getText())
            >= people.size())) {
          throw new Exception(resource.getString("index_out"));
        }

        if (spaceName.getText().matches("\\s*")) {
          expected_name = people.get(Integer.parseInt(index.getText())).name;
        } else {
          expected_name = spaceName.getText();
        }

        if (spaceAge.getText().matches("\\s*")) {
          expected_age = people.get(Integer.parseInt(index.getText())).age;
        } else if ((Integer.parseInt(spaceAge.getText())) < 0) {
          throw new Exception(resource.getString("age_can"));
        } else {
          expected_age = Integer.parseInt(spaceAge.getText());
        }

        if (spaceHeight.getText().matches("\\s*")) {
          expected_height = people.get(Integer.parseInt(index.getText())).height;
        } else if ((Double.parseDouble(spaceHeight.getText())) <= 0) {
          throw new Exception(resource.getString("height_can"));
        } else {
          expected_height = Double.parseDouble(spaceHeight.getText());
        }

        if (spaceHobby.getText().matches("\\s*")) {
          expected_hobby = people.get(Integer.parseInt(index.getText())).hobby;
        } else {
          expected_hobby = spaceHobby.getText();
        }

        Shorty shorty = new Shorty(expected_name, expected_age, expected_height, expected_hobby, meaning,
            people.get(Integer.parseInt(index.getText())).id);
        shorty.date = people.get(Integer.parseInt(index.getText())).date;

        if (Objects.equals(ServerConnection.sendCommand("modify", shorty.toString(),
            String.valueOf(people.get(Integer.parseInt(index.getText())).id) + "%"), "1")) {
          people.set(Integer.parseInt(index.getText()), shorty);
          Comparator<Shorty> shortyComparator = new Shorty();
          people.sort(shortyComparator);

          modifyTree(tree, people);
          CollectionController.getLastModificationDate();

          int style = SWT.APPLICATION_MODAL | SWT.OK;
          MessageBox window = new MessageBox(shell, style);
          window.setText("");
          window.setMessage(resource.getString("successfully_modified"));
          window.open();
        } else {
          extraLoading(shell, tree);
        }
      } catch (Exception ex) {
        errorMessageWindow(shell, ex.getMessage());
      }
    });
  }

  private static void addWindow(Display display, Tree tree, Shell mainShell) {
    Shell shell = new Shell(display, SWT.APPLICATION_MODAL | SWT.SHELL_TRIM);
    shell.setText(resource.getString("add_element"));
    shell.setSize(500, 570);
    GridLayout shellLayout = new GridLayout();
    shellLayout.numColumns = 2;
    shell.setLayout(shellLayout);

    Composite scalePlace = new Composite(shell, SWT.BORDER);
    scalePlace.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
    scalePlace.setLayout(new GridLayout(3, false));

    // Values
    Label values = new Label(scalePlace, SWT.NULL);
    values.setText(resource.getString("values"));
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

    new Label(valuesGroup, SWT.NULL).setText(resource.getString("name"));
    Text spaceName = new Text(valuesGroup, SWT.BORDER);
    new Label(valuesGroup, SWT.NULL).setText(resource.getString("age"));
    Text spaceAge = new Text(valuesGroup, SWT.BORDER);
    new Label(valuesGroup, SWT.NULL).setText(resource.getString("height"));
    Text spaceHeight = new Text(valuesGroup, SWT.BORDER);
    new Label(valuesGroup, SWT.NULL).setText(resource.getString("hobby"));
    Text spaceHobby = new Text(valuesGroup, SWT.BORDER);
    new Label(valuesGroup, SWT.NULL).setText(resource.getString("status"));
    Combo status = new Combo(valuesGroup, SWT.READ_ONLY);
    String statusOptions[] = {"all_is_complicated", "have_a_girlfriend", "married", "single", "idle"};
    status.setItems(statusOptions);

    //Text.MULTI Json
    Composite jsonGroup = new Composite(shell, SWT.BORDER);
    jsonGroup.setLayout(new GridLayout());
    jsonGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

    Text spaceJson = new Text(jsonGroup, SWT.V_SCROLL | SWT.BORDER | SWT.H_SCROLL | SWT.WRAP);
    spaceJson.setEnabled(false);
    gridDataDialog = new GridData(SWT.FILL, SWT.FILL, true, true);
    spaceJson.setLayoutData(gridDataDialog);

    // Info
    Label labelInfo = new Label(shell, SWT.LEFT);
    labelInfo.setLayoutData(new GridData(40, SWT.DEFAULT));

    //Button to add_element an object
    Button add_element = new Button(shell, SWT.PUSH);
    add_element.setText(resource.getString("add_element"));
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
      try {
        Shorty shorty = new Shorty();
        if (spaceJson.getEnabled()) {
          java.util.regex.Pattern removeAllRegex = java.util.regex.Pattern.compile("\\s*(\\{(.+\\s*)*)(\\{.+}\\s*})");
          Matcher matcher = removeAllRegex.matcher(spaceJson.getText());

          if (matcher.matches()) {
            // Removes extra brackets
            String shortyJson, group3;
            group3 = matcher.group(3).trim().substring(1, matcher.group(3).length() - 1);
            shortyJson = matcher.group(1) + group3;
            Gson gson = new Gson();

            shorty = gson.fromJson(shortyJson, Shorty.class);
            shorty.id = people.size();
            shorty.date = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));

            if (shorty.age < 0) {
              throw new Exception(resource.getString("age_can"));
            }

            if (shorty.height <= 0) {
              throw new Exception(resource.getString("height_can"));
            }
          }
        } else {
          Status meaning = switchStatus(status.getText());
          if ((spaceName.getText().matches("\\s*")) || (spaceAge.getText().matches("\\s*")) ||
              (spaceHeight.getText().matches("\\s*")) || (spaceHobby.getText().matches("\\s*"))) {
            throw new Exception(resource.getString("can_null"));
          } else if ((Integer.parseInt(spaceAge.getText())) < 0) {
            throw new Exception(resource.getString("age_can"));
          } else if ((Double.parseDouble(spaceHeight.getText())) <= 0) {
            throw new Exception(resource.getString("height_can"));
          }
          shorty = new Shorty(spaceName.getText(), Integer.parseInt(spaceAge.getText()),
              Double.parseDouble(spaceHeight.getText()), spaceHobby.getText(), meaning, people.size());
          shorty.date = ZonedDateTime.now(ZoneId.of("Europe/Moscow"));
        }
        if (Objects.equals(ServerConnection.sendCommand("add", shorty.toString(), "null"), "1")) {
          people.add(shorty);
          Comparator<Shorty> shortyComparator = new Shorty();
          people.sort(shortyComparator);

          modifyTree(tree, people);
          CollectionController.getLastModificationDate();

          int style = SWT.APPLICATION_MODAL | SWT.OK;
          MessageBox window = new MessageBox(shell, style);
          window.setText("");
          window.setMessage(resource.getString("successfully_added"));
          window.open();
        } else {
          extraLoading(shell, tree);
        }
      } catch (JsonSyntaxException ex) {
        errorMessageWindow(mainShell, resource.getString("empty_collection"));
      } catch (Exception ex) {
        errorMessageWindow(mainShell, ex.getMessage());
      }
    });
  }

  private static Status switchStatus(String meaning) {
    Status status;
    switch (meaning) {
      case "married":
        status = Status.married;
        break;
      case "have_a_girlfriend":
        status = Status.have_a_girlfriend;
        break;
      case "idle":
        status = Status.idle;
        break;
      case "single":
        status = Status.single;
        break;
      default:
        status = Status.all_is_complicated;
    }
    return status;
  }

  private static void toolItemInitialization(Event event, ToolItem toolItem, ToolBar toolBar, Menu menu) {
    if (event.detail == SWT.NONE) {
      Rectangle bounds = toolItem.getBounds();
      Point point = toolBar.toDisplay(bounds.x, bounds.y + bounds.height);
      menu.setLocation(point);
      menu.setVisible(true);
    }
  }

  private static void errorMessageWindow(Shell shell, String message) {
    try {
      int style = SWT.APPLICATION_MODAL | SWT.OK;
      MessageBox error = new MessageBox(shell, style);
      error.setText(resource.getString("error"));
      error.setMessage(message);
      error.open();
    } catch (Exception ex) {
      int style = SWT.APPLICATION_MODAL | SWT.OK;
      MessageBox error = new MessageBox(shell, style);
      error.setText(resource.getString("error"));
      error.setMessage(resource.getString("can_null"));
      error.open();
    }
  }

  private static void extraLoading(Shell shell, Tree tree) throws Exception {
    MessageBox messageBox = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.OK);
    messageBox.setText(resource.getString("warning"));
    messageBox.setMessage(resource.getString("loading"));
    messageBox.open();

    people = CollectionController.readFromServer(ServerConnection.sendCommand("load", "null",
        "null"));
    Comparator<Shorty> shortyComparator = new Shorty();
    people.sort(shortyComparator);
    modifyTree(tree, people);
    CollectionController.getLastModificationDate();
  }

  private static void loading(Tree tree) throws Exception {
    people = CollectionController.readFromServer(ServerConnection.sendCommand("load", "null",
        "null"));
    Comparator<Shorty> shortyComparator = new Shorty();
    people.sort(shortyComparator);
    modifyTree(tree, people);
  }
}

