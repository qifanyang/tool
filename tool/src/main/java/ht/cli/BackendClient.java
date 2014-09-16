package ht.cli;

import ht.JsonDecoder;
import ht.JsonEncoder;
import ht.msg.IMessage;
import ht.msg.ReqRegisterMsg;
import ht.msg.TestMsg;

import java.net.InetSocketAddress;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.DefaultChannelPipeline;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.frame.LengthFieldBasedFrameDecoder;
import org.jboss.netty.handler.codec.frame.LengthFieldPrepender;

//import com.kueem.pgame.biz.constant.Constants;
//import com.kueem.pgame.util.LoggerManager;


/**后台客户端*/
public class BackendClient {
//	private static int server_port = Integer.parseInt(Constants.getConfig().getProperty("backend.port"));
//	private static String server_host = Constants.getConfig().getProperty("backend.ip");
//	public static  int backendCheckTime = Integer.parseInt(Constants.getConfig().getProperty("backend.check.time"));//连接后台检测时间间隔,单位:秒
	static Channel channel = null;
//	private static ReentrantLock lock = new ReentrantLock();
	public static BackendClient me = new BackendClient();
	private ClientBootstrap bootstrap = null;
//	private volatile boolean running;
	//没有连接到后台提示太多了,修改
	private int tipscount = 0;
	 
	
	private final Executor sendMsgExecutor = Executors.newSingleThreadExecutor();//单线程
	//单线程,采用丢弃策略
//	private final Executor sendMsgExecutor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.DiscardPolicy());
	
	BackendClient(){
	}
	
	public static BackendClient getMe(){
		return me;
	}
	
	public void start(){
		
		// Configure the client.
        bootstrap = new ClientBootstrap(
                new NioClientSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));

        bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.setOption("child.sendBufferSize", 8192 * 4);
		bootstrap.setOption("child.receiveBufferSize", 8192 * 2);
        // Set up the pipeline factory.
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
            	DefaultChannelPipeline result = new DefaultChannelPipeline();

                result.addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2));//按长度读取出来
                result.addLast("JsonDecoder", new JsonDecoder());
                
                result.addLast("frameEncoder", new LengthFieldPrepender(2));
                result.addLast("JsonEncoder", new JsonEncoder());
               
                result.addLast("handler", new JsonClientHandler());
                
				return result;
            }
        });

        
        // Start the connection attempt.
//        ChannelFuture future = bootstrap.connect(new InetSocketAddress(server_host, server_port));
        ChannelFuture future = bootstrap.connect(new InetSocketAddress("", 9090));

        channel = future.awaitUninterruptibly().getChannel();
        if (!future.isSuccess()) {
        	channel = null;
            bootstrap.releaseExternalResources();
            if((tipscount++ % 30) == 0){//十分钟提示 一次
//            	LoggerManager.Backend.error("连接后台失败, " + backendCheckTime + "秒后再次连接", future.getCause());
            }
            return;
        }

      
    }
	
	public void close(){
		channel.close().awaitUninterruptibly();
        bootstrap.releaseExternalResources();
	}
	
	public boolean isConnected(){
		return channel != null && channel.isConnected() ? true : false; 
	}
	
	
	/**
	 * 采用单线程的线程池发送消息,不会阻塞
	 * @param msg
	 * @return
	 */
	public boolean sendMessage(IMessage msg){
		if(!isConnected()){
//			LoggerManager.Backend.error("没有连接到后台,发送消息失败, msg = " + msg.toString());
			return false;
		}
		try {
			SendMsgTask sendMsgTask = new SendMsgTask(msg);
			sendMsgExecutor.execute(sendMsgTask);
		} catch (Exception e) {
//			LoggerManager.Backend.error(e, e);//默认策略会抛异常
		}
		return true;
	}
	
	
	private static class SendMsgTask implements Runnable{

		private IMessage msg;
		
		public SendMsgTask(IMessage msg){
			this.msg = msg;
		}
		
		@Override
		public void run() {
			try {
//				lock.lock();//单线程的executor不用枷锁
				channel.write(msg);
			} catch (Exception e) {
//				LoggerManager.Backend.error(e, e);
			}finally{
//				lock.unlock();
			}
		}
		
	}
	
	/**连接到后台后,首先发送认证消息,在后台进行注册*/
	public void register(){
		ReqRegisterMsg msg = new ReqRegisterMsg();
//		msg.setSid(Integer.parseInt(Constants.getServerId()));
		msg.setKey("123456");
		sendMessage(msg);
	}
	
//	public static void main(String[] args) throws InterruptedException {
//		
//	}
}
