package front_end;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import back_end.FileManager;
import back_end.Tag;
import back_end.TagManager;
import photo_renamer.PhotoRenamer;

public class ManageTagList {


	public static ArrayList<TagCheckBox> allTagsPanelList = new ArrayList<TagCheckBox>();
	//public static boolean isTagManagerFrameOpen = false;
	

	public static JFrame buildTagManagerWindow(JPanel addTagPanel, JPanel deleteTagPanel, JPanel oldNamePanel, JLabel directoryLabel) {
		JFrame tagManagerFrame = new JFrame();
		
		tagManagerFrame.addWindowListener(new WindowListener(){

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void windowClosing(WindowEvent e) {
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				tagManagerFrame.dispose();
				ManageTagList.allTagsPanelList.clear();
			}
			
		});

		tagManagerFrame.setBounds(680, 300, 300, 400);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		tagManagerFrame.setContentPane(contentPane);

		JPanel addNewTagPanel = new JPanel();
		contentPane.add(addNewTagPanel, BorderLayout.NORTH);
		addNewTagPanel.setLayout(new GridLayout(0, 2, 0, 0));

		JTextField createNewTagText = new JTextField();
		addNewTagPanel.add(createNewTagText);
		createNewTagText.setColumns(10);

		JButton createNewTagB = new JButton("Create New Tag");
		addNewTagPanel.add(createNewTagB);

		JPanel allTagDisplayPanel = new JPanel();

		JScrollPane allTagDisplayPanelScroll = new JScrollPane();
		allTagDisplayPanelScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		allTagDisplayPanelScroll.setViewportView(allTagDisplayPanel);
		allTagDisplayPanel.setLayout(new GridLayout(40, 1, 0, 0));

		contentPane.add(allTagDisplayPanelScroll, BorderLayout.CENTER);
		
		////////
//		if (ManageTagList.allTagsPanelList.isEmpty()){
			for (Tag t : TagManager.tagList) {
				TagCheckBox tagCB = new TagCheckBox(t.getTagName(), t);
//				if (!ManageTagList.allTagsPanelList.contains(tagCB)) {
					allTagDisplayPanel.add(tagCB);
					ManageTagList.allTagsPanelList.add(tagCB);
//				}
			}
//		}else{
//			for (TagCheckBox tcb : ManageTagList.allTagsPanelList){
//				allTagDisplayPanel.add(tcb);
//			}
//		}
		
		JButton deleteB = new JButton("Delete Tag From Tag List");
		contentPane.add(deleteB, BorderLayout.SOUTH);
		deleteB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArrayList<TagCheckBox> temp = new ArrayList<TagCheckBox>();
				//find selected check box
				for (TagCheckBox tcb : ManageTagList.allTagsPanelList){
					if (tcb.isSelected()){
						temp.add(tcb);
					}
				}
				for (TagCheckBox tcb1: temp){
					allTagDisplayPanel.remove(tcb1);
					ManageTagList.allTagsPanelList.remove(tcb1);
					TagManager.deleteTag(tcb1.getTag());
					tagManagerFrame.revalidate();
					tagManagerFrame.repaint();
				}
				
				ArrayList<TagCheckBox> temp1 = new ArrayList<TagCheckBox>();
				for (TagCheckBox tcb1 : PhotoRenamer.addTagPanelList){
					for (TagCheckBox tcb2 : temp){
						if(tcb1.getText().equals(tcb2.getText())){
							temp1.add(tcb1);
						}
					}
				}
				for (TagCheckBox tcb3: temp1){
					addTagPanel.remove(tcb3);
					PhotoRenamer.addTagPanelList.remove(tcb3);
					addTagPanel.revalidate();
					addTagPanel.repaint();
				}
				
				
				/*reload the deleteTagPanel and oldNamePanel*/
				
				for(TagCheckBox b : PhotoRenamer.deleteTagPanelList){
					deleteTagPanel.remove(b);
				}
				PhotoRenamer.deleteTagPanelList.clear();
				
				for (Tag t: PhotoRenamer.currentFileNode.getTags()){
					TagCheckBox tagCB = new TagCheckBox(t.getTagName(), t); 
					deleteTagPanel.add(tagCB);
					PhotoRenamer.deleteTagPanelList.add(tagCB);
				}
				deleteTagPanel.revalidate();
				deleteTagPanel.repaint();
				
				/* Add the OldNames to OldName Panel */
				for(OldNameRadioButton b : PhotoRenamer.oldNamePanelList){
					oldNamePanel.remove(b);
				}
				PhotoRenamer.oldNamePanelList.clear();
				
				ButtonGroup buttonGroup = new ButtonGroup();
				for (String oldName: PhotoRenamer.currentFileNode.getOldNames().keySet()){
					OldNameRadioButton oldNameB = new OldNameRadioButton(oldName, oldName);
					buttonGroup.add(oldNameB);
					oldNamePanel.add(oldNameB);
					PhotoRenamer.oldNamePanelList.add(oldNameB);
				}
				oldNamePanel.revalidate();
				oldNamePanel.repaint();
				for (OldNameRadioButton tcb: PhotoRenamer.oldNamePanelList){
				System.out.println(tcb.getName());}
				directoryLabel.setText("Operating on: " + PhotoRenamer.currentFileNode.getName() + " (" + PhotoRenamer.currentFileNode.getPath() + ")");
			}});

		// ActionListener createNewTagBL = new
		// CreateNewTagButtonListener(createNewTagText, allTagDisplayPanel,
		// addTagPanel);
		createNewTagB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newTagText = createNewTagText.getText();
				if (!createNewTagText.getText().equals("")) {
					Tag newTag = new Tag(newTagText);
					TagManager.addTag(newTag);
					//search the check boxes in the panel to find if the tag already exits
					boolean flag = false;
					for (TagCheckBox tt : ManageTagList.allTagsPanelList){
						if (tt.getTag().getTagName().equals(newTag.getTagName())){
							flag = true;
						}
					}
					//add the tag check box to the allTagDisplayPanel
					if (!flag) {
						TagCheckBox newTagCB1 = new TagCheckBox(newTag.getTagName(), newTag);
						ManageTagList.allTagsPanelList.add(newTagCB1);
						allTagDisplayPanel.add(newTagCB1);
						tagManagerFrame.revalidate();
						tagManagerFrame.repaint();
					}
					//search the check boxes in the panel to find if the tag already exits
					boolean flag1 = false;
					for (TagCheckBox t : PhotoRenamer.addTagPanelList){
						if (t.getTag().getTagName().equals(newTag.getTagName())){
							flag1 = true;
						}
					}
					//add the tag check box to the addTagPanel
					if (!flag1){
						TagCheckBox newTagCB = new TagCheckBox(newTag.getTagName(), newTag);
						
						PhotoRenamer.addTagPanelList.add(newTagCB);
						addTagPanel.add(newTagCB);
						PhotoRenamer.addTagPanelList.add(newTagCB);
						addTagPanel.revalidate();
						addTagPanel.repaint();
					}
				}
			}
		});

		return tagManagerFrame;
	}
}
