package redis.trancation;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class TestTx {
	
	public boolean TransMethod () throws InterruptedException
	{
		Jedis jedis  = new Jedis ("192.168.1.101",6379);
		int balance; //�������
		int debt;	//Ƿ��
		int amtToSubtract = 10; //ʵˢ���
		
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
			 * ���Ҹ�֮ǰ���˽����ȵ� ���Ҿͻ��������
			 */
			jedis.unwatch();
			System.out.println("modify");
			return false;
		}
		else
		{
			System.out.println("**********************transaction");
			Transaction transaction = jedis.multi(); //��������
			transaction.decrBy("balance", amtToSubtract); //ˢ��
			transaction.incrBy("debt", amtToSubtract);
			transaction.exec();//�ύ
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