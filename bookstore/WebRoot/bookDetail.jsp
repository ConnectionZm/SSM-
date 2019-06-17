<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" import="edu.xit.ssm.po.Users,java.lang.*,edu.xit.ssm.service.impl.AccountServiceImpl,edu.xit.ssm.po.Account,edu.xit.ssm.mapper.AccountMapper1,edu.xit.ssm.service.AccountService" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head lang="zh-CN">

<%
	Users users=(Users)request.getSession().getAttribute("user");
	Integer userId=0;
	if(users==null){
		userId=0;
	}else{
		userId =users.getId();
		/* AccountServiceImpl accountService=new AccountServiceImpl();
		Account account=accountService.selectAccountByUid2(userId);
		request.setAttribute("points", account.getPoints()); */
	}
%>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <title>BookStore - 图书详情</title>

    <!-- Website Icon -->
    <link rel="shortcut icon" type="image/x-icon" href=" ${pageContext.request.contextPath}/images/favicon.ico"/>
   

    <!-- External Style -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet">

    <!-- Custom Style -->
    <link href="css/animate.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">

    <!--[if IE 6]>
    <script src="js/iepngfx.js" language="javascript" type="text/javascript"></script>
    <![endif]-->
    <style>


    </style>
</head>
<body class="gray-bg">

<!-- 引入头部文件 header1.jsp-->
<%@ include file="header1.jsp"%>

<!-- bookDetail -->
<div class="container bookDetail">
    <ol class="breadcrumb">
        <li><a href="index.jsp">首页</a></li>
        <li><a href="${pageContext.request.contextPath}/queryBooks.action">书库</a></li>
        <li class="active">商品详情</li>
    </ol>
    <div class="row">
        <div class="col-sm-4 bookPic">
            <img src="images/book/${book.pic }" alt=""/>
        </div>
        <div class="col-sm-6 bookSale">
            <h2 class="book-title">${book.bookName }</h2>
            <hr>
            <div class="book-data">
                <span>
                    作者：<em class="book-author">${book.author }</em>
                </span>
                <span>
                    出版社：<em class="book-author">${book.pub }</em>
                </span>
            </div>
            <div class="bookPrice gray-bg">
                <div class="price_r">
                    原&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价<del>¥${book.price }</del>
                </div>
                <div class="rob">
                    现&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价<span>¥${book.rob }</span>
                </div>
            </div>
            <div class="numberChange clearfix">
                <h3>数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量</h3>
                <div class="input-group">
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button" onclick="Sub()">-</button>
                    </span>
                    <input id="number" type="text" class="form-control" value="1" disabled>
                    <span class="input-group-btn">
                        <button class="btn btn-default" type="button" onclick="Add()">+</button>
                    </span>
                </div><!-- /input-group -->
            </div>
            <div class="sale">
                <a class="btn btn-lg btn-info shoppingCart"  href="javascript:void(0);">
                    <span class="fa fa-shopping-cart"></span>加入购物车
                </a>
                <a class="btn btn-lg btn-danger buy" href="javascript:void(0)">购买</a>
            </div>
        </div>
        <div class="col-sm-2 book-security">
            <h3>BS自营</h3>
            <ul class="fuwu clearfix">
                <li class="zpbz">
                    <i></i>
                    <a href="#">正品保障</a>
                </li>
                <li class="hdfk">
                    <i></i>
                    <a href="#">货到付款</a>
                </li>
                <li class="fwzc">
                    <i></i>
                    <a href="#">服务支持</a>
                </li>
                <li class="cjfh">
                    <i></i>
                    <a href="#">差价返还</a>
                </li>
                <li class="hh">
                    <i></i>
                    <a href="#">15天换货</a>
                </li>
                <li class="lpbz">
                    <i></i>
                    <a href="#">礼品包装</a>
                </li>
                <li class="zcth">
                    <i></i>
                    <a href="#">支持7天无理由退货</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="detail-content">
        <h3>
            <span class="bookDer">图书简介</span>
        </h3>
        <p>
            ${book.detail }
        </p>
    </div>
     <div class="detail-content">
        <h3>
            <span class="bookDer">用户评价</span>
        </h3>
         <c:forEach items="${commentVoList}" var="commentVo">
          	评论者：<span>${commentVo.username }</span>
             <p>
             ${commentVo.descr}
			<c:if test="${not empty user.id}">
        			<c:if test="${commentVo.uid==user.id}">
        				<span style="float:right"><a onClick="delComment('${commentVo.id}');">删除评论</a></span>
        			</c:if>
        	</c:if> 
        	</p>
        	
         </c:forEach>
        
    </div>
</div>
<!-- bookDetail END -->
<input type="hidden" id="inp" value="<%=userId%>"/>
<input type="hidden" id="points" value="${points}"/>
<%@ include file="footer.jsp"%>

<!-- Mainly scripts -->
<script src="js/jquery-2.1.4.min.js"></script>
<script src="js/bootstrap.min.js"></script>

