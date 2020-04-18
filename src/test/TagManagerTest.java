/**
 * 
 */
package test;
import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import back_end.FileManager;
import back_end.FileNode;
import back_end.FileType;
import back_end.Tag;
import back_end.TagManager;

/**
 * @author Jin Fang, Jingewen Xu
 * @version 1.0
 * @since   2016-11-14
 */
public class TagManagerTest extends TestCase{
	private TagManager tm;
	
	private Tag tag1;
	private Tag tag2;
	private Tag tag3;
	private Tag tag4;
	private Tag tag5;
	private Tag tag6;
	
	private FileNode f;
	private File file;
	private FileNode fn1;
	private FileNode fn2;
	private FileNode fn3;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Tag tag1 = new Tag("tag1");
		Tag tag2 = new Tag("tag2");
		Tag tag3 = new Tag("tag3");
		Tag tag4 = new Tag("tag4");
		Tag tag5 = new Tag("tag5");
		Tag tag6 = new Tag("tag6");
//		TagManager.addTag(tag1);
//		TagManager.addTag(tag2);
//		TagManager.addTag(tag3);
//		TagManager.addTag(tag4);
//		TagManager.addTag(tag5);
//		TagManager.addTag(tag6);
		
		FileNode f = new FileNode("tester", null, FileType.DIRECTORY, "/Users/Tarren/Desktop/tester");
		File file = new File("/Users/Tarren/Desktop/tester");
		FileManager.buildFileList(file, f);
		
