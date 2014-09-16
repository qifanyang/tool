package ht;

import ht.msg.JsonMsg;

import java.nio.charset.Charset;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneDecoder;

import com.alibaba.fastjson.JSON;

public class JsonDecoder extends OneToOneDecoder {
	private static final Log log = LogFactory.getLog(JsonDecoder.class);

	private Charset UTF8 = Charset.forName("utf-8");

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception {
		if (!(msg instanceof ChannelBuffer)) {
			log.error("json decoder 失败", new RuntimeException());
			return msg;
		}
		ChannelBuffer buf = (ChannelBuffer) msg;
		String json = buf.toString(UTF8);
		JsonMsg jsonMsg = JSON.parseObject(json, JsonMsg.class);
		return jsonMsg;
	}

}
