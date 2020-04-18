package back_end;

public class AddTag implements TagList {

	public static void addTag(Tag t){
		if (TagManager.tagList.contains(t) == false) {
			TagManager.tagList.add(t);
		}
	}
	
	@Override
	public void updateChangesToFileNodes(Tag t) {
	}

}
