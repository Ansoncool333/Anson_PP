<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" session="false" language="java" %>
<%@ page import="cn.anson.wechat.service.*" %>
<%@ page import="cn.anson.wechat.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	Env env = EnvUtils.getEnv();
	UserService ts = env.getBean(UserService.class);
// 	UserService ts = new UserService();
	
	pageContext.setAttribute("name", ts.testSelect());
%>
<html>
<body>
<h2>${name}</h2>
</body>
</html>
