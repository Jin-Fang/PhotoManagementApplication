package back_end;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.Collection;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import front_end.ManageTagList;

import java.io.IOException;

/**
* <h1>Stores File Information in FileNode Structure</h1>
* The root of a tree representing a directory structure. If this root is an image, it will
* contain the tags and names information of this image.
* <p>
* @author  Jin Fang, Jingwen Xu
* @version 1.0
* @since   2016-11-14
*/
public class FileNode implements Serializable{

	/**
	 * A unique identifier for this serializable class.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Finds and returns a map of oldNames and tags used in the oldNames of given FileNode.
	 * 
	 * @return a map of all the old names of a given FileNode, with the name as the key 
	 * of the map and an ArrayList of tags used in that old name as the value.
	 */
	public Map<String, ArrayList<Tag>> getOldNames() {
		return oldNames;
	}


	/** The name of the file or directory this node represents. */
	private String name;
	/** Whether this node represents a file or a directory. */
	public FileType type;
	/** This node's parent. */
	private FileNode parent;
	/** This node's path. */
	private String path;
	/** This node's tags. */
	private ArrayList<Tag> tags;
	/** 
	 * This node's oldNames map, a map of oldNames and tags used in the oldNames of 
	 * given FileNode. Every oldName in this map is unique, and this record is created
	 * when a name becomes an old name.
	 * */
	private Map<String, ArrayList<Tag>> oldNames;
	/** 
	 * A map of Date and Name of this node, mapping from the Date that a name is first 
	 * created to the names. A name could appear multiple times as the value for different date key. 
	 */
	private Map<Date, String> getDateToName;
	/** 
	 * A logger to log the changes made to file names.
	 */
	private static Logger lg = null;
	/**
	 * A fileHandler for the log file.
	 */
	private static Handler filehandler = null;
	
	/**
	 * This node's children, mapped from the file names to the nodes. If type is
	 * FileType.FILE, this is null.
	 */
	private Map<String, FileNode> children;
	
