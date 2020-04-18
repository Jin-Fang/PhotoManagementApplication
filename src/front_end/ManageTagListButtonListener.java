package front_end;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import back_end.Tag;
import back_end.TagManager;

public class ManageTagListButtonListener implements ActionListener{
	
	private JPanel addTagPanel;
	private JPanel deleteTagPanel;
	private JPanel oldNamePanel;
	private JLabel directoryLabel;
	
	
	public ManageTagListButtonListener(JPanel addTagPanel, JPanel deleteTagPanel, JPanel oldNamePanel, JLabel directoryLabel){
		this.addTagPanel = addTagPanel;
		this.deleteTagPanel = deleteTagPanel;
		this.oldNamePanel = oldNamePanel;
		this.directoryLabel = directoryLabel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
			ManageTagList.buildTagManagerWindow(this.addTagPanel, this.deleteTagPanel, this.oldNamePanel, this.directoryLabel).setVisible(true);
		}
		
		//if (ManageTagList.isTagManagerFrameOpen == false){
//			ManageTagList.buildTagManagerWindow(addTagPanel).setVisible(true);
		//}
//		else{
//			ManageTagList.tagManagerFrame.toFront();
//		}
	}

	

