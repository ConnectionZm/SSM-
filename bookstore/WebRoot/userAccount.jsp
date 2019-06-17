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
    <title>BookStore - 个人信息</title>

    <!-- Website Icon -->
    <link rel="shortcut icon" type="image/x-icon" href="images/favicon.ico"/>

    <!-- External Style -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet">

    
    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

   
    <script src="js/iepngfx.js"  type="text/javascript"></script>
   
</head>
<body class="gray-bg">

<!-- 引入头部文件header1.jsp -->
<%@ include file="header1.jsp"%>

<!-- UserSetting -->
<div class="userSetting container">
    <h1 class="userInfo-title">账户信息</h1>
    <div class="userInfoSetting">
       <form id="formId" class="user-detail" method="post" action="${pageContext.request.contextPath}/updateAccount.action" enctype="multipart/form-data">
       <c:if test="${not empty accountVo.remark}">
       		<span style="color:red">${accountVo.remark}</span>
       </c:if>
        <div class="user-tx">
        	<input type="hidden" name="tx" value="${accountVo.tx}">
            <img id="tx" src="images/person/${accountVo.tx}" alt=""/>
            <input class="btn btn-xs btn-default" type="button" value="点击充值" onclick="reChargeOpen();"/>
        </div>
            <input type="hidden" name="id" value="${accountVo.id}">
	    	<div class="form-group">
                <label for="userId">UserId</label>
                <input type="text" class="form-control" id="userId" name="userId" value="${accountVo.userId}" placeholder="id" readonly="readonly">
            </div>
            <div class="form-group">
                <label for="username">UserName</label>
                <input type="text" class="form-control" id="userName" name="userName" value="${accountVo.userName}" placeholder="userName" readonly="readonly">
            </div>
            <div class="form-group">
                <label for="money">Balance</label>
                <input type="text" class="form-control" id="balance" name="balance" value="${accountVo.balance}" placeholder="balance" readonly="readonly">
            </div>
            <div class="form-group" id="pointsDiv">
                <label for="points">Points</label>
                <input type="text" class="form-control" id="points" name="points" value="${accountVo.points}" placeholder="points" readonly="readonly">
            </div>
            <input id="recharge" class="btn btn-info submit" type="submit" value="充值">
        </form>
    </div>
</div>
<!-- UserSetting END -->

<%@ include file="footer.jsp"%>

<!-- Mainly scripts -->
<script src="js/jquery-2.1.4.min.js"></script>
<script src="js/bootstrap.min.js"></script>

 <script type="text/javascript">
 	var count=0;
    $('.dropdown-toggle').dropdown();
    function reChargeOpen(){
    	if(count==0){
    		$("#pointsDiv").append("<div class='form-group' id='smMoneyDiv'><label for='smMoney'>smMoney</label><input type='text' class='form-control' id='smMoney' name='smMoney' value='${accountVo.smMoney}' placeholder='smMoney' required pattern='^[0-9]+\.?[0-9]*$' title='请填写正确的价格'></div>");
    		$("#smMoneyDiv").append("<div style='text-align:center'> <img id='alipay' src='images/alipay/alipay.jpg'/></div>");
    		count++;
    	}
    	
    }
</script> 

</body>
</html>