package com.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

public class PermOrFilter extends AuthorizationFilter{

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		// TODO Auto-generated method stub
		
		Subject subject = getSubject(request, response);
		
		String[] perms = (String[]) mappedValue;
		
		if (perms == null || perms.length == 0) {
			return true;
		}
		
		for (String perm : perms) {
			if (subject.isPermitted(perm)) {
				return true;
			}
		}
		
		return false;
	}

}
