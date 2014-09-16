package ht.ser;

import ht.JsonDecoder;
import ht.JsonEncoder;
import ht.msg.IMessage;
import ht.msg.ReqNoticeMsg;
import ht.msg.TestMsg;

import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.DefaultChannelPipeline;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;
import org.jboss.netty.handler.execution.ExecutionHandler;

/**
 * 后台socket服务器,启动Tomcat时开启socket服务器,接受游戏服务器的连接
 * 
 */
public class BackendServer {
	private static final Log log = LogFactory.getLog(BackendServer.class);
	
	public static String PROGRAM_PATH = null;
	private String bindAddr = null;
	private int port = 9090;
	private ServerBootstrap bootstrap = null;
	private volatile boolean running;
	private int sessionIdleTime;
	// private SessionHandler handler;
	static ExecutionHandler EXECUTIONHANDLER;
	private Channel serverChannel;
	private ChannelGroup allChannels = new DefaultChannelGroup();

	private static BackendServer me = new BackendServer();
	
	private BackendServer(){}
	
	public static BackendServer getMe(){
		return me;
	}
	
	private void init(){
		try {
			GameServerManager.getMe().loadLoginConfig();
			GameServerManager.getMe().loadGSConfig();
			GameServerManager.getMe().loadLoginServer();
			GameServerManager.getMe().loadActivity();
//			ModelManager.getMe().loadAllModel();
		} catch (Exception e) {
			log.error("初始化后台配置出错", e);
		}
	}
	
	public void start() {
		if(running){
			log.error("后台socket服务器 已经开启", new RuntimeException());
			return;
		}
		
		init();
		ChannelFactory factory = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());

	    bootstrap = new ServerBootstrap(factory);


		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.setOption("reuseAddress", true);// 将允许将套接字绑定到已在使用中的地址
		bootstrap.setOption("child.sendBufferSize", 8192 * 4);
		bootstrap.setOption("child.receiveBufferSize", 8192 * 2);

		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() {
				DefaultChannelPipeline result = new DefaultChannelPipeline();

                result.addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));//按长度读取出来
                result.addLast("JsonDecoder", new JsonDecoder());

                result.addLast("frameEncoder", new LengthFieldPrepender(2));
                result.addLast("JsonEncoder", new JsonEncoder());

//                result.addLast("handler", new ProtobufHandler<T>(allChannels,workerFactory));
                result.addLast("handler", new JsonServerHandler(allChannels));
                
				return result;
			}
		});
		
		bootstrap.bind(new InetSocketAddress(port));
		running = true;
		log.error("后台socket服务器启动成功, port = " + port);
	}
	
	public boolean isRunning(){
		return running;
	}
	
	public void stop(){
		running = false;
		allChannels.close().awaitUninterruptibly();
		bootstrap.releaseExternalResources();
		log.error("关闭后台socket服务器");
	}
	
	
	public static void main(String[] args) {
		final BackendServer backendServer = getMe();
		backendServer.start();
		
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				try {
//					TestMsg testMsg = new TestMsg();
					ReqNoticeMsg testMsg = new ReqNoticeMsg();
					testMsg.setType(1);
					testMsg.setContent("公告测试");
					backendServer.allChannels.write(testMsg);
					log.error("服务端定时发送消息 :"+testMsg.toString());
				} catch (Exception e) {
					log.error("定时发送消息异常 ", e);
				}
				
			}
		}, 10*1000, 5000);
	}
}
