package other.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;


import org.junit.Test;

public class En {
	
	@Test
	public void test0(){
		try {
			FileInputStream fis = new FileInputStream("");
			FileOutputStream fos = new FileOutputStream("F:/x1.txt");
			byte[] bytes = new byte[1*1000*1000];
			int len;
			while((len = fis.read(bytes)) != -1){
				for(int i = 0; i < len; i++){
					bytes[i] -= 15;
				}
				fos.write(bytes, 0, len);
			}
			fos.close();
			fis.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
