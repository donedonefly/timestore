//系统列表
$(document).ready(function(){
var oNav=$(".Nav");

//系统列表get
  $(oNav[4]).click(function(){
	  $("#checkSystemTable").empty();

	  var url="http://localhost:8080/timestore/getSystem";

		$.get(url, function(json){		
			$str='';		
			$str+="<tr>";
			$str+="<th>"+"系统名称"+"</th>";
			$str+="<td>"+json.systemName+"</td>";
			$str+="</tr>";
			$str+="<tr>";
			$str+="<th>"+"系统版本号"+"</th>";
			$str+="<td>"+json.systemVersion+"</td>";
			$str+="</tr>";
			$str+="<th>"+"本次更新内容"+"</th>";
			$str+="<td>"+json.systemUpdatecontent+"</td>";
			$str+="</tr>";
			$str+="<th>"+"关于我们"+"</th>";
			$str+="<td>"+json.systemAboutus+"</td>";
			$str+="</tr>";
			$str+="<th>"+"联系我们"+"</th>";
			$str+="<td>"+json.systemContactus+"</td>";
			$str+="</tr>";
				$("#checkSystemTable").append($str);  
				$("#checkSystem .panel-footer .record").text("5");    
		});
	});

	//系统一键修改点击事件
$("#allUpdate").click(function() {
		var currTd=$("#checkSystemTable td");
		//保存原始值的数组
		var textArray=new Array();
		for (var i=0;i<currTd.length ;i++ )
		{	
			textArray[i] = $(currTd[i]).html();	
		} 
		if (currTd.children("input").length > 0) {
            //如果当前td中已包含有文本框元素，则不执行click事件
            return false;
        }
		var but=$(this).parent();
		$('<input type="button"class="update_but"  value="确认" />').appendTo(but);
		$('<input type="button" class="update_but" value="取消" />').appendTo(but);
		for (var i=0;i<currTd.length ;i++ )
		{	
			$(currTd[i]).html("");
			$("<input class='inputText'  type='text' />").val(textArray[i]).appendTo(currTd[i]);			
		} 
		//确认修改按钮点击事件
		var x=$(this).next();
		$(this).next().click(function(){
		var oinput=new Array();
		//	var x=currTd.length;
			for (var i=0;i<currTd.length ; i++)
			{
				oinput[i]=$(currTd[i]).find("input").val();
				 $(currTd[i]).html(oinput[i]);  
			}

			$(this).next().remove();
			$(this).remove();

			var url="http://localhost:8080/timestore/setSystem";

			//将修改的数据更新到数据库  系统信息
			$.post(url,{
			systemName:oinput[0],
			systemVersion:oinput[1],
			systemUpdateContent:oinput[2],
			systemAboutUs:oinput[3],
			systemContactUs:oinput[4]
			});

		});
		//取消修改按钮点击事件
		var x=$(this).next().next();
		$(this).next().next().click(function(){
			for (var i=0;i<currTd.length ; i++)
			{
                $(currTd[i]).html(textArray[i]);  
			}

			$(this).prev().remove();
			$(this).remove();

		});
    });	


});