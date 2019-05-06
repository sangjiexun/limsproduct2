<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
 <meta name="decorator" content="iframe"/>
	<head>
		<title></title>
		<meta name="Generator" content="gvsun">
		<meta name="Author" content="chenyawen">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="Keywords" content="">
		<meta name="Description" content="">
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/global.css" />
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css" />
		<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/lib.css" />
		<link href="${pageContext.request.contextPath}/css/index.css" rel="stylesheet" type="text/css">
		  <!-- 下拉框的样式 -->
 		 <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  		<link type="text/css" rel="stylesheet"  href="${pageContext.request.contextPath}/chosen/chosen.css" />
  		<!-- 下拉的样式结束 --> 
  		<link type="text/css" rel="stylesheet"  href="${pageContext.request.contextPath}/js/jquery-easyui/themes/gray/easyui.css" />
  		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/spring/Spring.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui/jquery-1.7.1.min.js" ></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui/jquery-1.7.1.min.js"></script>
		  <!-- 文件上传的样式和js -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.easyui.min.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/PopupDiv_v1.0.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/js/jquery.jsonSuggest-2.js"></script>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/swfupload/uploadify.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/swfupload.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/swfupload/jquery.uploadify.min.js"></script>  
		<script type="text/javascript">

$(function(){
    $("#Pop_content").window({
        top: ($(window).width() - 800) * 0.5 ,
        left: ($(window).width() - 1000) * 0.5   
    })
     $(".listTable").css('height',600); 
})
     
    
  }
  
  
  
    $(function(){
	         $("#userSubmit").click(function(){
	         var nameop = $("#nameop").val();
	         $.ajax({
	           url:"${pageContext.request.contextPath}/operation/getitem",
	           data:{nameop:nameop},
	           type:"POST",
	           success:function(data){
	                  /*  $("#npo").html("");
						document.getElementById("npo").style.display="none";
	                       $("#s").html("");
											  $("#s").append(data); */
											    $("#npo").html("");
										// document.getElementById("npo").style.display="none";
                                         
											  $("#npo").append(data);
	            
	           }
	         });
	         
	      }); 
  });
  function sunb(){
  
   var jie=[];
    $("#commencementnaturemap option:selected").each(function(){
	         jie.push(this.value);
     }); 
      var sss=[];
    $("#schoolMajorsa option:selected").each(function(){
	         sss.push(this.value);
     }); 
   if(jie.length ==0){
	   alert("请选择面向专业！");
	   return false;
   };
   if(sss.length == 0){
	   alert("请选择课程性质！");
	   return false;  
   }
  
  }
  
  function del(s){
  $("#"+s+"").remove();
   var d=   $("#projectitrms").val();
     var a= d.replace(s+",","");
       $("#projectitrms").attr("value","");
        $("#projectitrms").attr("value",a);
  }
</script>
<script type="text/javascript">
function uploaddocment(){
 			
 			 //获取当前屏幕的绝对位置
             var topPos = window.pageYOffset;
             //使得弹出框在屏幕顶端可见
             $('#searchFile').window({left:"0px", top:topPos+"px"});
			 $('#searchFile').window('open');
			 
			 $('#file_upload').uploadify({
				'formData':{id:1},    //传值
	            'swf': '${pageContext.request.contextPath}/swfupload/swfupload.swf',  
	            'uploader':'${pageContext.request.contextPath}/operation/uploaddnolinedocment;jsessionid=<%=session.getId()%>',		//提交的controller和要在火狐下使用必须要加的id
	            buttonText:'选择文件',
		       'onUploadSuccess' : function(file, data, response) {
        		  
					   $("#doc").append(data); 
    		 },
    		 onQueueComplete : function(data) {
    		   var ss="";
    		   
    		    $("tr[id*='s_']").each(function(){
		         var eval1=$(this).attr("id");
		          var str1= eval1.substr(eval1.indexOf("_")+1 ,eval1.lenght);  
		         var vals1=str1+"_"+$(this).attr("value");
		        
		         ss+=str1+",";
		         });
    		   
	            $("#docment").attr("value",ss); 
	             $('#searchFile').window('close');	
    		 }
	        });
			
		}
		
		function delectuploaddocment(s){
		if(confirm( '你真的要删除吗？ ')==false){return   false;}else{ 
		  $.post('${pageContext.request.contextPath}/operation/delectdnolinedocment?idkey='+s+'',function(data){  //serialize()序列化
				   if(data=="ok"){
				   $("#s_"+s+"").empty();
				   
				   }
				
			 });
			 }
		}
