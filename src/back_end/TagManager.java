package back_end;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * <h1>Manage the Tags</h1> The TagManager is mainly used to manage the
 * available tags that could later be choosen by user to add to some image.
 * <p>
 * 
 * @author Jin Fang, Jingwen Xu
 * @version 1.0
 * @since 2016-11-14
 */

public class TagManager implements Serializable {
	/**
	 * A unique identifier for this serializable class.
	 */
	private static final long serialVersionUID = 1L + 2;
	/**
	 * An arrayList of all tags that have been used in this application.
	 */
	public static ArrayList<Tag> tagList = new ArrayList<Tag>();

	/**
	 * Returns a string representation of tagList, which contains all the tags
	 * that has been used.
	 * 
	 * @return a string representation of tagList of this TagManaer.
	 * @see TagManager#tagList
	 */
	@Override
	public String toString() {
		return TagManager.tagList.toString();
	}

	/**
	 * Adds a new Tag t to the tagList of this TagManager only when the Tag t is
	 * not currently in the tagList of this TagManager.
	 * 
	 * @param t
	 *            the new Tag t the user want to add to the tagList of this
	 *            TagManager.
	 */
	public static void addTag(Tag t) {
		if (TagManager.tagList.contains(t) == false) {
			TagManager.tagList.add(t);
		}
	}

