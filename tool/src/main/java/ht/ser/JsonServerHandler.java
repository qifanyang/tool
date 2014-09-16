package ht.ser;

import java.util.concurrent.ConcurrentHashMap;

import ht.MessageProcessor;
import ht.NetConnection;
import ht.msg.JsonMsg;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.group.ChannelGroup;

public class JsonServerHandler extends SimpleChannelHandler {

	private static final Log log = LogFactory.getLog(JsonServerHandler.class);
	
	private ChannelGroup allChannels;
	private MessageProcessor<JsonMsg> msgProcessor;
	
	public JsonServerHandler(ChannelGroup allChannels){
		this.allChannels = allChannels;
//		log.error("创建 serverhandler "); 一个连接创建一个
	}
	
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)throws Exception {
		log.error("后台收到消息");
		JsonMsg msg = (JsonMsg) e.getMessage();
		NetConnection conn = (NetConnection) ctx.getAttachment();
		conn.messageReceived(msg);
//		super.messageReceived(ctx, e);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)throws Exception {
		super.exceptionCaught(ctx, e);
		log.error("连接发生异常, 关闭连接, " + ctx.getChannel().getRemoteAddress().toString(), e.getCause());
		NetConnection conn = (NetConnection) ctx.getAttachment();
		if(conn != null){
			int sid = conn.getSid();
			if(sid > 0){
				GameServerManager.getMe().remove(sid, conn.getIp());
			}
			ctx.getChannel().close();
		}
	}
	
    @Override
    public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
            throws Exception {
//        ((SocketChannelConfig) ctx.getChannel().getConfig())
//                .setPerformancePreferences(0, 1, 2); 
        
        //连接打开时检查ip
        String string = ctx.getChannel().getRemoteAddress().toString();
        String ip = string.substring(1, string.indexOf(":"));
        ConcurrentHashMap<String,LoginServer> loginserverMap = GameServerManager.getMe().getLoginserverMap();
//        if(loginserverMap.containsKey(ip) || ip.equals("127.0.0.1")){
        if(!loginserverMap.containsKey(ip) && !ip.equals("127.0.0.1")){
        	log.error("非法ip连接到后台, 直接断掉 ip = " + ip);
        	ctx.getChannel().close();
        	return;
        }
        super.channelOpen(ctx, e);
    }
    
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		super.channelConnected(ctx, e);
		Channel channel = ctx.getChannel();
		allChannels.add(channel);
		NetConnection conn = new NetConnection(channel);
		ctx.setAttachment(conn);
	}
	
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		super.channelClosed(ctx, e);
		NetConnection conn = (NetConnection) ctx.getAttachment();
		if(conn != null){
			int sid = conn.getSid();
			if(sid > 0){
				GameServerManager.getMe().remove(sid, conn.getIp());
			}
		}
	}
}
