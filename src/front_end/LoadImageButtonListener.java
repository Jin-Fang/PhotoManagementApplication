package front_end;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import back_end.FileManager;
import back_end.FileNode;
import back_end.FileType;
import back_end.TagManager;

public class LoadImageButtonListener implements ActionListener{
	/** The window the button is in. */
	private JFrame directoryFrame;
	/** The label for the full path to the chosen directory. */
	private JLabel directoryLabel;
	/** The file chooser to use when the user clicks. */
	private JFileChooser fileChooser;
	/** The area to use to display the nested directory contents. */
	private JPanel imageDisplayPanel;
	private JPanel addTagPanel;
	private JPanel deleteTagPanel;
	private JPanel oldNamePanel;

	/**
	 * An action listener for window dirFrame, displaying a file path on
	 * dirLabel, using fileChooser to choose a file.
	 *
	 * @param dirFrame
	 *            the main window
	 * @param dirLabel
	 *            the label for the directory path
	 * @param fileChooser
	 *            the file chooser to use
	 */
	public LoadImageButtonListener(JFrame dirFrame, JLabel dirLabel, JPanel imageDisplayPanel, JFileChooser fileChooser, JPanel addTagPanel, JPanel deleteTagPanel, JPanel oldNamePanel) {
		this.directoryFrame = dirFrame;
		this.directoryLabel = dirLabel;
		this.imageDisplayPanel = imageDisplayPanel;
		this.fileChooser = fileChooser;
		this.addTagPanel = addTagPanel;
		this.deleteTagPanel = deleteTagPanel;
		this.oldNamePanel = oldNamePanel;
	}

	/**
	 * Handle the user clicking on the open button.
	 *
	 * @param e
	 *            the event object
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		int returnVal = fileChooser.showOpenDialog(directoryFrame.getContentPane());

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			if (file.exists()) {
				directoryLabel.setText("Selected Directory" + file.getAbsolutePath() + "CLICK image to add/delete tags");

				// Make the root.
				FileNode root = new FileNode(file.getName(), null, FileType.DIRECTORY, file.getAbsolutePath());
				try {
					ArrayList<FileNode> allFile = FileManager.Deser();
					Boolean flag = false;
					FileNode temp = null;
					for (FileNode fn: allFile){
						if (fn.getPath().equals(root.getPath())){
							flag = true;
							temp = fn;
							break;
						}
					}
					if (flag){
						FileManager.buildFileList(temp);
					}
					else{
						FileManager.buildFileList(file, root);
					}
				} catch (ClassNotFoundException | IOException e1) {
					e1.printStackTrace();
				}
				// create image button
				for (FileNode fn: FileManager.fileList){
					if(!fn.isDirectory()){
					ImageIcon ii = new ImageIcon(fn.getPath());
					ImageButton bt = new ImageButton(ii, fn);
					/* add action listener */
					ActionListener imageAL = new ImageButtonListener(deleteTagPanel, oldNamePanel, directoryLabel, bt);
					bt.addActionListener(imageAL);
					this.imageDisplayPanel.add(bt);
					this.imageDisplayPanel.revalidate();
					this.imageDisplayPanel.repaint();
					}
					//System.out.println(bt.getFileNode());
				}
			}
		} else {
			directoryLabel.setText("No Path Selected");
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
