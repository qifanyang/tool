package fastjson.test;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author yangqifan
 */
public class TestFastJosn {

	public static void main(String[] args) {
		System.out.println("JSON.VERSION = " + JSON.VERSION);
		System.out.println(JSON.toJSONString(new TestBean()));
	}
}
