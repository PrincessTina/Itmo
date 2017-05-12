package com.company;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import javax.naming.ldap.Control;

public class Controller {
  Display display = new Display();
  Shell sh = new Shell(display);
sh.setLayout(new FillLayout());
  Label l = new Label(sh, SWT.BORDER);
l.setText("Hello!");
  l.pack
sh.open();
while (!shell.isDisposed()) {
    if (!display.readAndDispatch())
    {
      display.sleep();
    }
  }
display.dispose();
}
