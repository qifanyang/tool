package fastjson.test;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 
 * @author yangqifan
 */
public class TestFastJosn {

	@Test
	public  void test0() {
		System.out.println("JSON.VERSION = " + JSON.VERSION);
		 SerializeConfig config = new SerializeConfig();
	     config.setAsmEnable(false);//字段过多,需关闭asm
	     long start = System.currentTimeMillis();
//		System.out.println(JSON.toJSONString(new TestBean()));
	     String json = JSON.toJSONString(new TestBean1000(), config);
	     JSON.parseObject(json, TestBean1000.class);
	     System.out.println("asm cost : " + (System.currentTimeMillis() - start) + "ms");
//	     JSON.toJSONString(new TestBean(), config);
//		System.out.println("no asm cost : " + (System.currentTimeMillis() - start) + "ms");
//		System.out.println(JSON.toJSONString(new NNull(), SerializerFeature.WriteMapNullValue));
	}
}
