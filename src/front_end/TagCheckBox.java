package front_end;

import javax.swing.AbstractButton;
import javax.swing.JCheckBox;

import back_end.Tag;

public class TagCheckBox extends JCheckBox{
	
	private Tag tag;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L+5;

	/**
	 * @param text
	 * @param selected
	 */
	public TagCheckBox(String text, boolean selected, Tag tag) {
		super(text, selected);
		this.setTag(tag);
	}

	/**
	 * @param text
	 */
	public TagCheckBox(String text, Tag tag) {
		super(text);
		this.setTag(tag);
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}
	
	@Override
	public boolean equals(Object other){
		if(!(other instanceof TagCheckBox)){
			return false;
		}else{
			return this.getText() == ((AbstractButton) other).getText();
		}
	}
	

}
