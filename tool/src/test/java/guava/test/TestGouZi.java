package guava.test;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import junit.framework.TestCase;

public class TestGouZi extends TestCase{

	public void testgz(){
		System.out.println("start.....");
		
		Runtime.getRuntime().addShutdownHook(new Thread(){@Override
		public void run() {
			try {
				FileOutputStream fileImageOutputStream = new FileOutputStream("F:tttttt.txt");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("end.....");
		}});
		
		try {
			Thread.sleep(3000);
			System.exit(0);
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
