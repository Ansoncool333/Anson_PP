<%@page import="cn.pconline.best.util.Constants" %>
<%@ page import="cn.pconline.best.web.WebUtils" %>
<%@ page import="org.gelivable.auth.entity.GeliRole" %>
<%@ page import="org.gelivable.auth.entity.GeliSession" %>
<%@ page import="org.gelivable.auth.entity.GeliUser" %>
<%@ page import="org.gelivable.dao.GeliDao" %>
<%@ page import="org.gelivable.dao.GeliUtils" %>
<%@page contentType="text/html" pageEncoding="utf-8" session="false"%>
<%@include file="/WEB-INF/jspf/import.jspf"%>
<%
    GeliUser geliUser = GeliSession.getCurrentUser();
    if (geliUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    GeliDao dao = GeliUtils.getDao();
    String sql = "SELECT COUNT(1) FROM gl_acl WHERE userId=?";
    int count = dao.count(sql, geliUser.getUserId());
    if (count == 0) {
        response.sendRedirect("login.jsp");
        return;
    }
    pageContext.setAttribute("_USER_", geliUser);

    long roleId = WebUtils.getRole(geliUser.getUserId());
    GeliRole geliRole = dao.find(GeliRole.class, roleId);

    //出现 关键字"兼职"则判断为兼职
    request.setAttribute("isPartTimeRole", Constants.ROLE_PART_TIME.contains(geliRole.getName()));

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=7" />
<title>人妖小店</title>

<!--[if IE]>
<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->
<link href="dwz/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="dwz/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="dwz/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/> 

<script src="dwz/js/speedup.js" type="text/javascript"></script>
<script src="dwz/js/jquery-1.7.2.js" type="text/javascript"></script>

<script src="dwz/js/jquery.cookie.js" type="text/javascript"></script>
<script src="dwz/js/jquery.validate.js" type="text/javascript"></script>
<script src="dwz/js/jquery.bgiframe.js" type="text/javascript"></script>

<script src="dwz/js/dwz.core.js" type="text/javascript"></script>
<script src="dwz/js/dwz.util.date.js" type="text/javascript"></script>
<script src="dwz/js/dwz.validate.method.js" type="text/javascript"></script>
<script src="dwz/js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="dwz/js/dwz.barDrag.js" type="text/javascript"></script>
<script src="dwz/js/dwz.drag.js" type="text/javascript"></script>
<script src="dwz/js/dwz.tree.js" type="text/javascript"></script>
<script src="dwz/js/dwz.accordion.js" type="text/javascript"></script>
<script src="dwz/js/dwz.ui.js" type="text/javascript"></script>
<script src="dwz/js/dwz.theme.js" type="text/javascript"></script>
<script src="dwz/js/dwz.switchEnv.js" type="text/javascript"></script>
<script src="dwz/js/dwz.alertMsg.js" type="text/javascript"></script>
<script src="dwz/js/dwz.contextmenu.js" type="text/javascript"></script>
<script src="dwz/js/dwz.navTab.js" type="text/javascript"></script>
<script src="dwz/js/dwz.tab.js" type="text/javascript"></script>
<script src="dwz/js/dwz.resize.js" type="text/javascript"></script>
<script src="dwz/js/dwz.dialog.js" type="text/javascript"></script>
<script src="dwz/js/dwz.dialogDrag.js" type="text/javascript"></script>
<script src="dwz/js/dwz.sortDrag.js" type="text/javascript"></script>
<script src="dwz/js/dwz.cssTable.js" type="text/javascript"></script>
<script src="dwz/js/dwz.stable.js" type="text/javascript"></script>
<script src="dwz/js/dwz.taskBar.js" type="text/javascript"></script>
<script src="dwz/js/dwz.ajax.js" type="text/javascript"></script>
<script src="dwz/js/dwz.pagination.js" type="text/javascript"></script>
<script src="dwz/js/dwz.database.js" type="text/javascript"></script>
<script src="dwz/js/dwz.datepicker.js" type="text/javascript"></script>
<script src="dwz/js/dwz.effects.js" type="text/javascript"></script>
<script src="dwz/js/dwz.panel.js" type="text/javascript"></script>
<script src="dwz/js/dwz.checkbox.js" type="text/javascript"></script>
<script src="dwz/js/dwz.history.js" type="text/javascript"></script>
<script src="dwz/js/dwz.combox.js" type="text/javascript"></script>
<script src="dwz/js/dwz.print.js" type="text/javascript"></script> 
<!--
<script src="bin/dwz.min.js" type="text/javascript"></script>
-->
<script src="js/dwz.regional.zh.js" type="text/javascript"></script>

<script type="text/javascript">
$(function(){
	DWZ.init("dwz.frag.xml", {
		loginUrl:"login.jsp",
		statusCode:{ok:200, error:300, timeout:301},
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"},
		debug:false,
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"themes"});
		}
	});
});

