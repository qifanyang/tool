package ht.http;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.handler.codec.http.HttpRequest;

public interface IHttpHandler{

    /**
     * 不能是阻塞的, 阻塞的放到线程池里去
     * @param uri
     * @param request
     * @param channel
     */
    void handle(String uri, HttpRequest request, Channel channel);
    
    String getPath();
}
