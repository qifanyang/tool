package ht;

import ht.msg.IMessage;
import ht.msg.JsonMsg;

import java.nio.charset.Charset;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonEncoder extends OneToOneEncoder {
	private static final Log log = LogFactory.getLog(JsonEncoder.class);

	private Charset UTF8 = Charset.forName("utf-8");
	
	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		if(!(msg instanceof IMessage)){
			log.error("json encoder 失败", new RuntimeException());
			return msg;
		}
		IMessage imsg = (IMessage) msg;
		JsonMsg jsonmsg = new JsonMsg();
		jsonmsg.setId(imsg.getId());
		jsonmsg.setMsg(imsg);
		String json = JSON.toJSONString(jsonmsg, SerializerFeature.WriteClassName);
		return ChannelBuffers.wrappedBuffer(json.getBytes(UTF8));
		
	}

	
	
}
