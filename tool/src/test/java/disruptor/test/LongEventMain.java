package disruptor.test;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * 
 * @author yangqifan
 */
public class LongEventMain
{
//    public static void main(String[] args) throws Exception
//    {
//        // Executor that will be used to construct new threads for consumers
//        Executor executor = Executors.newCachedThreadPool();
//
//        // The factory for the event
//        HandlerEventFactory factory = new HandlerEventFactory();
//
//        // Specify the size of the ring buffer, must be power of 2.
//        int bufferSize = 1024;
//
//        // Construct the Disruptor
//        Disruptor<HandlerEvent> disruptor = new Disruptor<HandlerEvent>(factory, bufferSize, executor);
//
//        // Connect the handler
//        disruptor.handleEventsWith(new GameEventHandler());
//
//        // Start the Disruptor, starts all threads running
//        disruptor.start();
//
//        // Get the ring buffer from the Disruptor to be used for publishing.
//        RingBuffer<HandlerEvent> ringBuffer = disruptor.getRingBuffer();
//
//        LongEventProducer producer = new LongEventProducer(ringBuffer);
//
//        ByteBuffer bb = ByteBuffer.allocate(8);
//        for (long l = 0; true; l++)
//        {
//            bb.putLong(0, l);
//            Thread.sleep(1000);
//        }
//    }
}
