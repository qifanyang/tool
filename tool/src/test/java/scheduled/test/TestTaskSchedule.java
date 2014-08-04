package scheduled.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * @author TOBE
 */
public class TestTaskSchedule {

	static int i = 0;
	@Test
	public void test0() {

		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

		final Runnable beeper = new Runnable() {
			public void run() {
				System.out.println("beep");
			}
		};
		
		final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(beeper, 10, 10, TimeUnit.SECONDS);
		
//		scheduler.schedule(new Runnable() {
//			public void run() {
//				beeperHandle.cancel(true);
//			}
//		}, 5, TimeUnit.SECONDS);

	}

	public static void main(String[] args) {
		
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

		final Runnable beeper = new Runnable() {
			public void run() {
				System.out.println("beep");
			}
		};
		
		final Runnable costtime = new Runnable() {
			public void run() {
				try{
					System.out.println("run cost time task!");
					if(i == 0){
						Thread.sleep(10000);
						//当使用scheduleAtFixedRate执行时,某个任务花费时间过长,会导致任务很快快执行多次
						//当使用scheduleWithFixedDelay执行时,某个任务花费时间过长,不会导致任务很快快执行多次
						i++;
					}
					
				}catch(Exception e){
					
				}
			}
		};
		final Runnable exceptionTask = new Runnable() {
			public void run() {
				System.out.println("run exception task !");
				throw new NullPointerException();
				//抛异常后,后续任务将不会执行,需通过ScheduledFuture.cancel(true)来取消任务
			}
		};
		
		
		
//		final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(costtime, 2, 2, TimeUnit.SECONDS);
		final ScheduledFuture<?> beeperHandle = scheduler.scheduleWithFixedDelay(exceptionTask, 2, 2, TimeUnit.SECONDS);
	}
}
