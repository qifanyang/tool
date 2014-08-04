package objectsize.test;

import gnu.trove.map.hash.TIntIntHashMap;

import org.junit.Test;

import com.tobe.util.RamUsageEstimator;

public class TestSize {

	@Test
	public void test0(){
		OneOBJ obj = new OneOBJ();
//		obj.setS("tttt");
		System.out.println(RamUsageEstimator.shallowSizeOf(obj));
		System.out.println(RamUsageEstimator.isSupportedJVM());
		
		TIntIntHashMap t1 = new TIntIntHashMap();
		TIntIntHashMap t2 = new TIntIntHashMap();
		
		t1.put(12, 10);
		t1.put(12, 20);
		System.out.println(t1.get(12));
	}
	
}


