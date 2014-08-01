package jmx.test;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

import org.junit.Test;

public class JmxTest {
	
	@Test
	public void test1(){
		for(long i = 0; i < 1000000000l; i++){
			Long jj = new Long(i);
			jj++;
		}
		List<GarbageCollectorMXBean> gcbeans = ManagementFactory.getGarbageCollectorMXBeans();
		System.out.println(gcbeans.size());
		for(GarbageCollectorMXBean b : gcbeans){
			System.out.println(b.getCollectionCount());
		}
	}

}
