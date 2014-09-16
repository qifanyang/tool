package ht.http;

import java.io.Closeable;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.Executors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;

public class HttpServer implements Closeable{
    private static final Log logger = LogFactory.getLog(HttpServer.class);

//    private final PaddedAtomicBoolean started;

    private HandlerObject[] handlers;

    private final ServerBootstrap bootStrap;
    private final ChannelGroup allChannels;

    public HttpServer(){
//        this.started = new PaddedAtomicBoolean(false);

        this.allChannels = new DefaultChannelGroup();
        this.handlers = new HandlerObject[0];

        bootStrap = new ServerBootstrap(new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool(), 8));

        bootStrap.setOption("child.tcpNoDelay", true);
        bootStrap.setOption("child.keepAlive", true);
        bootStrap.setOption("child.sendBufferSize", 1024);
        bootStrap.setOption("child.receiveBufferSize", 1024);

        bootStrap.setPipelineFactory(new ChannelPipelineFactory(){

            @Override
            public ChannelPipeline getPipeline() throws Exception{
                return Channels.pipeline(new HttpRequestDecoder(),
                        new HttpResponseEncoder(), new HttpHandler());
            }
        });
    }

    public HttpServer(int workerCount){
//        this.started = new PaddedAtomicBoolean(false);

        this.allChannels = new DefaultChannelGroup();
        this.handlers = new HandlerObject[0];

        bootStrap = new ServerBootstrap(new NioServerSocketChannelFactory(
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool(), workerCount));

        bootStrap.setOption("child.tcpNoDelay", true);
        bootStrap.setOption("child.keepAlive", true);
        bootStrap.setOption("reuseAddress", true);
        bootStrap.setOption("child.sendBufferSize", 1024);
        bootStrap.setOption("child.receiveBufferSize", 1024);

        bootStrap.setPipelineFactory(new ChannelPipelineFactory(){

            @Override
            public ChannelPipeline getPipeline() throws Exception{
                return Channels.pipeline(new HttpRequestDecoder(),
                        new HttpResponseEncoder(), new HttpHandler());
            }
        });
    }

    //使用扫描包下面的类来注册,不用手动调用register方法
    public void register(String path, IHttpHandler handler){

        String actualPath = path.trim();

        if (path.length() == 0 || path.charAt(0) != '/'){
            throw new IllegalArgumentException("path格式不对, 必须是/开头的");
        }

        logger.error("registering :"+ actualPath);

        HandlerObject[] oldObjects = handlers;
        // 检查不能有相同的
//        for (HandlerObject o : oldObjects){
//            checkArgument(!o.path.equals(path), "HttpServer.register重复注册了: %s",
//                    path);
//        }

        HandlerObject[] newObjects = Arrays.copyOf(oldObjects,oldObjects.length + 1);
        
        newObjects[oldObjects.length] = new HandlerObject(path, handler);
        Arrays.sort(newObjects);

        handlers = newObjects;
    }

    public void start(int port){
     

        allChannels.add(bootStrap.bind(new InetSocketAddress(port)));
        logger.error("HttpServer serving at : "+port);
    }

    public void start(String address, int port){
//        boolean ok = started.compareAndSet(false, true);
//        checkArgument(ok, "HttpServer重复start了");

        allChannels.add(bootStrap.bind(new InetSocketAddress(address, port)));
        logger.error("HttpServer serving at address:"+ address +", port :" + port);
    }

    @Override
    public void close(){
//        boolean ok = started.compareAndSet(true, false);
//        checkArgument(ok, "HttpServer不在启动状态, 但调用了close");

        logger.error("正在关闭HttpServer");
        allChannels.close().awaitUninterruptibly();
        bootStrap.releaseExternalResources();
        logger.error("HttpServer已关闭");
    }

    private static final ChannelBuffer CHANNEL_BUFFER_404 = HttpUtil.codeOnly(HttpResponseStatus.NOT_FOUND);

    private static class HandlerObject implements Comparable<HandlerObject>{

        public final String path;

        public final IHttpHandler handler;

        public final int pathLen;

        HandlerObject(String path, IHttpHandler handler){
            super();
            this.path = path;
            this.handler = handler;
            this.pathLen = path.length();
        }

        private boolean isMatch(String uri){
            return uri.startsWith(path);
        }

        private String stripPrefix(String uri){
            return uri.substring(pathLen);
        }

        @Override
        public int compareTo(HandlerObject o){
            int len1 = path.length();
            int len2 = o.path.length();

            if (len1 < len2){
                return 1;
            }

            if (len1 > len2){
                return -1;
            }

            return path.compareTo(o.path);
        }

    }

    private class HttpHandler extends SimpleChannelUpstreamHandler{

        @Override
        public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
                throws Exception{
            if (e instanceof MessageEvent
                    && ((MessageEvent) e).getMessage() instanceof HttpRequest){
                HttpRequest request = (HttpRequest) ((MessageEvent) e)
                        .getMessage();

                boolean isKeepAlive = HttpHeaders.isKeepAlive(request);

                String uri = request.getUri();

                logger.error("收到http请求: "+ uri);

                // 检测有没有对应的handler

                for (HandlerObject o : handlers){
                    if (o.isMatch(uri)){
                        o.handler.handle(o.stripPrefix(uri), request,
                                ctx.getChannel());
                        return;
                    }
                }

                logger.error("没有handler: "+ uri);
                ChannelFuture future = ctx.getChannel().write(CHANNEL_BUFFER_404);

                if (!isKeepAlive){
                    if (future != null){
                        future.addListener(ChannelFutureListener.CLOSE);
                    }
                }
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e){
            if (e.getCause() instanceof java.io.IOException){
            } else{
                e.getCause().printStackTrace();
            }

            e.getChannel().close();
        }
        
        @Override
        public void channelConnected(ChannelHandlerContext ctx,
        		ChannelStateEvent e) throws Exception {
        	// TODO Auto-generated method stub
        	super.channelConnected(ctx, e);
        	
        	  String string = ctx.getChannel().getRemoteAddress().toString();
        	  String ip = string.substring(1, string.indexOf(":"));
        	  System.out.println(string);
        }

    }
}
