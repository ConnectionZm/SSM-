<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
<head lang="zh-CN">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>BookStore - 后台管理系统</title>
    <!-- Website Icon -->
    <link rel="shortcut icon" type="image/x-icon" href="images/favicon.ico"/>

    <!-- External Style -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet">

    <!-- Custom Style -->
    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    <link rel="stylesheet" href="css/index.css" type="text/css" media="screen"/>

</head>
<body>
<c:if var="result" test="${empty manager.managerName}">
		<script>
			alert("请先登录");
			window.location.href = 'managerLogin.jsp';
		</script>
	</c:if>
<!--顶部-->
<div class="top">
    <div style="float: left">
        <span style="font-size: 16px;line-height: 45px;padding-left: 20px;color: #fff">BookStore后台管理系统</span>
    </div>
</div>
<!--顶部结束-->
<!--菜单-->
<div class="left-menu">
    <ul id="menu">
        <li class="menu-list">
            <ul>
                <li><a href="${pageContext.request.contextPath}/queryUsers.action">用户信息管理</a></li>
                <li><a href="${pageContext.request.contextPath}/queryBooks2.action">书籍信息管理</a></li>
                <li><a href="${pageContext.request.contextPath}/queryOrder1.action">订单信息管理</a></li>
                <li><a href="${pageContext.request.contextPath}/selectCommentsAll.action">用户评论管理</a></li>
                <li><a href="${pageContext.request.contextPath}/selectAccount.action">用户账户管理</a></li>
                <li><a href="${pageContext.request.contextPath}/exitManager.action">退出</a></li>
            </ul>
        </li>
    </ul>
</div>

<div id="right-content" class="right-content">
    <div class="content">
        <div id="page_content">
            <h2 class="cp_title">订单信息管理</h2>
            <table class="table">
                <thead>
                <tr>
                    <th>订单号</th>
                    <th>书籍信息</th>
                    <th>单价（元）</th>
                    <th>数量</th>
                    <th>金额（元）</th>
                    <th>交易状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${orderList}" var="order">
                <tr>
                    <th style="vertical-align:middle">${order.orderSn }</th>
                    <th>
                    <div class="data-pic">
                        <img src="images/book/${order.pic }"/> 
                       <span>${order.bookName}</span>
                    </div>
                    
                    </th>
                    <th style="vertical-align:middle">${order.rob }</th>
                    <th style="vertical-align:middle">${order.number}</th>
                    <th style="vertical-align:middle">${order.subtotal}</th>
                    <th style="vertical-align:middle">
                    	<c:choose>
			                <c:when test="${order.tradingType==10 }">
			                    <span>未付款&nbsp;&nbsp;</span>              
			                </c:when>
			                <c:when test="${order.tradingType==20 }">
			                    <span>待发货&nbsp;&nbsp;</span>              
			                </c:when>
			                 <c:when test="${order.tradingType==30 }">
			                    <span>待收货&nbsp;&nbsp;</span>              
			                </c:when>
			                <c:otherwise>
						        <span>交易成功&nbsp;</span>
						    </c:otherwise>
			   			 </c:choose>
                    </th>
                    <th style="vertical-align:middle">
                        <c:choose>
			                <c:when test="${order.tradingType==10 }">
			                    <a href="${pageContext.request.contextPath}/deleteOrder1.action?id=${order.id}">取消订单</a>             
			                </c:when>
			                <c:when test="${order.tradingType==20 }">
			                    <a href="${pageContext.request.contextPath}/updateOrderState1.action?tradingType=30&id=${order.id}">设置发货</a>              
			                </c:when>
			                 <c:when test="${order.tradingType==30 }">
			                    <a href="${pageContext.request.contextPath}/updateOrderState1.action?tradingType=40&id=${order.id}">确认收货</a>              
			                </c:when>
			                <c:otherwise>
						         <a href="${pageContext.request.contextPath}/deleteOrder1.action?id=${order.id}">删除订单</a> 
						    </c:otherwise>
				       </c:choose>
                    </th>
                </tr>
                </c:forEach>
                </tbody>
            </table>
			<div style="height:33px;clear:both;"></div>
        </div>
    </div>
</div>


<script type="text/javascript" src="js/jquery-2.1.4.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/tendina.min.js"></script>
<script>
	/* $(function(){ 
		setInterval(repeat, 10000);
	});
	function repeat(){
		 window.location.reload();  
	} */
</script>
</body>
</html>