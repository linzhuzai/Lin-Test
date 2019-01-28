package org.mybatis.example;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService{
	@Autowired
	private UserMapper usermapper;
	
	@Autowired
	private SqlSession sqlSession;
	
	public User selectUser(int id){
//		sqlSession.selectOne("org.mybatis.example.UserMapper.selectUser",1);//第一种，这种需要在spring配置模版
//		sqlSession.getMapper(UserMapper.class).selectUser(id);//第二种，这种需要在spring配置模版
		
		//第三种，这个需要继承SqlSessionDaoSupport,SqlSessionDaoSupport 需要一个 sqlSessionFactory 或 sqlSessionTemplate 属性来 设 置 。 
		//这 些 被 明 确 地 设 置 或 由 Spring 来 自 动 装 配 。 如 果 两 者 都 被 设 置 了 , 那 么 SqlSessionFactory 是被忽略的
//		getSqlSession().selectOne("org.mybatis.example.UserMapper.selectUser",1);
		return usermapper.selectUser(id);//第四种
	}

}
