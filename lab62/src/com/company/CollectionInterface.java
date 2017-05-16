package com.company;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class CollectionInterface {
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
    remove_first.setText("Remove first");
    new MenuItem(commandsMenu, SWT.SEPARATOR);

    MenuItem remove_all = new MenuItem(commandsMenu, SWT.CASCADE);
    remove_all.setText("Remove all");
    new MenuItem(commandsMenu, SWT.SEPARATOR);

    MenuItem load = new MenuItem(commandsMenu, SWT.CASCADE);
    load.setText("Load");

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
    createTree(treeWindow);

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
      String peopleFileName = setFile.open();
    });

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

    // Add Event
    add.addListener(SWT.Selection, e -> addWindow(display));

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

  private static void addWindow (Display display) {
    Shell shell = new Shell(display);
    shell.setText("Add element");
    shell.setSize(500, 570);
    GridLayout shellLayout = new GridLayout();
    shellLayout.numColumns = 2;
    shell.setLayout(shellLayout);

    Composite scalePlace = new Composite (shell, SWT.BORDER);
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

    // Json format
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
    String statusOptions[] = {"married", "single", "all_is_complicated"};
    status.setItems(statusOptions);

    //Text.MULTI Json
    Composite jsonGroup = new Composite(shell, SWT.BORDER);
    jsonGroup.setLayout(new GridLayout());
    jsonGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

    Text spaceJson = new Text(jsonGroup, SWT.MULTI | SWT.V_SCROLL | SWT.BORDER);
    spaceJson.setEnabled(false);
    gridDataDialog = new GridData(SWT.FILL, SWT.FILL, true, true);
    spaceJson.setLayoutData(gridDataDialog);

    // Info
    Label labelInfo = new Label(shell, SWT.LEFT);
    labelInfo.setLayoutData(new GridData(40, SWT.DEFAULT));

    //Button to add an object
    Button add = new Button(shell, SWT.PUSH);
    add.setText("Add element");
    add.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true));

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
  }

  private static void createTree(Composite listingWindow) {
    Tree tree = new Tree(listingWindow, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
    GridData data = new GridData(SWT.FILL, SWT.NONE, true, true);
    tree.setHeaderVisible(true);
    TreeColumn column1 = new TreeColumn(tree, SWT.CENTER);
    column1.setText("Name");
    column1.setWidth(100);
    TreeColumn column2 = new TreeColumn(tree, SWT.CENTER);
    column2.setText("Age");
    column2.setWidth(100);
    TreeColumn column3 = new TreeColumn(tree, SWT.CENTER);
    column3.setText("Height");
    column3.setWidth(100);
    TreeColumn column4 = new TreeColumn(tree, SWT.CENTER);
    column4.setText("Hobby");
    column4.setWidth(100);
    TreeColumn column5 = new TreeColumn(tree, SWT.CENTER);
    column5.setText("Status");
    column5.setWidth(100);
    tree.setLayoutData(data);
  }
}
