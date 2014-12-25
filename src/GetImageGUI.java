import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.graphics.Image;

///////////GUI for image downloader///////////

public class GetImageGUI extends Composite {

	public static GetImage getImage;

	public static void main(String[]args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		GetImageGUI imageGui = new GetImageGUI(shell, SWT.NONE);

		getImage = new GetImage(imageGui);

		imageGui.pack();
		shell.pack();
		shell.open();
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch())display.sleep();
		}
	}

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Text text_url;
	private Text text_folderDirectory;
	private StyledText imageHeader;
	boolean folderSelected, downloaded, urlEntered;
	StyledText downloadInfo;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */

	public GetImageGUI(Composite parent, int style) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		setLayout(null);

		text_url = new Text(this, SWT.BORDER);
		text_url.setBounds(48, 62, 397, 27);
		toolkit.adapt(text_url, true, true);

		Label lblEnterUrl = new Label(this, SWT.NONE);
		lblEnterUrl.setBounds(48, 31, 55, 15);
		toolkit.adapt(lblEnterUrl, true, true);
		lblEnterUrl.setText("Enter URL");

		Label lblEnterDirectory = new Label(this, SWT.NONE);
		lblEnterDirectory.setBounds(48, 106, 85, 15);
		toolkit.adapt(lblEnterDirectory, true, true);
		lblEnterDirectory.setText("Enter Directory");

		text_folderDirectory = new Text(this, SWT.BORDER);
		text_folderDirectory.setBounds(47, 138, 398, 27);
		toolkit.adapt(text_folderDirectory, true, true);

		final Button setFolder = new Button(this, SWT.NONE);
		setFolder.setBounds(370, 196, 75, 25);
		setFolder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dlg = new DirectoryDialog(setFolder.getShell(), SWT.OPEN);
				String path = dlg.open();
				if (path == null){
					text_folderDirectory.setText("select directory");
					return;
				}
				else{
					text_folderDirectory.setText(path);
					folderSelected = true;
				}
			}
		});
		toolkit.adapt(setFolder, true, true);
		setFolder.setText("Select Folder");

		Button btnDownload = new Button(this, SWT.NONE);
		btnDownload.setBounds(370, 255, 75, 25);
		btnDownload.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				getImage.setDirectory(text_folderDirectory.getText());

				if(!folderSelected)
					downloadInfo.setText("No directory selected!");

				if(text_url.getText().toLowerCase().startsWith("http://")) {
					urlEntered = true;
					getImage.setUrl(text_url.getText());
				}

				else {
					downloadInfo.setText("Please enter a valid URL!");
					urlEntered = false;
				}

				if(urlEntered && folderSelected){	
					downloadInfo.setText("Attempting download...");
					try {
						getImage.run();
						downloaded = true;
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		toolkit.adapt(btnDownload, true, true);
		btnDownload.setText("Download");

		Composite composite = new Composite(this, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_CYAN));
		composite.setBounds(0, 0, 513, 418);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);

		Button btnImages = new Button(composite, SWT.NONE);
		btnImages.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				DisplayPics pics = new DisplayPics(getShell(), getShell().getStyle());
				pics.setDirectory(text_folderDirectory.getText());
				pics.open();
			}
		});

		btnImages.setBounds(371, 311, 75, 25);
		toolkit.adapt(btnImages, true, true);
		btnImages.setText("See Images");

		TextViewer textViewer = new TextViewer(composite, SWT.BORDER);
		downloadInfo = textViewer.getTextWidget();
		downloadInfo.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				downloadInfo.setTopIndex(downloadInfo.getLineCount() - 1);
			}
		});
		downloadInfo.setText("Select download folder to begin.");

		downloadInfo.setBounds(48, 196, 279, 136);
		toolkit.paintBordersFor(downloadInfo);

		Label label = new Label(composite, SWT.NONE);
		label.setImage(new Image(Display.getCurrent(), GetImageGUI.class.getResourceAsStream("background.jpg")));
		label.setBounds(0, 0, 513, 418);
		toolkit.adapt(label, true, true);

		imageHeader = new StyledText(this, SWT.BORDER);
		imageHeader.setLocation(0, 0);
		imageHeader.setSize(298, 176);
		toolkit.adapt(imageHeader);
		toolkit.paintBordersFor(imageHeader);
	}

	public void printDirect(String info) {
		downloadInfo.setText(info);
	}

	public void printInfo(String info) {
		downloadInfo.append("\n" + info);
	}
}