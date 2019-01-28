package org.mybatis.example;

import org.apache.ibatis.annotations.Select;

public interface UserMapper {
//	@Select("select * from User where id = #{id}")
	User selectUser(int id);
	
	int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