</script>
	</head>

	<body>
		<div id="bgheight">
			<script type="text/javascript">
				$(function() {
					$("#bgheight").height($(window).height() / 1);
				})
			</script>
			<div class="all_main_content">
				<div class="grey_line">
					<table>
						<tr>
							<td>
								<i class="fa fa-plus-square-o l lh33 mr10 f24"></i>
								<span>查看课程库</span>
							</td>
						</tr>
					</table>
					<button class="r mt7" type="button" onclick="returnToList()">返回</button>
				</div>
				
				<div class="bgw w100p ptb10">
					<div class="new_exp_block">
						<span class="new_tit">实验大纲基本信息</span>
						<table class="experimental_list_tab" cellspacing="0">
							<tr>
								<th>课程编号</th>
								<th>课程名称</th>
								<th>英文名称</th>
								<th>学分</th>
								<th>总学时</th>
								<th>实验学时</th>
								<th>课程类型</th>
								<th>课程性质</th>
								<th>适用专业</th>
								<th>开课学期</th>
								<th>开课院系</th>
								<th>课程负责人</th>
							</tr>
							<tr>
								<td class="tc"> ${schoolCourseInfo.courseNumber}</td>
								<td id="outlineName">${schoolCourseInfo.courseName}</td>
								<td>${schoolCourseInfo.courseEnName}</td>
								<td class="tc">${schoolCourseInfo.credits}</td>
								<td class="tc">${schoolCourseInfo.totalHours}</td>
								<td class="tc">${schoolCourseInfo.theoreticalHours}</td>
								<td class="tc"><c:if test="${schoolCourseInfo.courseType==1}">
								理论课
								</c:if>
								<c:if test="${schoolCourseInfo.courseType==2}">
								实验课
								</c:if>
								</td>
									<td class="tc"><c:if test="${schoolCourseInfo.courseNature==1}">
								通识通修课程
								</c:if>
								<c:if test="${schoolCourseInfo.courseType==2}">
								学科专业课程
								</c:if>
								<c:if test="${schoolCourseInfo.courseType==3}">
								开放选修课程
								</c:if>
								<c:if test="${schoolCourseInfo.courseType==4}">
								其他课程
								</c:if>
								</td>
								<td class="tc">
									<c:forEach items="${schoolCourseInfo.schoolMajor}" var="curr">
										${curr.majorName }
									</c:forEach>
								</td>
								<td class="tc">
									<c:forEach items="${schoolCourseInfo.schoolTerms}" var="curr">
										${curr.termName }
									</c:forEach>
								</td>
								<td class="tc">化学与材料学科学院
								</td>
								<td class="tc">
									<c:forEach items="${schoolCourseInfo.users}" var="curr">
										${curr.cname}
									</c:forEach>
								</td>
							</tr>
						</table>
						<div class="w100p ovh mt30">
							<div class="edit_block l">
								<div>课程的教学目标与任务</div>
								&nbsp; ${schoolCourseInfo.goal}
							</div>
							<div class="edit_block r">
								<div>考核方式</div>
								&nbsp; ${schoolCourseInfo.inspectionWay}
							</div>
							<div class="edit_block l">
								<div>课程基本内容</div>
								&nbsp;${schoolCourseInfo.content}
							</div>
							<div class="edit_block r">
								<div>推荐教材与参考资料</div>
								&nbsp;${schoolCourseInfo.resources}
							</div>
						</div>
						<div class="edit_bottom">
							<fieldset class="introduce-box">
						<input type="hidden" value="" id="xsd">
						<table>
							<tr >
								 <td>文档名称</td>
								 <input type="hidden" name="docment" id="docment" />
							 </tr>
							 <c:forEach items="${schoolCourseInfo.commonDocuments}" var="curr">
							 	<tr>
							 		<td>${curr.documentName }</td>
							 		<td><a href="${pageContext.request.contextPath}/timetable/downloadfile?idkey=${curr.id}">下载</a></td>
							 	</tr>
							 </c:forEach>
						</table>
						 <table>
							  <tbody id="doc"></tbody>
						 </table>	
					</fieldset>
						</div>
					</div>
					<div class="new_exp_block">
						<span class="new_tit">课程内容及基本要求</span>
						<table class="experimental_list_tab" cellspacing="0">
							<tr>
								<th>实验名称</th>
								<th>实验学时</th>
								<th>所属实验室</th>
								<th>基本要求</th>
							</tr>
							<c:forEach items="${items }" var="curr">
							<c:if test="${curr.CDictionaryByLpStatusCheck.id eq 545}">
								<tr id="showItem${curr.id }">
									<td>${curr.lpName}</td>
									<td class="tc">${curr.lpDepartmentHours}</td>
									<td class="tc">
										${curr.labRoom.labRoomName}
									</td>
									<td class="tc">
										${curr.lpPurposes}
									</td>
								</tr>
							</c:if>
							</c:forEach>
						</table>
					</div>

				</div>
				
			</div>
			<div id="searchFile" class="easyui-window" title="上传附件" closed="true" iconCls="icon-add" style="width:400px;height:200px">
	    <form id="form_file" method="post" enctype="multipart/form-data">
           <table  border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
            <td>
          	<div id="queue"></div>
		    <input id="file_upload" name="file_upload" type="file" multiple="true">
            </tr>   
            </table>
         </form>
     </div>		
		</div>
		<script type="text/javascript">
			$(".quickbtn").click(
				function() {
					$(".quick_above").addClass("block");
				}
			);
			$(document).bind("click", function() {
				$('.quick_above').removeClass("block");
			});
			$(".quickbtn").click(function(event) {
				event.stopPropagation();

			});
			$(".quick_above").click(function(event) {
				event.stopPropagation();

			});
			$(window).scroll(function() {
				$(".quick_above").removeClass("block");
			});
			
		
		</script>
<!-- 下拉框的js -->

					<script
						src="${pageContext.request.contextPath}/chosen/chosen.jquery.js"
						type="text/javascript"></script>

					<script
						src="${pageContext.request.contextPath}/chosen/docsupport/prism.js"
						type="text/javascript" charset="utf-8"></script>
							
					<script type="text/javascript">

    var config = {

      '.chzn-select'           : {},

      '.chzn-select-deselect'  : {allow_single_deselect:true},

      '.chzn-select-no-single' : {disable_search_threshold:10},

      '.chzn-select-no-results': {no_results_text:'选项, 没有发现!'},

      '.chzn-select-width'     : {width:"95%"}

    }

    for (var selector in config) {

      $(selector).chosen(config[selector]);

    }
	function returnToList(){
		
		window.location.href="${pageContext.request.contextPath}/timetable/listSchoolCourseInfo?currpage=1";
		
		
	}
</script>
	</body>

</html>