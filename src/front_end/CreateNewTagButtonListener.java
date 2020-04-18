package front_end;
//integrated to ManageTagList.java

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import back_end.Tag;
import back_end.TagManager;
import photo_renamer.PhotoRenamer;

public class CreateNewTagButtonListener implements ActionListener{

	/** The button that . */
	private JTextField createNewTagText;
	private JPanel allTagDisplayPanel;
	private JPanel addTagPanel;
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
	public CreateNewTagButtonListener(JTextField createNewTagText, JPanel allTagDisplayPanel,JPanel addTagPanel) {
		this.createNewTagText = createNewTagText;
		this.allTagDisplayPanel = allTagDisplayPanel;
		this.addTagPanel = addTagPanel;

	}
	
	/**
	 * Handle the user clicking on the open button.
	 *
	 * @param e
	 *            the event object
	 */
	public void actionPerformed(ActionEvent e) {
		String newTagText = this.createNewTagText.getText();
		if (newTagText != ""){
			Tag newTag = new Tag(newTagText);
			TagManager.addTag(newTag);
			TagCheckBox newTagCB = new TagCheckBox(newTag.getTagName(), newTag);
			System.out.println(newTagCB);
			if (!ManageTagList.allTagsPanelList.contains(newTagCB)){
				this.allTagDisplayPanel.add(newTagCB);
				newTagCB.setVisible(true);
				this.allTagDisplayPanel.repaint();
				this.allTagDisplayPanel.revalidate();
//				ManageTagList.tagManagerFrame.revalidate();
//				ManageTagList.tagManagerFrame.repaint();
			}
			if (!PhotoRenamer.addTagPanelList.contains(newTagCB)){
				this.addTagPanel.add(newTagCB);
				PhotoRenamer.addTagPanelList.add(newTagCB);
			}
//			for (Tag t : TagManager.tagList){
//				TagCheckBox tagCB = new TagCheckBox(t.getTagName(), t);
//				if (!ManageTagList.allTagsPanelList.contains(tagCB)){
//					allTagDisplayPanel.add(tagCB);
//					ManageTagList.allTagsPanelList.add(tagCB);
//				}
//			}
		}
	}

}
