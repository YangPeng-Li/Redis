package redisTest;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class TestJedisPool {
	
	public static void main(String[] args) {
		JedisPool jedisPool1 = JedisPoolUtil.getJedisPoolInstance();
		JedisPool jedisPool2 = JedisPoolUtil.getJedisPoolInstance();
		
		System.out.println(jedisPool1 == jedisPool2);
		
		Jedis jedis = null;
		
		try {
			jedis=jedisPool1.getResource();
			jedis.set("aa", "bb");
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			JedisPoolUtil.release(jedisPool1, jedis);
		}
	}
}