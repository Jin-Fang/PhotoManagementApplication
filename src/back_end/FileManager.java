package back_end;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

/**
 * A FileManager is mainly used to handle a group of FileNodes added to the
 * fileList of this FileManager.
 * <p>
 * 
 * @author Jin Fang, Jingwen Xu
 * @version 1.0
 * @since 2016-11-14
 */
public class FileManager {
	/**
	 * An arrayList containing all FileNodes.
	 */
	public static ArrayList<FileNode> fileList = new ArrayList<FileNode>();

	/**
	 * Returns whether the type of the file is a image type.
	 * 
	 * @param file
	 *            the file for type checking
	 * @return boolean whether the type of the file is a image type.
	 */
	public static boolean isImage(File file) {
		String mimetype = new MimetypesFileTypeMap().getContentType(file);
		String type = mimetype.split("/")[0];
		if (type.equals("image")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Builds the tree of nodes rooted at "file" in the file system; note curr
	 * is the FileNode corresponding to file, so this only adds nodes for
	 * children of file to the tree. And add all the FileNodes of this tree into
	 * the filelist of this FileManager.
	 * 
	 * Precondition: file represents a directory.
	 * 
	 * @param file
	 *            the file or directory we are building
	 * @param curr
	 *            the FileNode representing a file or directory
	 * @see FileManager#fileList
	 */
	public static void buildFileList(File file, FileNode curr) {
		// curr is always a FileNode of DIRECTORY
		FileManager.fileList.add(curr);
		for (File temp : file.listFiles()) {
			if (isImage(temp)) {
				FileNode image = new FileNode(temp.getName(), curr, FileType.IMAGE, temp.getAbsolutePath());
				curr.addChild(image.getName(), image);
				FileManager.fileList.add(image);
			} else {
				// when temp is directory
				if (temp.isDirectory()) {
					FileNode dir = new FileNode(temp.getName(), curr, FileType.DIRECTORY, temp.getAbsolutePath());
					curr.addChild(dir.getName(), dir);
					buildFileList(temp, dir);
				}
			}
		}
	}

	public static void buildFileList(FileNode root) {
		FileManager.fileList.add(root);
		if (root.isDirectory()) {
			for (FileNode child : root.getChildren()) {
				if (child.isDirectory()) {
					buildFileList(child);
				} else {
					FileManager.fileList.add(child);
				}
			}
		}
	}

	@Override
	/**
	 * Returns a string representation of the FileManager, by listing all the
	 * string representation of FileNodes in the fileList of this FileManager in
	 * an ArrayList.
	 * 
	 * @return a string representation of the FileManager
	 */
	public String toString() {
		ArrayList<String> acc = new ArrayList<>();
		for (FileNode f : FileManager.fileList) {
			acc.add(f.getPath());
		}
		return acc.toString();
	}

	/**
	 * Serializes all FileNode objects in the fileList of this FileManager and
	 * write it into a PhotoRenamer.ser file.
	 * 
	 * @throws FileNotFoundException
	 *             If the system could not find a file with the given path
	 * @throws IOException
	 *             If an input or output exception occurred
	 */
	public static void Ser(ArrayList<FileNode> lst) throws FileNotFoundException, IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("PhotoRenamer.ser"));
		for (FileNode fn : lst) {
			oos.writeObject(fn);
		}
		oos.close();
	}

	/**
	 * Deserializes all FileNode objects from given PhotoRenamer.ser file and
	 * add all the FileNode objects into the fileList of this FileManager.
	 * 
	 * @return An arrayList of all FileNodes read out from given ser file.
	 * @throws FileNotFoundException
	 *             If the system could not find a file with the given path
	 * @throws IOException
	 *             If an input or output exception occurred
	 * @throws ClassNotFoundException
	 *             If the class is not found when the application is trying to
	 *             load in a class
	 */
	public static ArrayList<FileNode> Deser() throws FileNotFoundException, IOException, ClassNotFoundException {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("PhotoRenamer.ser"));

			ArrayList<FileNode> allFileNode = new ArrayList<>();
			// loop over the ser file to extract FileNode until end stream
			// signal shows
			while (true) {
				try {
					FileNode fn = (FileNode) ois.readObject();
					if (!allFileNode.contains(fn)) {
						allFileNode.add(fn);
					}
				} catch (EOFException e) {
					ois.close();
					return allFileNode;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			ArrayList<FileNode> lst = new ArrayList<>();
			return lst;
		}

	}

