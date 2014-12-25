import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.wb.swt.SWTResourceManager;

///////////GUI for displaying the downloaded images///////////

public class DisplayPics extends Dialog {

	protected Shell shell;
	protected Object result;
	public String directory;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */

	public DisplayPics(Shell parent, int style) {
		super(parent, style);
	}

	public void open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	public void setDirectory(String dir) {
		directory = dir;
	}

	public void createContents() {
		//super(parent, style);
		shell = new Shell();
		shell.setBackground(SWTResourceManager.getColor(176, 224, 230));
		shell.setSize(1365, 817);
		//shell.setSize(1280, 800);
		shell.setText("Downloaded Images");
		shell.setLayout(new GridLayout(4, false));

		Label pic1 = new Label(shell, SWT.NONE);
		pic1.setBackground(SWTResourceManager.getColor(176, 224, 230));
		pic1.setImage(SWTResourceManager.getImage(directory + "\\" + "Image 1.jpeg"));
		GridData gd_pic1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_pic1.widthHint = 346;
		gd_pic1.heightHint = 237;
		pic1.setLayoutData(gd_pic1);

		Label pic2 = new Label(shell, SWT.NONE);
		pic2.setBackground(SWTResourceManager.getColor(176, 224, 230));
		pic2.setImage(SWTResourceManager.getImage(directory + "\\" + "Image 2.jpeg"));
		GridData gd_pic2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_pic2.widthHint = 336;
		pic2.setLayoutData(gd_pic2);

		Label pic3 = new Label(shell, SWT.NONE);
		pic3.setBackground(SWTResourceManager.getColor(176, 224, 230));
		pic3.setImage(SWTResourceManager.getImage(directory + "\\" + "Image 3.jpeg"));
		GridData gd_pic3 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_pic3.widthHint = 332;
		pic3.setLayoutData(gd_pic3);

		Label pic4 = new Label(shell, SWT.NONE);
		pic4.setBackground(SWTResourceManager.getColor(176, 224, 230));
		pic4.setImage(SWTResourceManager.getImage(directory + "\\" + "Image 4.jpeg"));
		GridData gd_pic4 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_pic4.widthHint = 324;
		pic4.setLayoutData(gd_pic4);

		Label pic5 = new Label(shell, SWT.NONE);
		pic5.setBackground(SWTResourceManager.getColor(176, 224, 230));
		pic5.setImage(SWTResourceManager.getImage(directory + "\\" + "Image 5.jpeg"));
		GridData gd_pic5 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_pic5.heightHint = 189;
		pic5.setLayoutData(gd_pic5);

		Label pic6 = new Label(shell, SWT.NONE);
		pic6.setImage(SWTResourceManager.getImage(directory + "\\" + "Image 6.jpeg"));

		Label pic7 = new Label(shell, SWT.NONE);
		pic7.setImage(SWTResourceManager.getImage(directory + "\\" + "Image 7.jpeg"));

		Label pic8 = new Label(shell, SWT.NONE);
		pic8.setImage(SWTResourceManager.getImage(directory + "\\" + "Image 8.jpeg"));

		Label pic9 = new Label(shell, SWT.NONE);
		pic6.setImage(SWTResourceManager.getImage(directory + "\\" + "Image 9.jpeg"));
		GridData gd_pic9 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_pic9.heightHint = 193;
		pic9.setLayoutData(gd_pic9);

		Label pic10 = new Label(shell, SWT.NONE);
		pic10.setImage(SWTResourceManager.getImage(directory + "\\" + "Image 10.jpeg"));

		Label pic11 = new Label(shell, SWT.NONE);
		pic11.setImage(SWTResourceManager.getImage(directory + "\\" + "Image 11.jpeg"));

		Label pic12 = new Label(shell, SWT.NONE);
		pic6.setImage(SWTResourceManager.getImage(directory + "\\" + "Image 12.jpeg"));
		pic12.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));

		Label pic13 = new Label(shell, SWT.NONE);
		pic6.setImage(SWTResourceManager.getImage(directory + "\\" + "Image 13.jpeg"));
		GridData gd_pic13 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_pic13.heightHint = 208;
		pic13.setLayoutData(gd_pic13);

		Label pic14 = new Label(shell, SWT.NONE);
		pic14.setImage(SWTResourceManager.getImage(directory + "\\" + "Image 14.jpeg"));

		Label pic15 = new Label(shell, SWT.NONE);
		pic15.setImage(SWTResourceManager.getImage(directory + "\\" + "Image 15.jpeg"));
		pic15.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, true, 1, 1));

		Label pic16 = new Label(shell, SWT.NONE);
		pic16.setImage(SWTResourceManager.getImage(directory + "\\" + "Image 16.jpeg"));
	}
}