<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<head>
<script type="text/javascript" src="scripts/jquery-1.9.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#resolveJson").click(function() {
			alert("fu");
			var data = {
				"adminid" : 5,
				"adminAccount" : "a",
				"adminPassword" : "aa",
				"adminAuthority" : 1,
				"adminName" : "aa"
			};
			$.ajax({
				url : "updatead",
				data : data,
				success : function() {
					alert("fu");
				}
			});
		});
	});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<body>
	<sf:form action="updatead" modelAttribute="admin">
		<sf:input path="adminid" name="adminid" />
adminAccount:<sf:input path="adminAccount" name="adminAccount" />
		<br>
		<br>
adminPassword:<sf:input path="adminPassword" name="adminPassword" />
		<br>
		<br>
adminAuthority:<sf:input path="adminAuthority" name="adminAuthority" />
		<br>
		<br>
adminName:<sf:input path="adminName" name="adminName" />
		<br>
		<br>
		<br>
		<br>
	</sf:form>
	<input type="button" value="haha" id="resolveJson" />
</body>
</html>