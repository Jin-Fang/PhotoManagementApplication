package front_end;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import back_end.FileManager;
import back_end.FileNode;
import back_end.Tag;
import back_end.TagManager;
import photo_renamer.PhotoRenamer;

public class AdvancedFeatures implements WindowListener{
	

	public static JFrame buildAdvancedFeaturesWindow(JPanel imageDisplayPanel2, JPanel deleteTagPanel2, JPanel oldNamePanel2){
		JFrame featureFrame = new JFrame();
		featureFrame.setBounds(110, 300, 550, 400);
		JComponent contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		featureFrame.setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JLabel featureLabel = new JLabel("Enjoy Advanced Features");
		featureLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(featureLabel, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(8, 1, 0, 0));
		
		JButton mostUsedTagB = new JButton("Get Most Frequently Used Tag");
		panel_1.add(mostUsedTagB);
		
		
		JButton mostSimilarImagesB = new JButton("Get Images with Most Similar Tag");
		panel_1.add(mostSimilarImagesB);
		
		JButton mostTagImage = new JButton("Get Image with Most Tags");
		panel_1.add(mostTagImage);
		
		JButton removeAllTagsB = new JButton("Remove All Tags from All Images");
		panel_1.add(removeAllTagsB);
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);
		
		JLabel dateInputLabel = new JLabel("Type in date (yyyy-MM-dd HH:mm:ss)");
		panel_1.add(dateInputLabel);
		
		JTextArea dateInputText = new JTextArea();
		dateInputText.setFont(new Font("Monaco", Font.PLAIN, 13));
		panel_1.add(dateInputText);
		
		JButton revertToDateB = new JButton("Revert To That Date");
		panel_1.add(revertToDateB);
		
		JPanel outputPanel = new JPanel();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(scrollPane);
		scrollPane.setViewportView(outputPanel);
		
		
		mostUsedTagB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				outputPanel.removeAll();
				JTextArea output = new JTextArea();
				output.setText(TagManager.findMostUsedTag().toString());
				output.setEditable(false);
				outputPanel.add(output);
				featureLabel.setText("Most frequently used tag found.");
			}
			
		});
		
		mostTagImage.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				outputPanel.removeAll();
				
				FileNode mostTagFileNode = FileManager.findImageWithMostTags();
				BufferedImage img = null;
				try {
					img = ImageIO.read(new File(mostTagFileNode.getPath()));
				} catch (IOException e1) {
				}
				ImageIcon icon = new ImageIcon(img);
				
				JLabel imageLabel = new JLabel(null, icon, JLabel.CENTER);
				outputPanel.add(imageLabel);
				
				featureLabel.setText("Image with most tags found.");
			}
			
		});
		
		mostSimilarImagesB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				outputPanel.removeAll();
				ArrayList<FileNode[]> temp = FileManager.findImageWithMostSimilarTag();
				ArrayList<FileNode> result = new ArrayList<FileNode>();
				
				for (FileNode[] fna: temp){
					for (FileNode fn : fna){
						if (! result.contains(fn)){
							result.add(fn);
						}
					}
				}
				
				JPanel imageOutPutPanel = new JPanel();
				imageOutPutPanel.setLayout(new GridLayout(10,1,0,0));
				for (FileNode f : result){
					BufferedImage img = null;
					try {
						img = ImageIO.read(new File(f.getPath()));
					} catch (IOException e3) {
					}
					ImageIcon icon = new ImageIcon(img);
					
					JLabel imageLabel1 = new JLabel(null, icon, JLabel.CENTER);
					imageOutPutPanel.add(imageLabel1);
				}
				outputPanel.add(imageOutPutPanel);
				
				featureLabel.setText("Images with most similar tags found.");
				
			}
			
		});
		
		revertToDateB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				outputPanel.removeAll();
				imageDisplayPanel2.removeAll();
				for (TagCheckBox tcb: PhotoRenamer.deleteTagPanelList){
					deleteTagPanel2.remove(tcb);
				}
				
				for (OldNameRadioButton tcb: PhotoRenamer.oldNamePanelList){
					oldNamePanel2.remove(tcb);
				}
				
				
				String dateString = dateInputText.getText();
				
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				try {
					Date date1 = fmt.parse(dateString);
					boolean flag = FileManager.revertToDate(date1);
					if (flag){
						featureLabel.setText("Reversion completed.\n Please reload images.");
					}
					else{
						featureLabel.setText("Date input is not valid, please input date in correct format.");
					}
				} catch (ParseException e1) {
					e1.printStackTrace();
					featureLabel.setText("Date input is not valid, please input date in correct format.");
				}
				outputPanel.revalidate();
				outputPanel.repaint();
				imageDisplayPanel2.revalidate();
				imageDisplayPanel2.repaint();
				deleteTagPanel2.revalidate();
				deleteTagPanel2.repaint();
				oldNamePanel2.revalidate();
				oldNamePanel2.repaint();
				
			}
			
		});
		
		removeAllTagsB.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				outputPanel.removeAll();
				imageDisplayPanel2.removeAll();
				for (TagCheckBox tcb: PhotoRenamer.deleteTagPanelList){
					deleteTagPanel2.remove(tcb);
				}
				
				for (OldNameRadioButton tcb: PhotoRenamer.oldNamePanelList){
					oldNamePanel2.remove(tcb);
				}
				
				for (Tag t : TagManager.tagList){
					try {
						FileManager.deleteTagFromAllImage(t);
						featureLabel.setText("Deletion completed.\n Please reload images.");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				outputPanel.revalidate();
				outputPanel.repaint();
				imageDisplayPanel2.revalidate();
				imageDisplayPanel2.repaint();
				deleteTagPanel2.revalidate();
				deleteTagPanel2.repaint();
				oldNamePanel2.revalidate();
				oldNamePanel2.repaint();
			}
			
			
		});
		
		return featureFrame;
	}
	
	
	

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}

	
}
