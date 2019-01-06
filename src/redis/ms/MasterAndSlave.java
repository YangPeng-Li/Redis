package redis.ms;

import redis.clients.jedis.Jedis;

/**
 * 
 * 
 * @author Lyp
 * @date 2018年12月25日
 * @version 1.0
 *
 *	6379 主从复制，读写分离 
 */
public class MasterAndSlave {
	public static void main(String[] args) {
		Jedis jedis_m = new Jedis ("",6379);
		Jedis jedis_s = new Jedis ("",6380);
		
		jedis_s.slaveof("127.0.0.1", 6379);
		jedis_m.set("key1", "v1");
		String string = jedis_s.get("key1");
		
		System.out.println(string);	
		
	}
}
