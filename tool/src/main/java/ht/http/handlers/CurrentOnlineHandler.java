package ht.http.handlers;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.HttpRequest;

import ht.http.IHttpHandler;

public class CurrentOnlineHandler implements IHttpHandler {

	@Override
	public void handle(String uri, HttpRequest request, Channel channel) {
		

	}

	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return null;
	}

}
