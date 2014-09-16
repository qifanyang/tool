package ht.http.handlers;

import static org.jboss.netty.handler.codec.http.HttpResponseStatus.OK;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.HttpRequest;

import com.alibaba.fastjson.JSONObject;

import ht.http.HttpUtil;
import ht.http.IHttpHandler;

public class IndexHandler implements IHttpHandler {

	@Override
	public void handle(String uri, HttpRequest request, Channel channel) {
		JSONObject jo = new JSONObject();
		jo.put("online", 2000);
//		ChannelBuffer buffer = ChannelBuffers.wrappedBuffer(jo.toString().getBytes());
//		HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
//        response.setContent(buffer);
//        response.headers().set(CONTENT_TYPE, "text/plain");
//        response.headers().set(CONTENT_LENGTH, response.getContent().readableBytes());
//		channel.write(response);
		ChannelBuffer r = HttpUtil.encodeResponse(OK, jo.toJSONString());
		channel.write(r);
	}

	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return null;
	}

}
