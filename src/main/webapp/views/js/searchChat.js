//查询聊天信息
$(document).ready(function(){
	var ochatMessageArray=new Array();
//聊天信息查询
$("#searchChatButton").click(function(){
	
	var ouser_name=$("#searchChatInput").val();
	$("#chatListSearchTable tbody").empty();
	
	var url ="http://localhost:8080/timestore/getAllChat";
  $.post(url,
  {
	  check_chat_by_account_or_username:ouser_name
  },
  function(json){
		$str='';
			for (var i=0;i<json.length ;i++ )
			{
				if (json[i].chatIssaw=='1')
				{chatIsSaw="是";}
				else chatIsSaw="否";
				ochatMessageArray[i]=json[i].chatMessage;
				var	chatTime=new Date(parseInt(json[i].chatTime)).toLocaleString();
				var chatMessage=json[i].chatMessage.slice(0,10);
				$str+="<tr>";
				$str+="<td>"+json[i].chatid+"</td>";//聊天记录ID
				$str+="<td>"+json[i].tbUserByChatFrom.userId+"</td>";// 发送方ID
				$str+="<td>"+json[i].tbUserByChatTo.userId+"</td>";    // 接收方ID 
				$str+="<td>"+"<a class='ochatMessage'>"+chatMessage+"</a>"+"</td>";    // 聊天信息
				$str+="<td>"+chatTime+"</td>";  //聊天时间
				$str+="<td>"+chatIsSaw+"</td>";//是否被查阅
				$str+="</tr>";
			}
			$("#chatListSearchTable").append($str);
			$("#chatListSearchTable .panel-footer .record").text(json.length);    
		if (json==null)
		{
			alert("聊天信息不存在！");
		}
  });
});
//查看具体聊天信息
$(".display").find("#chatListSearchTable").on('click', '.ochatMessage', function(){
	var id=$(this).parent().siblings().first().text();//当前聊天的id
	var i=this.parentNode.parentNode.rowIndex;//当前行数


		alert(ochatMessageArray[i-1]);
		
	//	$(this).parent().next().next().text("是");

	//	var url="是否查阅";

	//	$.get(url,{chatid:id})
//	});

});


});
