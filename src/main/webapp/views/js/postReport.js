//帖子报表
$(document).ready(function(){

	//帖子报表数据交互
	$("#postReportGet").click(function(){

		var url="http://localhost:8080/timestore/getDemandDetails";

		$.get(url,function(json){
		var x=$(".postNum");
		for (var i=0;i<json.length ;i++ )
		{
			$(x[i]).text(json[i]);
		}
		});

	});

	$("#showPostReport_1").click(function(){
		$("#postReport_1").slideToggle(); });
	
	$("#showPostReport_2").click(function(){
		$("#postReport_2").slideToggle(); });
	
//根据具体日期查询当天帖子数量
	$("#postReportSearch").click(function(){
		$("#postReportSearchTable").empty();
		var date=$("#chooseDatePost").val();

		var url="http://localhost:8080/timestore/getDemandDetails";

		$.post(url,{
		date:date
		},
		function(json){
			$str="当天帖子数量为："+json[0];
			$("#postReportSearchTable").append($str); 
		
		});
	});
	
});