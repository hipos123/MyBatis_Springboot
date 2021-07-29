package com.yaoxj.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.*;

import com.yaoxj.entity.UserEntity;

public interface UserMapper {
	@Select("select * from sys_user ")
	@Results({
            @Result(property = "userId", column = "user_id"),
			@Result(property = "nickName", column = "login_name"),
			@Result(property = "userCode", column = "user_type"),
			@Result(property = "userName", column = "user_name"),
			@Result(property = "userPwd", column = "password"),
			@Result(property = "createDate", column = "create_time"),
			@Result(property = "updateDate", column = "update_time") })
	public List<UserEntity> queryList();

    @Select("SELECT * FROM sys_user WHERE user_id = #{userId}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "nickName", column = "nick_name"),
            @Result(property = "userCode", column = "user_code"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "userPwd", column = "user_pwd"),
            @Result(property = "createDate", column = "create_date"),
            @Result(property = "updateDate", column = "update_date") })
    UserEntity findById(long userId);


    @Insert("INSERT INTO sys_user(login_name, user_name) VALUES(#{nickName}, #{userCode})")
    int insertParam(@Param("nickName") String nickName, @Param("userCode") String userCode);

    @Insert("INSERT INTO sys_user(login_name, user_name) VALUES(#{nickName,jdbcType=VARCHAR}, #{userCode,jdbcType=INTEGER})")
    int insertByMap(Map<String, Object> map);

    @Insert("insert into sys_user(login_name,user_code,user_name,password,create_time,update_time) values(#{nickName},#{userCode},#{userName},#{userPwd},#{createDate},#{updateDate})")
	public int insertEntity(UserEntity entity);

    @Update("UPDATE sys_user SET login_name=#{nickName} WHERE user_id=#{userId}")
    int updateEntity(UserEntity user);

    @Delete("DELETE FROM user WHERE user_id =#{userId}")
    int delete(Long userId);

    @Delete("DELETE FROM user WHERE user_id =#{userId}")
    int deleteEntity(UserEntity entity);
}
