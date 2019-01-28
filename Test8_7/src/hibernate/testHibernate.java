package hibernate;


import java.util.Calendar;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class testHibernate {
	
	@Before
	public void before(){
		System.out.println("开始");
	}
	@After
	public void after(){
		System.out.println("结束");
	}
	
	@Test
	public void testHibernate(){
		Simple simple = new Simple();
		simple.setId(1);
		simple.setTitle("s");
		simple.setDate(new Date());
		//1、获取配置
		Configuration config = new Configuration().configure("/hibernate/hibernate.cfg.xml");
		//2、获取工厂
		SessionFactory sessionFactory = config.buildSessionFactory();
		
//		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties())
//				.buildServiceRegistry();
//		SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
		//3、得到会话，CurrentSession需要配置current_session_context_class
		Session session = sessionFactory.getCurrentSession();
//		sessionFactory.openSession();
		//5、打开事务
		Transaction beginTransaction = session.beginTransaction();
		//6、操作
		session.save(simple);
		simple.setTitle("ssss");
//		Simple simp = (Simple)session.get(Simple.class, 5);
//		Simple simp = (Simple)session.load(Simple.class, 9);
//		simp.setTitle("ssss");
		
		Simple sim = (Simple)session.get(Simple.class, 9);
//		Simple sim = (Simple)session.createCriteria(Simple.class).uniqueResult();
//		session.refresh(sim);
//		System.out.println(sim.getTitle());
		//7、提交事务
		beginTransaction.commit();
		//8、关闭session和factory
		session.close();
		sessionFactory.close();
	}

}
