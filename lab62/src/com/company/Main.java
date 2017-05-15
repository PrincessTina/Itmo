package com.company;


import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

public class Main {
    public static void main(String[] args){
        Display display = new Display();
        Shell shell = new Shell(display);
        GridLayout layout = new GridLayout();
        shell.setLayout(layout);
        layout.numColumns = 3;
        shell.setText("Platoon Manager");
        shell.setSize(700,500);

        ToolBar toolbar = new ToolBar(shell, SWT.HORIZONTAL);
        ToolItem File = new ToolItem(toolbar, SWT.PUSH);
        File.setText("File");
        File.setToolTipText("Open File");
        Menu menu = new Menu(shell, SWT.POP_UP);
        MenuItem New = new MenuItem(menu, SWT.PUSH);
        New.setText("New");
        MenuItem Open= new MenuItem(menu, SWT.PUSH);
        Open.setText("Open");
        new MenuItem(menu, SWT.SEPARATOR);
        MenuItem Save= new MenuItem(menu, SWT.PUSH);
        Save.setText("Save");
        new MenuItem(menu, SWT.SEPARATOR);
        MenuItem Info= new MenuItem(menu, SWT.PUSH);
        Info.setText("Info About your Platoon");
        File.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event) {
                if(event.detail == SWT.NONE) {
                    Rectangle bounds = File.getBounds();
                    Point point = toolbar.toDisplay(bounds.x, bounds.y + bounds.height);
                    menu.setLocation(point);
                    menu.setVisible(true);
                }
            }
        });
        Listener selectionListener = new Listener() {
            public void handleEvent(Event event) {
                ToolItem item = (ToolItem)event.widget;
                System.out.println(item.getText() + " is selected");
                if( (item.getStyle() & SWT.RADIO) != 0 || (item.getStyle() & SWT.CHECK) != 0 )
                    System.out.println("Selection status: " + item.getSelection());
            }
        };
        ToolItem Help = new ToolItem(toolbar, SWT.PUSH);
        Help.setText("Help");
        Help.setToolTipText("There is no Help");

        GridData gridDataTool = new GridData();
        gridDataTool.horizontalAlignment = GridData.FILL;
        gridDataTool.horizontalSpan = 3;
        toolbar.setLayoutData(gridDataTool);






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