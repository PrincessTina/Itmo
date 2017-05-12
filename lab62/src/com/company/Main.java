package com.company;


import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class Main {

    public static void main(String[] args) {
      Display display = new Display();
      Shell sh = new Shell(display);
      sh.setLayout(new FillLayout());
      Label l = new Label(sh, SWT.BORDER);
      l.setText("Hello!");
      sh.open();

      display.dispose();
    }
}
