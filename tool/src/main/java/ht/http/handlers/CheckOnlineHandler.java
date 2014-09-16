package ht.http.handlers;

import static org.jboss.netty.handler.codec.http.HttpResponseStatus.OK;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;

import com.alibaba.fastjson.JSONObject;

import ht.http.HttpUtil;
import ht.http.IHttpHandler;

public class CheckOnlineHandler implements IHttpHandler {

	@Override
	public void handle(String uri, HttpRequest request, Channel channel) {
		
		JSONObject jo = new JSONObject();
		String[] split = uri.split("_");
		if(split.length == 2){
			boolean containsKey = false;
			//			boolean containsKey = ChannelManage.getInstance().getChannelMap().containsKey(split[1]);
			if(containsKey){
				jo.put("res", "1");
			}else{
				jo.put("res", "0");
			}
		}
		ChannelBuffer r = HttpUtil.encodeResponse(OK, jo.toJSONString());
		channel.write(r);

	}

	//isonline_uid
	@Override
	public String getPath() {
		return "/isonline";
	}

}
