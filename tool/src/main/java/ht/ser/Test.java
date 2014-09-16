package ht.ser;

import ht.msg.JsonMsg;
import ht.msg.ReqRegisterMsg;

import com.alibaba.fastjson.JSON;



public class Test {

	public static void main(String[] args) {
		ReqRegisterMsg msg = new ReqRegisterMsg();
		msg.setSid(8);
		msg.setKey("123456");
		
		GameServer gs = new GameServer(8);
		gs.sendMessage(msg);
		
		String json = "{\"@type\":\"net.msg.JsonMsg\",\"id\":1,\"msg\":{\"@type\":\"net.msg.ReqRegisterMsg\",\"id\":1,\"key\":\"123456\",\"sid\":8}}";
//		String json = "{\"id\":1,\"msg\":{\"id\":1,\"key\":\"123456\",\"sid\":8}}";
		
		JsonMsg jsonMessage = JSON.parseObject(json, JsonMsg.class);
		System.out.println("");
	}
}