	/**
	 * A node in this tree. The file type of the node would only be IMAGE or DIRECTORY.
	 *
	 * @param name
	 *            the file
	 * @param parent
	 *            the parent node
	 * @param type
	 *            file or directory
	 * @param path
	 * 			  the absolute path to the file or directory
	 * @see back_end.FileManager#buildFileList(File, FileNode)
	 */
	public FileNode(String name, FileNode parent, FileType type, String path) {
		this.name = name;
		this.parent = parent;
		this.type = type;
		this.path = path;
		
		//initiate log and filehandler and set level
		//use try catch block to handle SecurityException and IOException
		try {
			if (lg == null) {
				lg = Logger.getLogger(FileNode.class.getName());
				filehandler = new FileHandler("log.txt", true);
				filehandler.setFormatter(new SimpleFormatter());
				lg.setLevel(Level.ALL);
				lg.addHandler(filehandler);
			}
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
		if (type == FileType.DIRECTORY){
			children = new HashMap<String, FileNode>();
		}
		//IMAGE nodes do not have children
		if (type == FileType.IMAGE){
			tags = new ArrayList<Tag>();
			oldNames = new HashMap<>();
			//record time and current name when node is initiated
			getDateToName = new HashMap<>();
			Date d = new Date();
			getDateToName.put(d, this.name);
		}	
	}
	/** 
	 * Finds and returns the map of Date and Name of this node, mapping from the Date 
	 * that a name is first created to the names, including its current name. A name could 
	 * appear multiple times as the value for different date key. 
	 * <p>
	 * @return a map mapping Dates to names that the FileNode has used
	 */
	public Map<Date, String> getDateToName() {
		return getDateToName;
	}

	/**
	 * Returns a string representation of the FileNode of this form:
	 * <p>
	 * "FileNode name:  this.name, type: IMAGE/DIRECTORY, parent: this.parent.name,
	 * path: this.path, tags: this.tags, oldNames: this.getDateToName, children: this.children"
	 * <p>
	 * If that is the FileNode is IMAGE type, then return without "children", if that is the FileNode 
	 * is DIRECTORY type, then return without "tags". If the FileNode does not have a parent, then
	 * return with parent name being "no parent". 
	 * @return a string representation of the FileNode
	 */
	@Override
	public String toString() {
		if (this.parent != null){
			if (this.type == FileType.IMAGE){
				return "FileNode name: " + this.name + ", type: " + "IMAGE" + ", parent: "
						+ this.parent.name + ", path: " + this.path + ", tags: " + this.tags + ", oldNames: "
						+ this.oldNames + ", dateToName: " + this.getDateToName;
				} else { //this.type == FileType.DIRECTORY
					return "FileNode name: " + this.name + ", type: " + "DIRECTORY" + ", parent: "
							+ this.parent.name + ", path: " + this.getPath() + ", children: " + this.children;
				} 
		} else {
			if (this.type == FileType.IMAGE){
				return "FileNode name: " + this.name + ", type: " + "IMAGE" + ", parent: "
						+ "no parent" + ", path: " + this.path + ", tags: " + this.tags + ", oldNames: "
						+ this.oldNames + ", dateToName: " + this.getDateToName;
				} else { //this.type == FileType.DIRECTORY
					return "FileNode name: " + this.name + ", type: " + "DIRECTORY" + ", parent: "
							+ "no parent" + ", path: " + this.getPath()  + ", children: " + this.children;
				}
		}
	}
	
	/** 
	 * Compares this FileNode and another FileNode and see whether they are the same
	 * @param obj
	 * 			another FileNode to compare with this FileNode to check equality.
	 * @return 
	 * 			whether both of the name and path of this FileNode and another FileNode are 
	 * 			the same. 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		FileNode other = (FileNode) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;

		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

	/**
	 * Finds and returns a child node named name in this directory tree, or null
	 * if there is no such child node.
	 *
	 * @param name
	 *            the file name to search for
	 * @return the node named name
	 */
	public FileNode findChild(String name) {
		FileNode result = null;
	    if (this.getName() == name){ 
	        result = this; // base case
	    }
	    else{
	    	for (FileNode node: this.getChildren()) {
	    		FileNode temp = node.findChild(name); // recursion step
	    		if (temp != null){
	    			return temp;
	    		} // end if statement
	    	}// end for loop
	    } // end else statement
	    return result;
	}

	/**
	 * Returns the name of the file or directory represented by this node.
	 *
	 * @return name of this Node
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of the current node
	 *
	 * @param name
	 *            of the file/directory
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the child nodes of this node.
	 *
	 * @return the child nodes directly underneath this node.
	 */
	public Collection<FileNode> getChildren() {
		return this.children.values();
	}

	/** 
	 * Returns this node's parent.
	 * 
	 * @return the parent
	 */
	public FileNode getParent() {
		return parent;
	}

	/**
	 * Sets this node's parent to p.
	 * 
	 * @param p
	 *            the parent to set
	 */
	public void setParent(FileNode p) {
		this.parent = p;
	}

	public ArrayList<Tag> getTags() {
		return tags;
	}

	public String getPath() {
		return path;
	}
	
	/**
	 * Returns whether this node represents a directory.
	 * 
	 * @return whether this node represents a directory.
	 */
	public boolean isDirectory() {
		return this.type == FileType.DIRECTORY;
	}
	
	/**
	 * Adds a childNode, representing a file or directory named name, as a child of
	 * this node.
	 * 
	 * @param name
	 *            the name of the file or directory
	 * @param childNode
	 *            the node to add as a child
	 */
	public void addChild(String name, FileNode childNode) {
		if (this.type == FileType.DIRECTORY){
			this.children.put(name, childNode);
		}
	}
	/**
	 * Updates a childNode's name in the children map of its parent when the name of the FileNode (childNode
	 * of its parent) has been modified due to tag add/delete operation.  
	 * 
	 * @param oldName
	 * 			the oldName of the childNode
	 * @param newName
	 * 			a name the childNode's name is changing to
	 */			
	public void UpdateParentChildrenList(String oldName, String newName){
		if (this.parent != null){
			FileNode temp = this.parent.children.get(oldName);
			this.parent.children.remove(oldName);
			this.parent.children.put(newName, temp);
		}
	}
	
	/**
	 * Keeps track of a name and the tags used in this this name when it's about to be changed.
	 * <p>
	 * This would only be performed if this name is not currently recorded in the oldNames list 
	 * of this FileNode.
	 * @see FileNode#oldNames   
	 */
	@SuppressWarnings("unchecked")
	public void addOldName(){
		if (! this.oldNames.keySet().contains(this.name)){
			// clone the tag list that a specific old name had, so that the tag  
			// list will not be changed when this.tags is changed
			this.oldNames.put(this.name, (ArrayList<Tag>) this.tags.clone());
		}
	}
	/**
	 * Puts the current Date and current name into the getDateToName map of this FileNode. 
	 * <p>
	 * This is usually used to keep track of a newly generated name when the user modify the tags of a FileNode.
	 * @see FileNode#getDateToName   
	 */
	public void updateDateToName(){
		Date d = new Date();
		// copy the name
		String nameCopy = this.name.substring(0);
		this.getDateToName.put(d, nameCopy);
	}
	/**
	 * Adds a given tag t to the FileNode and change the name of the file accordingly only if the given
	 * tag t is not one of the tags the FileNode currently has.
	 * @param t
	 * 			the tag that user want to add to this FileNode.
	 * @param logging
	 * 			whether to record this change to the log file.
	 * @param updateOldName
	 * 			whether to record this new name and the Date it is generated to the 
	 * 			getDateToName map of this FileNode. 
	 * @see FileNode#getDateToName 
	 */
	public void addTag(Tag t, Boolean logging, Boolean updateOldName){
		if(this.type == FileType.IMAGE && !this.tags.contains(t)){
			//update old name list
			if (updateOldName){
				addOldName();
			}
			//change the name of the file in the file system 
			File oldFile = new File(this.path);
			String oldName = oldFile.getName();
			String newName = t.getTagName() + " " + oldName;
			String newFilePath = oldFile.getAbsolutePath().replace(oldName, newName);
			File newFile = new File(newFilePath);
			oldFile.renameTo(newFile);
			//update FileNode attributes
			this.name = newName;
			this.path = newFilePath;
			//prepend new tag to tags so that the order is the same as they appear in the name
			this.tags.add(0, t); 
			//update dateToName
			if (updateOldName){
			updateDateToName();
			}
			//update old name list
			addOldName();
			
			UpdateParentChildrenList(oldName, newName);
			//update the tag used time
			t.addUsedTimes();
			//write log
			if (logging) {
				lg.log(Level.FINEST, "Action:" + "addTag" + " " + "Old_name:" + oldName + " " + "New_name:" + newName);
			}
		}
	}
	/**
	 * Deletes the given tag t that this FileNode currently has, and changes its file name accordingly.
	 * <p>
	 * Does not do anything if the given tag t is not one of the tags this FileNode currently has.
	 * 
	 * @param t
	 * 			the Tag to delete
	 * @param logging
	 * 			whether to record this change into the log file.
	 * @param updateOldName
	 * 			whether to record this new name and the Date it is generated to the 
	 * 			getDateToName map and also record to OldNames map of this FileNode. 
	 * @see FileNode#getDateToName
	 */
	public void deleteTag(Tag t, Boolean logging, Boolean updateOldName){
		if(this.type == FileType.IMAGE && this.tags.contains(t)){
			
			//update old name list
			if (updateOldName){
				addOldName();
			}
			//delete tag from the name of the file
			File oldFile = new File(this.path);
			String oldName = oldFile.getName();
			String newName = oldName.replace(t.getTagName() + " ", "");
			String newFilePath = oldFile.getAbsolutePath().replace(oldName, newName);
			File newFile = new File(newFilePath);
			oldFile.renameTo(newFile);
			//update FileNode attributes
			this.name = newName;
			this.path = newFilePath;
			this.tags.remove(t);
			//update dateToName
			if (updateOldName){
			updateDateToName();
			}
			
			UpdateParentChildrenList(oldName, newName);
			//update tag used time
			t.reduceUsedTimes();
			//write log
			if (logging){
				lg.log(Level.FINEST, "Action:deleteTag Old_name:" + oldName + " " + "New_name:" + newName);
			}
		}
	}
	
	/**
	 * Deletes all the tags this FileNode currently has, and changes its file name to its
	 * original file name (without any tag as prefix). 
	 * 
	 * @param logging
	 * 			whether to record this change into the log file.
	 * @param updateDateToName
	 * 			whether to record this new name and the Date it is generated to the 
	 * 			getDateToName map of this FileNode. 
	 */
	@SuppressWarnings("unchecked")
	public void deleteAllTags(Boolean logging, Boolean updateDateToName){
		String oldName = this.name.substring(0);
		if ( ! this.tags.isEmpty()){
			addOldName();
			ArrayList<Tag> taglist =  (ArrayList<Tag>) this.tags.clone();
			// loop over the tag list and delete all of tags
			for (Tag temp : taglist){
				//do not use deleteTag's logging, addOLdName, and updateDateToName
				deleteTag(temp, false, false);
			}
		}
		String newName = this.name;
		//updateDateToName when all changes in deleteAllTags have finished
		if (updateDateToName){
		updateDateToName();
		}
		//write log
		if (logging){
			lg.log(Level.FINER, "Action:deleteAllTags Old_name:" + oldName + " " + "New_name:" + newName);
		}
	}
	
	/**
	 * Changes the name of this FileNode to an already used name toName in the oldNames list.
	 * And records this change in the log file if logging is set to true.
	 * 
	 * @param toName 
	 * 			the name (an already used name) that the user want to change this FileNode to.
	 * @param logging 
	 * 			whether to record this change into the log file.
	 * @see FileNode#oldNames
	 */
	public void revertToOldName(String toName, Boolean logging){
		
		String oldName = this.name.substring(0);
		//check if oldName is in oldName map
		if (this.oldNames.containsKey(toName)){
			//update oldNames before current name changed to another one
			addOldName();
			//delete all tags the image currently have
			//not using deleteAllTag's logging and updateDateToName
			deleteAllTags(false, false);
			}
			//recover the tags in oldName in correct order
			ArrayList<Tag> oldTags = this.oldNames.get(toName);
			for(int i = oldTags.size() - 1; i >= 0; i--){
				Tag currTag = oldTags.get(i);
				addTag(currTag, false, false);
				}
			String newName = this.name;
			//updateDateToName when all changes in revertToOldName have finished
			updateDateToName();
			
			if (logging){
				lg.log(Level.FINEST, "Action:revertToOldName Old_name:" + oldName + " " + "New_name:" + newName);
			}
			
			ArrayList<Tag> temp = new ArrayList<>();
			for (Tag t : this.tags){
				boolean flag = true;
				for (Tag t1: TagManager.tagList){
					if (t.getTagName().equals(t1.getTagName())){
						flag = false;
						break;
					}
				}
				if(flag){
					temp.add(t);
				}
			}
			for (Tag t : temp){
				TagManager.addTag(t);
			}
		}
	public void setOldNames(Map<String, ArrayList<Tag>> anotherOldNames) {
		this.oldNames = anotherOldNames;
		
	}
	
//	/**
//	 * This method is for code that tests this class.
//	 * 
//	 * @param args
//	 *            the command line args.
//	 * @throws IOException 
//	 * @throws FileNotFoundException 
//	 */
//	public static void main(String[] args) throws FileNotFoundException, IOException {
//		
//	}

}
