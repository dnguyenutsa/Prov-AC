package edu.utsa.cs.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class RequestScreen {
	public static void main(String args[]){
		Display display = new Display();
		Shell shell = new Shell(display);
		// Create a new Gridlayout with 2 columns where the 2 column do no need
		// to be same size
		GridLayout layout = new GridLayout(2, false);
		// set the layout of the shell
		shell.setLayout(layout);
		
		Label objectsLabel = new Label(shell, SWT.BORDER);
		objectsLabel.setText("Pick an object: ");
		
		Combo combo = new Combo (shell, SWT.READ_ONLY);
		combo.setItems (new String [] {"o1v1", "O1v2"});
		Rectangle clientArea = shell.getClientArea();
		combo.setBounds(clientArea.x, clientArea.y, 200, 200);
		
		Label actionsLabel = new Label(shell, SWT.BORDER);
		actionsLabel.setText("Pick an action: ");
		
		combo = new Combo (shell, SWT.READ_ONLY);
		combo.setItems (new String [] {"upload", "replace", "submit"});
		combo.setBounds(clientArea.x, clientArea.y, 200, 200);
		
		Button button;
		button = new Button(shell, SWT.PUSH | SWT.WRAP | SWT.CENTER);
		button.setText("Request");
		
		shell.setSize(shell.computeSize(400, SWT.DEFAULT));
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
