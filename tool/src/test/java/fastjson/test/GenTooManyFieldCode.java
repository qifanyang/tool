package fastjson.test;

import java.io.FileOutputStream;

/**
 * 
 * @author yangqifan
 */
public class GenTooManyFieldCode {

	public static void main(String[] args) {
		//C:\Users\Administrator\git\tool\tool\src\test\java\fastjson\test\TestBean.java
		
		String path = "C:\\Users\\Administrator\\git\\tool\\tool\\src\\test\\java\\fastjson\\test\\TestBean.java";
		int fieldNum = 400;
		
		StringBuilder builder = new StringBuilder();
		 builder.append("package fastjson.test; \r\n").append("public class TestBean {\r\n");
		 
		 //body
		 
		 //int a
		 for(int i = 0; i < fieldNum; i++){
			 builder.append("\tprivate int a").append(i).append(" = ").append(i).append(";\r\n");
		 }
		 
		 for(int i = 0; i < fieldNum; i++){
			 builder.append("public int getA").append(i).append("() { return a").append(i).append(";}\r\n");
			 builder.append("public void setA").append(i).append("(int a").append(i).append("){ this.a").append(i).append(" = a").append(i).append(";}\r\n");
		 }
		 
		 //end }
		 builder.append("}");
		 try {
			 FileOutputStream outputStream = new FileOutputStream(path);
			 outputStream.write(builder.toString().getBytes());
			 outputStream.close();
		 } catch (Exception e) {
			e.printStackTrace();
		 }
	}
}