</script>
</head>

<body scroll="no">
	<div id="layout">
		<div id="header">
			<div class="headerNav">
                <a class="logo" href="${ctx}/admin/index.jsp" style="width: 100px;">logo</a>
                <div style="float: left; color: white; font-size: 28px; padding-top: 15px;">best</div>
				<ul class="nav">
					<li><a href="javascript:;">welcome, ${_USER_.name}!</a></li>
					<li><a href="${ctx}/admin/login.jsp?action=logout">logout</a></li>
                    <c:choose>
                        <c:when test="${isPartTimeRole}">
                            <%-- 暂时不允许兼职自己绑定
                            <li><a href="${ctx}/admin/userinfo/bindPassport.do" target="dialog" rel="list-bindPassport">绑定通行证</a></li>
                            --%>

                        </c:when>
                        <c:otherwise>
                            <li><a href="${ctx}/admin/userinfo/detail.do" target="dialog" rel="dlg_page9">设置</a></li>
                        </c:otherwise>
                    </c:choose>
            </ul>
            <ul class="themeList" id="themeList">
                <li theme="default"><div class="selected"></div></li>
                <li theme="green"><div></div></li>
                <li theme="purple"><div></div></li>
                <li theme="silver"><div></div></li>
                <li theme="azure"><div></div></li>
            </ul>
        </div>
    </div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>主菜单</h2><div>-</div></div>

				<div class="accordion" fillSpace="sidebar">
                    <c:choose>
                        <c:when test="${isPartTimeRole}">
                            <%--兼职只允许下面操作--%>
                            <div class="accordionHeader">
                                <h2><span>Folder</span>兼职管理</h2>
                            </div>
                            <div class="accordionContent">
                                <ul class="tree treeFolder">
                                    <li><a href="${ctx}/admin/partTime/list.do?auditStatus=0" target="navTab" rel="list-topic0">更新列表</a></li>
                                    <li><a href="${ctx}/admin/partTime/list.do?auditStatus=5" target="navTab" rel="list-topic5">审核列表</a></li>
                                    <li><a href="${ctx}/admin/partTime/list.do?auditStatus=1" target="navTab" rel="list-topic1">已审列表</a></li>
                                </ul>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="accordionHeader">
                                <h2><span>Folder</span>爆料管理</h2>
                            </div>
                            <div class="accordionContent">
                                <ul class="tree treeFolder">
                                    <li><a href="${ctx}/admin/topic/list.do?orderField=createAt&orderDirection=desc" target="navTab" rel="list-topic">爆料列表</a></li>
                                    <li><a href="${ctx}/admin/topic/list.do?statusType=1&auditStatus=0,4&orderField=createAt&orderDirection=desc" target="navTab" rel="list-topic">爆料审核</a></li>
                                    <li><a href="${ctx}/admin/topic/list.do?flag=1&orderField=createAt&orderDirection=desc" target="navTab" rel="list-topic">兼职爆料</a></li>
                                    <li><a href="${ctx}/admin/topic/list.do?auditStatus=5&flag=1&orderField=createAt&orderDirection=desc" target="navTab" rel="list-topic">兼职待审爆料</a></li>
                                    <li><a href="${ctx}/admin/partTime/partTimeList.do" target="navTab" rel="list-partTimeList">兼职列表</a></li>
                                     <li><a href="${ctx}/admin/topic/upload.do" target="_blank" rel="list-upload">上传图片</a></li>
                                    <%--
                                    <li><a href="${ctx}/action/topic/showList2.jsp" target="navTab" rel="list-topic2">爆料数据迁移</a></li>
                                    --%>
                                </ul>
                            </div>

                            <div class="accordionHeader">
                                <h2><span>Folder</span>晒物广场</h2>
                            </div>
                            <div class="accordionContent">
                                <ul class="tree treeFolder">
                                    <li><a href="${ctx}/admin/sunplaza/list.do" target="navTab" rel="list-sunplaza">晒物广场</a></li>
                                </ul>
                            </div>
                            
                            <div class="accordionHeader">
                                <h2><span>Folder</span>网站配置</h2>
                            </div>
                            <div class="accordionContent">
                                <ul class="tree treeFolder">
                                    <li><a href="${ctx}/admin/topictype/list.do" target="navTab" rel="list-topictype">爆料类型列表</a></li>
                                    <li><a href="${ctx}/admin/topictag/list.do" target="navTab" rel="list-topictag">爆料标签列表</a></li>
                                    <li><a href="${ctx}/admin/brand/list.do" target="navTab" rel="list-brand">品牌列表</a></li>
                                    <li><a href="${ctx}/admin/report/list.do" target="navTab" rel="list-report">举报列表</a></li>
                                    <li><a href="${ctx}/admin/gold/list.do" target="navTab" rel="list-gold">金币列表</a></li>
                                    <li><a href="${ctx}/admin/focusimage/list.do" target="navTab" rel="list-focusimage">焦点图列表</a></li>
									<li><a href="${ctx}/admin/base/list.do" target="navTab" rel="list-base">基础配置管理</a></li>
									<li><a href="${ctx}/admin/appSortChannel/list.do" target="navTab" rel="list-appsortchannel">app分类设置管理</a></li>
                                    <li><a href="${ctx}/admin/adposition/list.do" target="navTab" rel="list-adposition">广告位管理</a></li>
                                </ul>
                            </div>

							<div class="accordionHeader">
                                <h2><span>Folder</span>活动管理</h2>
                            </div>
                            <div class="accordionContent">
                                <ul class="tree treeFolder">
                                    <li><a href="${ctx}/admin/activity/list.do" target="navTab" rel="list-activity">活动管理</a></li>
                                </ul>
                            </div>

                            <div class="accordionHeader">
                                <h2><span>Folder</span>用户管理</h2>
                            </div>
                            <div class="accordionContent">
                                <ul class="tree treeFolder">
                                    <li><a href="${ctx}/admin/user/list.do" target="navTab" rel="list-user">用户列表</a></li>
                                </ul>
                            </div>

                            <div class="accordionHeader">
                                <h2><span>Folder</span>积分管理</h2>
                            </div>
                            <div class="accordionContent">
                                <ul class="tree treeFolder">
                                    <li><a href="${ctx}/admin/award/list.do" target="navTab" rel="list-award">奖励规则管理</a></li>
                                    <li><a href="${ctx}/admin/moneylog/list.do" target="navTab" rel="list-moneylog">流水账管理</a></li>
                                    <li><a href="${ctx}/admin/experienceLevelRelation/list.do" target="navTab" rel="list-experiencelevelrelation">等级规则</a></li>
                                </ul>
                            </div>

                            <div class="accordionHeader">
                                <h2><span>Folder</span>公告管理</h2>
                            </div>
                            <div class="accordionContent">
                                <ul class="tree treeFolder">
                                    <li><a href="${ctx}/admin/notice/list.do" target="navTab" rel="list-notice">公告列表</a></li>
                                </ul>
                            </div>

                            <div class="accordionHeader">
                                <h2><span>Folder</span>电商管理</h2>
                            </div>
                            <div class="accordionContent">
                                <ul class="tree treeFolder">
                                    <li><a href="${ctx}/admin/mall/list.do" target="navTab" rel="list-mall">电商列表</a></li>
                                </ul>
                            </div>

                            <div class="accordionHeader">
                                <h2><span>Folder</span>礼品兑换</h2>
                            </div>
                            <div class="accordionContent">
                                <ul class="tree treeFolder">
                                    <li><a href="${ctx}/admin/gift/list.do" target="navTab" rel="list-gift">礼品列表</a></li>
                                    <li><a href="${ctx}/admin/giftExchangRule/list.do" target="navTab" rel="list-giftexchangrule">兑换规则</a></li>
                                    <li><a href="${ctx}/admin/giftexchange/list.do" target="navTab" rel="list-giftexchangorder">兑换订单</a></li>
                                </ul>
                            </div>

                            <div class="accordionHeader">
                                <h2><span>Folder</span>推送功能</h2>
                            </div>
                            <div class="accordionContent">
                                <ul class="tree treeFolder">
                                	<li><a href="${ctx}/admin/push/keywordList.do" target="navTab" rel="list-keyword">订阅关键词</a></li>
                                	<li><a href="${ctx}/admin/push/msgList.do" target="navTab" rel="list-push">分类推送列表</a></li>
                                    <li><a href="${ctx}/admin/push/msgSecondList.do" target="navTab" rel="list-pushsecond">精准推送列表</a></li>
                                    <%-- 
                                    <li><a href="${ctx}/admin/push/sendMsg.do" target="navTab" rel="list-msgpush">消息推送</a></li>
                                    --%>
                                    <li><a href="${ctx}/admin/push/msgReport.do" target="navTab" rel="list-msgReport">推送分类报表</a></li>
                                </ul>
                            </div>

							<div class="accordionHeader">
								<h2><span>Folder</span>代购管理</h2>
							</div>
		                    <div class="accordionContent">
								<ul class="tree treeFolder">
									<li><a href="${ctx}/admin/purchasingnotice/list.do" target="navTab" rel="list-purchasingnotice">代购公告</a></li>
									<li><a href="${ctx}/admin/purchasing/listTopic.do" target="navTab" rel="list-purchasing-topic">商品大全</a></li>
									<li><a href="${ctx}/admin/purchasing/list.do" target="navTab" rel="list-purchasing">代购管理</a></li>
									<li><a href="${ctx}/admin/userinfo/listMerchant.do" target="navTab" rel="list-purchasing-merchant">商家管理</a></li>
									<li><a href="${ctx}/admin/purchasinguserlevel/list.do" target="navTab" rel="list-purchasinguserlevel">商家等级管理</a></li>
								</ul>
							</div>

                            <div class="accordionHeader">
                                <h2><span>Folder</span>权限设置</h2>
                            </div>
                            <div class="accordionContent">
                                <ul class="tree treeFolder">
                                    <li><a href="${ctx}/admin/gelifunction/list.do" target="navTab" rel="list-gelifunction">功能列表</a></li>
                                    <li><a href="${ctx}/admin/gelirole/list.do" target="navTab" rel="list-gelirole">角色列表</a></li>
                                    <li><a href="${ctx}/admin/gelirolefunction/list.do" target="navTab" rel="list-gelirolefunction">角色功能</a></li>
                                    <li><a href="${ctx}/admin/acl/list.do" target="navTab" rel="list-geliacl">访问控制</a></li>
                                    <li><a href="${ctx}/admin/geliuser/list.do" target="navTab" rel="list-geliuser">用户列表</a></li>
                                    <li><a href="${ctx}/admin/gelilog/list.do?orderField=logId&orderDirection=desc"
                                            target="navTab" rel="list-gelilog">操作日志</a></li>
                                    <li><a href="${ctx}/admin/gelilogdetail/list.do?orderField=logDetailId&orderDirection=desc"
                                            target="navTab" rel="list-gelilogdetail">日志数据</a></li>
                                    <li><a href="${ctx}/admin/gelitool/list.do" target="navTab" rel="list-gelitool">代码定制</a></li>
                                </ul>
                            </div>
                        </c:otherwise>
                    </c:choose>
				</div>
			</div>
		</div>

		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div>
					<div class="tabsRight">right</div>
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">more</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div class="pageFormContent" layoutH="80" style="margin-right:230px">
                            这里缺应用的个人主页内容，请补充。
                        </div>
					</div>
				</div>
			</div>
		</div>

	</div>
</body>
</html>
