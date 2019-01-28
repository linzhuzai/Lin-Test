package org.mybatis.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class TestU {
	@SuppressWarnings("unused")
	@Test
	public void test(){
		//SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域
		SqlSession session = null;
		try {
			String resource = "mybatis-config.xml";
//			String pro = "config.properties";
			InputStream input = Resources.getResourceAsStream(resource);
//			Properties properties = new Properties();
//			properties.load(Resources.getResourceAsStream(pro));
			//一旦创建了 SqlSessionFactory，就不再需要它了。因此 SqlSessionFactoryBuilder 实例的最佳作用域是方法作用域（也就是局部方法量）。
			//你可以重用 SqlSessionFactoryBuilder 来创建多个 SqlSessionFactory 实例
			SqlSessionFactory sqlSession = new SqlSessionFactoryBuilder().build(input);
			session = sqlSession.openSession();
//			User user = session.selectOne("org.mybatis.example.UserMapper.selectUser",1);
			
			UserMapper userMapper = session.getMapper(UserMapper.class); //用注解时要注释掉xml
			User user = userMapper.selectByPrimaryKey(1l);
			System.out.println(user.getName());
			User usern = new User();
			usern.setId(1l);
			usern.setName("lin");
//			System.out.println(userMapper.insert(usern));
//			userMapper.updateByPrimaryKey(usern);
//			userMapper.deleteByPrimaryKey(13l);
			
			session.commit();
			session.close();
			
		} catch (IOException e) {
			if(null != session){
				session.close();
			}
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testSpring(){
		try {
			ApplicationContext appli = new ClassPathXmlApplicationContext("spring.xml");
			
			UserService userService = (UserService)appli.getBean("userService");
			
			System.out.println(userService.selectUser(1).getName());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
