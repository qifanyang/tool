package jedis.test;

import org.junit.Test;

import redis.clients.jedis.Jedis;

public class RedisTest {
	
	@Test
	public void test0(){
		//redis-server redis.conf启动服务器
		//在windows下启动指定参数文件 , 用来修改参数
		//可以改端口,密码认证....
		//requirepass foobared 指定redis密码
		//masterauth  配置主从时,如果master设置了密码,这里写master的requirepass密码, 用于slave连接master
		//默认端口6379
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("foobared");
		
		//string 类型的value值最大为1GB
		String res = jedis.set("foo", "bar");
		System.out.println(res);
		
		//不存在则设置, 0:表示失败, 1:成功
		long r = jedis.setnx("foo1", "bar");
		System.out.println(r);
		
		
		String value = jedis.get("foo");
		System.out.println("redis value = " + value);
		
		//写到master,从slave中读取数据
		jedis.set("masterv", "hahahgggggggggggggggggggggggggh");
		
		Jedis s1 = new Jedis("localhost", 7001);
		s1.auth("foobared");
		
		System.out.println("s1 get master = " + s1.get("masterv"));
	}

}
