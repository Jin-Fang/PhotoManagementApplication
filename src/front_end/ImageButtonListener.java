package front_end;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import back_end.FileManager;
import back_end.FileNode;
import back_end.Tag;
import photo_renamer.PhotoRenamer;

public class ImageButtonListener implements ActionListener{
	/** The panel the button will be loaded by this button. */
	private JPanel deleteTagPanel;
	/** The panel the button will be loaded by this button. */
	private JPanel oldNamePanel;
	/** The label for the full path to the chosen directory. */
	private JLabel directoryLabel;
	private ImageButton bt;
	/**
	 * An action listener for window dirFrame, displaying a file path on
	 * dirLabel, using fileChooser to choose a file.
	 *
	 * @param deleteTagPanel
	 *            
	 * @param addTagPanel
	 *            
	 * @param directoryLabel
	 *            
	 */
	public ImageButtonListener(JPanel deleteTagPanel, JPanel oldNamePanel, JLabel directoryLabel, ImageButton bt) {
		this.deleteTagPanel = deleteTagPanel;
		this.oldNamePanel = oldNamePanel;
		this.directoryLabel = directoryLabel;
		this.bt = bt;
	}
	
	/**
	 * Handle the user clicking on the open button.
	 *
	 * @param e
	 *            the event object
	 */
	public void actionPerformed(ActionEvent e) {
		
		PhotoRenamer.currentFileNode = bt.getFileNode();
		System.out.println(PhotoRenamer.currentFileNode.getOldNames());
		
		/* Add the TagCheckBox to deletTagePanel */
		for(TagCheckBox b : PhotoRenamer.deleteTagPanelList){
			deleteTagPanel.remove(b);
		}
		PhotoRenamer.deleteTagPanelList.clear();
		
		for (Tag t: bt.getFileNode().getTags()){
			TagCheckBox tagCB = new TagCheckBox(t.getTagName(), t); 
			this.deleteTagPanel.add(tagCB);
			PhotoRenamer.deleteTagPanelList.add(tagCB);
		}
		this.deleteTagPanel.revalidate();
		this.deleteTagPanel.repaint();
		
		/* Add the OldNames to OldName Panel */
		for(OldNameRadioButton b : PhotoRenamer.oldNamePanelList){
			oldNamePanel.remove(b);
		}
		PhotoRenamer.oldNamePanelList.clear();
		
		ButtonGroup buttonGroup = new ButtonGroup();
		for (String oldName: bt.getFileNode().getOldNames().keySet()){
			OldNameRadioButton oldNameB = new OldNameRadioButton(oldName, oldName);
			buttonGroup.add(oldNameB);
			this.oldNamePanel.add(oldNameB);
			PhotoRenamer.oldNamePanelList.add(oldNameB);
		}
		this.oldNamePanel.revalidate();
		this.oldNamePanel.repaint();
		
		directoryLabel.setText("Operating on: " + bt.getFileNode().getName() + " (" + bt.getFileNode().getPath() + ")");
	}
	
}
