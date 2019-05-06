/**
 * 版本：女神发飙版
 * 时间：20141222
 */


//页面时间的js
// setInterval(function(){
// 					Day=new Array(7);
// 					Day[0]="日"; Day[1]="一";
// 					Day[2]="二"; Day[3]="三";
// 					Day[4]="四"; Day[5]="五";
// 					Day[6]="六";
// 					today=new Date();
// 					year=today.getFullYear();
// 					month=today.getMonth()+1;
// 					day=today.getDate();
// 					weekly=today.getDay();
// 					hour=today.getHours();
// 					minutes=today.getMinutes();
// 					seconds=today.getSeconds();
// 					if(seconds<10){
// 						seconds="0"+seconds;
// 					}
// 					mydate="<Font color=#000><b>"+year+"</b>年<b>"+month+"</b>月<b>"+day+"</b>日 星期<b>"+Day[weekly]+"</b>  <b>"+hour+":"+minutes+":"+seconds+"</b></Font>";
// 					document.getElementById("adian_today").innerHTML=mydate;},1000)
					
$(function($) {
	getLabInfo();
	window.setInterval("getLabInfo()",300000);
});

//页面清垃圾数据的函数
function cleanRubbish(){
	$("span[id='classrooma']").each(function(index,dom){
		var labState=$("#scrolltext").children().eq(index).children().children().eq(4).text();
		if(labState=='无课'){
			$("#scrolltext").children().eq(index).children().children().eq(5).text("无");
			$("#scrolltext").children().eq(index).children().children().eq(6).text("无");
		}
	})
	
}

//获取每个实验室的数据
function getLabInfo(){
	cleanRubbish();
	var path = $("#pageContext").val();
	$("span[id='classrooma']").each(function(index,dom){
		var labId=$(this).attr("lab");
		$.ajax({
			type: 'POST',
			url: path+'/public/getLabInfo',
			data: {'labId':labId},
			dataType:'text',
			success:function(text){
				var myArray=new Array();
				myArray=text.split(",");
				$("#scrolltext").children().eq(index).children().children().eq(1).html(myArray[0]);
				$("#scrolltext").children().eq(index).children().children().eq(2).html(myArray[1]);
				$("#scrolltext").children().eq(index).children().children().eq(3).html(myArray[2]);
				$("#scrolltext").children().eq(index).children().children().eq(4).html(myArray[3]);
				$("#scrolltext").children().eq(index).children().children().eq(5).html(myArray[4]);
				//假如有课就进一步获取实到和应到人数
				if(myArray[4]!='-'&&myArray[4]!='不限'){
					$("#scrolltext").children().eq(index).children().children().eq(6).html("加载中");
					$.getJSON("/zjcclims/public/getRealNumber"+myArray[5], function(data){
						$("#scrolltext").children().eq(index).children().children().eq(6).html(data);
					});
				}else{
					$("#scrolltext").children().eq(index).children().children().eq(6).html(myArray[5]);

				}

			},
			error:function(){
				console.info("数据加载失败");
			}
		});
	})

}



