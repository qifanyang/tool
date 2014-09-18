package file.test;

import org.junit.Test;

import com.tobe.project.IFolder;
import com.tobe.project.impl.Folder;

public class FolderTest {

	@Test
	public void test0(){
		Folder folder = new Folder("E:/ffolder/test");
		
		folder.createSubFile("filetet.txt", "测试文件写入");
		
		folder.createSubFolder("fflllttt");
		
		folder.createSubFolder("com.game.test");
		
		//注意con  com1 这类文件夹不可以创建
		folder.createSubFolder("com1/game.test");
		
		
	}
}
