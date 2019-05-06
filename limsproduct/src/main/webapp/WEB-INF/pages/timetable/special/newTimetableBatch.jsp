<%@page language="java" isELIgnored="false"	contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<!DOCTYPE jsp:directive.include PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <html>
 <head>
	<meta name="decorator" content="none" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap/easyui.css">
	 <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
	<style>
	.introduce-box{
    border: 1px solid #b9b7b7;
    border-radius: 5px;
    box-shadow: 0 0 3px #fff inset;
    }
	.introduce-box table input[type="text"]{
	height:25px;
	line-height:25px;
	border: 1px solid #b1b1b1;
    box-shadow: 1px 1px 2px #c1c1c1 inset;
    border-radius: 3px;
	}
	#startDate,
	#endDate{
	padding-left:30px;
	}
	.Wdate {
    background-position: 5px 2px!important;
    }
    #edit_form{
        box-sizing:border-box;
        width:100%;
        margin:0;
        padding:10px;
    }
    .TabbedPanelsContent .content-box.m0{
        border: 1px solid #e4e5e7;
        margin:0;
    }
    .title{
        height:41px;
        line-height:41px;
        border-bottom: 1px solid #e4e5e7;
    }
    .title .btn-new {
        margin-top: 10px;
    }
    .content-box fieldset{
        width:100%!important;
        margin:0;
    }
    .editinfo_tab th{
        text-align:right;
    }
    .editinfo_tab td{
        text-align:left;
    }
    .editinfo_tab input[type="text"]{
        width:100%!important;
    }
    #edit_form .courseinfo_tab{
        width:100%!important;
        left:0!important;
        margin:0!important;
    }
    #edit_form .courseinfo_tab input[type="text"]{
        width:100%;
        line-height:23px;
        border: 1px solid #d0d6dc;
        border-radius: 3px;
    }
    #edit_form .Wdate{
       height:25px;
    }
	</style>
 </head>
 <body>
 <div class="TabbedPanelsContent batch_box">
     <div class="content-box m0">
         <div class="title">分批信息</div>	                        
		                        <form:form id="edit_form" action="" method="post" modelAttribute="timetableBatch">
								<!--新增或修改的标记位  -->
									<table class="courseinfo_tab" id="listTable" cellpadding="0">
										<tr>											
											<th>批次名称</th>
											<td><form:input path="batchName" id="batchName" required="true"/></td>
											<th>每批组数</th>
											<td><form:input path="countGroup" id="countGroup" required="true"/></td>
										</tr>
										<tr>
											<th>选课形式</th>
											<td colspan="3">
												<input type="radio" id="free" name="group1"/>学生自选
												<input type="radio" id="force" name="group1"/>随机分配
    											<form:input type="hidden" path="ifselect" id="ifselect" required="true"/>
											</td>	
										</tr>
										<tr id="chooseTime">
											<th>开始时间:</th>
											<td>
												<input id="startDate" class="Wdate" type="text" name="startDate"
												onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required="true"
												value="<fmt:formatDate value="${timetableBatch.startDate.time}" pattern="yyyy-MM-dd" />"readonly />
											</td>
											<th>结束时间:</th>
											<td>
												<input id="endDate" class="Wdate" type="text" name="endDate" 
												onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" required="true"
												value="<fmt:formatDate value="${timetableBatch.endDate.time}" pattern="yyyy-MM-dd" />" readonly />
											</td>
										</tr>
										<tr>
											<td colspan="4">											    
									            <input class="btn" type="button" onclick="saveTimetableBatch()" value="提交" style="float:right;">
											</td>
										</tr>
									</table>							
								<input type="hidden" name="courseCode" value="${courseCode}" />
								<form:input type="hidden" path="id" />
							</form:form>
   </div>
</div>

<script type="text/javascript">

// 选课类型为学生选课
$('#free').change(function(){
	$('#ifselect').val(1);
	document.getElementById('chooseTime').style.display='';
});

// 选课类型为自动排课
$('#force').change(function(){
	$('#ifselect').val(0);
	document.getElementById('chooseTime').style.display='none';
});

//编辑的时候判断原来是学生还是自动分配
$(document).ready(function(){
	if(${timetableBatch.ifselect == 0}){// 随机分配
		$('#force').attr('checked','checked');
		document.getElementById('chooseTime').style.display='none';
	}else if(${timetableBatch.ifselect == 1}){// 学生自选
		$('#free').attr('checked','checked');
	}
});

// 保存分批信息
function saveTimetableBatch(){
	
	if($('#ifselect').val()==1&&($('#startDate').val()==''||$('#endDate').val()=='')){
		alert('请选择开始和结束时间');
		return false;
	}else{
		$.ajax({
	       url:'${pageContext.request.contextPath}/timetable/saveTimetableBatch?isSelf='+${isSelf},
	       type:'POST',
	       data:$('#edit_form').serialize(),
	       error:function (request){
	         alert('请求错误!');
	       },
	       success:function(){
	         parent.location.reload();
	       }         
	    });
	}

}

</script>

</body>
</html>