	/**
	 * Deletes a Tag t from the tagList of this TagManager only when the Tag is
	 * currently in the tagList of this TagManager.
	 * <p>
	 * Also deletes this Tag t from all the image files that has this Tag t in
	 * its tags.
	 * 
	 * @param t
	 *            a Tag t the user want to delete from the tagList of this
	 *            TagManager.
	 */
	public static void deleteTag(Tag t) {
		if (TagManager.tagList.contains(t)) {
			TagManager.tagList.remove(t);
			try {
				FileManager.deleteTagFromAllImage(t);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Returns a Tag that has been used most times. If there are more than one
	 * tag that has the highest number of used times, then return one of these
	 * tags.
	 * 
	 * @return a Tag that has the highest number of usedTimes.
	 */
	public static Tag findMostUsedTag() {
		Tag result = TagManager.tagList.get(0);
		for (Tag temp : TagManager.tagList) {
			if (temp.getUsedTimes() > result.getUsedTimes()) {
				result = temp;
			}
		}
		return result;
	}

	/**
	 * Serializes all Tag objects in the tagList of this TagManager and writes
	 * it into a ser file.
	 * 
	 * @throws FileNotFoundException
	 *             If the system could not find a file with the given path
	 * @throws IOException
	 *             If an input or output exception occurred
	 */
	public static void Ser() throws FileNotFoundException, IOException {
		// check if .ser file already exist
		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream("TagManager.ser"));
		oos1.writeObject(TagManager.tagList);
		oos1.close();
	}

	/**
	 * Deserializes all Tag objects from given ser file and adds all the Tag
	 * objects into the tagList of this TagManager.
	 * 
	 * @throws FileNotFoundException
	 *             If the system could not find a file with the given path
	 * @throws IOException
	 *             If an input or output exception occurred
	 * @throws ClassNotFoundException
	 *             If the class is not found when the application is trying to
	 *             load in a class
	 */
	@SuppressWarnings("unchecked")
	public static void Deser() throws IOException {

		try {
			ObjectInputStream ois1 = new ObjectInputStream(new FileInputStream("TagManager.ser"));
			TagManager.tagList = (ArrayList<Tag>) ois1.readObject();
			ois1.close();
		} catch (IOException | ClassNotFoundException e4) {
		}
	}

	/**
	 * Returns the Tag at the index i in the tagList of this TagManager.
	 * 
	 * @param i
	 *            the index of the tag that the user is trying to retreive
	 * @return a Tag at the index i in the tagList
	 * @see back_end.TagManager#tagList
	 */
	public static Tag getTagAtIndex(int i) {
		return TagManager.tagList.get(i);
	}

	/**
	 * Finds and returns a Tag named name in the tagList of this TagManager,
	 * only if the Tag exists in the tagList.
	 * 
	 * @param name
	 *            the name of the Tag that the user wants to find in the tagList
	 * @return a Tag named name in the tagList of this TagManager
	 */
	public static Tag getTag(String name) {
		for (Tag temp : TagManager.tagList) {
			if (temp.getTagName().equals(name)) {
				return temp;
			}
			// cast null as Tag to avoid warning
		}
		return (Tag) null;
	}
	// /**
	// * This method is for code that tests this class.
	// *
	// * @throws FileNotFoundException
	// * If the system could not find a file with the given path
	// * @throws IOException
	// * If an input or output exception occurred
	// */
	// public static void main(String[] args) throws FileNotFoundException,
	// IOException{
	// Tag a =new Tag("Matt");
	// Tag b =new Tag("Tinney");
	// Tag c =new Tag("Pius");
	// Tag d =new Tag("Solomon");
	// Tag e =new Tag("Matt");
	//
	// a.addUsedTimes();
	// a.addUsedTimes();
	// b.addUsedTimes();
	// b.addUsedTimes();
	// b.addUsedTimes();
	//
	// TagManager.addTag(a);
	// TagManager.addTag(b);
	// TagManager.addTag(c);
	// TagManager.addTag(d);
	// Ser();
	//
	// System.out.println(TagManager.tagList);
	// System.out.println(TagManager.findMostUsedTag());
	// TagManager.addTag(e);
	// System.out.println(TagManager.tagList);
	//
	// System.out.println(TagManager.getTag("@Mathew"));
	// System.out.println(TagManager.getTag("@Matt"));
	// }

	// public static void main(String[] args) throws FileNotFoundException,
	// ClassNotFoundException, IOException{
	// //testing toString()
	// TagManager tm = new TagManager();
	// Tag tag1 = new Tag("tag1");
	// Tag tag2 = new Tag("tag2");
	// Tag tag3 = new Tag("tag3");
	// Tag tag4 = new Tag("tag4");
	// Tag tag5 = new Tag("tag5");
	// Tag tag6 = new Tag("tag6");
	// TagManager.addTag(tag1);
	// TagManager.addTag(tag2);
	// TagManager.addTag(tag3);
	// TagManager.addTag(tag4);
	// TagManager.addTag(tag5);
	// TagManager.addTag(tag6);
	// FileNode f1 = new FileNode("tester", null, FileType.DIRECTORY,
	// "/Users/Tarren/Desktop/tester");
	// FileNode f2 = new FileNode("1111.png", f1, FileType.IMAGE,
	// "/Users/Tarren/Desktop/tester/1111.png");
	// f2.addTag(tag2, true, true);
	// f2.addTag(tag3, true, true);
	// f2.addTag(tag2, true, true); // test add already existing tags
	// FileNode f3 = new FileNode("sub1", f1, FileType.DIRECTORY,
	// "/Users/Tarren/Desktop/tester/sub1");
	// FileNode f4 = new FileNode("aaaa.png", f3, FileType.IMAGE,
	// "/Users/Tarren/Desktop/tester/sub1/aaaa.png");
	// f4.addTag(tag3, true, true);
	// f4.addTag(tag2, true, true);
	// f4.addTag(tag1, true, true);
	// f3.addTag(tag5, true, true);// test adding tag to a DIRECTORY
	// System.out.println(TagManager.tagList); // test toString of tagList
	// System.out.println(tm); // test toString of TagManager
	// }
	public static void main(String[] args) {
		Tag tag1 = new Tag("tag1");
		Tag tag2 = new Tag("tag2");
		Tag tag3 = new Tag("tag3");
		Tag tag4 = new Tag("tag4");
		Tag tag5 = new Tag("tag5");
		Tag tag6 = new Tag("tag6");
		TagManager.addTag(tag1);
		TagManager.addTag(tag2);
		TagManager.addTag(tag3);
		TagManager.addTag(tag4);
		TagManager.addTag(tag5);
		TagManager.addTag(tag6);

		// System.out.println(TagManager.tagList);

		FileNode f = new FileNode("tester", null, FileType.DIRECTORY, "/Users/Tarren/Desktop/tester");
		File file = new File("/Users/Tarren/Desktop/tester");
		FileManager.buildFileList(file, f);

		FileNode f1 = FileManager.fileList.get(1);
		System.out.println("FileNode before change:\n" + f1);
		System.out.println(TagManager.tagList);
		f1.addTag(tag1, true, true);
		// System.out.println("FileNode after change:\n" + f1 + "\n");
		// System.out.println(TagManager.tagList);

		FileNode f2 = FileManager.fileList.get(2);
		FileNode f3 = FileManager.fileList.get(3);
		f1.addTag(tag3, true, true);
		System.out.println(f1.getTags());
		f1.addTag(tag4, true, true);
		f2.addTag(tag1, true, true);
		f2.addTag(tag2, true, true);
		f2.addTag(tag4, true, true);
		f3.addTag(tag1, true, true);
		// f1:tag134 f2:tag124 f3:tag1
		System.out.println(TagManager.tagList);
		System.out.println("test tag used times:\n");
		// f2.deleteTag(tag1, true, true);
		// f1.deleteAllTags(true, true);
		// FileManager.deleteTagFromAllImage(tag4);
		System.out.println(TagManager.tagList);
	}
}
