<%@page import="com.earl.timestore.entity.TbUser"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/timestore/js/jquery-1.12.2.min.js"></script>
<script type="text/javascript">
	
	window.onload=function(){
		var url = "http://localhost:8080/timestore/toAddLikesAndComments";
		$.post(url,{"demandId":5},function(fakeUserWithLike){
			var users = fakeUserWithLike.users;
			var isLikes = fakeUserWithLike.isLike;
			var comments = fakeUserWithLike.comments;
			for(var i=0;i<users.length;i++){
				var userId = users[i].userId;
				var comment = comments[i];
				var checked = "";
				if(isLikes[i]==1)
					checked="checked='checked'";
				var $tr = "<tr><td>"+userId+"</td><td>"
							+"<input type='checkbox' name='userId' value='"+userId+"' "+checked+">"
							+"</td><td>"+"<input type='text' name='comment' value='"+comment+"'>"+"</td></tr>";
				$(".table tr:last").after($tr);
			}
		});
	}
	
</script>
</head>
<body>

	<form action="http://localhost:8080/timestore/addLikesAndComments" method="post">
		<table border="1" cellspacing="0" cellpadding="10" class="table">
			<tr>
				<td>用户id</td>
				<td>点赞</td>
				<td>评论内容</td>
			</tr>
		</table>
		<br/><br/>
		<input type="hidden" name="demandId" value="5">
		<input type="submit" value="添加">
	</form>

</body>
</html>