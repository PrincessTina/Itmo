package com.company;


import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Main {
    public static void main(String[] args) {
        // windows's settings
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("Platoon Manager");
        shell.setSize(1010,690);
        shell.setBackground(new Color (display, 255, 255, 255));
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

        Menu menu = new Menu(shell, SWT.POP_UP);
        Menu submenu = new Menu(menu);

        MenuItem set = new MenuItem(menu, SWT.PUSH);
        set.setText("Set File");
        new MenuItem(menu, SWT.SEPARATOR);
        MenuItem save = new MenuItem(menu, SWT.PUSH);
        save.setText("Save All");
        new MenuItem(menu, SWT.SEPARATOR);
        MenuItem mode = new MenuItem(menu, SWT.CASCADE);
        mode.setText("Mode");
        mode.setMenu(submenu);

        MenuItem dayLight = new MenuItem(submenu, SWT.PUSH);
        dayLight.setText("DayLight");
        new MenuItem(submenu, SWT.SEPARATOR);

        MenuItem nightTime = new MenuItem(submenu, SWT.PUSH);
        nightTime.setText("NightTime");
        new MenuItem(submenu, SWT.SEPARATOR);

        MenuItem blonde = new MenuItem(submenu, SWT.PUSH);

        file.addListener(SWT.Selection, event -> {
            if(event.detail == SWT.NONE) {
                Rectangle bounds = file.getBounds();
                Point point = toolBar.toDisplay(bounds.x, bounds.y + bounds.height);
                menu.setLocation(point);
                menu.setVisible(true);
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

        // field where words for search will be written
        final Text search = new Text(shell, SWT.BORDER);
        data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.horizontalSpan = 1;
        data.grabExcessHorizontalSpace = true;
        search.setLayoutData(data);
        search.setBackgroundImage(new Image (display, "..\\dark.jpg"));

        // new column for listing
        data = new GridData(SWT.FILL, SWT.FILL, true, true);
        Composite listingWindow = new Composite (shell, SWT.BORDER);
        listingWindow.setSize(500, SWT.NONE);
        listingWindow.setLayoutData(data);
        listingWindow.setLayout(new GridLayout());
        listingWindow.setBackgroundImage(new Image (display, "..\\dark.jpg"));

        // list of the shorty's
        Tree tree = new Tree(listingWindow, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        data = new GridData(SWT.FILL, SWT.NONE, true, true);
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

        // new column for browser
        data = new GridData(SWT.FILL, SWT.FILL, true, true);
        Composite browserWindow = new Composite (shell, SWT.BORDER);
        browserWindow.setLayoutData(data);
        browserWindow.setLayout(new GridLayout());
        new Label(browserWindow, SWT.FILL).setText("Search");

        // browser's search in column
        final Browser browser = new Browser(browserWindow, 0);
        browser.setLayoutData(data);
        browser.setBackgroundImage(new Image (display, "..\\dark.jpg"));

        // search event
        search.addListener(SWT.DefaultSelection, e -> {
          String url = "";
          for(String tokens: search.getText().split(" ")) {
            url = url + tokens + "%20";
          }
          browser.setUrl("https://yandex.ru/search/?text=" + url);
        });

        dayLight.addListener (SWT.Selection, e -> {
          shell.setBackground(new Color (display, 255, 255, 255));
          browserWindow.setBackground(new Color (display, 230, 230, 230));
        });

        nightTime.addListener (SWT.Selection, e -> {
          shell.setBackground(new Color (display, 209, 28, 9));
          browserWindow.setBackground(new Color (display, 0, 0, 0));
        });

        blonde.setText("Blond");
        blonde.addListener (SWT.Selection, e -> {
          shell.setBackground(new Color (display, 0, 0, 0));
          browserWindow.setBackground(new Color (display, 207, 127, 199));
        });

        // Диалоговое окно чтения файла
        set.addListener (SWT.Selection, e -> {
              FileDialog setFile = new FileDialog(shell, SWT.OPEN);
              setFilters(setFile);
              String peopleFileName = setFile.open();
            });

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

        add.addListener(SWT.Selection, e -> {
          addWindow(display);
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

    // set file for working with collection
    private static void setFilters (FileDialog dialog) {
      String[] FILTERS = {"Файлы Excel (*.csv)", "*.csv"};
      String[] fileName = new String[FILTERS.length];
      String[] extension  = new String[FILTERS.length];
        fileName[0] = FILTERS[0];
        extension[0] = FILTERS[1];
      dialog.setFilterNames(fileName);
      dialog.setFilterExtensions(extension);
    }

    public static void addWindow (Display display) {
      Shell shell = new Shell(display);
      shell.setText("Add element");
      shell.setSize(400, 570);
      shell.setLayout(new GridLayout(1, true));

      Label label = new Label(shell, SWT.NULL);
      label.setText("Brightness:");

      // Scale
      Scale scale = new Scale(shell, SWT.HORIZONTAL);
      scale.setMinimum(0);
      scale.setIncrement(1);
      scale.setPageIncrement(5);
      scale.setMaximum(100);

      // Info
      Label labelInfo = new Label(shell, SWT.CENTER);
      labelInfo.setLayoutData(new GridData(40, SWT.DEFAULT));

      // Event
      scale.addSelectionListener(new SelectionAdapter() {
        public void widgetSelected(SelectionEvent e) {
          int value = scale.getMinimum() + scale.getSelection();
          labelInfo.setText("" + value);
        }
      });

      shell.open();
    }

}