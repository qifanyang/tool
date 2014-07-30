package disruptor.test;

import com.lmax.disruptor.EventFactory;

/**
 * 
 * @author yangqifan
 */
public class HandlerEventFactory implements EventFactory<HandlerEvent>
{
    public HandlerEvent newInstance()
    {
        return new HandlerEvent();
    }
}
