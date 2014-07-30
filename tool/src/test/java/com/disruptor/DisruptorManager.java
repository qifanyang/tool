package com.disruptor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

/**
 * 
 * @author yangqifan
 */
public class DisruptorManager {
	
	private static DisruptorManager ins = new DisruptorManager();
	
	HandlerEventProducer producer;
	
	public static DisruptorManager getIns(){
		return ins;
	}
	
	public DisruptorManager() {
		 // Executor that will be used to construct new threads for consumers
        Executor executor = Executors.newCachedThreadPool();

        // The factory for the event
        HandlerEventFactory factory = new HandlerEventFactory();

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        RingBuffer<HandlerEvent> muli = RingBuffer.createMultiProducer(factory, bufferSize, new BlockingWaitStrategy());
       
        // Construct the Disruptor
        Disruptor<HandlerEvent> disruptor = new Disruptor<HandlerEvent>(factory, bufferSize, executor);
        // Connect the handler
        disruptor.handleEventsWith(new GameEventHandler());

        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<HandlerEvent> ringBuffer = disruptor.getRingBuffer();

        producer = new HandlerEventProducer(ringBuffer);
        
	}
	
	public void addEvent(Handler h){
		producer.handle(h);
	}

}
