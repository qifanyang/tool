package snappy.test;


import ht.msg.JsonMsg;
import ht.msg.ReqMailMsg;
import ht.msg.ReqNoticeMsg;

import org.junit.Test;
import org.xerial.snappy.Snappy;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class TestCompress {

	@Test
	public void test() throws Exception {

//		String input = "Hello 我是懒得加福禄寿酒店方老师就颠覆了就睡觉地方snappy-java! Snappy-java is a JNI-based wrapper of Snappy, a fast compresser/decompresser.";
//		input += input;
//		input += input;
//		input += input;
//		input += input;
		
		ReqMailMsg msg = new ReqMailMsg();
		msg.setAll(true);
		msg.setContent("wo沃尔夫奖阿斯兰的金佛水电费建设的经费乐山大佛李双江的雷锋精神两地分居");
		msg.setName("类似的解放路");
		msg.setUid("100001542");
		JsonMsg jsonmsg = new JsonMsg();
		jsonmsg.setId(msg.getId());
		jsonmsg.setMsg(msg);
		String json = JSON.toJSONString(jsonmsg, SerializerFeature.WriteClassName);
		
		byte[] bytes = json.getBytes("UTF-8");
		System.out.println("input size = " + bytes.length);
		byte[] compressed = Snappy.compress(bytes);
		System.out.println("compressed input size = " + compressed.length);
		byte[] uncompressed = Snappy.uncompress(compressed);

		String result = new String(uncompressed, "UTF-8");
		System.out.println(result);
	}

}
