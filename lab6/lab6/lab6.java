
package lab6;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class lab6 {
	public static void main(String[] args){
		Display display = new Display();
		Shell shell = new Shell(display);
		GridLayout layout = new GridLayout();
	    shell.setLayout(layout);
	    layout.numColumns = 3;
		shell.setText("Platoon Manager");
		shell.setSize(700,500);
		
		GridData gridDataTool = new GridData();
		gridDataTool.horizontalAlignment = GridData.FILL;
		gridDataTool.horizontalSpan = 3;
		ToolBar toolbar = new ToolBar(shell, SWT.NONE);
	    ToolItem File = new ToolItem(toolbar, SWT.PUSH);
	    File.setText("File");
	    ToolItem Help = new ToolItem(toolbar, SWT.PUSH);
	    Help.setText("Help");
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
