package edu.utsa.cs.gui;


import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;

public class LoginScreenSWT {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		// Create a new Gridlayout with 2 columns where the 2 column do no need
		// to be same size
		GridLayout layout = new GridLayout(2, false);
		// set the layout of the shell
		shell.setLayout(layout);
		
		Label userNameLabel = new Label(shell, SWT.BORDER);
		userNameLabel.setText("User ID: ");
		
		Text text = new Text(shell, SWT.NONE);
		text.setText("");
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		text.setLayoutData(gridData);
		
		Label userPwdLabel = new Label(shell, SWT.BORDER);
		userPwdLabel.setText("Password: ");
		
		text = new Text(shell, SWT.NONE);
		text.setText("");
		text.setLayoutData(gridData);
		
		Label orgLabel = new Label(shell, SWT.BORDER);
		orgLabel.setText("Pick an organization: ");
		
		Combo combo = new Combo (shell, SWT.READ_ONLY);
		combo.setItems (new String [] {"Org1", "Org2"});
		Rectangle clientArea = shell.getClientArea();
		combo.setBounds(clientArea.x, clientArea.y, 200, 200);
		
		Button button;
		button = new Button(shell, SWT.PUSH | SWT.WRAP | SWT.CENTER);
		button.setText("Log in");
		


//		// Create a new label that will spam two columns
//		userNameLabel = new Label(shell, SWT.BORDER);
//		userNameLabel.setText("This is a label");
//		// Create new layout data
//		GridData data = new GridData(GridData.FILL, GridData.BEGINNING, true,
//				false, 2, 1);
//		userNameLabel.setLayoutData(data);
//
//		// Create a new label which is used as a separator
//		userNameLabel = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
//		// Create new layout data
//		data = new GridData(GridData.FILL, GridData.BEGINNING, true,
//				false, 2, 1);
//		data.horizontalSpan=2;
//		userNameLabel.setLayoutData(data);
//
//		// Create a right aligned button
//		Button b = new Button(shell, SWT.PUSH);
//		b.setText("New Button");
//
//		data = new GridData(GridData.END, GridData.BEGINNING, false,
//				false, 2, 1);
//		b.setLayoutData(data);
//
//		Spinner spinner = new Spinner(shell, SWT.READ_ONLY);
//		spinner.setMinimum(0);
//		spinner.setMaximum(1000);
//		spinner.setSelection(500);
//		spinner.setIncrement(1);
//		spinner.setPageIncrement(100);
//		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
//		gridData.widthHint = SWT.DEFAULT;
//		gridData.heightHint = SWT.DEFAULT;
//		gridData.horizontalSpan=2;
//		spinner.setLayoutData(gridData);
//
//		Composite composite = new Composite(shell, SWT.BORDER);
//		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
//		gridData.horizontalSpan= 2;
//		composite.setLayoutData(gridData);
//		composite.setLayout(new GridLayout(1, false));
//
//
//		
//
//		text = new Text(composite, SWT.NONE);
//		text.setText("Another test");
//		//		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
//		//		text.setLayoutData(gridData);
//		Group group = new Group(shell, SWT.NONE);
//		group.setText("This is my group");
//		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
//		gridData.horizontalSpan= 2;
//		group.setLayoutData(gridData);
//		group.setLayout(new RowLayout(SWT.VERTICAL));
//		text = new Text(group, SWT.NONE);
//		text.setText("Another test");


		shell.setSize(shell.computeSize(200, SWT.DEFAULT));
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}
