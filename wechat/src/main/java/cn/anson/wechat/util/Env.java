package cn.anson.wechat.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Anson Chan  (cp form geli)
 */
public class Env {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private ServletContext servletContext;
    private Map<String, Object> firstLevelCache = new HashMap<String, Object>();

    /*
     * Web context part
     */
    public HttpServletRequest getRequest() {
        return request;
    }
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    public HttpServletResponse getResponse() {
        return response;
    }
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
    public ServletContext getServletContext() {
        return servletContext;
    }
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    /*
     * Bean factory part
     */
    public WebApplicationContext getApplicationContext(){
        if (request != null) {
            return WebApplicationContextUtils.getWebApplicationContext(servletContext);
        } else {
            return null;
        }
    }

    public <T>T getBean(Class<T> type) {
        try {
			return getApplicationContext().getBean(type);
		} catch (NoUniqueBeanDefinitionException e) {
			String beanName = getApplicationContext().getBeanNamesForType(type)[0];
			return  getApplicationContext().getBean(beanName, type);
        }
    }

    public <T>T getBean(String beanName, Class<T> type) {
        return getApplicationContext().getBean(beanName, type);
    }

    public TransactionTemplate getTransactionTemplate() {
        WebApplicationContext ctx = getApplicationContext();
        PlatformTransactionManager transactionManager = (PlatformTransactionManager)ctx.getBean("transactionManager");
        return new TransactionTemplate(transactionManager);
    }

    public JdbcTemplate getJdbcTemplate() {
        WebApplicationContext ctx = getApplicationContext();
        return (JdbcTemplate)ctx.getBean("jdbcTemplate");
    }

    public SimpleJdbcTemplate getSimpleJdbcTemplate() {
        WebApplicationContext ctx = getApplicationContext();
        return (SimpleJdbcTemplate)ctx.getBean("simpleJdbcTemplate");
    }

    /*
     * Cache first level part
     */
    public Object getCache(String key) {
        return firstLevelCache.get(key);
    }
    public void setCache(String key, Object value) {
        firstLevelCache.put(key, value);
    }
    public void deleteCache(String key) {
        firstLevelCache.remove(key);
    }

    public static String escapeJavaScript(String s) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0, c = s.length(); i < c; i++) {
            char ch = s.charAt(i);
            switch (ch) {
                case '\n':
                    buf.append("\\n");
                    break;
                case '\r':
                    buf.append("\\r");
                    break;
                case '\"':
                    buf.append("\\\"");
                    break;
                case '\'':
                    buf.append("\\\'");
                    break;
                default:
                    buf.append(ch);
            }
        }
        return buf.toString();
    }

    ////////////////////////////////////////////////////////////////////////////
    /* Thread Local Date format
     *
     */
    private DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private DateFormat datetimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     *
     * @param date
     * @return
     */
    public Date parseDate(String date) {
        try {
            if (date.length() == 10) {
                return dateFormatter.parse(date);
            }
            if (date.length() == 19) {
                return datetimeFormatter.parse(date);
            }
        } catch (ParseException ex) {
            throw new IllegalArgumentException(date + "is not an valid format for: [yyyy-MM-dd] or [yyyy-MM-dd HH:mm:ss]");
        }
        throw new IllegalArgumentException(date + "is not an valid format for: [yyyy-MM-dd] or [yyyy-MM-dd HH:mm:ss]");
    }

    public String formatDate(Date date) {
        return dateFormatter.format(date);
    }

    public String formatDatetime(Date date) {
        return datetimeFormatter.format(date);
    }

    ////////////////////////////////////////////////////////////////////////////
    /*
     *
     */

    public String param(String name) {
        return request.getParameter(name);
    }

    public String param(String name, String def) {
        String v = request.getParameter(name);
        return v == null ? def : v;
    }

    public int paramInt(String name) {
        return paramInt(name, 0);
    }

    public int paramInt(String name, int def) {
        String v = request.getParameter(name);
        if (v == null || v.length() == 0) {
            return def;
        }
        try {
            return Integer.parseInt(v);
        } catch (Exception ex) {
            return def;
        }
    }

    public long paramLong(String name) {
        return paramLong(name, 0);
    }

    public long paramLong(String name, long def) {
        String v = request.getParameter(name);
        if (v == null || v.length() == 0) {
            return def;
        }
        try {
            return Long.parseLong(request.getParameter(name));
        } catch (Exception ex) {
            return def;
        }
    }

    public double paramDouble(String name) {
        return paramDouble(name, 0.0);
    }

    public double paramDouble(String name, double def) {
        String v = request.getParameter(name);
        if (v == null || v.length() == 0) {
            return def;
        }
        try {
            return Double.parseDouble(request.getParameter(name));
        } catch (Exception ex) {
            return def;
        }
    }

    public String cookie(String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals(cookieName)) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

    public void noCache() {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);
    }

}
