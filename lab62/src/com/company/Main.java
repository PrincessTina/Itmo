package com.company;


import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Main {
    public static void main(String[] args){
        // windows's settings
        Display display = new Display();
        Shell shell = new Shell(display);
        shell.setText("Platoon Manager");
        shell.setSize(1010,690);
        shell.setBackground(new Color (display, 255, 255, 255));
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

        file.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event) {
                if(event.detail == SWT.NONE) {
                    Rectangle bounds = file.getBounds();
                    Point point = toolBar.toDisplay(bounds.x, bounds.y + bounds.height);
                    menu.setLocation(point);
                    menu.setVisible(true);
                }
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

        command.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event) {
                if(event.detail == SWT.NONE) {
                    Rectangle bounds = command.getBounds();
                    Point point = toolBar.toDisplay(bounds.x, bounds.y + bounds.height);
                    commandsMenu.setLocation(point);
                    commandsMenu.setVisible(true);
                }
            }
        });

        ToolItem help = new ToolItem(toolBar, SWT.PUSH);
        help.setText("Help");

        // field where words for search will be written
        final Text location = new Text(shell, SWT.BORDER);
        data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.horizontalSpan = 2;
        data.grabExcessHorizontalSpace = true;
        location.setLayoutData(data);

        // new column for listing
        data = new GridData(SWT.FILL, SWT.FILL, true, true);
        Composite listingWindow = new Composite (shell, SWT.BORDER);
        listingWindow.setLayoutData(data);
        listingWindow.setLayout(new GridLayout());
        listingWindow.setBackgroundImage(new Image (display, "C:\\Users\\Queen\\Documents\\itmo\\2.jpg"));

        // list of the shorty's
        Tree tree = new Tree(listingWindow, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
        tree.setHeaderVisible(true);
        TreeColumn column1 = new TreeColumn(tree, SWT.LEFT);
        column1.setText("Name");
        column1.setWidth(100);
        TreeColumn column2 = new TreeColumn(tree, SWT.LEFT);
        column2.setText("Age");
        column2.setWidth(50);
        TreeColumn column3 = new TreeColumn(tree, SWT.LEFT);
        column3.setText("Height");
        column3.setWidth(70);
        TreeColumn column4 = new TreeColumn(tree, SWT.LEFT);
        column4.setText("Hobby");
        column4.setWidth(100);
        TreeColumn column5 = new TreeColumn(tree, SWT.LEFT);
        column5.setText("Status");
        column5.setWidth(100);

        // new column for browser
        data = new GridData(SWT.FILL, SWT.FILL, true, true);
        Composite browserWindow = new Composite (shell, SWT.BORDER);
        browserWindow.setLayoutData(data);
        browserWindow.setLayout(new GridLayout());
        new Label(browserWindow, SWT.FILL).setText("Search");

        // browser's location in column
        final Browser browser = new Browser(browserWindow, 0);
        data = new GridData();
        data.horizontalAlignment = SWT.FILL;
        data.verticalAlignment = SWT.FILL;
        data.grabExcessHorizontalSpace = true;
        data.grabExcessVerticalSpace = true;
        browser.setLayoutData(data);

        // search event
        location.addListener(SWT.DefaultSelection, new Listener() {
          public void handleEvent(Event e) {
            String url = "";
            for(String tokens: location.getText().split(" ")) {
              url = url + tokens + "%20";
            }
            browser.setUrl("https://yandex.ru/search/?text=" + url);
          }
        });

      dayLight.addListener (SWT.Selection, new Listener () {
        public void handleEvent (Event e) {
          shell.setBackground(new Color (display, 255, 255, 255));
          browserWindow.setBackground(new Color (display, 230, 230, 230));
        }
      });

      nightTime.addListener (SWT.Selection, new Listener () {
        public void handleEvent (Event e) {
          shell.setBackground(new Color (display, 209, 28, 9));
          browserWindow.setBackground(new Color (display, 0, 0, 0));
        }
      });

      blonde.setText("Blonde");
      blonde.addListener (SWT.Selection, new Listener () {
        public void handleEvent (Event e) {
          shell.setBackground(new Color (display, 0, 0, 0));
          browserWindow.setBackground(new Color (display, 207, 127, 199));
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

}