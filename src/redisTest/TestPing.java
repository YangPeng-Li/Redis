package redisTest;

import redis.clients.jedis.Jedis;

public class TestPing {

	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.1.101",6379);
		System.out.println(jedis.ping());
	}

}
