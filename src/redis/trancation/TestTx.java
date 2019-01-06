package redis.trancation;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestTx {
	
	public boolean TransMethod () throws InterruptedException
	{
		Jedis jedis  = new Jedis ("192.168.1.101",6379);
		int balance; //可用余额
		int debt;	//欠额
		int amtToSubtract = 10; //实刷额度
		
		jedis.watch("balance");
		Thread.sleep(7000);
		balance = Integer.parseInt(jedis.get("balance"));
	/*	if (balance>0)
		{
			logger.info("balance"+balance);
			return true;
		}
		else
		{
			return false;
		}
		*/
		if(balance <amtToSubtract)
		{
			/**
			 * 在我改之前有人捷足先登 ，我就会放弃更改
			 */
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
		}
	}
	public static void main(String[] args) throws InterruptedException {
		TestTx t = new TestTx();
		boolean TestTx = t.TransMethod();
		System.out.println("main---"+TestTx);
	}
}