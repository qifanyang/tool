package guava.test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import junit.framework.TestCase;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.util.concurrent.ListenableFuture;

public class TestCache extends TestCase {
	
	public void testuse(){
		
		System.out.println(System.getProperties());
//		1、原子的内容加载，当Key在Cache中不存在时，会回调Loader函数，如果有其他并发的对该Key的请求会等待Loader函数而不是重复加载。
//		2、maximum 数量限制，超出时用LRU（least-recently-used)算法清除。
//		3、超时限制，设置max idle time 或 max live time，在每次取元素时都会做超时检查。
//		4、Key是weak reference， Value是weak 或者 soft reference，真的内存不足时，会被GC掉，慎用。
		
		int s = 1;
		for(;s < 10000;){
			s += s;
		}
		System.out.println(s);
		//cacheLoader
		 LoadingCache<String,String> cahceBuilder=CacheBuilder.newBuilder().maximumSize(3)
				 .removalListener(new RemovalListener<String,String>() {

					@Override
					public void onRemoval(RemovalNotification<String, String> notification) {
						//缓存超过最大数量限制,，超出时用LRU（least-recently-used)算法清除。
						System.out.println("yichu key :"+notification.getKey());
						System.out.println("yichu value :"+notification.getValue());
						
					}})
				 .build(new CacheLoader<String, String>(){
			            @Override
			            public String load(String key){        
			                String strProValue="hello "+key+"!";                
			                return strProValue;
			            }
			            
			            @Override
			            @GwtIncompatible("Futures")
			            public ListenableFuture<String> reload(String key,
			            	String oldValue) throws Exception {
			            // TODO Auto-generated method stub
			            return super.reload(key, oldValue);
			            }
			            
			        });  
		 
		 	//线程安全的
	        System.out.println("jerry value:"+cahceBuilder.getUnchecked("one"));
	        System.out.println("peida value:"+cahceBuilder.getUnchecked("two"));
	        System.out.println("peida value:"+cahceBuilder.getUnchecked("three"));
	        System.out.println("jerry value:"+cahceBuilder.getUnchecked("one"));
	        System.out.println("peida value:"+cahceBuilder.getUnchecked("four"));
	       
//	        cahceBuilder.refresh("three");
	        
	        
	        Cache<String, String> cache = CacheBuilder.newBuilder().maximumSize(1000).build();  
	        String resultVal = null;
			try {
				resultVal = cache.get("jerry", new Callable<String>() {  
				    public String call() {  
				        String strProValue="hello "+"jerry"+"!";                
				        return strProValue;
				    }  
				});
			} catch (ExecutionException e) {
				e.printStackTrace();
			}  
	        System.out.println("jerry value : " + resultVal);
	        
	        try {
				resultVal = cache.get("peida", new Callable<String>() {  
				    public String call() {  
				        String strProValue="hello "+"peida"+"!";                
				        return strProValue;
				    }  
				});
			} catch (ExecutionException e) {
				e.printStackTrace();
			}  
	        System.out.println("peida value : " + resultVal);     
	}

}
