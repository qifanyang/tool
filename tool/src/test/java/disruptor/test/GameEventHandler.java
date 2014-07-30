package disruptor.test;

import com.lmax.disruptor.EventHandler;

/**
 * 
 * @author yangqifan
 */
public class GameEventHandler implements EventHandler<HandlerEvent>
{
    public void onEvent(HandlerEvent event, long sequence, boolean endOfBatch)
    {
        System.out.println("Handler: " + event + ", value = " +  event.getHandler().getClass().toString());
        //处理游戏逻辑
//        event.getHandler().action();
    }
}
