package redisTest;

import org.apache.log4j.Logger;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * 
	Redis的事务
	是什么：可以一次执行多个命令，本质是一组命令的集合，一个事务中所有命令都会序列化，按顺序地串行化执行而不会被其他命令插入，不许加塞。
	执行 监控 2.2以上支持CAS
	能干什么：一个队列中，一次性顺序行，排他性的执行一系列命令
	怎么玩：
	MULTI（标记一个事务的开始）、
	EXEC（执行所有事务块内的命令）、
	DISCARD（取消事务，放弃执行事务块内的所有命令）、
	WATCH(监视一个或多个，如果事务执行之前这个[这些]key被其他命令所改动那么事务将被打断)、
	UNWATCH(取消watch命令对所有key的监视)
 * ①开启②入队③执行
 * 
 * @author Lyp
 * @date 2018年12月24日
 * @version 1.0
 *
 */
public class TestTransaction {
	
	private static Logger logger = Logger.getLogger(TestAPI.class);
	public static void main(String[] args) {
		
		
		//散步操作 
		Jedis jedis = new Jedis ("192.168.1.101",6379);
		
		Transaction transaction = jedis.multi();
		transaction.set("k5", "v5");
		//transaction.set("k5", "v55");// 前面添加了k5 ，后面就无法添加了
		transaction.set("k6", "v6"); 
		transaction.set("k6", "v66"); //6
		//transaction.exec();
		transaction.discard(); //取消事务，放弃执行事务块内的所有命令
	}
	//加锁
	
	
	/**
	 * 锁：
	 * 通俗点讲，watch命令就是标记一个键，如果标记了一个键，
	 * 在提交事务前如果该键被别人修改过，那事务就会失败，这种情况下通常可以在程序中重新在尝试一次
	 * 
	 * 首先标记了键balance,然后检查余额是否足够，不足就取消标记，并不做扣减；
	 * 足够的话就启动事务进行操作
	 * 如果在此期间balance 被其他它修改，那再提交事务（执行exec），时就会报错，
	 * 程序中通常可以捕获这类错误再重新执行一次，直到成功
	 * 
	 * @return
	 */
	
	@Test
	public boolean Testrancation ()
	{
		Jedis jedis  = new Jedis ("192.168.1.101",6379);
		int balance; //可用余额
		int debt;	//欠额
		int amtToSubtract = 10; //实刷额度
		
	//	jedis.watch("balance");
		balance = Integer.parseInt(jedis.get("balance"));
		if (balance>0)
		{
			logger.info("balance"+balance);
			return true;
		}
		else
		{
			return false;
		}
		
		
		
		/*if(balance <amtToSubtract)
		{
			jedis.unwatch();
			System.out.println("modify");
			return false;
		}
		else
		{
			System.out.println("**********************transaction");
			Transaction transaction = jedis.multi(); //开启事务
			transaction.decrBy("balance", amtToSubtract); //刷卡
			transaction.incrBy("debt", amtToSubtract);
			transaction.exec();//提交
			balance=Integer.parseInt(jedis.get("balance"));
			debt=Integer.parseInt(jedis.get("debt"));
			
			System.out.println("********"+balance);
			System.out.println("********"+debt);
			
			return true;
		}*/
	} 
	
	@Test
	public void testTrance ()
	{
		
		TestTransaction	t = new  TestTransaction();
		boolean testrancation = t.Testrancation();
		System.out.println("test TestTransaction"+testrancation);
	}
	
}
