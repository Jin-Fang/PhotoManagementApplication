package back_end;

import java.io.IOException;

public class DeleteTag implements TagList {
	
	public static void deleteTag(Tag t){
		if (TagManager.tagList.contains(t)) {
			TagManager.tagList.remove(t);
		}
		
	}
	@Override
	public void updateChangesToFileNodes(Tag t) {
		if (TagManager.tagList.contains(t)) {
			try {
				FileManager.deleteTagFromAllImage(t);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}}
	}

}
