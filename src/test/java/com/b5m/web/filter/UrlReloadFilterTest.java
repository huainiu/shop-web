package com.b5m.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
/**
 * @Company B5M.com
 * @description
 * 
 * @author echo
 * @since 2013-7-19
 * @email echo.weng@b5m.com
 */
public class UrlReloadFilterTest {
	private UrlReloadFilter urlReloadFilter;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private FilterChain chain;
	
	@Before
	public void setUp(){
		request = EasyMock.createMock(HttpServletRequest.class);
		response = EasyMock.createMock(HttpServletResponse.class);
		chain = EasyMock.createMock(FilterChain.class);
		urlReloadFilter = new UrlReloadFilter();
	}
	
	@Test
	public void testDoFilter() throws IOException, ServletException{
		/*StringBuffer sb = new StringBuffer("http://localhost:8080/iphone.html");
		EasyMock.expect(request.getRequestURL()).andReturn(sb);
		
		response.sendRedirect("http://localhost:8080/search/s/___image________________iphone.html");
		EasyMock.expectLastCall();
		
		EasyMock.replay(response, request);
		urlReloadFilter.doFilter(request, response, chain);*/
	}
	
	@After
	public void tearDown(){
		urlReloadFilter = null;
	}
	
}
