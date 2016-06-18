//添加帖子
$(document).ready(function(){

//获取用户id
	$("#addPostUserId").focus(function(){		
		
		var url="http://localhost:8080/timestore/getAllFakeUsers";
		
		$("#addPostUserId").empty();

		$.get(url,function(json){
			$str=' ';
			for (var i=0;i<json.length ;i++ )
			{
				var userId=json[i].userId; //用户id

				$str+="<option>";
				$str+=userId;   //用户id
				$str+="</option>"; 
			}
			$("#addPostUserId").append($str); 
		});
	});

	//确认添加帖子
	$(".display").find("#lo").on('click', '#submitPost', function(){	
	
		var url="http://localhost:8080/timestore/addDemand";

		var demand_title=$("#demandTitle").val();//标题
		var demand_user_id=$("#addPostUserId").val();//发布者id
		var demand_content=$("#demandContent").val();//内容
		var demand_type=$("#demandType").val();//需求类型
		var demand_pay=$("#demandPay").val();//报酬
		var demand_photo_url_json=new Array();//图片
		var photo=$(".photos");
			for (var i=0;i<photo.length;i++ )
			{
				demand_photo_url_json[i]=$(photo[i]).val();
			}
		console.log(demand_title);
		console.log(demand_user_id);
		console.log(demand_content);
		console.log(demand_type);
		console.log(demand_pay);
		$.ajax({
			   type: "POST",
			    url: url,
			    beforeSend: function(request) {
                request.setRequestHeader("Content-Type", "multipart/form-data; boundary=6ff46e0b6b5148d984f148b6542e5a5d");
                },
			   data: {
				'demandTitle':demand_title,//标题
				'demandUserId':demand_user_id,//发布者id
				'demandContent':demand_content,//内容
				'demandType':demand_type,//需求类型
				'demandPay':demand_pay,//报酬
				'demandPhotoUrlJson':demand_photo_url_json
			    },
			   success: function () {
			     alert("Data Uploaded: ");
			    }
			  });
			
//		$.post(url,{
//			demandTitle:demand_title,//标题
//			demandUserId:demand_user_id,//发布者id
//			demandContent:demand_content,//内容
//			demandType:demand_type,//需求类型
//			demandPay:demand_pay,//报酬
//			demandPhotoUrlJson:photo
//			
//		},function(data){
//				if (data==1)
//				{
//					alert("添加成功！");
//				}
//				else alert("添加失败！");
//			});
	
	
	});	
});