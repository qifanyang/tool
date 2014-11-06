public class ModTest {
	
	
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

	@Override
	public int hashCode() {
		return 1;
	}
	
	public static void main(String[] args) {
		ModTest modTest = new ModTest();
		System.out.println(System.identityHashCode(modTest));
		System.out.println(modTest.toString());
		System.out.println(modTest.hashCode());
		System.out.println("sleep 5 seconds");
		try{
			Thread.sleep(5000);
		}catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("ANT 调用 java 代码执行结束");
	}
}
