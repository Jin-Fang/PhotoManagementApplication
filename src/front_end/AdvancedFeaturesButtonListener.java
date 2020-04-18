package front_end;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class AdvancedFeaturesButtonListener implements ActionListener{

	private JPanel imageDisplayPanel;
	private JPanel deleteTagPanel;
	private JPanel oldNamePanel;

	public AdvancedFeaturesButtonListener(JPanel imageDisplayPanel, JPanel deleteTagPanel, JPanel oldNamePanel) {
		this.imageDisplayPanel = imageDisplayPanel;
		this.deleteTagPanel = deleteTagPanel;
		this.oldNamePanel = oldNamePanel;
	}


	/**
	 * @return the imageDisplayPanel
	 */
	public JPanel getImageDisplayPanel() {
		return imageDisplayPanel;
	}

	/**
	 * @param imageDisplayPanel the imageDisplayPanel to set
	 */
	public void setImageDisplayPanel(JPanel imageDisplayPanel) {
		this.imageDisplayPanel = imageDisplayPanel;
	}

	/**
	 * @return the deleteTagPanel
	 */
	public JPanel getDeleteTagPanel() {
		return deleteTagPanel;
	}

	/**
	 * @param deleteTagPanel the deleteTagPanel to set
	 */
	public void setDeleteTagPanel(JPanel deleteTagPanel) {
		this.deleteTagPanel = deleteTagPanel;
	}

	/**
	 * @return the oldNamePanel
	 */
	public JPanel getOldNamePanel() {
		return oldNamePanel;
	}

	/**
	 * @param oldNamePanel the oldNamePanel to set
	 */
	public void setOldNamePanel(JPanel oldNamePanel) {
		this.oldNamePanel = oldNamePanel;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		AdvancedFeatures.buildAdvancedFeaturesWindow(imageDisplayPanel, deleteTagPanel, oldNamePanel).setVisible(true);	
	}
}
