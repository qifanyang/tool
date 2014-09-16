package ht.msg;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class MessagePool {
	
	private static Map<Integer, Class<?>> msgMap = new ConcurrentHashMap<Integer, Class<?>>();
	
	
	static{
		msgMap.put(MsgCode.REGISTER, ReqRegisterMsg.class);
	}
	
}
