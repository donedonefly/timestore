<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<c:if test="${empty requestScope.likes }">
		没有点赞信息
	</c:if>
	<c:if test="${!empty requestScope.likes }">
		<table border="1px" cellspacing="0" cellpadding="10">
			<tr>
				<td>点赞者</td>
				<td>点赞时间</td>
				<td>阅读状态</td>
			</tr>
			<c:forEach items="${requestScope.likes }" var="like">
				<tr>
					<td>${like.tbUser.userName }</td>
					<td><fmt:formatDate value="${like.likeTime }" pattern="yyyy-MM-dd hh:mm:ss"/></td>
					<td>${like.likeIssaw==0?"未阅读":"已阅读" }</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

</body>
</html>