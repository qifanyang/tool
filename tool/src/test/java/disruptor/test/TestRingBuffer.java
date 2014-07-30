package disruptor.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;

import com.lmax.disruptor.RingBuffer;

/**
 * 
 * @author yangqifan
 */
public class TestRingBuffer {
	
	public static void main(String[] args) {
		//test ringbuffer
		RingBuffer<HandlerEvent> ringBuffer = RingBuffer.createMultiProducer(new HandlerEventFactory(), 16777216);
		
		int s = 100;
		CountDownLatch downLatch = new CountDownLatch(s);
		
//		for(int i = 0; i < s; i++){
//			new Thread(new AddRingBufferThread(downLatch, ringBuffer)).start();
//			downLatch.countDown();
//		}
//		
		for(int i = 0; i < s; i++){
			new Thread(new AddBlockingQueueThread(downLatch, ringBuffer)).start();
			downLatch.countDown();
		}
		
		
	}
	
	
	static class AddRingBufferThread implements Runnable{
		
		private CountDownLatch downLatch;
		private RingBuffer<HandlerEvent>  ringBuffer;
		
		public AddRingBufferThread(CountDownLatch downLatch, RingBuffer<HandlerEvent>  ringBuffer){
			this.downLatch = downLatch;
			this.ringBuffer = ringBuffer;
		}
		
		@Override
		public void run() {
			try {
				downLatch.await();
				long start = System.currentTimeMillis();
				for(int i = 0; i < 100000; i++){
					long next = ringBuffer.next();
					HandlerEvent handlerEvent = ringBuffer.get(next);
					handlerEvent.setHandler(new Handler() {
						
						@Override
						public void action() {
							
						}
					});
				}
				System.out.println(System.currentTimeMillis() - start);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	static class AddBlockingQueueThread implements Runnable{
		
		private CountDownLatch downLatch;
		private LinkedBlockingDeque<Handler> euqueBlockingDeque = new LinkedBlockingDeque<Handler>(16777216);
		
		public AddBlockingQueueThread(CountDownLatch downLatch, RingBuffer<HandlerEvent>  ringBuffer){
			this.downLatch = downLatch;
		}
		
		@Override
		public void run() {
//			try {
//				downLatch.await();
				long start = System.currentTimeMillis();
				for(int i = 0; i < 100000; i++){
					euqueBlockingDeque.offer(new Handler() {
						
						@Override
						public void action() {
							
						}
					});
				}
				System.out.println(System.currentTimeMillis() - start);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
	}

}
