package com.disruptor;

import com.lmax.disruptor.RingBuffer;

/**
 * 
 * @author yangqifan
 */
public class HandlerEventProducer
{
    private final RingBuffer<HandlerEvent> ringBuffer;

    public HandlerEventProducer(RingBuffer<HandlerEvent> ringBuffer)
    {
        this.ringBuffer = ringBuffer;
    }

    public void handle(Handler h)
    {
        long sequence = ringBuffer.next();  // Grab the next sequence
        try
        {
            HandlerEvent event = ringBuffer.get(sequence); // Get the entry in the Disruptor
                                                        // for the sequence
            event.setHandler(h);  // Fill with data
        }
        finally
        {
            ringBuffer.publish(sequence);
        }
    }
}
