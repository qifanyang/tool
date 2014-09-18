package file.test;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class FileTest {
	
	@Test
	public void t0() throws IOException{
		File file = new File("E:/kkkkkk/j.txt");
		if(!file.exists()){
//			file.mkdirs();//若E:/kkkkkk/j.txt不存在将创建,该路径表示的文件夹,恶心
			
//			file.createNewFile();//若E:/kkkkkk/j.txt不存在,将报错提示找不到指定的路径
			
			//处理先创建父目录,再创建新文件,不用检测,方法里面检测了目录是否存在
			file.getParentFile().mkdirs();
//			if(!file.getParentFile().exists()){
//			}
			
			file.createNewFile();
		}
		
	}

}
