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
            <h2 class="cp_title">用户账户信息管理</h2>
            <table class="table">
                <thead>
                <tr>
                	<th>用户头像</th>
                    <th>用户Id</th>
                    <th>用户姓名</th>
                    <th>用户充值提交金额（元）</th>
                    <th>余额</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${accountVoList}" var="accountVo">
                <tr>
                   <%--  <th style="vertical-align:middle">${accountVo.tx}</th>
                    <th>
                    <div class="data-pic">
                        <img src="images/person/${accountVo.tx}"/> 
                       <span>${order.bookName}</span>
                    </div> 
                    
                    </th>--%>
                    <th style="vertical-align:middle"><img src="images/person/${accountVo.tx}" style="max-width:100px;"/></th>
                    <th style="vertical-align:middle">${accountVo.userId }</th>
                    <th style="vertical-align:middle">${accountVo.userName}</th>
                    <th style="vertical-align:middle">${accountVo.smMoney}</th>
                    <th style="vertical-align:middle">${accountVo.balance}</th>
                    <th style="vertical-align:middle">
                    	<c:choose>
			                <c:when test="${accountVo.status eq '0' }">
			                    <span>未处理&nbsp;&nbsp;</span>              
			                </c:when>
			                <c:when test="${accountVo.status eq '1'}">
			                    <span>已处理&nbsp;&nbsp;</span>              
			                </c:when>
			   			 </c:choose>
                    </th>
                    <th style="vertical-align:middle">
                        <c:choose>
			                <c:when test="${accountVo.status eq '0' }">
			                    <a onclick="confirmRecharge('${accountVo.id}','${accountVo.userId }','${accountVo.smMoney}','${accountVo.balance}','${accountVo.points}')">确认充值</a>&nbsp;           
			                </c:when>
				       </c:choose>
				       <a onClick="sendUserWithAccount('${accountVo.id}');">通知用户</a>  
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
	$(function(){ 
		setInterval(repeat, 10000);
	});
	function repeat(){
		 window.location.reload();  
	}
	function confirmRecharge(id,userId,smMoney,balance,points){
		$.ajax({
	    	url:" ${pageContext.request.contextPath}/confirmRecharge.action",
	    	type:'post',
	    	dataType:'text',
	    	data:{'id':id,'userId':userId,'smMoney':smMoney,'balance':balance,'points':points},
	    	success:function(data){
	    		if(data=="success"){
	    			alert('充值成功');
	    			window.location.reload();
	    		}else{
	    			alert("充值失败");
	    		}
	    	}
	      }); 
	}
	function sendUserWithAccount(id){
		 var msg=prompt("请输入你想对用户说些什么!")
		 msg=$.trim(msg);
		 if(msg==""){
			 alert("通知内容不能为空!通知失败");
			 return false;
		 }
		 $.ajax({
		    	url:" ${pageContext.request.contextPath}/sendUserWithAccount.action",
		    	type:'post',
		    	dataType:'text',
		    	data:{'id':id,'msg':msg},
		    	success:function(data){
		    		if(data=="success"){
		    			alert('通知成功');
		    			window.location.reload();
		    		}else{
		    			alert("通知失败");
		    		}
		    	}
		      }); 
	}

</script>
</body>
</html>