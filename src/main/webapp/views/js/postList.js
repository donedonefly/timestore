//帖子列表
$(document).ready(function(){

	
	
//帖子列表get
$("#postListGet").click(function(){

	$("#postListTable tbody").empty();

	 var url="http://localhost:8080/timestore/getDemandsAndRequestNum";

	$.get(url, function(json){
		
			var demands = json.demands;
			var requestNums = json.requestNum;
			
			$str='';
			for (var i=0;i<demands.length ;i++ )
			{
			
			$str+="<tr>";
			$str+="<td>"+demands[i].demandid+"</td>";//帖子id
			$str+="<td>"+demands[i].tbUserByDemandUser.userId+"</td>"; //发布者
			$str+="<td>"+"<a class='demandTitle'>"+demands[i].demandTitle+"</a>"+"</td>";  //需求标题
			$str+="<td>"+"<a class='popPostComment'>"+demands[i].demandCommentcount+"</a>"+"</td>";//  评论列表
			$str+="<td>"+"<a class='popPostLike'>"+demands[i].demandLikecount+"</a>"+"</td>";    // 		点赞列表
			$str+="<td>"+"<a class='popPostRequest'>"+requestNums[i]+"</a>"+"</td>";    // 		请求列表
			$str+="<td>"+"<button class='likeComment'>"+"点赞或评论"+"</button>"+"</td>";// 		具体操作
			$str+="<td>"+"<a class='delPost'>"+"删除该帖子"+"</a>"+"</td>";    // 		删除帖子
			$str+="</tr>";
		 
			}
			$("#postListTable").append($str);  
			$("#postList .panel-footer .record").text(demands.length);    
		});


});

	//查看需求内容
$(".display").find("tbody").on('click', '.demandTitle', function(){
		var id=$(this).parent().siblings().first().text();//获得当前帖子的id
		
		var url="http://localhost:8080/timestore/getRequestReport";

		$.post(url,{
		demandId:id
		},function(data){
		alert(data);
		})
	
	});


//查看评论列表
$(".display").find("tbody").on('click', '.popPostComment', function(){
			$("#demo table").empty();
			$("#demo").css("display","block");
			var id=$(this).parent().siblings().first().text();

			var url="http://localhost:8080/timestore/commentDetails";

			$.post(url,{
				 demandId:id
			 },
			 function(json){
				$str='';
				$str+="<tr>";
				$str+="<th>"+"用户ID"+"</th>"; //用户id
				$str+="<th>"+"评论内容"+"</th>";  //评论内容
				$str+="<th>"+"评论时间"+"</th>";    //评论时间
				$str+="</tr>";
				for (var i=0;i<json.length ;i++ )
				{
					var commentTime=new Date(parseInt(json[i].commentTime)).toLocaleString();
					$str+="<tr>";
					$str+="<td>"+json[i].tbUserByCommentUser.userId+"</td>"; //用户id
					$str+="<td>"+json[i].commentContent+"</td>";  //评论内容
					$str+="<td>"+commentTime+"</td>";    //评论时间
					$str+="</tr>";
				}
				
				$("#demo table").append($str); 
			});
			$("#close").click(function(){
				$("#demo").css("display","none");
			 });
	});


//查看点赞列表
$(".display").find("tbody").on('click', '.popPostLike', function(){
			$("#demo table").empty();
			$("#demo").css("display","block");
			var id=$(this).parent().siblings().first().text();

			var url="http://localhost:8080/timestore/likerDetails";

			$.post(url,{
				 demandId:id
			 },
			 function(json){
				$str='';
				$str+="<tr>";
				$str+="<th>"+"点赞用户ID"+"</th>"; //点赞用户id
				$str+="<th>"+"点赞时间"+"</th>";  //点赞时间
				$str+="</tr>";
				for (var i=0;i<json.length ;i++ )
				{
					var likeTime=new Date(parseInt(json[i].likeTime)).toLocaleString();
					$str+="<tr>";
					$str+="<td>"+json[i].tbUser.userId+"</td>"; //点赞用户id
					$str+="<td>"+likeTime+"</td>";  //点赞时间
					$str+="</tr>";
				}
				$("#demo table").append($str); 
			});
			$("#close").click(function(){
				$("#demo").css("display","none");
			 });
	});


//查看请求列表
$(".display").find("tbody").on('click', '.popPostRequest', function(){
			$("#demo table").empty();
			$("#demo").css("display","block");
			var id=$(this).parent().siblings().first().text();

			var url="http://localhost:8080/timestore/requestDetails";

			$.post(url,{
				 demandId:id
			 },
			 function(json){
				$str='';
				$str+="<tr>";
				$str+="<th>"+"请求用户ID"+"</th>"; //请求用户id
				$str+="<th>"+"请求时间"+"</th>";  //请求时间
				$str+="<th>"+"请求内容"+"</th>";    //请求内容
				$str+="<th>"+"请求状态"+"</th>";    //请求状态
				$str+="</tr>";
				for (var i=0;i<json.length ;i++ )
				{
					var requestTime=new Date(parseInt(json[i].requestTime)).toLocaleString();
					$str+="<tr>";
					$str+="<td>"+json[i].tbUser.userId+"</td>"; //请求用户ID
					$str+="<td>"+requestTime+"</td>";  //请求时间
					$str+="<td>"+json[i].requestMessage+"</td>";    //请求内容
					$str+="<td>"+json[i].requestStatus+"</td>";    //请求状态
					$str+="</tr>";
				}
				$("#demo table").append($str); 
			});
			$("#close").click(function(){
				$("#demo").css("display","none");
			 });
	});

//点赞或评论
$(".display").find("tbody").on('click', '.likeComment', function(){
	$("#likeComment #likeCommentTable tbody").empty();
			$("#likeComment").css("display","block");
			var id=$(this).parent().siblings().first().text();

			var url="http://localhost:8080/timestore/toAddLikesAndComments";

			$.post(url,{
				 demandId:id
			 },function(json){
				var users = json.users;
				var isLikes = json.isLike;
				$str='';
				for (var i=0;i<users.length ;i++ )
				{
					var checked = "";
					if(isLikes[i]==1)
						checked="checked='checked'";
				
					$str+="<tr>";
					$str+="<td>"+users[i].userId+"</td>";//用户id
					$str+="<td>"+"<input type='checkbox' name='userId' value='"+users[i].userId+"' "+checked+">"+"</td>"; //点赞选择框
					$str+="<td>"+"<input type='text' name='comment' />"+"</td>";  //评论内容
					$str+="</tr>";
			 
				}
				$("#likeCommentTable").append($str);  
			
			});
			$("#oclose").click(function(){
				$("#likeComment").css("display","none");
			 });

});


//删除帖子
$(".display").find("tbody").on('click', '.delPost', function(){
		var i=this.parentNode.parentNode.rowIndex;
		var id=$(this).parent().parent().parent().parent().attr("id");
		var odemandId=$(this).parent().siblings().first().text();
		document.getElementById(id).deleteRow(i);

		//将删除行的id传给后台
		var url="http://localhost:8080/timestore/deleteDemand";

		$.post(url,
	   {
		demandId:odemandId
	   });

	  });

});