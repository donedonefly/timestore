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

	<c:if test="${empty requestScope.requests }">
		没有请求信息
	</c:if>
	<c:if test="${!empty requestScope.requests }">
		<table border="1" cellspacing="0" cellpadding="10">
			<tr>
				<td>请求者</td>
				<td>请求内容</td>
				<td>请求时间</td>
				<td>请求状态</td>
			</tr>
			<c:forEach items="${requestScope.requests }" var="request">
				<tr>
					<td>${request.tbUser.userName }</td>
					<td>${request.requestMessage }</td>
					<td><fmt:formatDate value="${request.requestTime }" pattern="yyyy-MM-dd hh:mm:ss"/></td>
					<td>${request.requestStatus>0?"允许":(request.requestStatus==0?"未处理":"取消") }</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	

</body>
</html>