<script type="text/javascript">
	
    function Add()
    {
        var num= $('#number');
        var a= new Number(num.attr('value'));
        a++;
        num.attr('value',a);
    }
    function Sub()
    {
        var num= $('#number');
        var a= new Number(num.attr('value'));
        if( a > 1 ){
            a--;
            num.attr('value',a);
        }
    }
   
    //加入购物车
    $('.shoppingCart').click(function(){
    	var userId=$("#inp").val();
    	var number=parseInt($('#number').val());
    	var subtotal=number*${book.rob};
    	var bookId=${book.id};
    	var bookName='${book.bookName}';
    	var rob=${book.rob};
    	var price=${book.price};
    	var pic='${book.pic}';
    	//userId=${user.id}&bookId=${book.id}&bookName=${book.bookName}&rob=${book.rob}&price=${book.price}&number="+number+"&subtotal="+subtotal
    	 $.ajax({
        	url:" ${pageContext.request.contextPath}/insertToCart.action",
        	type:'post',
        	dataType:'text',
        	data:{'userId':userId,'bookId':bookId,'bookName':bookName,'rob':rob,'price':price,'pic':pic,'number':number,'subtotal':subtotal},
        	success:function(data){
        		if(data=="ok"){
        			window.location.href="${pageContext.request.contextPath}/queryCart.action";
        		}else if(data=="unlogin"){
        			window.location.href="${pageContext.request.contextPath}/login.jsp";
        		}
        	}
        }); 
    });
    
     //购买
     $('.buy').click(function(){
    	
    	var check=confirm("是否使用积分?你当前账户有"+"${points}"+"分");
    	var userId=$("#inp").val();
    	var number=parseInt($('#number').val());
    	var confirmMoney=0;
    	var subtotal=number*${book.rob};
    	var points=$("#points").val();
    	var changeMoney=(points*1.0)/10;
    	if(check){
    		confirmMoney=subtotal-changeMoney;
    	}else{
    		confirmMoney=subtotal
    	}
    	var a=confirm('确认支付'+confirmMoney+'元吗');
    	//var userId=${user.id};
    	var bookId=${book.id};
    	var bookName='${book.bookName}';
    	var rob=${book.rob};
    	//var price=${book.price};
    	var pic='${book.pic}';
    	if(a==true&&check==true){//订单已支付，状态为20
    		var trading_type=20;
    		points=$("#points").val();
    		$.ajax({
            	url:" ${pageContext.request.contextPath}/insertOneOrder.action",
            	type:'post',
            	dataType:'text',
            	data:{'userId':userId,'bookId':bookId,'bookName':bookName,'rob':rob,'pic':pic,'number':number,'subtotal':subtotal,'tradingType':trading_type,'points':points},
            	success:function(data){
            		if(data=="20"){
            			alert('购买成功!');
            			window.location.href="${pageContext.request.contextPath}/queryOrder.action";
            		}else if(data=="10"){
            			alert('购买失败!');
            		}else if(data=="30"){
            			alert("余额不足，购买失败");
            		}else if(data=="unlogin"){
            			window.location.href="${pageContext.request.contextPath}/login.jsp";
            		}
            	}
              }); 
    	}else if(a==true&&check==false){
    		var trading_type=20;
    		points=0;
    		$.ajax({
            	url:" ${pageContext.request.contextPath}/insertOneOrder.action",
            	type:'post',
            	dataType:'text',
            	data:{'userId':userId,'bookId':bookId,'bookName':bookName,'rob':rob,'pic':pic,'number':number,'subtotal':subtotal,'tradingType':trading_type,'points':points},
            	success:function(data){
            		if(data=="20"){
            			alert('购买成功!');
            			window.location.href="${pageContext.request.contextPath}/queryOrder.action";
            		}else if(data=="10"){
            			alert('购买失败!');
            		}else if(data=="30"){
            			alert("余额不足，购买失败");
            		}else if(data=="unlogin"){
            			window.location.href="${pageContext.request.contextPath}/login.jsp";
            		}
            	}
              }); 
    	}else if(a==false&&check==true){
    		points=$("#points").val();
    		var trading_type=20;
    		$.ajax({
            	url:" ${pageContext.request.contextPath}/insertOneOrder.action",
            	type:'post',
            	dataType:'text',
            	data:{'userId':userId,'bookId':bookId,'bookName':bookName,'rob':rob,'pic':pic,'number':number,'subtotal':subtotal,'tradingType':trading_type,'points':points},
            	success:function(data){
            		if(data=="20"){
            			alert('购买成功!');
            			window.location.href="${pageContext.request.contextPath}/queryOrder.action";
            		}else if(data=="10"){
            			alert('购买失败!');
            		}else if(data=="30"){
            			alert("余额不足，购买失败");
            		}else if(data=="unlogin"){
            			window.location.href="${pageContext.request.contextPath}/login.jsp";
            		}
            	}
              }); 
    	}else if(a==false&&check==false){
    		//订单未支付，状态为10
        		var trading_type=10;
        		points=0;
        		$.ajax({
                	url:" ${pageContext.request.contextPath}/insertOneOrder.action",
                	type:'post',
                	dataType:'text',
                	data:{'userId':userId,'bookId':bookId,'bookName':bookName,'rob':rob,'pic':pic,'number':number,'subtotal':subtotal,'tradingType':trading_type,'points':points},
                	success:function(data){
                		if(data=="20"){
                			alert('购买成功!');
                			window.location.href="${pageContext.request.contextPath}/queryOrder.action";
                		}else if(data=="10"){
                			alert('购买失败!');
                		}else if(data=="30"){
                			alert("余额不足，购买失败");
                		}else if(data=="unlogin"){
                			window.location.href="${pageContext.request.contextPath}/login.jsp";
                		}
                	}
                  }); 
        	}
    	
    	  	 
     });  
  
    function delComment(id){
    	$.ajax({
        	url:" ${pageContext.request.contextPath}/deleteComment.action",
        	type:'post',
        	dataType:'text',
        	data:{'id':id},
        	success:function(data){
        		if(data=="delCommSuccess"){
        			alert('删除评论成功！');
        			window.location.reload();
        		}else{
        			alert("删除评论失败");
        		}
        	}
          }); 
    }

</script>

</body>
</html>