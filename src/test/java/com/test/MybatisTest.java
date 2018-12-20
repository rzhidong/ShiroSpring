package com.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.shiro.dao.UserDao;

@RunWith(SpringJUnit4ClassRunner.class)//表示继承了SpringJUnit4ClassRunner类
@ContextConfiguration(locations="classpath:spring/spring.xml")
public class MybatisTest {
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public void test() {
		System.out.println(userDao.getPasswordByUsername("Mark"));
		List<String> roles = userDao.getRolesByUsername("Mark");
		System.out.println(roles);
		List<String> permission = new ArrayList<String>();
		for (String role : roles) {
			permission.addAll(userDao.getPermissionsByRolename(role));
		}
		System.out.println(permission);
	}

}
