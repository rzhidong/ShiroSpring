package com.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shiro.vo.User;

@Controller
@RequestMapping("/shiro")
public class UserController {

	@RequestMapping(value = "/subLogin", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public String subLogin(User user) {

		Subject subject = SecurityUtils.getSubject();

		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());

		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			return e.getMessage();
		}

		if (subject.hasRole("admin")) {
			System.out.println(subject.getPrincipal() + "有admin权限");
		}

		if (subject.isPermitted("user:update")) {
			System.out.println(subject.getPrincipal() + "有update权限");
		}

		if (subject.isPermitted("user:select")) {
			System.out.println(subject.getPrincipal() + "有select权限");
		}

		return "登录成功";
	}

	@RequiresRoles("admin")
	@RequestMapping(value = "/testAdmin", method = RequestMethod.GET)
	@ResponseBody
	public String testAdmin() {
		return "testAdmin success";
	}

	@RequiresRoles("system")
	@RequestMapping(value = "/testSystem", method = RequestMethod.GET)
	@ResponseBody
	public String testSystem() {
		return "testSystem success";
	}

	@RequiresPermissions("user:select")
	@ResponseBody
	@RequestMapping(value = "/testSelect", method = RequestMethod.GET)
	public String testSelect() {
		return "testSelect success";
	}

	@RequiresPermissions("user:update")
	@ResponseBody
	@RequestMapping(value = "/testUpdate", method = RequestMethod.GET)
	public String testUpdate() {
		return "testUpdate success";
	}
	
	/**
     * 过滤器
     */

    @ResponseBody
    @RequestMapping(value = "/testRole",method = RequestMethod.GET)
    public String testRole(){
        return  "testRole success";
    }

    @ResponseBody
    @RequestMapping(value = "/testRole1",method = RequestMethod.GET)
    public String testRole1(){
        return  "testRole1 success";
    }

    @ResponseBody
    @RequestMapping(value = "/testPerm",method = RequestMethod.GET)
    public String testPerm(){
        return  "testPerm success";
    }

    @ResponseBody
    @RequestMapping(value = "/testPerm1",method = RequestMethod.GET)
    public String testPerm1(){
        return  "testPerm1 success";
    }
    
    /**
     * 自定义filter
     */

    @ResponseBody
    @RequestMapping(value = "/testRole2",method = RequestMethod.GET)
    public String testRole2(){
        return  "testRole2 success";
    }

    @ResponseBody
    @RequestMapping(value = "/testPerm2",method = RequestMethod.GET)
    public String testPerm2(){
        return  "testPerm2 success";
    }

}
