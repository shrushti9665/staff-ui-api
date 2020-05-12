package gov.samhsa.c2s.staffuiapi.config;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import gov.samhsa.c2s.staffuiapi.service.TokenHolder;

public class IncomingRequest extends HandlerInterceptorAdapter {
	@Autowired
    private TokenHolder tokenHolder;
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    	Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            //System.out.println(key+" :- "+value);
        }
      
    	String umsdb=(String) request.getHeader("tenantId");
    	UmsDbID.setCurrentTenant(umsdb);
    	String umsversion=(String) request.getHeader("version");
    	UmsDbID.setCurrentVersion(umsversion);
    	
    	String token=(String) request.getHeader("authorization");
    	UmsDbID.setCurrentToken(token);
    	//umsdb="apollo";
    	String ums=new String(request.getRequestURL());
    
        return true;
    }
    
    
    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    //    TenantContext.clear();
    }
}