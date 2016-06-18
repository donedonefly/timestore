//聊天列表
$(document).ready(function(){

var chatMessageArray=new Array();
//聊天列表获取
 $("#chatListGet").click(function(){

		$("#chatListTable tbody").empty();
		
		var url="http://localhost:8080/timestore/getAllChat";

		$.get(url, function(json){
			
			var chatIsSaw;
			$str='';
			for (var i=0;i<json.length ;i++ )
			{
				if (json[i].chatIssaw=='1')
				{chatIsSaw="是";}
				else chatIsSaw="否";
				var	chatTime=new Date(parseInt(json[i].chatTime)).toLocaleString();
				chatMessageArray[i]=json[i].chatMessage;
				var chatMessage=json[i].chatMessage.slice(0,10);
				$str+="<tr>";
				$str+="<td>"+json[i].chatid+"</td>";//聊天记录ID
				$str+="<td>"+json[i].tbUserByChatFrom.userId+"</td>";// 发送方ID
				$str+="<td>"+json[i].tbUserByChatTo.userId+"</td>";    // 接收方ID 
				$str+="<td>"+"<a class='chatMessage'>"+chatMessage+"</a>"+"</td>";    // 聊天信息
				$str+="<td>"+chatTime+"</td>";  //聊天时间
				$str+="<td>"+chatIsSaw+"</td>";//是否被查阅
				$str+="</tr>";
		 
			}
			$("#chatListTable").append($str);
			$("#chatList .panel-footer .record").text(json.length);    
		});
	});


//查看具体聊天信息
$(".display").find("tbody").on('click', '.chatMessage', function(){
	var id=$(this).parent().siblings().first().text();//当前聊天的id
	var i=this.parentNode.parentNode.rowIndex;//当前行数


		alert(chatMessageArray[i-1]);
	//	$(this).parent().next().next().text("是");

	//	var url="是否查阅";

	//	$.get(url,{chatid:id})
//	});

});





});
