package classloader.test;

import java.net.URL;
import java.net.URLClassLoader;

import model.UpdateClass;


public class URLLoaderTest {

     public static void main(String[] args) throws Exception {
		URL url = new URL("file:/E:\\sourcetreerepo\\tool\\tool\\target\\classes");
		ClassLoader myloader = new URLClassLoader(new URL[] { url });
		Class c = myloader.loadClass("model.UpdateClass");
		System.out.println(myloader.getParent());
		System.out.println("----------");
		UpdateClass t = (UpdateClass) c.newInstance();
		t.say();
		System.out.println("睡眠后重新加载");
		Thread.sleep(1);
		ClassLoader myloader1 = new URLClassLoader(new URL[] { url });
		System.out.println(myloader1.getParent());
		Class c1 = myloader1.loadClass("model.UpdateClass");
		System.out.println("----------");
		UpdateClass t1 = (UpdateClass) c1.newInstance();
		t1.say();
	}
}
