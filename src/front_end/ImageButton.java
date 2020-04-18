package front_end;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

import back_end.FileNode;

public class ImageButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L+3;
	private FileNode fileNode;
	
	

	/**
	 * 
	 */
	public ImageButton() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param a
	 */
	public ImageButton(Action a) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param icon
	 */
	public ImageButton(Icon icon, FileNode fn) {
		super(icon);
		this.fileNode = fn;
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param text
	 */
	public ImageButton(String text) {
		super(text);
		// TODO Auto-generated constructor stub
	}

	public FileNode getFileNode() {
		return fileNode;
	}

	public void setFileNode(FileNode fileNode) {
		this.fileNode = fileNode;
	}

}
