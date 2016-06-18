<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/timestore/js/jquery-1.12.2.min.js"></script>
<script type="text/javascript">


	$(document).ready(function() {

		$(".demandDetails_button").click(function() {
			var url = "getDemandDetails";
			var date = $(".check_by_time").val();
			var args = {
				"date" : date
			};
			$.post(url, args, function(data) {
				$tr = "<tr><td>" + data[0]
						+ "</td><td>" + data[1]
						+ "</td><td>" + data[2]
						+ "</td><td>" + data[4]
						+ "</td><td>" + data[3]
						+ "</td></tr>";
				$(".demandDetails_table tr:last")
						.after($tr);
			});
		});

		$(".demandDetails").click(function() {
			var url = this.href;
			$.post(url, function(data) {
				$tr = "<tr><td>" + data[0]
						+ "</td><td>" + data[1]
						+ "</td><td>" + data[2]
						+ "</td><td>" + data[4]
						+ "</td><td>" + data[3]
						+ "</td></tr>";
				$(".demandDetails_table tr:last").after($tr);
			});

			return false;
		});

		$(".request_report").click(function() {
			var url = "getChatReport";
			$.post(url, function(chatNum) {
				var $tr = "<tr><td>" + chatNum + "</td></tr>";

				$(".table_chatnum tr:last").after($tr);
			});

			return false;
		});

		$(".check_two").click(function() {
			var userName = $(".check_by_username").val();
			var args = {
				"userName" : userName
			};
			var url = "getRequestReport";
			$.post(url, args, function(chatNum) {
				var $tr = "<tr><td>" + chatNum + "</td></tr>";

				$(".table_chatnum tr:last").after($tr);
			});
		});

		$(".get_demands").click(function() {
			var url = "getDemandsAndRequestNum";
			$.post(url,function(demandsAndRequestNum) {
			var len = demandsAndRequestNum.demands.length;
			for (var i = 0; i < len; i++) {
				var publisher = demandsAndRequestNum.demands[i].tbUserByDemandUser.userName;
				var publisherAccount = demandsAndRequestNum.demands[i].tbUserByDemandUser.userAccount;
				var demandId = demandsAndRequestNum.demands[i].demandid;
				var content = demandsAndRequestNum.demands[i].demandContent;
				var likersNum = demandsAndRequestNum.demands[i].demandLikecount;
				var commentNum = demandsAndRequestNum.demands[i].demandCommentcount;
				var status = demandsAndRequestNum.demands[i].demandStatus < 1 ? "等待中": (demandsAndRequestNum.demands[i].demandStatus == 1 ? "进行中" : "已完成");
				var requestNum = demandsAndRequestNum.requestNum[i];
				var $tr = "<tr><td>"
						+ publisher
						+ "</td><td>"
						+ publisherAccount
						+ "</td><td>"
						+ content
						+ "</td><td><a href='likerDetails?demandId="
						+ demandId
						+ "'>"
						+ likersNum
						+ "</a></td><td><a href='commentDetails?demandId="
						+ demandId
						+ "'>"
						+ commentNum
						+ "</a></td><td>"
						+ status
						+ "</td><td>"
						+ requestNum
						+ "</td><td><a href='requestDetails?demandId="
						+ demandId
						+ "'>"
						+ "详情"
						+ "</a></td><td><a href='deleteDemand?demandId="
						+ demandId
						+ "'>"
						+ "删除帖子"
						+ "</a></td><td><a href='toAddLikesAndComments?demandId="+demandId+"'>点赞评论</a></td></tr>";
						
						$(".damends tr:last").after($tr);
			}
			});
		});

		$(".changeSystem").click(function() {
			var system_name = $(":input[name=system_name]").val();
			var system_version = $(
					":input[name=system_version]")
					.val();
			var system_update_content = $(
					":input[name=system_update_content]")
					.val();
			var system_about_us = $(
					":input[name=system_about_us]")
					.val();
			var system_contact_us = $(
					":input[name=system_contact_us]")
					.val();
			var args = {
				"systemName" : system_name,
				"systemVersion" : system_version,
				"systemUpdateContent" : system_update_content,
				"systemAboutUs" : system_about_us,
				"systemContactUs" : system_contact_us
			};
			var url = "setSystem";
			$.post(url, args, function(data) {
				if (data == true) {
					alert("修改成功");
				}
			});
		});

		$(".check").click(function() {
			var url = "listAllChat";
			var check_chat_by_account_or_username = $(".check_chat_by_account_or_username").val();
			var args = {"check_chat_by_account_or_username" : check_chat_by_account_or_username};
			$.post(url, args, function(chats) {
				showChats(chats);
			});
		});

		$(".listAllChat").click(function() {
			var url = this.href;
			$.post(url, function(chats) {
				alert(chats);
				showChats(chats);
			});

			return false;
		});

		function showChats(chats) {
			for (var i = 0; i < chats.length; i++) {
				var toAccount = chats[i].tbUserByChatTo.userAccount;
				var toUserName = chats[i].tbUserByChatTo.userName;
				var fromAccount = chats[i].tbUserByChatFrom.userAccount;
				var fromUserName = chats[i].tbUserByChatFrom.userName;
				var chatMessage = chats[i].chatMessage;
				var chatTime = new Date(parseInt(chats[i].chatTime)).toLocaleString();
				var chatIssaw = chats[i].chatIssaw == 1 ? "是" : "否";
				var $tr = "<tr><td>" + fromAccount
						+ "</td><td>" + fromUserName
						+ "</td><td>" + toAccount + "</td><td>"
						+ toUserName + "</td><td>"
						+ chatMessage + "</td><td>" + chatTime
						+ "</td><td>" + chatIssaw
						+ "</td></tr>";

				$(".chat_table tr:last").after($tr);
			}
		}
	});
