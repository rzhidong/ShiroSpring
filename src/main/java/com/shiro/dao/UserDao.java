package com.shiro.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.shiro.vo.User;

public interface UserDao {
	
	@Select({"SELECT username,password FROM users WHERE username = #{username}"})
	User getPasswordByUsername(@Param("username")String username);
	
	@Select({"SELECT role_name FROM user_roles WHERE username =#{username}"})
    List<String> getRolesByUsername(@Param("username")String username);
	
    List<String> getPermissionsByRolename(@Param("role_name")String role);
    
}