	/**
	 * Returns the pair(s) of two FileNodes in the fileList with most similar
	 * tags. The pair(s) with most similar tags is the pair(s) of FileNodes that
	 * have the highest number of shared tags, and with the highest percentage
	 * of their shared tags versus all the tags they have.
	 * <p>
	 * If there are more than one pair of FileNodes that has the most similar
	 * tags, then returns all of the pairs that satisfy the description above.
	 * 
	 * @return the pair(s) of two FileNodes with most similar tags
	 * @see back_end.FileManager#fileList
	 */
	public static ArrayList<FileNode[]> findImageWithMostSimilarTag() {
		ArrayList<FileNode[]> fl = new ArrayList<>();
		// check fileList to avoid NullPointerException
		if (FileManager.fileList.isEmpty()) {
			return fl;
		}
		HashMap<Integer, ArrayList<FileNode[]>> allPairs = new HashMap<>();
		// find all pairs of FileNode Objects that have the same number of tags
		for (FileNode temp1 : FileManager.fileList) {
			for (FileNode temp2 : FileManager.fileList) {
				if (!temp1.equals(temp2) && !temp1.isDirectory() && !temp2.isDirectory()) {
					int acc = 0;
					FileNode[] pair = new FileNode[] { temp1, temp2 };
					// find the number of same tags
					for (Tag t : temp1.getTags()) {
						if (temp2.getTags().contains(t)) {
							acc++;
						}
					}
					// if the key exist, add the pair to corresponding ArrayList
					if (allPairs.containsKey(acc)) {
						allPairs.get(acc).add(pair);
					} else {
						// if the key does not exist, create an ArrayList
						// containing the pair and add to the HashMap
						ArrayList<FileNode[]> pairContainer = new ArrayList<>();
						pairContainer.add(pair);
						allPairs.put(acc, pairContainer);
					}
				}
			}
		}
		// find the largest number of mutual tags
		int maximum = Collections.max(allPairs.keySet());
		// get the pairs of FileNode share the largest number of mutual tags.
		ArrayList<FileNode[]> goodPairs = allPairs.get(maximum);
		HashMap<Integer, ArrayList<FileNode[]>> bestPairs = new HashMap<>();
		/*
		 * add pairs of FileNodes selected from the first round to a HashMap the
		 * key is the total number of tags in the pair of FileNode objects
		 */
		for (FileNode[] temp : goodPairs) {
			int score;
			/*
			 * the lower the score(the key in HashMap), the larger the
			 * percentage of mutual tags in all tags of a pair of FileNode i.e.
			 * the pair with lowest score is determined to be the images with
			 * most similar tags
			 */
			score = temp[0].getTags().size() + temp[1].getTags().size();
			if (bestPairs.containsKey(score)) {
				bestPairs.get(score).add(temp);
			} else {
				ArrayList<FileNode[]> newScorePair = new ArrayList<>();
				newScorePair.add(temp);
				bestPairs.put(score, newScorePair);
			}
		}
		return bestPairs.get(Collections.min(bestPairs.keySet()));
	}

