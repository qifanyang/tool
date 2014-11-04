package base.test;

import org.junit.Test;

public class ModTest {
	
	@Test
	public void test1(){
		int i = 1;
		for(;i <= 10; i++){
			//低位全一,模拟取模
			System.out.println(i&3);
		}
		System.out.println("============");
		i = 1;
		for(;i <= 10; i++){
			System.out.println(i%4);
		}
	}

}
