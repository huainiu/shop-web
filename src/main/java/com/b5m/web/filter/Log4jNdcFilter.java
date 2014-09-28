package com.b5m.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.NDC;

public class Log4jNdcFilter implements Filter
{

    @Override
    public void destroy()
    {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException
    {
        String address = request.getRemoteAddr();
        // 把网络地址放入NDC中. 那么在在layout pattern 中通过使用 %x，就可在每条日之中增加网络地址的信息.
        NDC.push(address);
        //继续处理其他的filter链.
        chain.doFilter(request, response);
        // 从NDC的堆栈中删除网络地址.
        NDC.pop();
    }

    @Override
    public void init(FilterConfig conf) throws ServletException
    {

    }

}
