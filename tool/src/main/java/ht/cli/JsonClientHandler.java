package ht.cli;



import ht.msg.JsonMsg;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;



public class JsonClientHandler extends SimpleChannelUpstreamHandler {
	private static final Log log = LogFactory.getLog(JsonClientHandler.class);
	
	private MsgProcessor processor = new MsgProcessor();
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		JsonMsg msg = (JsonMsg) e.getMessage();
		processor.onMessage(msg);
	}
	
	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		super.channelOpen(ctx, e);
		//TODO 验证是否合法 ,不合法关掉连接
			
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext arg0, ExceptionEvent arg1)
			throws Exception {
//		LoggerManager.Backend.error("连接后台发生异常", arg1.getCause());
//		super.exceptionCaught(arg0, arg1);
//		BackendClient.getMe().close();
		//Must not be called from a I/O-Thread to prevent deadlocks!
		
	}
}