	/**
	 * Reverts all the tag changes made to the FileNodes in the FileList and
	 * actual file in the system right before a given Date
	 * 
	 * @param date
	 *            A date that the user choose to go back to
	 */
	public static boolean revertToDate(Date date) {
		// loop over all FileNodes in fileList
		for (FileNode fn : FileManager.fileList) {
			// only revert FileNodes of type IMAGE
			if (fn.type == FileType.IMAGE) {
				// find all Date objects and put them into an ArralyList for
				// sorting
				ArrayList<Date> allDate = new ArrayList<>();
				for (Date d : fn.getDateToName().keySet()) {
					allDate.add(d);
				}
				// sort the Date objects from earliest to latest
				Collections.sort(allDate);
				int index = 0;
				// find the index of the latest date that is earlier than given
				// date
				while (index < allDate.size() && allDate.get(index).compareTo(date) <= 0) {
					index++;
				}
				if (index < allDate.size()){
					String oldName = fn.getDateToName().get(allDate.get(index));
					if (fn.getOldNames().keySet().contains(oldName)) {
						fn.revertToOldName(oldName, true);
						return true;
					}
				}else{
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * Adds/Deletes a list of Tags to/from the given file and its corresponding
	 * FileNode.
	 * 
	 * @param file
	 *            The file that the user would like make change to
	 * @param isAddTag
	 *            whether to perform AddTag or DeleteTag
	 * @param taglist
	 *            the taglist containing the all of tags the user would like to
	 *            batch add/delete
	 * @see back_end.Tag#Tag(String)
	 */
	public static void changeName(FileNode fn, Boolean isAddTag, ArrayList<Tag> taglist) {
		// loop over the file list to find the fileNode we want to change name
//		FileNode temp = null;
//		for (FileNode fn : FileManager.fileList) {
//			if (fn.getPath().equals(file.getAbsolutePath())) {
//				temp = fn;
//			}
//		}
//		if (temp != null) {
//			temp.addOldName();
//			for (Tag t : taglist) {
//				if (isAddTag) {
//					// use logging part of the addTag but don't use addOldName
//					// and updateDateToName
//					temp.addTag(t, true, false);
//				} else {
//					// use logging part of the addTag but don't use addOldName
//					// and updateDateToName
//					temp.deleteTag(t, true, false);
//				}
//			}
//			temp.updateDateToName();
//		}
		fn.addOldName();
		for (Tag t : taglist) {
			if (isAddTag) {
				// use logging part of the addTag but don't use addOldName
				// and updateDateToName
				fn.addTag(t, true, false);
			} else {
				// use logging part of the addTag but don't use addOldName
				// and updateDateToName
				fn.deleteTag(t, true, false);
			}
		}
	}

	/**
	 * Deletes all tags of a given file, and changes the file name back to its
	 * original name (without any tag).
	 * 
	 * @param file
	 *            the file which the user want to delete its tags
	 * @param taglist
	 *            the taglist that keep track of all the tags of the current
	 *            file.
	 */
	public static void clearTags(File file, ArrayList<Tag> taglist) {
		FileNode temp = null;
		for (FileNode fn : FileManager.fileList) {
			if (fn.getPath().equals(file.getAbsolutePath())) {
				temp = fn;
			}
		}
		if (temp != null) {
			// use deleteAllTags's logging, addOldName, and updateDateToName
			temp.deleteAllTags(true, true);
		}
	}

	/**
	 * Deletes a Tag t from the tagList only when the Tag exists at least one of
	 * the tags list of all images in FileManager.
	 * <p>
	 * Also deletes this Tag t from all the image files that has this Tag t in
	 * its tags.
	 * 
	 * @param t
	 *            a Tag t the user want to delete from the all the Images.
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void deleteTagFromAllImage(Tag t) throws FileNotFoundException, IOException {
		for (FileNode fn : FileManager.fileList) {
			// use deleteTag's logging, addOldName, and updateDateToName
			fn.deleteTag(t, true, true);
		}
		//delete the tag from all files
		ArrayList<FileNode> temp;
		try {
			temp = FileManager.Deser();
			for (FileNode fn : temp) {
				fn.deleteTag(t, true, true);
			FileManager.Ser(temp);}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static FileNode findImageWithMostTags() {
		int max = 0;
		FileNode fileWithMostTags = null;
		for (FileNode fn : FileManager.fileList) {

			if (!fn.isDirectory()) {
				if (fn.getTags().size() > max) {
					max = fn.getTags().size();
					fileWithMostTags = fn;
				}
			}
		}
		return fileWithMostTags;
	}
	// /**
	// * This method is for code that tests this class.
	// * @param
	// * @throws FileNotFoundException
	// * If the system could not find a file with the given path
	// * @throws IOException
	// * If an input or output exception occurred
	// * @throws ClassNotFoundException
	// * If the system is trying to load a class that cannot be found
	// */
	// public static void main(String[] args) throws FileNotFoundException,
	// ClassNotFoundException, IOException {
	// Deser();
	// FileManager fm = new FileManager();
	// System.out.println(fm);
	// File f1 = new File("/Users/Ramborghini/Desktop/tester/1111.png");
	// FileManager.isImage(f1);
	// }

}
