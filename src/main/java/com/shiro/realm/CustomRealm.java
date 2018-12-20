package com.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.shiro.dao.UserDao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {

	@Autowired
    private UserDao userDao;
	
    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        
        String username = (String) principals.getPrimaryPrincipal();
        Set<String> roles = getRolesByUsername(username);
        System.out.println(roles);
        Set<String> permissions = getPermissionsByRolename(roles);
        System.out.println(permissions);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        
        return simpleAuthorizationInfo;
    }

    private Set<String> getPermissionsByRolename(Set<String> roles) {
    	List<String> perList = new ArrayList<>();
    	for (String role : roles) {
    		perList.addAll(userDao.getPermissionsByRolename(role));
    	}
        Set<String> sets = new HashSet<String>(perList);
        return  sets;
    }

    private Set<String> getRolesByUsername(String username) {
    	List<String> roleList = userDao.getRolesByUsername(username);
        Set<String> sets = new HashSet<String>(roleList);
        return  sets;
    }


    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        //1、从主体传过来的认证信息中，获得用户名
        String username = (String) token.getPrincipal();
        System.out.println(username);
        //2、通过用户名到数据库、缓存中获取凭证
        String password = getPasswordByUsername(username);
        System.out.println(password);
        if (username == null){
            return  null;
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username,password,"helloRealm");
        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(username));
        return simpleAuthenticationInfo;
    }

    private String getPasswordByUsername(String username) {
        return userDao.getPasswordByUsername(username).getPassword();
    }

    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("123456","Mark");
        System.out.println(md5Hash.toString());
    }
}
