<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<jsp:directive.include file="/WEB-INF/sitemesh-decorators/include.jsp"/>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <meta name="decorator" content="iframe"/>
  
  <!-- 下拉框的样式 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/docsupport/prism.css" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/chosen/chosen.css" />
  <!-- 下拉的样式结束 -->
  <script src="${pageContext.request.contextPath}/js/Calendar.js" type="text/javascript"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
  </head>
  <body>
  	<div class="navigation">
    <div id="navigation">
	  <ul>
	    <li><a href="javascript:void(0)">导师互选</a></li>
		<li class="end"><a href="javascript:void(0)">备选导师列表</a></li>
	  </ul>
	</div>
   </div>
   <div class="right-content">
  <div id="TabbedPanels1" class="TabbedPanels">
  <div class="TabbedPanelsContentGroup">
  <div class="TabbedPanelsContent">
  <div class="content-box">
    <div class="title">
            新建
	</div>
	<form:form name="form" action="${pageContext.request.contextPath }/nwuChose/saveChoseThemeFour" method="post" modelAttribute="choseTheme" >
	 <div class="new-classroom">
	  <form:hidden path="id"/>
	  <fieldset>
	    <label>互选开始时间: </label>
	   	<input class="easyui-datebox" id="start" name="start" required="true" type="text" autocomplete="off" onclick="new Calendar().show(this);" style="width:100px;" />
	   </fieldset>
	   <fieldset>
	     <label>互选结束时间: </label>
	   	 <input id="end" name="end" type="text" required="true"  class="easyui-datebox" autocomplete="off"  onclick="new Calendar().show(this);" style="width:100px;" />
	   </fieldset>
	   <fieldset>
	     <label>批次数量：</label>
	  	 <form:input path="batchNumber"  type="text" id="batchNumber" class="easyui-validatebox" autocomplete="off" onchange="addBatch()"  onkeyup="this.value=this.value.replace(/[^1-5-]+/,'');"/>
	  </fieldset>
	  <div id="addBatch"></div>
	 </div> 
	<div class="moudle_footer">
        <div class="submit_link">
		  <input class="btn" id="save" type="button" value="下一步" onclick="check()"/>
		  <input class="btn btn-return" type="button" value="返回" onclick="window.history.go(-1)">
        </div>
	</div>
	</form:form>
	<!-- 下拉框的js -->
	<%-- <script src="${pageContext.request.contextPath}/chosen/chosen.jquery.js" type="text/javascript"></script> --%>
	<script src="${pageContext.request.contextPath}/chosen/docsupport/prism.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript">
	   function addBatch(){
	   	var number=$("#batchNumber").val();
	   	if(number > ${teacherNum}){
	   	    alert("志愿数量超过导师数量，请重设");
	   	    return false;
        }
	   	$.ajax({
	   		url:"${pageContext.request.contextPath}/nwuChose/addBatch",
	   		type:"post",
	   		data:{"batchNumber":number},
	   		success:function(data){
	   			$("#addBatch").html(data);
	   		}
	   	});
	   }
	   function check(){
	     if ($('.form').form('validate')){
	     	var start=$('#start').datebox('getValue');
	     	var end=$('#end').datebox('getValue');
	     	var stdt=new Date(start.replace("-","/"));
            var etdt=new Date(end.replace("-","/"));
            var arraySt=new Array();//存放每个志愿的开始时间
            var arrayEn=new Array();//存放每个志愿的结束时间
            var flag=true;
            var s=document.getElementsByName("startTime");
            var e=document.getElementsByName("endTime");
            for(var i=0;i<s.length;i++){
                if(s[i].value == ""){
                    alert("请填写志愿开始时间");
                    return false;
				}
              arraySt.push(s[i].value);
            }
            for(var i=0;i<e.length;i++){
                if(e[i].value == ""){
                    alert("请填写志愿结束时间");
                    return false;
                }
              arrayEn.push(e[i].value);
            }
            for(var i=0;i<arraySt.length;i++){
              var sta=new Date(arraySt[i]);
              var end=new Date(arrayEn[i]);
              if(i+1<arraySt.length){
              	//下一志愿的开始时间
              	var stas=new Date(arraySt[i+1]);
              	if(end>stas){
                flag=false;
				msg="上一批次间结束时间超过下一批的结束时间";
				break;
               }
              }
              if(end-sta<0){
              	flag=false;
				msg="开始时间超过结束时间";
				break;
              }
              
            }
	     	if(etdt-stdt<0){
	     		flag=false;
				msg="互选开始时间超过结束时间";
			}
			if(flag){
					document.form.submit();//进入新建大纲的下一步
			}
			else{
				alert(msg);
			}
	     }
	   }
		</script>
	<!-- 下拉框的js -->
  </div>
  </div>
  </div>
  </div>
  </div>
</body>
</html>