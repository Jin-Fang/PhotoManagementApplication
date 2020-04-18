package back_end;
import java.io.Serializable;

/**
 * A tag that user can choose to rename photos with. A tag could usually be 
 * person's name, a place where the photo is taken, or other information associated
 * with the photo. The tag class would mainly keep track of the number of times that a
 * tag has been used.
 * <p>
 * @author  Jin Fang, Jingwen Xu
 * @version 1.0
 * @since   2016-11-14
 */

public class Tag implements Serializable {
	
	/**
	 * A unique identifier for this serializable class.
	 */
	private static final long serialVersionUID = 1L + 1;
	/**
	 * The full name of the given tag, with its prefix.
	 */
	private String tagName;
	/**
	 * The number of times that a name has been used.
	 */
	private int usedTimes = 0;
	/**
	 * The prefix used to added to the tag name to give a full tag name
	 */
	public static final String PREFIX = "@";
	/**
	 * Creates a full name of a tag, for given tagName.
	 *
	 * @param tagName
	 * 			the name of a tag, without the prefix. 
	 */
	public Tag(String tagName) {
		this.tagName = Tag.PREFIX + tagName;
	}

	/**
	 * Finds and returns the tagName of a tag.
	 * @return the name of a tag
	 * @see back_end.Tag#tagName
	 */
	public String getTagName() {
		return tagName;
	}


	/**
	 * Finds and returns the number of times a tag has been used.
	 * @return the number of times the tag has been used.
	 * @see back_end.Tag#usedTimes
	 */
	public int getUsedTimes() {
		return usedTimes;
	}
	
	/**
	 * Checks whether a tag is equal to another tag, by comparing their tagNames.
	 * @param other
	 * 			another tag to compare with this tag to check equality.
	 * @return whether the name of a tag equals the name of another tag.
	 * @see back_end.Tag#tagName
	 */
	@Override
	public boolean equals(Object other){
		if(other instanceof Tag == false){
			return false;
		}else{
			return this.tagName.equals(((Tag) other).tagName);
		}
	}
	
    /**
     * Returns a String representation of this tag of this form:
     *
     * "tagName (used usedTimes times)"
     * 
     * @return a String representation of this tag
     * @see back_end.Tag#tagName
     * @see back_end.Tag#usedTimes
     */
	@Override
	public String toString(){
		return this.tagName + " (used " + this.usedTimes + " times)";
	}
	
    /**
     * Updates the usedTime of a tag by adding one to the used times of a tag.
     * @see Tag#usedTimes
     */
	public void addUsedTimes(){
		this.usedTimes = this.usedTimes + 1;
	}
    /**
     * Updates the usedTime of a tag by reducing one to the used times of a tag.
     * @see Tag#usedTimes
     */
	public void reduceUsedTimes(){
		this.usedTimes = this.usedTimes - 1;
	}
//	public static void main(String[] args){
//		Tag t = new Tag("Matt");
//		Tag s = new Tag("Matt");
//		FileNode f1 = new FileNode("1111.jpg", null, FileType.IMAGE, "/Users/Tarren/Desktop/tester/1111.png");
//		f1.addTag(t, true, true);
//		f1.addTag(t, true, true);
//		System.out.println(t);
//		System.out.println(s);
//		System.out.println(f1);
//		t.addUsedTimes();
//		System.out.println(t);
//		System.out.println(t.equals(s));
//		System.out.println(t.equals(null));
//	}
}
