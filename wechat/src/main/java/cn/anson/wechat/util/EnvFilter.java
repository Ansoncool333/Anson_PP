/*
 * Copyright 1997-2011
 *
 * http://www.pconline.com.cn
 *
 */
package cn.anson.wechat.util;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Anson Chan (cp from geli)
 */
public class EnvFilter implements Filter {
    static final Log LOG = LogFactory.getLog(EnvFilter.class);

    ServletContext servletContext;
    Map<Env, String> envMap = new ConcurrentHashMap<Env, String>();

    @Override
    public void destroy() {
        servletContext.removeAttribute("_envSet");
        servletContext = null;
        envMap.clear();
        envMap = null;
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        servletContext = filterConfig.getServletContext();
        servletContext.setAttribute("_envSet", envMap.keySet());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        long start = System.currentTimeMillis();
        Env env = EnvUtils.getEnv();
        envMap.put(env, "");
        try {
            env.setRequest((HttpServletRequest) request);
            env.setResponse((HttpServletResponse) response);
            env.setServletContext(servletContext);

            request.setAttribute("env", env);

            chain.doFilter(request, response);

        } finally {
            long end = System.currentTimeMillis();
            if (end - start > 10000) {
//                LOG.warn(env.getLogString());
            } else {
//                LOG.info(env.getLogString());
            }
            envMap.remove(env);
            EnvUtils.removeEnv();
        }
    }

}
