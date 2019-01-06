package redisTest;

import org.apache.log4j.Logger;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * 
	Redis������
	��ʲô������һ��ִ�ж�����������һ������ļ��ϣ�һ��������������������л�����˳��ش��л�ִ�ж����ᱻ����������룬���������
	ִ�� ��� 2.2����֧��CAS
	�ܸ�ʲô��һ�������У�һ����˳���У������Ե�ִ��һϵ������
	��ô�棺
	MULTI�����һ������Ŀ�ʼ����
	EXEC��ִ������������ڵ������
	DISCARD��ȡ�����񣬷���ִ��������ڵ����������
	WATCH(����һ���������������ִ��֮ǰ���[��Щ]key�������������Ķ���ô���񽫱����)��
	UNWATCH(ȡ��watch���������key�ļ���)
 * �ٿ�������Ӣ�ִ��
 * 
 * @author Lyp
 * @date 2018��12��24��
 * @version 1.0
 *
 */
public class TestTransaction {
	
	private static Logger logger = Logger.getLogger(TestAPI.class);
	public static void main(String[] args) {
		
		
		//ɢ������ 
		Jedis jedis = new Jedis ("192.168.1.101",6379);
		
		Transaction transaction = jedis.multi();
		transaction.set("k5", "v5");
		//transaction.set("k5", "v55");// ǰ�������k5 ��������޷������
		transaction.set("k6", "v6"); 
		transaction.set("k6", "v66"); //6
		//transaction.exec();
		transaction.discard(); //ȡ�����񣬷���ִ��������ڵ���������
	}
	//����
	
	
	/**
	 * ����
	 * ͨ�׵㽲��watch������Ǳ��һ��������������һ������
	 * ���ύ����ǰ����ü��������޸Ĺ���������ͻ�ʧ�ܣ����������ͨ�������ڳ����������ڳ���һ��
	 * 
	 * ���ȱ���˼�balance,Ȼ��������Ƿ��㹻�������ȡ����ǣ��������ۼ���
	 * �㹻�Ļ�������������в���
	 * ����ڴ��ڼ�balance ���������޸ģ������ύ����ִ��exec����ʱ�ͻᱨ��
	 * ������ͨ�����Բ����������������ִ��һ�Σ�ֱ���ɹ�
	 * 
	 * @return
	 */
	
	@Test
	public boolean Testrancation ()
	{
		Jedis jedis  = new Jedis ("192.168.1.101",6379);
		int balance; //�������
		int debt;	//Ƿ��
		int amtToSubtract = 10; //ʵˢ���
		
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
			Transaction transaction = jedis.multi(); //��������
			transaction.decrBy("balance", amtToSubtract); //ˢ��
			transaction.incrBy("debt", amtToSubtract);
			transaction.exec();//�ύ
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
