<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:if test="${empty requestScope.comments }">
		没有评论信息
	</c:if>

	<c:if test="${!empty requestScope.comments }">
		<table border="1px" cellspacing="0" cellpadding="10">
			<tr>
				<td>评论者</td>
				<td>受评者</td>
				<td>评论内容</td>
				<td>评论时间</td>
				<td>阅读状态</td>
			</tr>
			<c:forEach items="${requestScope.comments }" var="comment">
				<tr>
					<td>${comment.tbUserByCommentUser.userName }</td>
					<td>${comment.tbUserByCommentParent==null?comment.tbDemand.tbUserByDemandUser.userName:comment.tbUserByCommentParent.tbUserByCommentUser.userName}</td>
					<td>${comment.commentContent }</td>
					<td><fmt:formatDate value="${comment.commentTime }" pattern="yyyy-MM-dd hh:mm:ss"/></td>
					<td>${comment.commentIssaw==1?"已阅读":"未阅读" }</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</body>
</html>

