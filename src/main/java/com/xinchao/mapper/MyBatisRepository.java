package com.xinchao.mapper;



import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xinchao.model.UserEntity;
@Mapper
public interface MyBatisRepository {
	@Select("select * from user")
	public List<UserEntity> findAll();
	
	@Select("SELECT * FROM user WHERE id=#{id}")
	public UserEntity findbyID(long id);
	
	@Select("SELECT * FROM user WHERE username=#{username}")
	public UserEntity findbyUserName(String username);
	
	@Delete("DELETE FROM user WHERE id=#{id}")
	public int deleteByID(long id);
	
	@Insert("INSERT INTO user(id,username,password,email)" + 
			"VALUES(#{id},#{username},#{password},#{email})")
	public int insert(UserEntity user);
	
	@Update("Update user set username=#{username}," +
			"password=#{password},email=#{email} where id=#{id}")
	 public int update(UserEntity user);
	
	
}
