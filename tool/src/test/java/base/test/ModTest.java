package base.test;

import org.junit.Test;

public class ModTest {
	
	@Test
	public void test1(){
		int i = 1;
		int r = 0;
		int max = 2000000000;
		long start = System.nanoTime();
		for(;i <= max; i++){
			//低位全一,模拟取模
//			System.out.println(i&3);
			r = i & 3;
		}
		System.out.println(System.nanoTime() - start);
		i = 1;
		start = System.nanoTime();
//		System.out.println("============");
		for(;i <= max; i++){
//			System.out.println(i%4);
			r = i % 4;
		}
		System.out.println(System.nanoTime() - start);
	}

}