</script>

</head>
<body>
	<a class="listAllChat" href="listAllChat">list Chats</a>
	<br>
	<input type="text" placeholder="请输入用户名或用户账户进行查询" style="width: 250px;"
		class="check_chat_by_account_or_username" />
	<input type="button" value="查询" class="check" />

	<div class="chat_div" style="display: false;">
		<table border="1px" cellspacing="0" cellpadding="10"
			class="chat_table">
			<tr>
				<td>发送方账号</td>
				<td>发送方昵称</td>
				<td>接收方账号</td>
				<td>接受方昵称</td>
				<td>聊天内容</td>
				<td>聊天时间</td>
				<td>聊天信息是否被查阅</td>
			</tr>
		</table>
	</div>
	<div style="margin-top: 20px;">
		<table border="1px" cellspacing="0" cellpadding="10">
			<tr>
				<td>系统名称</td>
				<td><input type="text" name="system_name" /></td>
			</tr>
			<tr>
				<td>系统版本号</td>
				<td><input type="text" name="system_version" /></td>
			</tr>
			<tr>
				<td>本次系统更新内容</td>
				<td><input type="text" name="system_update_content" /></td>
			</tr>
			<tr>
				<td>关于我们</td>
				<td><input type="text" name="system_about_us" /></td>
			</tr>
			<tr>
				<td>联系我们</td>
				<td><input type="text" name="system_contact_us" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="button" value="修改"
					class="changeSystem" /></td>
			</tr>
		</table>
	</div>
	<div style="margin-top: 20px;">
		<table class="damends" border="1px" cellspacing="0" cellpadding="10">
			<tr>
				<td>需求发布者昵称</td>
				<td>需求发布者账号</td>
				<td>需求内容</td>
				<td>点赞人数</td>
				<td>评论人数</td>
				<td>完成状态</td>
				<td>接受者昵称</td>
				<td>请求详情</td>
				<td>删除帖子</td>
				<td>具体操作</td>
			</tr>
		</table>
		<input type="button" value="获得需求信息" class="get_demands" />
	</div>
	<a href="requestReport" class="request_report">request report</a>
	<input type="text" class="check_by_username" placeholder="请输入用户名进行查询" />
	<input type="button" value="查询" class="check_two">
	<div style="margin-top: 10px;">
		<table class="table_chatnum" border="1" cellspacing="0"
			cellpadding="10">
			<tr>
				<td>当前聊天数目</td>
			</tr>
		</table>
	</div>
	<div style="margin-top: 20px;">
		<a class="demandDetails" href="getDemandDetails">demand details</a><br>
		<input type="text" class="check_by_time" /> <input type="button"
			value="查询" class="demandDetails_button">

		<table class="demandDetails_table" border="1" cellspacing="0"
			cellpadding="10">
			<tr>
				<td>当前帖子总数</td>
				<td>完成交易数量</td>
				<td>等待交易数量</td>
				<td>流通中的时间</td>
				<td>流通中的金额</td>
			</tr>
		</table>
	</div>

	<div style="margin-top: 10xp;">
		<form:form id="formId" action="addDemand" method="post" enctype="multipart/form-data">
			<table class="" border="1" cellspacing="0" cellpadding="10">
				<tr>
					<td>标题</td>
					<td><input type="text" name="demandTitle" /></td>
				</tr>
				<tr>
					<td>用户id</td>
					<td><input type="text" name="tbUserByDemandUser.userId" /></td>
				</tr>
				<tr>
					<td>内容</td>
					<td><input type="text" name="demandContent" /></td>
				</tr>
				<tr>
					<td>类型</td>
					<td><input type="text" name="demandType" /></td>
				</tr>
				<tr>
					<td>报酬</td>
					<td><input type="text" name="demandPay" /></td>
				</tr>
				<tr>
					<td>图片</td>
					<td>
					<input type="file" name="photos" />
					<input type="file" name="photos" />
					<input type="file" name="photos" />
					<input type="file" name="photos" />
					<input type="file" name="photos" />
					</td>
				</tr>
			</table>
			<input type="submit" value="添加" onclick="submit"/>
		</form:form>
	</div>
	<table border="1">
		<tr>
			<td>用户id</td>
			<td><input type="checkbox" name="userId" value="json.usrid"></td>
			<td><input type="text" name="comment"></td>
		</tr>
	</table>

</body>
</html>