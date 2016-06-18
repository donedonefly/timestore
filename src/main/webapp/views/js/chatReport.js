//聊天报表
$(document).ready(function(){

	//聊天报表数据获取
	$("#chatReportGet").click(function(){

		var url="http://localhost:8080/timestore/getChatReport";

	
		 $.get(url,function(data){
			$("#chatNum").text(data);//获得当前聊天数目
		});

	});

	//根据用户名查询其聊天数目
	$("#userNameSearchButton").click(function(){
		$("#searchChatCount").empty();
		var name=$("#userNameSearch").val();

		var url="http://localhost:8080/timestore/getChatReport";

		$.post(url,{userName:name},function(num){
			$("#searchChatCount").text("该用户聊天数目为："+num);
		
		});
	
	});
	

});