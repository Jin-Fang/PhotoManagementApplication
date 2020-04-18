package front_end;

import javax.swing.JRadioButton;

public class OldNameRadioButton extends JRadioButton{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L+6;
	/**
	 * @return the oldName
	 */
	public String getOldName() {
		return oldName;
	}

	/**
	 * @param oldName the oldName to set
	 */
	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	private String oldName;

	/**
	 * @param text
	 */
	public OldNameRadioButton(String text, String oldName) {
		super(text);
		this.oldName = oldName;
		// TODO Auto-generated constructor stub
	}

	
	
}
