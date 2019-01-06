package redisTest;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;

import redis.clients.jedis.Jedis;

/**
 * 热点高频的信息放在Redis 上面
 * 
 * @author Lyp
 * @date 2018年12月24日
 * @version 1.0
 *
 */
public class TestAPI {
	
	private static Logger logger = Logger.getLogger(TestAPI.class);
			
	public static void main(String[] args) {
		Jedis jedis = new Jedis ("192.168.1.101",6379);
		jedis.set("k1", "v1");
		jedis.set("k2", "v2");
		jedis.set("k3", "v3");
		jedis.set("k4", "v4");
		jedis.set("k5", "v5");
		
		Set<String> sets = jedis.keys("*");
		String echo = jedis.echo("123456789");
		System.out.println(echo);
		String strs = jedis.get("*");
		String aa = jedis.get("aa");
		logger.info("aa------->"+aa);
		logger.info(strs);
		logger.info(sets);
		
		System.out.println(sets.size());
	}
	
	/**
	 *五大命令
	 */
	@Test
	public void testRedis ()
	{
		//key
		Jedis jedis  = new Jedis ("192.168.1.101",6379);
		Set<String> keys = jedis.keys("*");
		
		for (Iterator iterator = keys.iterator();iterator.hasNext();)
		{
			Object key = iterator.next();
			System.out.println("key-----------"+key);
		}
		System.out.println("Jedis.exists=====>"+jedis.exists("k6"));
		System.out.println("jedis.ttl(k2)"+jedis.ttl("k2"));
		//logger.info();
		//logger.info(jedis.get("k3"));
		jedis.append("k2", "ymh");
		System.out.println("jedis.get(\"k2\")"+jedis.get("k2"));
		//System.out.println();
		
		logger.info("-----------List----------------");
		
		
		jedis.mset("str1","v1","str2","v2","str3","v3");
		//logger.info("jedis.mget-----》 keys"+jedis.mget("keys *"));
		//logger.info(jedis.mget("str1 str2 str3----->"+"str1","str2","str3"));
		System.out.println("jedis.mget-----》 keys"+jedis.mget("keys *"));
		System.out.println("str1 str2 str3----->"+jedis.mget("str1","str2","str3"));
		List <String> list  =  jedis.lrange("mylist", 0, -1);
		for (String element :list )
		{
			System.out.println("LIST-----------"+element);
			//logger.info(element);
		}
		logger.info("---------set-------------");
		//set
		jedis.sadd("orders", "jd001");
		jedis.sadd("orders", "jd002");
		jedis.sadd("orders", "jd003");
		Set<String> set = jedis.smembers("orders");
		
		
		for (Iterator iterator = set.iterator();iterator.hasNext();)
		{
			Object next = iterator.next();
			//logger.info(next);
			System.out.println("Set-------"+next);
		}
		jedis.srem("orders", "jd002");
		System.out.println(jedis.smembers("orders").size());
		//hash
		logger.info("---------hash-----------");
		jedis.hset("hash1", "userName", "lisi");
		jedis.hset("hash1", "id", "1001");
		System.out.println("HGET HASH1"+jedis.hget("hash1", "userName"));
		//logger.info(jedis.hget("hash1", "userName"));
		Map<String,String> map = new HashMap<String,String> ();
		map.put("telphone", "13310001112");
		map.put("address", "NJNAN");
		map.put("email", "abc@169.com");
		jedis.hmset("hash2", map);
		List<String> result = jedis.hmget("hash2", "telphone","email");
		for (String element : result)
		{
			System.out.println("set----------"+element);
		}
		
		//Zset
		logger.info("------------Zset---------------");
		jedis.zadd("zset01",60d,"v1");
		jedis.zadd("zset01",70d,"v2");
		jedis.zadd("zset01",80d,"v3");
		jedis.zadd("zset01",90d,"v4");
		
		Set<String> zrg = jedis.zrange("zset01", 0, -1);
	
		
		for (Iterator<String> iterator = zrg.iterator();iterator.hasNext();)
		{
			String next = iterator.next();
			System.out.println("Zset-----------"+next);
		}
		
	}
}