		FileNode fn1 = FileManager.fileList.get(1); // this is a directory
		FileNode fn2 = FileManager.fileList.get(2); // this is a file
//		System.out.println(fn2);
//		System.out.println(tag1);
		FileNode fn3 = FileManager.fileList.get(3);
	}
	
	

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
    /**
     * Test method for addTag.
     * Normal addTag: add a tag existed in tagList to an image.
     */
    @Test
    public void testNormalAddTag1() {
    	// @Before start
		Tag tag1 = new Tag("tag1");
		Tag tag2 = new Tag("tag2");
		Tag tag3 = new Tag("tag3");
		Tag tag4 = new Tag("tag4");
		Tag tag5 = new Tag("tag5");
		Tag tag6 = new Tag("tag6");
//		TagManager.addTag(tag1);
//		TagManager.addTag(tag2);
//		TagManager.addTag(tag3);
//		TagManager.addTag(tag4);
//		TagManager.addTag(tag5);
//		TagManager.addTag(tag6);
		
		FileNode f = new FileNode("tester", null, FileType.DIRECTORY, "/Users/Tarren/Desktop/tester");
		File file = new File("/Users/Tarren/Desktop/tester");
		FileManager.buildFileList(file, f);
		
		FileNode fn1 = FileManager.fileList.get(1); // this is a directory
		FileNode fn2 = FileManager.fileList.get(2);
//		System.out.println(fn2);
//		System.out.println(tag1);
		FileNode fn3 = FileManager.fileList.get(3);
		// @Before end
		
        fn2.addTag(tag1, true, true);
        ArrayList<Tag> actual = fn2.getTags();
        ArrayList<Tag> expected = new ArrayList<Tag>();
        expected.add(tag1);
        
        ArrayList<String> actual1= new ArrayList<String>();
        actual1.add("you");
        ArrayList<String> expected1 = new ArrayList<String>();
        expected1.add("you");
        assertArrayEquals("Add Tag to Image Fail", expected, actual);
    }
    
    /**
     * Test method for addTag.
     */
    @Test
    public void testAddANotExistingTagToTagManager() {
		TagManager.addTag(tag1);
		//set up expected
		ArrayList<Tag> expected = new ArrayList<Tag>();
		expected.add(tag1);
		// set up actual
		ArrayList<Tag> actual = TagManager.tagList;
		// System.out.println("this is the taglist" + TagManager.tagList);
		// System.out.println("this is the expecte" + actual);
		assertArrayEquals("Add a tag to tagmanager fail", expected, actual);
		}
    
    /**
     * Test method for add a existing Tag.
     */
    @Test
    public void testAddAnExistingTagToTagManager() {
		TagManager.addTag(tag1);
		TagManager.addTag(tag1);
		//set up expected
		ArrayList<Tag> expected = new ArrayList<Tag>();
		expected.add(tag1);
		// set up actual
		ArrayList<Tag> actual = TagManager.tagList;
		// System.out.println("this is the taglist" + TagManager.tagList);
		// System.out.println("this is the expecte" + actual);
		assertArrayEquals("Add an exiting tag to tagmanager fail to detect its existance", expected, actual);
		}
    /**
     * Test method for deleteTag.
     */
    @Test
    public void testDeleteAnExistingTagToTagManager() {
		TagManager.addTag(tag1);
		TagManager.addTag(tag2);
    	TagManager.deleteTag(tag1);
		//set up expected
		ArrayList<Tag> expected = new ArrayList<Tag>();
		expected.add(tag2);
		// set up actual
		ArrayList<Tag> actual = TagManager.tagList;
		// System.out.println("this is the taglist" + TagManager.tagList);
		// System.out.println("this is the expecte" + actual);
		assertArrayEquals("Add a tag to tagmanager fail", expected, actual);
		}
    
    /**
     * Test method for testing deleteTag.
     */
    @Test
    public void testDeleteNonExistingTagToTagManager() {
		TagManager.addTag(tag1);
    	TagManager.deleteTag(tag2);
		//set up expected
		ArrayList<Tag> expected = new ArrayList<Tag>();
		expected.add(tag1);
		// set up actual
		ArrayList<Tag> actual = TagManager.tagList;
		// System.out.println("this is the taglist" + TagManager.tagList);
		// System.out.println("this is the expecte" + actual);
		assertArrayEquals("Add a non existing tag to tagmanager fail", expected, actual);
		}
    
    /**
     * Find most used tag.
     */
    @Test
    public void testFindMostUsedTag() {
    	// @Before start
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
		
		FileNode f = new FileNode("tester", null, FileType.DIRECTORY, "/Users/Tarren/Desktop/tester");
		File file = new File("/Users/Tarren/Desktop/tester");
		FileManager.buildFileList(file, f);
		
		FileNode fn1 = FileManager.fileList.get(1); // this is a directory
		FileNode fn2 = FileManager.fileList.get(2);
//		System.out.println(fn2);
//		System.out.println(tag1);
		FileNode fn3 = FileManager.fileList.get(3);
		// @Before end
		
		fn2.addTag(tag1, false, false);
		fn2.addTag(tag2, false, false);
		fn2.addTag(tag3, false, false);
		
		fn3.addTag(tag2, false, false);
		fn3.addTag(tag3, false, false);
		fn3.addTag(tag4, false, false);
		//set up expected
		Tag expected = tag2;
		// set up actual
		Tag actual = TagManager.findMostUsedTag();
		assertEquals("Find most used tag fail", expected, actual);
		}
    
    /**
     * Find most used tag.
     */
    @Test
    public void testFindMostUsedTagWhenNoneUse() {
    	// @Before start
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
		
		FileNode f = new FileNode("tester", null, FileType.DIRECTORY, "/Users/Tarren/Desktop/tester");
		File file = new File("/Users/Tarren/Desktop/tester");
		FileManager.buildFileList(file, f);
		
		FileNode fn1 = FileManager.fileList.get(1); // this is a directory
		FileNode fn2 = FileManager.fileList.get(2);
//		System.out.println(fn2);
//		System.out.println(tag1);
		FileNode fn3 = FileManager.fileList.get(3);
		// @Before end
		
		//set up expected
		Tag expected = (Tag) null;
		// set up actual
		Tag actual = TagManager.findMostUsedTag();
		assertEquals("Find most used tag when none of the tags has been used fail", expected, actual);
		}
    /**
     * Test get tag named name at index
     */
    public void testGetNameAtNormalIndex() {
    	// @Before start
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
		// @Before end
		
		//set up expected
		Tag expected = tag1;
		// set up actual
		Tag actual = TagManager.getTagAtIndex(0);
		assertEquals("Find get name at normal index fail", expected, actual);
    }
    
    /**
     * Test get tag named name at index
     */
    public void testGetNameAtOutofBoundIndex() {
    	// @Before start
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
		// @Before end
		
		//set up expected
		Tag expected = (Tag)null;
		// set up actual
		Tag actual = TagManager.getTagAtIndex(7);
		assertEquals("Find get name at a out-of-bound index fail", expected, actual);
    }
	

    
    
    /**
     * This is a helper function to check if two ArrayList of Tags are equal.
     * Return true if they are null. 
     */
	private void assertArrayEquals(String message, ArrayList<Tag> expected,
			ArrayList<Tag> actual) {
			boolean flag = true;
			if (expected.size() == actual.size()){
				for (int i = 0; i < expected.size(); i++) {
				    Tag t1 = expected.get(i);
				    Tag t2 = actual.get(i);
				    if (!t1.equals(t2)){
				    flag = false;
				    }
				}
			}else{
				flag = false;
			}
			if(flag){
				return;
			}else{
				fail(message);
			}

	}
	
	
//	private void assertArrayEquals(String message, ArrayList<String> expected,
//			ArrayList<String> actual) {
//			boolean flag = true;
//			if (expected.size() == actual.size()){
//				for (int i = 0; i < expected.size(); i++) {
//				    String t1 = expected.get(i);
//				    String t2 = actual.get(i);
//				    if (!t1.equals(t2)){
//				    flag = false;
//				    }
//				}
//			}else{
//				flag = false;
//			}
//			if(flag){
//				return;
//			}else{
//				fail(message);
//			}
//
//	}
    
}
    
    


